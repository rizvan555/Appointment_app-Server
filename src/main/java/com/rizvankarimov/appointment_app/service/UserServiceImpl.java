package com.rizvankarimov.appointment_app.service;

import com.rizvankarimov.appointment_app.entity.MyServices;
import com.rizvankarimov.appointment_app.entity.Role;
import com.rizvankarimov.appointment_app.entity.User;
import com.rizvankarimov.appointment_app.repository.ServiceRepository;
import com.rizvankarimov.appointment_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final PasswordEncoder passwordEncoder;

    public void createAdminUser() {
        User adminUser = new User();
        adminUser.setUsername("rizvan");
        adminUser.setPassword(passwordEncoder.encode("111111"));
        adminUser.setRole(Role.ADMIN);
        adminUser.setCreateTime(LocalDateTime.now());

        userRepository.save(adminUser);
    }

    @Override
    public void addService() {

    }
    @Override
    @Transactional
    public MyServices addService(MyServices myServices) {
        return serviceRepository.save(myServices);
    }

    @Override
    public User saveUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreateTime(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional //Transactional is required when executing an update/delete query.
    public void changeRole(Role newRole, String username)
    {
        userRepository.updateUserRole(username, newRole);
    }

    @Override
    public List<User> findAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<MyServices> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public MyServices getServiceById(Long id) {
        return serviceRepository.findById(id).get();
    }

}