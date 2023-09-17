package com.jvel.edify.config;

import com.jvel.edify.controller.exceptions.UserNotFoundException;
import com.jvel.edify.entity.User;
import com.jvel.edify.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${edify-jwt.SECRET_KEY}")
    private String SECRET_KEY;
    private String ISSUER = "Edify";
    private String AUDIENCE = "Edify";
    @Autowired
    private UserRepository userRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractAudience(String token) {
        return extractClaim(token, Claims::getAudience);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractIssuer(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    public Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public String extractId(String token) {
        return extractClaim(token, Claims::getId);
    }

    public Integer extractUserId(String token) { return (Integer) extractAllClaims(token).get("id"); }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 240))) // Expires in 240 minutes
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        final String audience = this.extractAudience(token);
        final String issuer = this.extractIssuer(token);
        final Date expiration = this.extractExpiration(token);

        if ((username == null) || !username.equals(userDetails.getUsername())) {
            System.out.println("JWT invalid: username");
            return false;
        }

        if ((audience == null) || !audience.equals(AUDIENCE)) {
            System.out.println("JWT invalid: audience");
            return false;
        }

        if ((issuer == null) || !issuer.equals(ISSUER)) {
            System.out.println("JWT invalid: issuer");
            return false;
        }

        if ((expiration == null) || expiration.before(new Date())) {
            System.out.println("JWT invalid: expiration date");
            return false;
        }

        return true;
    }

    public String resolveTokenUsername(String bearerToken) {
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer "))
            throw new IllegalStateException("Bearer token not specified");

        String jwt = bearerToken.substring(7);
        String username = extractUsername(jwt);
        return username;
    }

    public Integer resolveToken(String bearerToken) {
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer "))
            throw new IllegalStateException("Bearer token not specified");

        String jwt = bearerToken.substring(7);
        Integer userId = extractUserId(jwt);
        return userId;
    }
}
