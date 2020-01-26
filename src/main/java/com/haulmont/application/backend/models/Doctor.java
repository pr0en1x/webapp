package com.haulmont.application.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    @Enumerated(EnumType.STRING)
    private Enum specialization;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REFRESH)
    private List<Recipe> recipes;

    public Doctor(String name, String surname, String patronymic, Enum specialization) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.specialization = specialization;
        recipes = new ArrayList<>();
    }

    public Doctor() {
    }


    public void addRecipe(Recipe recipe) {
        recipe.setDoctor(this);
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", specialization=" + specialization +
                ", recipes=" + recipes +
                '}';
    }
}


