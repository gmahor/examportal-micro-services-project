package com.examportal.utils;

import com.examportal.configs.EnvConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    @Autowired
    private EnvConfiguration envConfiguration;

    private PrivateKey privateKey;


    public String getToken(Object id, Object roles, String email) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        long expirationTime = envConfiguration.getAccessTokenExpiryTime();
        Key key = getJwtPrivateKey();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("roles", roles);
        return buildToken(claims, email, expirationTime, key);
    }

    private String buildToken(Map<String, Object> claims, String email, long expirationTime, Key key) {
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    private PrivateKey getJwtPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (this.privateKey == null) {
            String privateKeyPEM = new String(Files.readAllBytes(Paths.get(envConfiguration.getJwtKeyPath())), StandardCharsets.UTF_8);
            privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "");
            privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
            privateKeyPEM = privateKeyPEM.replaceAll("[\n|\r]", "");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyPEM));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            this.privateKey = kf.generatePrivate(keySpec);
        }
        return this.privateKey;
    }

//    public String validateRefreshTokenAndGetNewAccessToken(String refreshToken) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
//        Key key = Keys.hmacShaKeyFor(envConfiguration.getJwtKeyPath().getBytes());
//        Claims claims = parseTokenAndGetClaims(refreshToken, key);
//        Object userId = claims.get("id");
//        Object roles = claims.get("roles");
//        Object langCode = claims.get("lang");
//        Object device = claims.get("device");
//        return getToken(userId, roles, claims.getSubject(), GenericConstants.ACCESS, langCode.toString(), String.valueOf(device));
//    }

    private Claims parseTokenAndGetClaims(String token, Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateCommonToken(String email, long expiryTime) {
        Map<String, Object> claims = new HashMap<>();
        Key key = Keys.hmacShaKeyFor(envConfiguration.getJwtSecret().getBytes());
        return buildToken(claims, email, expiryTime, key);
    }

    public boolean validateCommonToken(String refreshToken) {
        Key key = Keys.hmacShaKeyFor(envConfiguration.getJwtSecret().getBytes());
        Claims claims = parseTokenAndGetClaims(refreshToken, key);
        return claims.getExpiration().after(new Date());
    }

    public String emailByToken(String token) {
        Key key = Keys.hmacShaKeyFor(envConfiguration.getJwtSecret().getBytes());
        Claims claims = parseTokenAndGetClaims(token, key);
        if (claims.getExpiration().after(new Date())) {
            return claims.getSubject();
        }
        return null;
    }

}
