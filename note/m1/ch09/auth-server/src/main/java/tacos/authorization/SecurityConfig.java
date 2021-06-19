package tacos.authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.authorization.users.TacoUserRepository;

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
	        throws Exception {
		return http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests.anyRequest().authenticated()
			)

			.formLogin()

			.and().build();
	}

	@Bean
	UserDetailsService userDetailsService(TacoUserRepository userRepo) {
	  return username -> userRepo.findByUsername(username);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	  return NoOpPasswordEncoder.getInstance();
	}
}
