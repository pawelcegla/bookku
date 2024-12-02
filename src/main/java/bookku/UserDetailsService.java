package bookku;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Owners owners;

    public UserDetailsService(Owners owners) {
        this.owners = owners;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return owners
                .findByName(username)
                .map(o -> User.withUsername(o.name()).password(o.hashedPassword()).build())
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
