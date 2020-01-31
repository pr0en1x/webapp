package com.haulmont.application.backend.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public Recipe() {
    }

    public Recipe(String description, Patient patient, Doctor doctor, LocalDate dateCreation, LocalDate validity, Priority priority) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.dateCreation = dateCreation;
        this.validity = validity;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatient() {
        return patient.getName() + " " + patient.getSurname() + " " + patient.getPatronymic();
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public void setValidity(LocalDate validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return description + '\'' + priority;
    }
}
