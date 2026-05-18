package com.spring.demo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.spring.demo.Filters.JwtFilter;
import com.spring.demo.Service.UserDetailServiceImpl;

import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(token -> token.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/public/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/api/user/**").hasRole("USER")
						.requestMatchers("/utility/**").hasRole("DEVELOPER").anyRequest().authenticated())
				.httpBasic(httpBasic -> {
				}).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

				.build();
	}

	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
