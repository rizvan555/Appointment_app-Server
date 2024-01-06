package com.rizvankarimov.appointment_app.service;

import com.rizvankarimov.appointment_app.dto.UserDto;
import com.rizvankarimov.appointment_app.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto); // save user to database
    User findUserByEmail(String email); // find user by email
    List<UserDto> findAllUsers(); // find all users
}
