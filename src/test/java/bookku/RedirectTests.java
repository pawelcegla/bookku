package bookku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.client.TestRestTemplate;

import java.util.stream.Stream;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TEMPORARY_REDIRECT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.datasource.url=jdbc:sqlite::memory:", "spring.http.client.redirects=dont-follow"})
class RedirectTests {

	@Autowired TestRestTemplate rest;

	@ParameterizedTest
	@MethodSource
	void redirectsShouldWork(String slug, String target) {
		var response = rest.getForEntity(slug, Void.class);
		assertEquals(TEMPORARY_REDIRECT, response.getStatusCode());
		assertEquals(target, response.getHeaders().getFirst(LOCATION));
	}

	static Stream<Arguments> redirectsShouldWork() {
		return Stream.of(
				arguments("/b/foo", "test://flight.of.opportunity"),
				arguments("/b/bar", "test://brain.access.router"),
				arguments("/b/123", "test://one.two.three"),
				arguments("/b/857620a5-79ed-4988-8439-382b912ef943", "test://undefined.unsafe.initial.design")
		);
	}

	@Test
	void httpNotFoundShouldBeReturnedForNonExistentBookmark() {
		var nonExistentBookmarkResponse = rest.getForEntity("/b/" + randomUUID(), Void.class);
		assertEquals(NOT_FOUND, nonExistentBookmarkResponse.getStatusCode());
	}

	@Test
	void httpNotFoundShouldBeReturnedForMultipleSeparatorsInQuery() {
		var multipleSeparatorsQueryResponse = rest.getForEntity("/b/d-_-b", Void.class);
		assertEquals(NOT_FOUND, multipleSeparatorsQueryResponse.getStatusCode());
	}

	@Test
	void loginLocationShouldBeReturnedForSecuredEndpoint() {
		var securedEndpointResponse = rest.getForEntity("/__", String.class);
		assertTrue(securedEndpointResponse.getStatusCode().is3xxRedirection());
		assertTrue(securedEndpointResponse.getHeaders().containsHeader(LOCATION));
		assertTrue(securedEndpointResponse.getHeaders().getFirst(LOCATION).endsWith("/login"));
	}
}
