package bookku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.TEMPORARY_REDIRECT;

@RestController
public class Redirects {

    private static final Logger log = LoggerFactory.getLogger(Redirects.class);

    private final Bookmarks bookmarks;

    public Redirects(Bookmarks bookmarks) {
        this.bookmarks = bookmarks;
    }

    @GetMapping("/{slug:[a-z][a-z0-9]*(?:[\\-_][a-z][a-z0-9]*)*}")
    public ResponseEntity<?> query(@PathVariable Slug slug) {
        log.info("Querying bookmarks for '{}'", slug.value());
        return bookmarks.findBySlug(slug).map(t ->  {
            log.info("Found, redirecting to '{}'", t.value());
            return ResponseEntity.status(TEMPORARY_REDIRECT).header(LOCATION, t.value()).build();
        }).orElseGet(() -> {
            log.info("Not found");
            return ResponseEntity.notFound().build();
        });
    }
}
