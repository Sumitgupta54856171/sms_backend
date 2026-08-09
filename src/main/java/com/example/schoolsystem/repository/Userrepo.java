package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface Userrepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User existsByEmail(String email);

    boolean existsUserByEmail(String email);

    User findByEmail(String email);


}
