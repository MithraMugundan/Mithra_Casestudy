package com.hexaware.casestudy.carrs.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.casestudy.carrs.entity.UserInfo;
import com.hexaware.casestudy.carrs.repository.UserInfoRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	public static final String SECRET = "j0lYz3pWhH7DQo2x2X/kCmMoW1iB0W9xDlLSP6Q1vQo=";

	
	
	public String createToken(Map<String, Object> claims, String username) {

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

	}

	private Key getSignKey() {

		byte[] keyBytes = Decoders.BASE64.decode(SECRET);

		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String username) {
		
		 UserInfo user = userInfoRepository.findByUserName(username);
         if(user==null)
         {
        	 throw new UsernameNotFoundException("User not found");
         }

		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", user.getUserId());
		claims.put("role", user.getRoles());

		return createToken(claims, username);

	}
	
	// BELOW METHODS HELP for reading TOKEN FROM CLIENT AND GET Claims , username, exp time etc from token
	
	
			public Claims extractAllClaims(String token) {

				return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

			}

			public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

				final Claims claims = extractAllClaims(token);
				

				return claimsResolver.apply(claims);

			}

			 public String extractUsername(String token) {
			        return extractClaim(token, Claims::getSubject);
			    }

			    public Date extractExpiration(String token) {
			        return extractClaim(token, Claims::getExpiration);
			    }

			
			    private Boolean isTokenExpired(String token) {
			        return extractExpiration(token).before(new Date());
			    }

			    public Boolean validateToken(String token, UserDetails userDetails) {
			        final String username = extractUsername(token);
			    	return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
			    } 
			


}
