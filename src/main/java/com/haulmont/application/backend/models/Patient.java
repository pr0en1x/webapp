package com.haulmont.application.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String patronymic;
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

    public Patient(String name, String surname, String patronymic, @Pattern(regexp = "[1-9]\\d{9}") String phone) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone = phone;
        recipes = new ArrayList<>();
    }

    public Patient() {
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phone='" + phone + '\'' +
                ", recipes=" + recipes +
                '}';
    }
}
