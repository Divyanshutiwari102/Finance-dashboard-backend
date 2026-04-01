package org.com.example.finance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true),
        @Index(name = "idx_user_active", columnList = "active")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, unique = true, length = 180)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}