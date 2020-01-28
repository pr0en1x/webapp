package com.haulmont.application.views.patients;

import com.haulmont.application.backend.dao.PatientDAOImpl;
import com.haulmont.application.backend.models.Patient;
import com.haulmont.application.backend.services.Services;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;



@Route("managePatient")
public class ManagePatient extends Dialog {
    private TextField name = new TextField("Имя");
    private TextField surname = new TextField("Фамилия");
    private TextField patronymic = new TextField("Отчество");
    private TextField phone = new TextField("Телефон");
    private Button save = new Button("Ок");
    private Button cancel = new Button("Отменить");

    private Binder<Patient> binder = new Binder<>(Patient.class);
    PatientView patientView;
    private Services<Patient> patientServices = new Services<>(new PatientDAOImpl());

    public ManagePatient(PatientView patientView) {
        this.patientView = patientView;

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(name, surname, patronymic, phone, buttons);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> save());
        cancel.addClickListener(event -> cancel());
    }

    public void setPatient(Patient patient) {
        binder.setBean(patient);

        if (patient == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    private void cancel() {
        this.close();
    }

    private void save() {
        Patient patient = binder.getBean();
        patientServices.save(patient);
        patientView.updateList();
        setPatient(null);
    }

    public void updatePatient() {
        Patient patient = binder.getBean();
        patientServices.update(patient);
        patientView.updateList();
        setPatient(null);
    }

}

