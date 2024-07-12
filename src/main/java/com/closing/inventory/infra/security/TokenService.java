package com.closing.inventory.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.closing.inventory.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            logger.info("Gerando token para usuário: {}", user.getUsername());
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("login-auth-ipa")
                    .withSubject(user.getUsername())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            logger.info("Token gerado: {}", token);
            return token;
        } catch (JWTCreationException exception) {
            logger.error("Erro enquanto está autenticando!", exception);
            throw new RuntimeException("Erro enquanto está autenticando!", exception);
        }
    }

    public String validateToken(String token) {
        try {
            logger.info("Validando token: {}", token);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("login-auth-ipa")
                    .build()
                    .verify(token)
                    .getSubject();
            logger.info("Token válido. Usuário: {}", subject);
            return subject;
        } catch (JWTVerificationException exception) {
            logger.error("Token inválido ou expirado", exception);
            throw new RuntimeException("Token inválido ou expirado", exception);
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}