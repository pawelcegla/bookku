package bookku;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class Bookmarks {

    private final NamedParameterJdbcTemplate jdbc;

    public Bookmarks(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    Optional<Target> findBySlug(Slug slug) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT target FROM bookmark WHERE slug = :slug;",
                            Map.of("slug", slug.value()),
                            Target.class
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    void create(Slug slug, Target target) {
        jdbc.update(
                "INSERT INTO bookmark (slug, target) VALUES (:slug, :target);",
                Map.of("slug", slug.value(), "target", target.value())
        );
    }
}
