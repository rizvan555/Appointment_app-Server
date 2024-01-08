package com.rizvankarimov.appointment_app.repository;

import com.rizvankarimov.appointment_app.entity.Role;
import com.rizvankarimov.appointment_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.username = :username")
    void updateUserRole(@Param("username") String username, @Param("role") Role role);

}