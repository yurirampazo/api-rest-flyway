package com.alura.api_rest.infra.config.security;

import com.alura.api_rest.domain.model.user.UserApp;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Log4j2
@Service
public class TokenService {
    private static final String ISSUER = "API voll.med";
    @Value("${app.security.token.client-secret}")
    private String clientSecret;


    public String generateToken(UserApp userApp) {
        log.info("Generating JWT...");
        final var plusMillisToExpire = 20000;
        try {
            var algorithm = Algorithm.HMAC512(clientSecret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userApp.getUsername())
                    .withExpiresAt(Instant.now().plusMillis(plusMillisToExpire))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error generating JWT", exception);
        }
    }

    public String getSubjectFromToken(String jwtToken) {
        DecodedJWT decodedJWT;
        final var BEARER = "Bearer ";
        try {
            var algorithm = Algorithm.HMAC512(clientSecret);
            var verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            jwtToken = jwtToken.replace(BEARER, Strings.EMPTY);
            decodedJWT = verifier.verify(jwtToken);
            log.debug("Decoded JWT: {}", decodedJWT);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Problem verifying JWT Token!");
        }
    }

    public boolean isValidToken(String jwtToken) {
        final var BEARER = "Bearer ";
        try {
            var algorithm = Algorithm.HMAC512(clientSecret);
            var verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            jwtToken = jwtToken.replace(BEARER, Strings.EMPTY);
            verifier.verify(jwtToken);
            return true;
        } catch (JWTVerificationException exception) {
            log.warn("Invalid JWT Token: {}", exception.getMessage());
            return false;
        }
    }
}
