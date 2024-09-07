package com.zosh.controller;

import com.zosh.model.User;
import com.zosh.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/profile")
    public ResponseEntity findUserByJwt(@RequestHeader("Authorization") String jwt){
        try{
            User user = this.userService.findUserByJwt(jwt);

            return new ResponseEntity<User>(user, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>("No User Found", HttpStatus.BAD_REQUEST);
        }
    }
}
