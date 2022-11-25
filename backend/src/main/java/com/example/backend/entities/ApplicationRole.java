package com.example.backend.entities;

public enum ApplicationRole
{
    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String names;

    ApplicationRole(String names) {
        this.names = names;
    }
}
