package bookku;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class Owners {

    private final NamedParameterJdbcTemplate jdbc;

    public Owners(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    Optional<Owner> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbc.queryForObject(
                            "SELECT name, hashed_password FROM owner WHERE name = :name;",
                            Map.of("name", name),
                            (rs, n) -> new Owner(rs.getString("name"), rs.getString("hashed_password"))
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
