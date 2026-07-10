package com.example.schoolsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@Entity
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq")
    @SequenceGenerator(name = "invoice_seq", sequenceName = "invoice_sequence", allocationSize = 1)
    private Long invoice_id;

    @ManyToOne
    @JoinColumn(name="enrollment_id")
    private Enrollement_session enrollementSession;

    private String paymentMethod;


    private Long scholar_no;
    private  String class_no;
    @Transient
    @JsonProperty("enrollment_id")
    private Long enrollement_id;
    private Long amount;
    private String paymentType;
    private String remarks;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}
