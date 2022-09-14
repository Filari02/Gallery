package org.example.gallery.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.example.gallery.models.User;
import org.example.gallery.models.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
public class UserDetailsImp implements UserDetails {
    private int id;
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private String password;

    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String SIMPLE_USER_ROLE = "ROLE_USER";

    public static UserDetailsImp of(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getUserType().equals(UserType.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority(ADMIN_ROLE));
        }
        authorities.add(new SimpleGrantedAuthority(SIMPLE_USER_ROLE));

        return new UserDetailsImp(
                user.getId(),
                user.getName(),
                user.getEmail(),
                authorities,
                user.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
