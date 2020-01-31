package com.haulmont.application.backend.models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "\\D{1,}")
    private String name;
    @Pattern(regexp = "\\D{1,}")
    private String surname;
    @Pattern(regexp = "\\D{1,}")
    private String patronymic;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REFRESH)
    private List<Recipe> recipes;

    public Doctor() {
    }

    public Doctor(String name, String surname, String patronymic, Specialization specialization) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.specialization = specialization;
        recipes = new ArrayList<>();
    }

    public void addRecipe(Recipe recipe) {
        recipe.setDoctor(this);
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
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

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return name +
                " " + surname +
                " " + patronymic;
    }
}


