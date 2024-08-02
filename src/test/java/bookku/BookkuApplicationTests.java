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

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "bookmarks=classpath:test-bookmarks.txt")
class BookkuApplicationTests {

	@Test
	void redirectsShouldWork(@Autowired TestRestTemplate rest) {
		var foo = rest.getForEntity("/?q=foo", Void.class);
		assertEquals(TEMPORARY_REDIRECT, foo.getStatusCode());
		assertEquals("test://flight.of.opportunity", foo.getHeaders().getFirst(LOCATION));
		var bar = rest.getForEntity("/?q=bar", Void.class);
		assertEquals(TEMPORARY_REDIRECT, bar.getStatusCode());
		assertEquals("test://brain.access.router", bar.getHeaders().getFirst(LOCATION));
	}

	@Test
	void httpNotFoundShouldBeReturnedForNonExistentBookmark(@Autowired TestRestTemplate rest) {
		var qwerty = rest.getForEntity("/?q=" + randomUUID(), Void.class);
		assertEquals(NOT_FOUND, qwerty.getStatusCode());
	}
}
