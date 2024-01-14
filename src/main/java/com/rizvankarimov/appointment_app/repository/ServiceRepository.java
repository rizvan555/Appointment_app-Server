package com.rizvankarimov.appointment_app.repository;


import com.rizvankarimov.appointment_app.entity.MyServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<MyServices, Long> {
    <Optional> MyServices findByService(String service);
}
