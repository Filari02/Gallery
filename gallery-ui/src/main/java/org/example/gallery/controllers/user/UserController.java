package org.example.gallery.controllers.user;

import lombok.RequiredArgsConstructor;
import org.example.gallery.services.user.UserService;
import org.example.gallery.views.user.UserLoginView;
import org.example.gallery.views.user.UserRegisterView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void register(@RequestBody UserRegisterView userRegisterView) {
        userService.register(userRegisterView);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginView userLoginView) {
        return userService.login(userLoginView);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return userService.logout();
    }

}
