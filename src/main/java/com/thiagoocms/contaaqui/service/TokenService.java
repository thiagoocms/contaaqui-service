package com.thiagoocms.contaaqui.service;

import com.thiagoocms.contaaqui.contaaqui_core.exception.UnauthorizedException;
import com.thiagoocms.contaaqui.domain.auth.Auth;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public TokenService(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public String generateToken(Auth auth) {
        LocalDateTime now = LocalDateTime.now();
        long expiry = 2L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("contaaqui-service")
                .issuedAt(now.toInstant(ZoneOffset.of("-03:00")))
                .expiresAt(now.plusMinutes(expiry).toInstant(ZoneOffset.of("-03:00")))
                .subject(auth.getUsername())
                .build();

        return encoder.encode(
                        JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public void validateToken(String token) {
        Jwt jwt;
        try {
            jwt = decoder.decode(token);
        } catch (BadJwtException e) {
            throw new UnauthorizedException("invalid token");
        }
        if (Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now())) {
            throw new UnauthorizedException("expired date");
        }
    }

}