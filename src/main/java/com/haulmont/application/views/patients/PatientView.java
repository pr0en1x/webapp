package com.haulmont.application.views.patients;

import com.haulmont.application.backend.dao.PatientDAOImpl;
import com.haulmont.application.backend.models.Patient;
import com.haulmont.application.backend.services.Services;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;

@Route("patients")
public class PatientView extends VerticalLayout {
    private Grid<Patient> grid = new Grid<>(Patient.class);
    private Services<Patient> patientServices = new Services<>(new PatientDAOImpl());
    private ManagePatient form = new ManagePatient(this);
    private TextField name = new TextField("Имя");
    private TextField surname = new TextField("Фамилия");
    private TextField patronymic = new TextField("Отчество");
    private TextField phone = new TextField("Телефон");
    private FormLayout formLayout = new FormLayout();

    public PatientView() {
        Binder<Patient> binder = new Binder<>(Patient.class);
        binder.bindInstanceFields(this);
        Button addPatientBtn = new Button("Добавить");
        addPatientBtn.addClickListener(e -> {
            formLayout.add(name, surname, patronymic, phone);
            Dialog dialog = new Dialog();
            dialog.add(formLayout);
            dialog.setSizeFull();
            Button confirm = new Button("Ок");
            Button cancel = new Button("Отмена");
            dialog.add(confirm);
            dialog.add(cancel);

            confirm.addClickListener(clickEvent -> {
                if (binder.validate().isOk()) {
                    Patient patient = new Patient();
                    patient.setName(name.getValue());
                    patient.setSurname(surname.getValue());
                    patient.setPatronymic(patronymic.getValue());
                    patient.setPhone(phone.getValue());
                    patientServices.save(patient);
                    updateList();
                    dialog.close();
                    Notification notification = new Notification("Пациент создан", 1000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                }
            });

            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });

            dialog.open();

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
