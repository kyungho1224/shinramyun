package com.dongyw.shinramyun.jwt;

import com.dongyw.shinramyun.exception.ApiErrorCode;
import com.dongyw.shinramyun.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

	private final Key key;
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;

	public JwtProvider(
	  @Value("${spring.jwt.secret-key}") String secretKey
	) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String generatedToken(String email) {
		long now = (new Date()).getTime();
		Date accessTokenExpiredTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		return Jwts.builder()
		  .setSubject(email)
		  .claim("email", email)
		  .setExpiration(accessTokenExpiredTime)
		  .signWith(key, SignatureAlgorithm.HS256)
		  .compact();
	}

	public String validateToken(String accessToken) {
		try {
			JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
			Claims claims = parser.parseClaimsJws(accessToken).getBody();
			return String.valueOf(claims.get("email"));
		} catch (Exception e) {
			throw new ApiException(ApiErrorCode.TOKEN_ERROR);
		}
	}

}
