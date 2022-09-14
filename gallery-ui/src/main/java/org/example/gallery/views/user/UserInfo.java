package org.example.gallery.views.user;

import lombok.Builder;
import lombok.Data;
import org.example.gallery.models.User;
import org.example.gallery.security.UserDetailsImp;

@Data
@Builder
public class UserInfo {
    private String name;
    private String email;

    public static UserInfo of(UserDetailsImp user) {
        return UserInfo.builder()
                .email(user.getEmail())
                .name(user.getUsername())
                .build();
    }
}
