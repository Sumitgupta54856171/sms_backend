package com.example.schoolsystem;

import com.example.schoolsystem.entity.User;
import com.example.schoolsystem.repository.Userrepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.schoolsystem.entity.Role.SUPER_ADMIN;
import static java.lang.IO.print;

@SpringBootApplication
public class SchoolsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolsystemApplication.class, args);
    }
    @Bean
    CommandLineRunner initSuperAdmin(Userrepo userrepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check karega ki admin pehle se toh nahi hai

            boolean existbyemail = userrepo.existsUserByEmail("admin@roseconvent.com");
            print(existbyemail);
            if (!existbyemail) {
                User admin = new User();
                admin.setUsername("SuperAdmin123");
                admin.setEmail("admin@roseconvent.com");
                // Spring Boot khud password encrypt kar dega
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(SUPER_ADMIN);

                userrepo.save(admin);
                System.out.println("✅ SUPER_ADMIN successfully created in Database!");
            }
        };
    }
}
