package com.rizvankarimov.appointment_app.repository;

import com.rizvankarimov.appointment_app.entity.UserServices;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserServiceRepository extends JpaRepository<UserServices, Long>{
    //Optional<UserServices> addUserServices(String serviceName);

}
