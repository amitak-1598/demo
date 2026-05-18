package com.spring.demo.Utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private String SECRET_KEY = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";

	public SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
//		claims.put("name", "Amit Kumar");/
		System.out.println("username to set " + username);
		return getToken(username, claims);

	}

	public String getToken(String subject, Map<String, Object> claims) {
		Map<String, Object> header = new HashMap<>();
		header.put("typ", "jwt");
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)).setHeader(header)
				.signWith(getSigningKey()).compact();

	}

	
	               // validate Token
	public boolean validateToken(String username, String token) {
		if (extractUsername(token).equals(username) && !isTokenExpired(token)) {
			return true;
		}
		return false;

	}

	public boolean isTokenExpired(String token) {
		Date expirationTime = extractClaims(token).getExpiration();
		Date currentTime = new Date(System.currentTimeMillis());
		if (expirationTime.before(currentTime)) {
			return true;
		}
		return false;

	}
	
	
	public String extractUsername(String token) {
		Claims claims = extractClaims(token);
		System.out.println(claims);
		return claims.getSubject();
	}

	public Claims extractClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

	}

}
