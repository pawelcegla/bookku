package bookku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Comparator;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
@EnableWebSecurity
public class Bookku {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(Bookku.class)
				.listeners((ApplicationPreparedEvent ape) -> applicationPrepared(ape))
				.run(args);
	}

	private static void applicationPrepared(ApplicationPreparedEvent event) {
		LoggerFactory.getLogger("bookku.base-url").info(event.getApplicationContext().getEnvironment().getProperty("bookku.base-url"));
		LoggerFactory.getLogger("bookku.git.revision").info(event.getApplicationContext().getEnvironment().getProperty("git.commit.id", "N/A"));
		System.getProperties().entrySet().stream()
				.filter(p -> p.getKey().toString().startsWith("java."))
				.sorted(Comparator.comparing(p -> p.getKey().toString()))
				.forEach(p -> LoggerFactory.getLogger("bookku."+ p.getKey()).info(String.valueOf(p.getValue())));
	}

	@Bean
	@SuppressWarnings("unused")
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth.requestMatchers("/__").authenticated()).formLogin(withDefaults())
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
	}
}
