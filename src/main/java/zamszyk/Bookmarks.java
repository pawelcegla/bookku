package zamszyk;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Repository
public class Bookmarks {

    private final NamedParameterJdbcTemplate jdbc;

    public Bookmarks(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    Optional<Target> findBySlug(Slug slug, Principal principal) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT target FROM bookmark WHERE slug = :slug AND restricted < :restricted;",
                            Map.of(
                                    "slug", slug.value(),
                                    "restricted", principal == null ? 1 : 2
                            ),
                            Target.class
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    void create(Slug slug, Target target, boolean restricted) {
        jdbc.update(
                "INSERT INTO bookmark (slug, target, restricted) VALUES (:slug, :target, :restricted);",
                Map.of("slug", slug.value(), "target", target.value(), "restricted", restricted ? 1 : 0)
        );
    }
}
