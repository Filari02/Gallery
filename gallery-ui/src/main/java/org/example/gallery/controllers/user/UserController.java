package org.example.gallery.controllers.user;

import lombok.RequiredArgsConstructor;
import org.example.gallery.services.user.UserService;
import org.example.gallery.views.user.UserLoginView;
import org.example.gallery.views.user.UserRegisterView;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void register(@RequestBody UserRegisterView userRegisterView) {
        userService.register(userRegisterView);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public void login(@RequestBody UserLoginView userLoginView) throws Exception {
        userService.login(userLoginView);
    }
}
