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
@Table(name = "Fees")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Fee_id;


    @ManyToOne
    @JoinColumn(name="enrollment_id")
    private Enrollement_session enrollementSession;

    private PaymentMethod paymentMethod;

    @Transient
    @JsonProperty("enrollment_id")
    private Long enrollement_id;

    private Long totalamount;

    private Long studentId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;





}
