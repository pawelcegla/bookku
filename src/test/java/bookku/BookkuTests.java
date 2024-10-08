package bookku;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TEMPORARY_REDIRECT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:testdb", "spring.http.client.redirects=dont-follow"})
class BookkuTests {

	@Test
	void redirectsShouldWork(@Autowired TestRestTemplate rest) {
		var foo = rest.getForEntity("/foo", Void.class);
		assertEquals(TEMPORARY_REDIRECT, foo.getStatusCode());
		assertEquals("test://flight.of.opportunity", foo.getHeaders().getFirst(LOCATION));
		var bar = rest.getForEntity("/bar", Void.class);
		assertEquals(TEMPORARY_REDIRECT, bar.getStatusCode());
		assertEquals("test://brain.access.router", bar.getHeaders().getFirst(LOCATION));
	}

	@Test
	void httpNotFoundShouldBeReturnedForNonExistentBookmark(@Autowired TestRestTemplate rest) {
		var nonExistentBookmarkResponse = rest.getForEntity("/" + randomUUID(), Void.class);
		assertEquals(NOT_FOUND, nonExistentBookmarkResponse.getStatusCode());
	}

	@Test
	void httpNotFoundShouldBeReturnedForNumericQuery(@Autowired TestRestTemplate rest) {
		var numericQueryResponse = rest.getForEntity("/123", Void.class);
		assertEquals(NOT_FOUND, numericQueryResponse.getStatusCode());
	}

	@Test
	void httpNotFoundShouldBeReturnedForMultipleSeparatorsInQuery(@Autowired TestRestTemplate rest) {
		var multipleSeparatorsQueryResponse = rest.getForEntity("/d-_-b", Void.class);
		assertEquals(NOT_FOUND, multipleSeparatorsQueryResponse.getStatusCode());
	}

	@Test
	void loginLocationShouldBeReturnedForSecuredEndpoint(@Autowired TestRestTemplate rest) {
		var securedEndpointResponse = rest.getForEntity("/__/b", String.class);
		assertTrue(securedEndpointResponse.getStatusCode().is3xxRedirection());
		assertTrue(securedEndpointResponse.getHeaders().containsKey(LOCATION));
		assertTrue(securedEndpointResponse.getHeaders().getFirst(LOCATION).endsWith("/login"));
	}
}
