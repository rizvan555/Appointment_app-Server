package com.rizvankarimov.appointment_app.service;

import com.rizvankarimov.appointment_app.entity.MyServices;
import com.rizvankarimov.appointment_app.entity.Role;
import com.rizvankarimov.appointment_app.entity.User;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author sa
 * @date 23.07.2023
 * @time 14:24
 */
public interface UserService
{
    void addService();

    @Transactional
    MyServices addService(MyServices myServices);

    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void changeRole(Role newRole, String username);

    List<User> findAllUsers();

    User findUserById(Long id);

    void createAdminUser();

    List<MyServices> getAllServices();

}
