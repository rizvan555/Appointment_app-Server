package com.rizvankarimov.appointment_app.repository;


import com.rizvankarimov.appointment_app.entity.MyServices;
import com.rizvankarimov.appointment_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<MyServices, Long> {
    Optional<User> findByUsername(String username);
}
