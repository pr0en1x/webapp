package com.haulmont.application.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "recipe")
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private LocalDate dateCreation;
    private LocalDate validity;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    public Recipe(String description, Patient patient, Doctor doctor, LocalDate dateCreation, LocalDate validity, Priority priority) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.dateCreation = dateCreation;
        this.validity = validity;
        this.priority = priority;
    }

    public Recipe() {
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", dateCreation=" + dateCreation +
                ", validity=" + validity +
                ", priority=" + priority +
                '}';
    }
}
