package zamszyk;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Service
public class Owners implements UserDetailsService {

    private final NamedParameterJdbcTemplate jdbc;

    public Owners(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static UserDetails mapRow(ResultSet rs, int n) throws SQLException {
        return User.withUsername(rs.getString("name")).password(rs.getString("hashed_password")).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return jdbc.queryForObject(
                    "SELECT name, hashed_password FROM owner WHERE name = :name;",
                    Map.of("name", username),
                    Owners::mapRow
            );
        } catch (DataAccessException e) {
            throw new UsernameNotFoundException("username not found", e);
        }
    }
}
