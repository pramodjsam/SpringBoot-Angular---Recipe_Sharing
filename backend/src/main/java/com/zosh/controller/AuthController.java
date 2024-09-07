package com.zosh.controller;

import com.zosh.config.JwtProvider;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import com.zosh.request.LoginRequest;
import com.zosh.response.AuthResponse;
import com.zosh.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody User user){
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();

        User isExistingUser = userRepository.findByEmail(email);
        if(isExistingUser != null){
            return new ResponseEntity<String>("Email is already used with another account", HttpStatus.BAD_REQUEST);
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(this.passwordEncoder.encode(password));
        createdUser.setFullName(fullName);

        this.userRepository.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signin success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!this.passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
