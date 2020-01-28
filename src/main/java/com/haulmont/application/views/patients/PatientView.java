package com.haulmont.application.views.patients;

import com.haulmont.application.backend.dao.PatientDAOImpl;
import com.haulmont.application.backend.models.Patient;
import com.haulmont.application.backend.services.Services;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;


@Route("patients")
public class PatientView extends VerticalLayout {
    private Grid<Patient> grid = new Grid<>(Patient.class);
    private Services<Patient> patientServices = new Services<>(new PatientDAOImpl());
    private ManagePatient form = new ManagePatient(this);

    public PatientView() {
        Button addPatientBtn = new Button("Добавить");
        addPatientBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setPatient(new Patient());
        });

        Button updateBtn = new Button("Изменить");
        updateBtn.addClickListener(e -> {
            grid.asSingleSelect().getValue();
            form.updatePatient();
        });

        HorizontalLayout toolbar = new HorizontalLayout(addPatientBtn, updateBtn);

        grid.setColumns("name", "surname", "patronymic", "phone");


        grid.addColumn(new NativeButtonRenderer<>("Удалить", patient -> {
            Dialog dialog = new Dialog();
            Button confirm = new Button("Удалить");
            Button cancel = new Button("Отмена");
            dialog.add("Вы уверены что хотите удалить пациента?");
            dialog.add(confirm);
            dialog.add(cancel);

            confirm.addClickListener(clickEvent -> {
                patientServices.delete(patient);
                updateList();
                dialog.close();
                Notification notification = new Notification("Пациент удален", 1000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();

            });

            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });

            dialog.open();
        }));

        HorizontalLayout patientContent = new HorizontalLayout(grid, form);
        patientContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, patientContent);

        setSizeFull();

        updateList();
        form.setPatient(null);

    }

    public void updateList() { grid.setItems(patientServices.findAll());}
}
