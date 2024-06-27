package bookku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Users implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(Users.class);

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Users(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: '{}'", username);
        if ("pafciu".equals(username)) {
            return User.withUsername("user").password(passwordEncoder.encode("password")).roles("USER").build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
