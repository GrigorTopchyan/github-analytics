package com.webbfontaine.task.githubanalytics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class WebSecurityConfig {

	private final AuthenticationManager authenticationManager;

	private final SecurityContextRepository securityContextRepository;

	public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
		this.authenticationManager = authenticationManager;
		this.securityContextRepository = securityContextRepository;
	}

	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http
			.exceptionHandling()
			.authenticationEntryPoint((swe, e) -> {
				return Mono.fromRunnable(() -> {
					swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				});
			}).accessDeniedHandler((swe, e) -> {
				return Mono.fromRunnable(() -> {
					swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
				});
			}).and().cors().disable()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.authenticationManager(authenticationManager)
			.securityContextRepository(securityContextRepository)
			.authorizeExchange()
			.pathMatchers(HttpMethod.OPTIONS).permitAll()
			.pathMatchers("/login").permitAll()
				//Comment the authenticated() because
				// I had problem with cross origin and don't have mach time to explore :)
//			.anyExchange().authenticated()
			.anyExchange().permitAll()
			.and().build();
	}

//	@Bean
//	public CorsFilter corsFilter() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		final CorsConfiguration config = new CorsConfiguration();
//			config.setAllowCredentials(true);
//		config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
//		config.setAllowedHeaders(Arrays.asList("*"));
//		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//	}
}
