package org.example.gallery.services.user;

import org.example.gallery.views.user.UserLoginView;
import org.example.gallery.views.user.UserRegisterView;

public interface UserService {

    void register(UserRegisterView userRegisterView);

    void login(UserLoginView userLoginView) throws Exception;
}
