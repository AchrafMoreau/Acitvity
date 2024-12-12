package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.experationTime}")
    private Long experationTime;

    @Autowired
    private MyUserDetails myUserDetails;


    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<UserRole> userRoles)
    {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", userRoles.stream()
                .map(s -> new SimpleGrantedAuthority(s.getAuthority())
                ).filter(Objects::nonNull).collect(Collectors.toList())
        );

        Date now = new Date();
        Date expiration = new Date(now.getTime() + experationTime);

        String token =  Jwts.builder()
                        .setExpiration(expiration)
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .signWith(SignatureAlgorithm.HS256, secretKey)//
                        .compact();
        return token;
    }

    public Authentication getAuthentication(String token)
    {
        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token)
    {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req)
    {
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken(String token)
    {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}
