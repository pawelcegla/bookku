package bookku;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Repository
public class Bookmarks {

    private final static Logger log = LoggerFactory.getLogger(Bookmarks.class);

    private final Map<String, String> bookmarks;
    private final Resource resource;

    @Autowired
    public Bookmarks(@Value("${bookmarks}") Resource resource) {
        this.resource = resource;
        this.bookmarks = new LinkedHashMap<>();
    }

    @PostConstruct
    void loadBookmarksFromResource() throws IOException {
        log.info("Loading bookmarks from '{}'", resource.getURI());
        try (var lines = Files.lines(resource.getFile().toPath(), UTF_8)) {
            lines.map(line -> line.split("\\|")).forEach(ss -> bookmarks.put(ss[0], ss[1]));
        }
    }

    public Optional<String> findByKey(String key) {
        return Optional.ofNullable(bookmarks.get(key));
    }
}
