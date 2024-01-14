package com.rizvankarimov.appointment_app.service;


import com.rizvankarimov.appointment_app.entity.MyServices;
import com.rizvankarimov.appointment_app.entity.Role;
import com.rizvankarimov.appointment_app.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author sa
 * @date 23.07.2023
 * @time 14:24
 */
public interface UserService
{
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void changeRole(Role newRole, String username);

    List<User> findAllUsers();

    User findUserById(Long id);

    Service addService(MyServices myServices);
}
