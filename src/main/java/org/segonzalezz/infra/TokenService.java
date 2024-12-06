package org.segonzalezz.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.segonzalezz.dominio.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    private static final String ISSUER = "alura";
    private static final int EXPIRATION_HOURS = 6;

    public String generarToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getCorreoElectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaVencimiento())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException("Failed to generate token");
        }
    }

    public String getSubject(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("El token proporcionado es nulo o está vacío.");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token no válido o caducado: " + e.getMessage(), e);
        }
    }

    private Instant generarFechaVencimiento() {
        return LocalDateTime.now().plusHours(EXPIRATION_HOURS).toInstant(ZoneOffset.of("-03:00"));
    }
}
