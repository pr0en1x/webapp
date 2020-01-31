package com.haulmont.application.views.doctors;

import com.haulmont.application.backend.dao.DoctorDAOImpl;
import com.haulmont.application.backend.models.Doctor;
import com.haulmont.application.backend.services.Services;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("statistics")
public class Statistics extends VerticalLayout {

    private Grid<Doctor> grid = new Grid<>(Doctor.class);
    private Services<Doctor> doctorServices = new Services<>(new DoctorDAOImpl());
    private Button button = new Button("Ок");

    public Statistics() {
        grid.setColumns("name", "patronymic", "surname");
        grid.addColumn(doctor -> doctor.getRecipes().size()).setHeader("Количество рецептов");
        HorizontalLayout layout = new HorizontalLayout(grid);
        layout.setSizeFull();
        grid.setSizeFull();
        add(layout, button);
        setSizeFull();
        grid.setItems(doctorServices.findAll());
        button.addClickListener(e -> {
            UI.getCurrent().navigate(DoctorView.class);
        });
    }
}
