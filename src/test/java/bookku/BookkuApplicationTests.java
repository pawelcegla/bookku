package bookku;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TEMPORARY_REDIRECT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "bookmarks=classpath:test-bookmarks.txt")
class BookkuApplicationTests {

	@Test
	void redirectsShouldWork(@Autowired TestRestTemplate rest) {
		var foo = rest.getForEntity("/?q=foo", Void.class);
		assertEquals(TEMPORARY_REDIRECT, foo.getStatusCode());
		assertEquals("https://foo", foo.getHeaders().getFirst("Location"));
		var bar = rest.getForEntity("/?q=bar", Void.class);
		assertEquals(TEMPORARY_REDIRECT, bar.getStatusCode());
		assertEquals("https://bar", bar.getHeaders().getFirst("Location"));
	}

	@Test
	void httpNotFoundShouldBeReturnedForNonExistentBookmark(@Autowired TestRestTemplate rest) {
		var qwerty = rest.getForEntity("/?q=qwerty", Void.class);
		assertEquals(NOT_FOUND, qwerty.getStatusCode());
	}
}
