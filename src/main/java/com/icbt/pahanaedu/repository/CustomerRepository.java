package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByEmail(String email);
    List<Customer> findAllByPhoneNumber(String phoneNumber);
}
