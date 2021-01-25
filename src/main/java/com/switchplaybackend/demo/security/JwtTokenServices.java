package com.switchplaybackend.demo.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class JwtTokenServices {

    @Value("${security.jwt.token.secret-key:secret}")
    private SecretKey key;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 36000000; // 10h

    private final String rolesFieldName = "roles";

    @PostConstruct
    protected void init(){
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(String email, List<String> roles){
        //add custom field to the token
        Claims claims = Jwts.claims().setSubject(email);
        claims.put(rolesFieldName, roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    boolean validateToken (String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch (JwtException | IllegalArgumentException e){
            log.debug("JWT token invalid " + e);
        }
        return false;
    }

    Authentication parseUserFromTokenInfo(String token) throws UsernameNotFoundException{
        Claims body = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        String email = body.getSubject();
        List<String> roles = (List<String>) body.get(rolesFieldName);
        List<SimpleGrantedAuthority> authorities = new LinkedList<>();

        for(String role : roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new UsernamePasswordAuthenticationToken(email, "", authorities);
    }

}
