package com.rizvankarimov.appointment_app.repository;


import com.rizvankarimov.appointment_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
