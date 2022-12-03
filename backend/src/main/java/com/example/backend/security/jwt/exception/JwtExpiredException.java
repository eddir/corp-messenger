package com.example.backend.security.jwt.exception;

import io.jsonwebtoken.JwtException;

public class JwtExpiredException extends JwtException {
    public JwtExpiredException(String message) {
        super(message);
    }

    public JwtExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
