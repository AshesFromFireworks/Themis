package com.themis.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwt {

    public static String genJWT(String id, String username){
        HashMap<String, String> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        return JWT.create().withClaim("identity", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(Algorithm.HMAC256("Ashes"));
    }

    public static boolean verJWT(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("Ashes")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("identity"));
        return !claims.isEmpty();
    }


}
