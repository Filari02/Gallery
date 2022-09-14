package org.example.gallery.services.user;

import lombok.RequiredArgsConstructor;
import org.example.gallery.models.User;
import org.example.gallery.models.UserType;
import org.example.gallery.repositories.user.UserRepository;
import org.example.gallery.security.JwtUtils;
import org.example.gallery.security.UserDetailsImp;
import org.example.gallery.views.user.UserInfo;
import org.example.gallery.views.user.UserLoginView;
import org.example.gallery.views.user.UserRegisterView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public void register(UserRegisterView userRegisterView) {
        if(!(checkPassword(userRegisterView.getPassword()) && isEmailValid(userRegisterView.getEmail()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(userRegisterView.getEmail());
        user.setName(userRegisterView.getName());
        user.setPassword(encoder.encode(userRegisterView.getPassword()));
        user.setUserType(UserType.SIMPLE_USER);

        userRepository.save(user);
    }

    @Override
    public ResponseEntity<UserInfo> login(UserLoginView userLoginView) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginView.getEmail(), userLoginView.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(UserInfo.of(userDetails));
    }

    @Override
    public boolean checkPassword(String password) {
        return password.matches("(?=.[A-Z])(?=.[a-z])(?=.\\d)");
    }

    @Override
    public boolean isEmailValid(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }


    @Override
    public ResponseEntity<UserInfo> logout() {
        ResponseCookie responseCookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).build();
    }
}
