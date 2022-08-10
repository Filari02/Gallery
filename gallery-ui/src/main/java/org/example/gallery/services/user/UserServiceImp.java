package org.example.gallery.services.user;

import lombok.RequiredArgsConstructor;
import org.example.gallery.models.User;
import org.example.gallery.models.UserType;
import org.example.gallery.repositories.user.UserRepository;
import org.example.gallery.views.user.UserLoginView;
import org.example.gallery.views.user.UserRegisterView;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;


    public void register(UserRegisterView userRegisterView) {
        User user = new User();
        user.setEmail(userRegisterView.getEmail());
        user.setName(userRegisterView.getName());
        user.setPassword(encoder.encode(userRegisterView.getPassword()));
        user.setUserType(UserType.SIMPLE_USER);

        userRepository.save(user);
    }

    @Override
    public void login(UserLoginView userLoginView) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (userLoginView.getEmail(), userLoginView.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }
    }
}
