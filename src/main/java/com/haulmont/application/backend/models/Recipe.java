package com.haulmont.application.backend.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private LocalDate dateCreation;
    private LocalDate validity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    public Recipe() {
    }

    public Recipe(@NotNull @Size(min = 1) String description, Patient patient, Doctor doctor, LocalDate dateCreation, LocalDate validity, @NotNull Priority priority) {
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

    public Patient getPatient() {
        return patient;
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
