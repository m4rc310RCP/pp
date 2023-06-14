package foundation.cmo.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MSecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET, "/gui").permitAll()
			.antMatchers(HttpMethod.POST, "/graphql").permitAll()
			.antMatchers(HttpMethod.GET, "/graphql").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable();
		return http.build();
	}
}
