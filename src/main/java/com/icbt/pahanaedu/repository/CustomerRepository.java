package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Customer;
import com.icbt.pahanaedu.entity.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByEmail(String email);
    List<Customer> findAllByPhoneNumber(String phoneNumber);
    int countByCustomerRegNoStartingWith(String prefix);

    @Query("SELECT c.membershipType, COUNT(c) FROM Customer c GROUP BY c.membershipType")
    List<Object[]> countCustomersGroupedByMembershipType();
}
