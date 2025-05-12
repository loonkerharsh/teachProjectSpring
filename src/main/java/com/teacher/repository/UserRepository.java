package com.teacher.repository;

import com.teacher.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id); // Should already exist via JpaRepository
    User findByUsername(String username);
    long countByRole(String role);
    boolean existsByUsername(String username);
}