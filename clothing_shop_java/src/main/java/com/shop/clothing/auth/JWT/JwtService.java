package com.shop.clothing.auth.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SignatureException;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public String generateToken(Map<String, Object> claims, long expirationInMilliSeconds) {
        String secretKeyString = jwtConfig.getSecret();
        Key secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new java.util.Date(System.currentTimeMillis()))
                .expiration(new java.util.Date(System.currentTimeMillis() + expirationInMilliSeconds))
                .signWith(secretKey)
                .issuer("shop-clothing")
                .compact();
    }

    private Claims getAllClaims(String token) throws ExpiredJwtException, SignatureException{
        String secretKeyString = jwtConfig.getSecret();
        Key secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public <T> T getValue(String token, Function<Claims, T> claimsResolver) throws SignatureException {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) throws SignatureException {
        return getValue(token, Claims::getExpiration).before(new java.util.Date());
    }
}
