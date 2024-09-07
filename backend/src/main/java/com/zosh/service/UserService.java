package com.zosh.service;

import com.zosh.model.User;

public interface UserService {
    User findUserById(Long id) throws Exception;
    User findUserByJwt(String jwt) throws Exception;
}
