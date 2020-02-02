package com.haulmont.application.backend.models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "\\D{1,}")
    private String name;
    @Pattern(regexp = "\\D{1,}")
    private String surname;
    @Pattern(regexp = "\\D{1,}")
    private String patronymic;
    @Pattern(regexp = "[1-9]\\d{10}")
    private String phone;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REFRESH)
    private List<Recipe> recipes;

    public Patient() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return id +
                " - " + name +
                " " + surname +
                " " + patronymic;
    }
}
