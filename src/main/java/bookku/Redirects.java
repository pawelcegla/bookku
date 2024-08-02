package bookku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/")
    public ResponseEntity<?> query(@RequestParam(value = "q", required = false) String query) {
        log.info("Querying bookmarks for '{}'", query);
        return bookmarks.findByKey(query).map(target ->  {
            log.info("Found, redirecting to '{}'", target);
            return ResponseEntity.status(TEMPORARY_REDIRECT).header(LOCATION, target).build();
        }).orElseGet(() -> {
            log.info("Not found");
            return ResponseEntity.notFound().build();
        });
    }
}
