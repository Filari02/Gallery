package org.example.gallery.models;

public enum UserType {
    ADMIN("ROLE_ADMIN"),
    SIMPLE_USER ("ROLE_USER");

    public final String role;

    private UserType(String role) {
        this.role = role;
    }
}
