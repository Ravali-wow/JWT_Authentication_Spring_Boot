package com.bootai.boot.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    
    
    public String generateToken(String username)
    {
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,username);
    }

    public String createToken(Map<String,Object> claims,String usernmae)
    {
        String token= Jwts.builder()
               .setClaims(claims)
               .setSubject(usernmae)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
               .signWith(getSignKey(),SignatureAlgorithm.HS256)
               .compact();
        System.out.println("In JwtService -- createToken() -- token is : "+token);
            return token;
    }

    private Key getSignKey() {
        System.out.println("In JwtService -- getSignKey()");
        byte[] key=Decoders.BASE64.decode("mYsECRETkEYmYsECRETkEYmYsECRETkEYmYsECRETkEYmYsECRETkEYmYsECRETk");
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token)
    {
        System.out.println("In JwtService -- extractAllClaims() -- extracting claims from token : "+token);
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T extractClaim(String token,Function<Claims,T> claimsResolver)
    {
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token)
      {  return extractClaim(token,Claims::getSubject);  }


    public Date extractExpiration(String token)
    {    return extractClaim(token,Claims::getExpiration);   }
    
    public Boolean isTokenExpired(String token)
      {  Boolean isTokenValid= extractExpiration(token).before(new Date()); System.out.println("In JwtService -- isTokenExpired() -- isTokenValid : "+isTokenValid); return isTokenValid; }

    public Boolean validateToken(String token,UserDetails userDetails)
    {
        final String username=extractUsername(token);
        Boolean tokenValid=username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        System.out.println("In JwtService -- validateToken() -- isTokenValid : "+tokenValid);
        return tokenValid;
    }
    
}
