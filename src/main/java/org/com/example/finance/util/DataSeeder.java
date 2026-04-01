package org.com.example.finance.util;

import org.com.example.finance.entity.Role;
import org.com.example.finance.entity.User;
import org.com.example.finance.entity.enums.UserRole;
import org.com.example.finance.repository.RoleRepository;
import org.com.example.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName(UserRole.ADMIN)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.ADMIN).build()));
        Role analystRole = roleRepository.findByName(UserRole.ANALYST)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.ANALYST).build()));
        Role viewerRole = roleRepository.findByName(UserRole.VIEWER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.VIEWER).build()));

        createIfNotExists("admin@finance.com", "Admin User", "Admin@123", adminRole);
        createIfNotExists("analyst@finance.com", "Analyst User", "Analyst@123", analystRole);
        createIfNotExists("viewer@finance.com", "Viewer User", "Viewer@123", viewerRole);
    }

    private void createIfNotExists(String email, String name, String rawPassword, Role role) {
        if (!userRepository.existsByEmail(email)) {
            userRepository.save(User.builder()
                    .email(email)
                    .name(name)
                    .password(passwordEncoder.encode(rawPassword))
                    .active(true)
                    .role(role)
                    .build());
        }
    }
}