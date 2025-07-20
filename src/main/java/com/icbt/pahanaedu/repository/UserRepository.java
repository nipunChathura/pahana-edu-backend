package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndStatusNot(String username, String status);
    List<User> findAllByStatusNot(String status);

    List<User> findAllByStatus(String status);

    List<User> findAllByStatusAndUsernameContaining(String status, String searchValue);
    List<User> findAllByStatusNotAndUsernameContaining(String status, String searchValue);
}
