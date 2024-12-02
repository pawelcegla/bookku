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

    Optional<Bookmark> findBySlug(String slug) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT slug, target FROM bookmark WHERE slug = :slug;",
                            Map.of("slug", slug),
                            (rs, n) -> new Bookmark(rs.getString("slug"), rs.getString("target"))
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
