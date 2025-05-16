package org.pehlivan.mert.awssqs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.pehlivan.mert.awssqs.dto.UserCreateRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column
    private String validationToken;

    private LocalDateTime validationTokenExpiry;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static User create(UserCreateRequest request) {
        User user = new User();
        user.email = request.getEmail();
        user.username = request.getUsername();
        user.password = request.getPassword();
        user.validationToken = UUID.randomUUID().toString();
        user.validationTokenExpiry = LocalDateTime.now().plusHours(24);
        return user;
    }

    public void activateUser() {
        this.isActive = true;
        this.validationToken = null;
        this.validationTokenExpiry = null;
    }
}
