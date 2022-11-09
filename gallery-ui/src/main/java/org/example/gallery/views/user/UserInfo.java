package org.example.gallery.views.user;

import lombok.Builder;
import lombok.Data;
import org.example.gallery.models.User;
import org.example.gallery.security.UserDetailsImp;

import java.util.List;

@Data
@Builder
public class UserInfo {
    private int id;
    private String name;
    private String email;
    private List<String> roles;

    public static UserInfo of(UserDetailsImp user, List<String> roles) {
        return UserInfo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getUsername())
                .roles(roles)
                .build();
    }
}
