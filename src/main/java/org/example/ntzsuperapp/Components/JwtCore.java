package org.example.ntzsuperapp.Components;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.ntzsuperapp.Security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtCore {
    @Value("anusxdguyad237nxdgjagwd72313nxgajsgd712gs781nsngvo938274bd")
    private String secret;
    @Value("3600000")
    private int lifeTime;

    public String generateToken(Authentication authentication){
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + lifeTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getNameFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public long getExpirationTime(){
        return System.currentTimeMillis() + lifeTime;
    }
}

