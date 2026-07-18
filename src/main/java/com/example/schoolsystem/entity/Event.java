package com.example.schoolsystem.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name ="event")
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long eventid;
    private String eventname;
    private LocalDate eventdate;
    private String venue;
    private String color;
    private Long sessionId;

}

