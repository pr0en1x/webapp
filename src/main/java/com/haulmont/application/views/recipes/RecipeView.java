package com.haulmont.application.views.recipes;

import com.haulmont.application.backend.dao.DoctorDAOImpl;
import com.haulmont.application.backend.dao.PatientDAOImpl;
import com.haulmont.application.backend.dao.RecipeDAOImpl;
import com.haulmont.application.backend.models.Doctor;
import com.haulmont.application.backend.models.Patient;
import com.haulmont.application.backend.models.Priority;
import com.haulmont.application.backend.models.Recipe;
import com.haulmont.application.backend.services.Services;
import com.haulmont.application.views.doctors.DoctorView;
import com.haulmont.application.views.patients.PatientView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.data.validator.DateRangeValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.time.LocalDate;

@Route("recipes")
public class RecipeView extends VerticalLayout {
    private Grid<Recipe> grid = new Grid<>(Recipe.class);
    private Services<Recipe> recipeServices = new Services<>(new RecipeDAOImpl());
    private Services<Doctor> doctorServices = new Services<>(new DoctorDAOImpl());
    private Services<Patient> patientServices = new Services<>(new PatientDAOImpl());
    private ComboBox<Recipe> filterPatient = new ComboBox<>();
    private ComboBox<Priority> filterPriority = new ComboBox<>();
    private TextField filterDescription = new TextField();
    private ComboBox<Patient> patient = new ComboBox<>("Пациент");
    private ComboBox<Doctor> doctor = new ComboBox<>("Доктор");
    private ComboBox<Priority> priority = new ComboBox<>("Приоритет");
    private DatePicker dateCreation = new DatePicker();
    private DatePicker validity = new DatePicker();
    private TextField description = new TextField();
    private FormLayout formLayout = new FormLayout();
    private Binder<Recipe> binder = new Binder<>();
    private RouterLink link = new RouterLink("  Список пациентов", PatientView.class);
    private RouterLink link2 = new RouterLink("  Список докторов", DoctorView.class);

    public RecipeView() {
        add("Список рецептов");
        priority.setItems(Priority.values());
        patient.setItems(patientServices.findAll());
        patient.setItemLabelGenerator(patient -> patient.getName() + " " + patient.getPatronymic() + " " + patient.getSurname());
        doctor.setItems(doctorServices.findAll());
        doctor.setItemLabelGenerator(doctor -> doctor.getName() + " " + doctor.getPatronymic() + " " + doctor.getSurname());

        dateCreation.setLabel("Дата создания");
        validity.setLabel("Срок действия");

        binder.forField(description).bind(Recipe::getDescription, Recipe::setDescription);
        binder.forField(patient).bind(Recipe::getPatient, Recipe::setPatient);
        binder.forField(doctor).bind(Recipe::getDoctor, Recipe::setDoctor);
        binder.forField(dateCreation).bind(Recipe::getDateCreation, Recipe::setDateCreation);
        binder.forField(validity).bind(Recipe::getValidity, Recipe::setValidity);
        binder.forField(priority).bind(Recipe::getPriority, Recipe::setPriority);

        dateCreation.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            LocalDate endDate = validity.getValue();
            if (selectedDate != null) {
                validity.setMin(selectedDate.plusDays(1));
                if (endDate == null) {
                    validity.setOpened(true);
                }
            } else {
                validity.setMin(null);
            }
        });

        validity.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            if (selectedDate != null) {
                dateCreation.setMax(selectedDate.minusDays(1));
            } else {
                dateCreation.setMax(null);
            }
        });

        // Добавление рецепта
        Button addRecipeBtn = new Button("Добавить");
        addRecipeBtn.addClickListener(e -> {
            formLayout.add(description, patient, doctor, dateCreation, validity, priority);
            Dialog dialog = new Dialog();
            dialog.add(formLayout);
            Button confirm = new Button("Ок");
            Button cancel = new Button("Отмена");
            dialog.add(confirm);
            dialog.add(cancel);

            confirm.addClickListener(clickEvent -> {
                Recipe recipe = new Recipe();
                recipe.setDescription(description.getValue());
                recipe.setPatient(patient.getValue());
                recipe.setDoctor(doctor.getValue());
                recipe.setDateCreation(dateCreation.getValue());
                recipe.setValidity(validity.getValue());
                recipe.setPriority(priority.getValue());
                recipeServices.save(recipe);
                updateList();
                description.clear();
                patient.clear();
                doctor.clear();
                dateCreation.clear();
                validity.clear();
                dialog.close();
            });
            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });
            dialog.open();
        });


        // Изменение рецепта
        Button updateBtn = new Button("Изменить");
        updateBtn.addClickListener(e -> {
            Recipe recipe = null;
            for (Recipe selectedRecipe : grid.getSelectedItems()) {
                recipe = selectedRecipe;
            }
            if (recipe != null) {
                formLayout.add(description, patient, doctor, dateCreation, validity, priority);
                binder.readBean(recipe);
                Dialog dialog = new Dialog();
                dialog.add(formLayout);
                Button confirm = new Button("Ок");
                Button cancel = new Button("Отмена");
                dialog.add(confirm);
                dialog.add(cancel);

                Recipe finalRecipe = recipe;
                confirm.addClickListener(clickEvent -> {
                    finalRecipe.setDescription(description.getValue());
                    finalRecipe.setPatient(patient.getValue());
                    finalRecipe.setDoctor(doctor.getValue());
                    finalRecipe.setDateCreation(dateCreation.getValue());
                    finalRecipe.setValidity(validity.getValue());
                    finalRecipe.setPriority(priority.getValue());
                    recipeServices.update(finalRecipe);
                    updateList();
                    description.clear();
                    patient.clear();
                    doctor.clear();
                    dateCreation.clear();
                    validity.clear();
                    dialog.close();
                });
                cancel.addClickListener(clickEvent -> {
                    dialog.close();
                });
                dialog.open();
            } else {
                Notification notification = new Notification("Выберите рецепт", 2000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            }
        });

        HorizontalLayout toolbar = new HorizontalLayout(addRecipeBtn, updateBtn);
        //Фильтры
        filterDescription.setPlaceholder("Фильтер по описанию...");
        filterDescription.setClearButtonVisible(true);
        filterDescription.setValueChangeMode(ValueChangeMode.EAGER);
        filterDescription.addValueChangeListener(e -> updateFilterList());

        filterPatient.setItems(recipeServices.findAll());
        filterPatient.setItemLabelGenerator(Recipe::getPatientView);
        filterPatient.setPlaceholder("Фильтер по пациенту...");
        filterPatient.addValueChangeListener(e -> grid.setItems(recipeServices.findAllPatients(filterPatient.getValue())));

        filterPriority.setItems(Priority.values());
        filterPriority.setPlaceholder("Фильтер по приоритету...");
        filterPriority.addValueChangeListener(e -> grid.setItems(recipeServices.findAllPriority(filterPriority.getValue())));

        HorizontalLayout tabs = new HorizontalLayout(link, link2);
        HorizontalLayout priorityBar = new HorizontalLayout(filterDescription, filterPatient, filterPriority);

        grid.setColumns("description", "patient", "doctor", "dateCreation", "validity", "priority");
        // Удаление рецепта
        grid.addColumn(new NativeButtonRenderer<>("Удалить", recipe -> {
            Dialog dialog = new Dialog();
            Button confirm = new Button("Удалить");
            Button cancel = new Button("Отмена");
            dialog.add("Вы уверены что хотите удалить рецепт?");
            dialog.add(confirm);
            dialog.add(cancel);

            confirm.addClickListener(clickEvent -> {
                recipeServices.delete(recipe);
                updateList();
                dialog.close();
                Notification notification = new Notification("Рецепт удален", 2000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            });
            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });
            dialog.open();
        }));

        HorizontalLayout recipeContent = new HorizontalLayout(grid);
        recipeContent.setSizeFull();
        grid.setSizeFull();
        add(tabs, toolbar, priorityBar, recipeContent);
        setSizeFull();
        updateList();
    }

    public void updateList() {
        grid.setItems(recipeServices.findAll());
    }
    public void updateFilterList() {
        grid.setItems(recipeServices.findAllFilter(filterDescription.getValue()));
    }
}