package com.zosh.service;

import com.zosh.config.JwtProvider;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserById(Long id) throws Exception {
        Optional<User> user = this.userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        }else{
            throw new Exception("User not found");
        }
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = this.jwtProvider.getEmailFromJwtToken(jwt);
        if(email == null){
            throw new Exception("Email not found");
        }
        User user = this.userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("User not found");
        }

        return user;
    }
}
