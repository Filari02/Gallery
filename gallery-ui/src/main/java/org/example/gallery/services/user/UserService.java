package org.example.gallery.services.user;

import org.example.gallery.views.user.UserInfo;
import org.example.gallery.views.user.UserLoginView;
import org.example.gallery.views.user.UserRegisterView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

public interface UserService {
    void register(UserRegisterView userRegisterView);

    ResponseEntity<UserInfo> login(UserLoginView userLoginView);

    ResponseEntity<UserInfo> logout();
}
