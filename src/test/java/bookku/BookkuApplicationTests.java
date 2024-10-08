package bookku;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TEMPORARY_REDIRECT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.datasource.url=jdbc:h2:mem:testdb")
class BookkuApplicationTests {

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
		var qwerty = rest.getForEntity("/" + randomUUID(), Void.class);
		assertEquals(NOT_FOUND, qwerty.getStatusCode());
	}

	@Test
	void httpNotFoundShouldBeReturnedForNumericQuery(@Autowired TestRestTemplate rest) {
		var qwerty = rest.getForEntity("/123", Void.class);
		assertEquals(NOT_FOUND, qwerty.getStatusCode());
	}

	@Test
	void httpNotFoundShouldBeReturnedForMultipleSeparatorsInQuery(@Autowired TestRestTemplate rest) {
		var qwerty = rest.getForEntity("/d-_-b", Void.class);
		assertEquals(NOT_FOUND, qwerty.getStatusCode());
	}
}
