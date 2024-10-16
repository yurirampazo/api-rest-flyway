package com.alura.api_rest.infra.security;

import com.alura.api_rest.domain.model.user.UserApp;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${app.security.token.client-secret}")
    private String clientSecret;

    public String generateToken(UserApp userApp) {
        final long plusMillisToExpire = 20000;
        try {
            Algorithm algorithm = Algorithm.HMAC256(clientSecret);
            return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(userApp.getUsername())
                    .withExpiresAt(Instant.now().plusMillis(plusMillisToExpire))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error generating JWT", exception);
        }
    }
}
