package com.neo.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityConfig {
	
	@Bean
	List<RequestMatcher> rotasPublicas() {
	    var paths = PathPatternRequestMatcher.withDefaults();

	    return List.of(
	        paths.matcher(HttpMethod.POST, "/login/**"),
	        paths.matcher("/h2-console/**"),
	        paths.matcher(HttpMethod.POST, "/usuarios/**"),
	        paths.matcher(HttpMethod.GET, "/swagger-ui/**"),
	        paths.matcher(HttpMethod.GET, "/v3/**")
	    );
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
