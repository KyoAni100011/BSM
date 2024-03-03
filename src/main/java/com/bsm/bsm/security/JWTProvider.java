package com.bsm.bsm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JWTProvider {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateJwtToken(String userId) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();

        return token;
    }

    public static String decodeJwtToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

        Claims body = claimsJws.getBody();
        return body.getSubject();
    }
}
