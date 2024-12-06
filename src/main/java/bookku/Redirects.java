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
    public ResponseEntity<?> query(@PathVariable String slug) {
        log.info("Querying bookmarks for '{}'", slug);
        return bookmarks.findBySlug(slug).map(b ->  {
            log.info("Found, redirecting to '{}'", b.target());
            return ResponseEntity.status(TEMPORARY_REDIRECT).header(LOCATION, b.target()).build();
        }).orElseGet(() -> {
            log.info("Not found");
            return ResponseEntity.notFound().build();
        });
    }
}
