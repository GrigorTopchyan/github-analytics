package com.webbfontaine.task.githubanalytics;

import com.webbfontaine.task.githubanalytics.model.Role;
import com.webbfontaine.task.githubanalytics.model.User;
import com.webbfontaine.task.githubanalytics.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableWebFluxSecurity
@Log4j2
public class GithubAnalyticsApplication{

	public static void main(String[] args) {
		SpringApplication.run(GithubAnalyticsApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService, PasswordEncoder passwordEncoder){
		return args -> {
			User user = User.builder()
					.email("admin@gmail.com")
					.username("admin")
					.password(passwordEncoder.encode("qwerty12345"))
					.roles(Arrays.asList(Role.ROLE_ADMIN)).build();
			log.info("Deleting previous users");
			userService.deleteAll().block();
			userService.save(user).block();
			log.info(userService.findByUsername(user.getUsername()).block());
			log.info("Save new user with username: {}",user.getUsername());

		};
	}

//	@Bean
//	public CorsWebFilter corsFilter() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		final CorsConfiguration config = new CorsConfiguration();
//			config.setAllowCredentials(true);
//		config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
//		config.setAllowedHeaders(Arrays.asList("*"));
//		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
//		source.registerCorsConfiguration("/**", config);
//		return new CorsWebFilter(source);
//	}
}
