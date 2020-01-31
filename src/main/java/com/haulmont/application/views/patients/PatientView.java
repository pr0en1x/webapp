package com.haulmont.application.views.patients;

import com.haulmont.application.backend.dao.PatientDAOImpl;
import com.haulmont.application.backend.models.Patient;
import com.haulmont.application.backend.services.Services;
import com.haulmont.application.views.doctors.DoctorView;
import com.haulmont.application.views.recipes.RecipeView;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("patients")
public class PatientView extends VerticalLayout {
    private Grid<Patient> grid = new Grid<>(Patient.class);
    private Services<Patient> patientServices = new Services<>(new PatientDAOImpl());
    private TextField name = new TextField("Имя");
    private TextField surname = new TextField("Фамилия");
    private TextField patronymic = new TextField("Отчество");
    private TextField phone = new TextField("Телефон");
    private FormLayout formLayout = new FormLayout();
    private RouterLink link = new RouterLink("  Список докторов", DoctorView.class);
    private RouterLink link2 = new RouterLink("  Список рецептов", RecipeView.class);

    public PatientView() {
        add("Список пациентов");
       // Добавление пациента
        Button addPatientBtn = new Button("Добавить");
        addPatientBtn.addClickListener(e -> {
            formLayout.add(name, surname, patronymic, phone);
            Dialog dialog = new Dialog();
            dialog.add(formLayout);
            Button confirm = new Button("Ок");
            Button cancel = new Button("Отмена");
            dialog.add(confirm);
            dialog.add(cancel);

            confirm.addClickListener(clickEvent -> {
                    Patient patient = new Patient();
                    patient.setName(name.getValue());
                    patient.setSurname(surname.getValue());
                    patient.setPatronymic(patronymic.getValue());
                    patient.setPhone(phone.getValue());
                    patientServices.save(patient);
                    updateList();
                    name.clear();
                    surname.clear();
                    patronymic.clear();
                    phone.clear();
                    dialog.close();
            });
            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });
            dialog.open();
        });
        // Изменение пациента
        Button updateBtn = new Button("Изменить");
        updateBtn.addClickListener(e -> {
                    Patient patient = null;
                    for (Patient selectedPatient : grid.getSelectedItems()) {
                        patient = selectedPatient;
                    }
                    if (patient != null) {
                        formLayout.add(name, surname, patronymic, phone);
                        Dialog dialog = new Dialog();
                        dialog.add(formLayout);
                        Button confirm = new Button("Ок");
                        Button cancel = new Button("Отмена");
                        dialog.add(confirm);
                        dialog.add(cancel);

                        Patient finalPatient = patient;
                        confirm.addClickListener(clickEvent -> {
                            finalPatient.setName(name.getValue());
                            finalPatient.setSurname(surname.getValue());
                            finalPatient.setPatronymic(patronymic.getValue());
                            finalPatient.setPhone(phone.getValue());
                            patientServices.update(finalPatient);
                            updateList();
                            name.clear();
                            surname.clear();
                            patronymic.clear();
                            phone.clear();
                            dialog.close();
                        });
                        cancel.addClickListener(clickEvent -> {
                            dialog.close();
                        });
                        dialog.open();
                    } else {
                        Notification notification = new Notification("Выберите пациента", 2000);
                        notification.setPosition(Notification.Position.MIDDLE);
                        notification.open();
                    }
                });

        HorizontalLayout tabs = new HorizontalLayout(link, link2);
        HorizontalLayout toolbar = new HorizontalLayout(addPatientBtn, updateBtn);
        grid.setColumns("name", "surname", "patronymic", "phone");
        // Удаление пациента
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
                Notification notification = new Notification("Пациент удален", 2000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            });
            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });
            dialog.open();
        }));

        HorizontalLayout patientContent = new HorizontalLayout(grid);
        patientContent.setSizeFull();
        grid.setSizeFull();
        add(tabs, toolbar, patientContent);
        setSizeFull();
        updateList();
    }

    public void updateList() { grid.setItems(patientServices.findAll());}
}
