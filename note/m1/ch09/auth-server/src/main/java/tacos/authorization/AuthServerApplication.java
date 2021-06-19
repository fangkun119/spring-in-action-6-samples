package tacos.authorization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.authorization.users.TacoUser;
import tacos.authorization.users.TacoUserRepository;

@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
	
	@Bean
	public ApplicationRunner dataLoader(TacoUserRepository repo, PasswordEncoder encoder) {
	  return args -> {
	    repo.save(
	        new TacoUser("habuma", encoder.encode("password"), "ROLE_ADMIN"));
      repo.save(
          new TacoUser("tacochef", encoder.encode("password"), "ROLE_ADMIN"));
	  };
	}

}
