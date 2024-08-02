package bookku;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Repository
public class Bookmarks {

    private final static Logger log = LoggerFactory.getLogger(Bookmarks.class);

    private final Map<String, String> bookmarks;
    private final Resource resource;

    public Bookmarks(@Value("${bookmarks}") Resource resource) {
        this.resource = resource;
        this.bookmarks = new LinkedHashMap<>();
    }

    @PostConstruct
    void loadBookmarksFromResource() throws IOException {
        log.info("Loading bookmarks from '{}'", resource.getURI());
        var contents = resource.getContentAsString(UTF_8);
        Arrays.stream(contents.split("[\r\n]+")).map(line -> line.split("\\|")).forEach(ss -> bookmarks.put(ss[0], ss[1]));
        if (Map.of("ex", "https://example.org").equals(bookmarks)) {
            log.warn("Loaded default bookmarks with only 1 entry");
        } else {
            log.info("Loaded {} bookmarks", bookmarks.size());
        }
    }

    public Optional<String> findByKey(String key) {
        return Optional.ofNullable(bookmarks.get(key));
    }
}
