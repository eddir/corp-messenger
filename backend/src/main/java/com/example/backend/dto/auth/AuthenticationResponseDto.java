package com.example.backend.dto.auth;

import com.fasterxml.jackson.annotation.JsonGetter;

public class AuthenticationResponseDto
{
    private String accessToken;
    private String refreshToken;
    private Long userId;

    public AuthenticationResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public AuthenticationResponseDto(String accessToken, String refreshToken, Long userId) {
        this(accessToken, refreshToken);
        this.userId = userId;
    }

    @JsonGetter("user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
