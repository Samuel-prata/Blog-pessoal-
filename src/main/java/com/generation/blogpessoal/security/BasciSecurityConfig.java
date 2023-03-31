package com.generation.blogpessoal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasciSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfig)
			throws Exception {
		return authenticationConfig.getAuthenticationManager();
	}

    @Bean
    SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable().cors();

        http.authorizeHttpRequests(
                (auth) -> auth.requestMatchers("/usuarios/cadastrar").permitAll().requestMatchers("/usuarios/logar")
                        .permitAll().requestMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated())
                .httpBasic();

        return http.build();
    }

}
