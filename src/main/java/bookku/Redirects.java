package bookku;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.LOCATION;

@RestController
public class Redirects {

    private static final Logger log = LoggerFactory.getLogger(Redirects.class);

    private final Map<String, String> bookmarks;
    private final Resource resource;

    public Redirects(@Value("${bookmarks}") Resource resource) {
        this.bookmarks = new LinkedHashMap<>();
        this.resource = resource;
    }

    @PostConstruct
    private void loadBookmarksFromResource() throws IOException {
        log.info("Loading bookmarks from '{}'", resource.getURI());
        var contents = resource.getContentAsString(UTF_8);
        Arrays.stream(contents.split("[\r\n]+")).map(line -> line.split("\\|")).forEach(ss -> bookmarks.put(ss[0], ss[1]));
        if (Map.of("ex", "https://example.org").equals(bookmarks)) {
            log.warn("Loaded default bookmarks with only 1 entry");
        } else {
            log.info("Loaded {} bookmarks", bookmarks.size());
        }
    }

    @GetMapping("/")
    public ResponseEntity<Void> query(@RequestParam(value = "q", required = false) String query) {
        log.info("Querying bookmarks for '{}'", query);
        if (bookmarks.containsKey(query)) {
            log.info("Found, redirecting to '{}'", bookmarks.get(query));
            return ResponseEntity.status(307).header(LOCATION, bookmarks.get(query)).build();
        } else {
            log.info("Not found");
            return ResponseEntity.notFound().build();
        }
    }
}
