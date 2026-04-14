package com.example.Sistema_Requisicao.services;

//import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.security.Key;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Gera o token com o email do usuário
    public String gerarToken(String email, Integer idUsuario, Integer tipoPerfil) {
        return Jwts.builder()
                .setSubject(email)
                .claim("idUsuario", idUsuario)
                .claim("tipoPerfil", tipoPerfil)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrai o email do token
    public String extrairEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Extrai o idUsuario do token
    public Integer extrairIdUsuario(String token) {
        return getClaims(token).get("idUsuario", Integer.class);
    }

    // Verifica se o token é válido
    public boolean tokenValido(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}