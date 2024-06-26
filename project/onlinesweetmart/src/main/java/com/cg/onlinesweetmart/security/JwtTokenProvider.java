package com.cg.onlinesweetmart.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private String jwtSecret = "daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";
	
	private long jwtExpirationDate = 604800000;
	
	public String generateToken(Authentication authentication) {
		
		String username = authentication.getName();
		
		Date currentDate = new Date();
		
		Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder()
							.setSubject(username)
							.setIssuedAt(new Date())
							.setExpiration(expirationDate)
							.signWith(key())
							.compact();
		
		return token;
		
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getUsername(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(key())
							.build()
							.parseClaimsJws(token)
							.getBody();
			
		String username = claims.getSubject();
		
		return username;
	}
	
	public boolean validateToken(String token) {
		Jwts.parser()
			.setSigningKey(key())
			.build()
			.parse(token);
		
		return true;
	}
	
}
