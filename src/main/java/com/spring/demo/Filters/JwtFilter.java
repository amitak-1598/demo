package com.spring.demo.Filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.demo.Service.UserDetailServiceImpl;
import com.spring.demo.Utility.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// takes the token from the header
		String fetchToken = request.getHeader("Authorization");
		// check validity
		String username = null;
		String token = null;
		if (fetchToken != null && fetchToken.startsWith("Bearer ")) {
			// First Validation Passed
			// set token
			token = fetchToken.substring(7);
			// get the user using token
			username = jwtUtil.extractUsername(token);
			// fetch from db
			System.out.println("Extracted username "+username);
			UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
			// final validation
			if (jwtUtil.validateToken(userDetails.getUsername(), token)) {
				// validation passed
//				do auth
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder context = new SecurityContextHolder();
				context.getContext().setAuthentication(authenticationToken);

			}

		}
		doFilter(request, response, filterChain);

	}

}
