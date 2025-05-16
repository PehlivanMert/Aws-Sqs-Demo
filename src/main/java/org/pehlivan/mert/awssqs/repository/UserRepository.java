package org.pehlivan.mert.awssqs.repository;

import org.pehlivan.mert.awssqs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByValidationTokenAndValidationTokenExpiryAfter(
            String token, LocalDateTime now);

    Optional<User> findByEmail(String email);
}
