package com.example.schoolsystem.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="gmail")
public class Gmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gamilId;

    private String email;
    private String password;
    private String smtp_host;
    private int smtp_port;

    private boolean smtp_auth;
    private boolean starttls;
    private boolean enable;
}
