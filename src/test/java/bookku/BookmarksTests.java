package bookku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BookmarksTests {

    private Bookmarks bookmarks;

    @BeforeEach
    void setUp() throws IOException {
        bookmarks = new Bookmarks(new ClassPathResource("test-bookmarks.txt"));
        bookmarks.loadBookmarksFromResource();
    }

    static Stream<Arguments> bookmarks() {
        return Stream.of(arguments("foo", "https://foo"), arguments("bar", "https://bar"));
    }

    @ParameterizedTest
    @MethodSource("bookmarks")
    void asdf(String query, String expectedTarget) {
        var b = bookmarks.findByKey(query);
        assertTrue(b.isPresent());
        assertEquals(expectedTarget, b.get());
    }

    @Test
    void werwer() {
        var miss = bookmarks.findByKey("wat");
        assertFalse(miss.isPresent());
    }
}
