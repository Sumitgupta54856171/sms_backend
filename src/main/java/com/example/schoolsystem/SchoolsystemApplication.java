package com.example.schoolsystem;

import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.entity.User;
import com.example.schoolsystem.repository.Sessionrepo;
import com.example.schoolsystem.repository.Userrepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.schoolsystem.entity.Role.SUPER_ADMIN;

@SpringBootApplication
public class SchoolsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolsystemApplication.class, args);
    }
    @Bean
    CommandLineRunner initSuperAdmin(Userrepo userrepo, PasswordEncoder passwordEncoder, Sessionrepo sessionrepo) {
        return args -> {
            // Check karega ki admin pehle se toh nahi hai

            boolean existsession = sessionrepo.findByIs_currentIsTrue(true).isPresent();
            if(!existsession){
                Session session = new Session();
                session.set_current(true);
                session.set_active(true);
                session.setSession_name("2026-27");
                session.setSession_start_date("2026-04-01");
                session.setSession_end_date("2027-03-31");
                session.setDescription("Academic Session 2026-27");
                sessionrepo.save(session);

            }
            System.out.println("Session exists: " + existsession);
            boolean existbyemail = userrepo.existsUserByEmail("admin@roseconvent.com");
            System.out.println("Admin exists: " + existbyemail);
            if (!existbyemail) {
                User admin = new User();
                admin.setUsername("SuperAdmin123");
                admin.setEmail("admin@roseconvent.com");
                // Spring Boot khud password encrypt kar dega
                admin.setPassword(passwordEncoder.encode("admin7$12345687"));
                admin.setRole(SUPER_ADMIN);

                userrepo.save(admin);
                System.out.println("✅ SUPER_ADMIN successfully created in Database!");
            }
        };
    }
}
