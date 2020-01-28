package com.haulmont.application.backend.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1)
    private String name;
    @NotNull
    @Size(min = 1)
    private String surname;
    @NotNull
    @Size(min = 1)
    private String patronymic;
    @NotNull
    @Pattern(regexp = "[1-9]\\d{9}")
    private String phone;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REFRESH)
    private List<Recipe> recipes;

    public void addRecipe(Recipe recipe) {
        recipe.setPatient(this);
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }

    public Patient() {
    }

    public Patient(String name, String surname, String patronymic, String phone) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
        recipes = new ArrayList<>();
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
