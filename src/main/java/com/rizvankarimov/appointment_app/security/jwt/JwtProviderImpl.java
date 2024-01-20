package com.rizvankarimov.appointment_app.security.jwt;

import com.rizvankarimov.appointment_app.security.UserPrincipal;
import com.rizvankarimov.appointment_app.utils.SecurityUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long expirationInMs;

    @Override
    public String generateToken(UserPrincipal auth) {
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationInMs);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(auth.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .claim("roles", roles)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return null;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if (token == null) {
            return null;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", ArrayList.class);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SecurityUtils::convertToAuthority)
                    .collect(Collectors.toList());

            Set<GrantedAuthority> authoritiesSet = new HashSet<>(authorities);

            UserDetails userDetails = UserPrincipal.builder()
                    .username(username)
                    .authorities(authoritiesSet)
                    .build();

            return new UsernamePasswordAuthenticationToken(userDetails, null, authoritiesSet);
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return null;
        }
    }



    @Override
    public boolean isTokenValid(HttpServletRequest request) {
        Claims claims = extractClaims(request);

        return claims != null && claims.getExpiration().after(new Date());
    }

    private Claims extractClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        if (token == null) {
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
