package com.example.schoolsystem.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="Session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long session_id;
    private String session_name;
    private String session_start_date;
    private String session_end_date;
    private boolean is_current;
    private boolean is_active;
    private String description;
    @CreationTimestamp
    @Column(updatable = false, name = "created_date")
    private LocalDate created_at;
}
