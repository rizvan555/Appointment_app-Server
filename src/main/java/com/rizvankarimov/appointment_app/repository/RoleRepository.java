package com.rizvankarimov.appointment_app.repository;


import com.rizvankarimov.appointment_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
