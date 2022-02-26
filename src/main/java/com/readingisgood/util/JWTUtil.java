package com.readingisgood.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {

	private static final String SECRET_KEY = "getirbimutluluk";
	private static final long ADDITIONAL_TIME = 1000 * 60 * 15;	// 15min

	/**
	 * Creates token with given claims and subject
	 * 
	 * @param claims
	 * @param subject
	 * @return token
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + ADDITIONAL_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	/**
	 * Returns all extracted claims from given token
	 * 
	 * @param token
	 * @return Claims
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Returns extracted claim by function from given token
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return claim value
	 */
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	/**
	 * Checks token is expired or not
	 * 
	 * @param token
	 * @return boolean value
	 */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Generates token with no claims and username
	 * 
	 * @param userDetails
	 * @return token
	 */
	public String generateToken(UserDetails userDetails) {
		return createToken(new HashMap<>(), userDetails.getUsername());
	}
	
	/**
	 * Returns username from token
	 * 
	 * @param token
	 * @return username
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	/**
	 * Returns expiration date from token
	 * 
	 * @param token
	 * @return expiration date
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	/**
	 * Checks token is valid or not with username and expiration date
	 * 
	 * @param token
	 * @param userDetails
	 * @return boolean value
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
}
