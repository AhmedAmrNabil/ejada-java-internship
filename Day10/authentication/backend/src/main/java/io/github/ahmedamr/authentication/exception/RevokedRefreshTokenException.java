package io.github.ahmedamr.authentication.exception;

import javax.security.sasl.AuthenticationException;

public class RevokedRefreshTokenException extends AuthenticationException {
    public RevokedRefreshTokenException() {
        super("{error.jwt.revoked}");
    }
}
