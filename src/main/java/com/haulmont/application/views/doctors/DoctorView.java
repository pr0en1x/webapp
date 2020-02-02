package com.haulmont.application.views.doctors;

import com.haulmont.application.backend.dao.DoctorDAOImpl;
import com.haulmont.application.backend.models.Doctor;
import com.haulmont.application.backend.models.Specialization;
import com.haulmont.application.backend.services.Services;
import com.haulmont.application.views.patients.PatientView;
import com.haulmont.application.views.recipes.RecipeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;




@Route("doctors")
    public class DoctorView extends VerticalLayout {
        private Grid<Doctor> grid = new Grid<>(Doctor.class);
        private Services<Doctor> doctorServices = new Services<>(new DoctorDAOImpl());
        private TextField name = new TextField("Имя");
        private TextField surname = new TextField("Фамилия");
        private TextField patronymic = new TextField("Отчество");
        private ComboBox<Specialization> specialization = new ComboBox<>("Специализация");
        private FormLayout formLayout = new FormLayout();
        private Binder<Doctor> binder = new Binder<>();
        private RouterLink link = new RouterLink("  Список пациентов", PatientView.class);
        private RouterLink link2 = new RouterLink("  Список рецептов", RecipeView.class);

        public DoctorView() {
            add("Список докторов");
            specialization.setItems(Specialization.values());
            binder.forField(name).bind(Doctor::getName, Doctor::setName);
            binder.forField(surname).bind(Doctor::getSurname, Doctor::setSurname);
            binder.forField(patronymic).bind(Doctor::getPatronymic, Doctor::setPatronymic);
            binder.forField(specialization).bind(Doctor::getSpecialization, Doctor::setSpecialization);
            // Добавление доктора
            Button addDoctorBtn = new Button("Добавить");
            addDoctorBtn.addClickListener(e -> {
                formLayout.add(name, surname, patronymic, specialization);
                Dialog dialog = new Dialog();
                dialog.add(formLayout);
                Button confirm = new Button("Ок");
                Button cancel = new Button("Отмена");
                dialog.add(confirm);
                dialog.add(cancel);

                confirm.addClickListener(clickEvent -> {
                    Doctor doctor = new Doctor();
                    doctor.setName(name.getValue());
                    doctor.setSurname(surname.getValue());
                    doctor.setPatronymic(patronymic.getValue());
                    doctor.setSpecialization(specialization.getValue());
                    doctorServices.save(doctor);
                    updateList();
                    name.clear();
                    surname.clear();
                    patronymic.clear();
                    specialization.clear();
                    dialog.close();
                });
                cancel.addClickListener(clickEvent -> {
                    dialog.close();
                });
                dialog.open();
            });

            //Изменение доктора
            Button updateBtn = new Button("Изменить");
            updateBtn.addClickListener(e -> {
                Doctor doctor = null;
                for (Doctor selectedDoctor : grid.getSelectedItems()) {
                    doctor = selectedDoctor;
                }

                if (doctor != null) {
                    formLayout.add(name, surname, patronymic, specialization);
                    binder.readBean(doctor);
                    Dialog dialog = new Dialog();
                    dialog.add(formLayout);
                    Button confirm = new Button("Ок");
                    Button cancel = new Button("Отмена");
                    dialog.add(confirm);
                    dialog.add(cancel);

                    Doctor finalDoctor = doctor;
                    confirm.addClickListener(clickEvent -> {
                        finalDoctor.setName(name.getValue());
                        finalDoctor.setSurname(surname.getValue());
                        finalDoctor.setPatronymic(patronymic.getValue());
                        finalDoctor.setSpecialization(specialization.getValue());
                        doctorServices.update(finalDoctor);
                        updateList();
                        name.clear();
                        surname.clear();
                        patronymic.clear();
                        specialization.clear();
                        dialog.close();
                    });
                    cancel.addClickListener(clickEvent -> {
                        dialog.close();
                    });
                    dialog.open();
                } else {
                    Notification notification = new Notification("Выберите доктора", 2000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();
                }
            });

            Button statisticsBtn = new Button("Показать статистику");
            statisticsBtn.addClickListener(e -> {
                UI.getCurrent().navigate(Statistics.class);
        });



            HorizontalLayout tabs = new HorizontalLayout(link, link2);
            HorizontalLayout toolbar = new HorizontalLayout(addDoctorBtn, updateBtn, statisticsBtn);
            grid.setColumns("name", "patronymic", "surname", "specialization");
            //Удаление доктора
            grid.addColumn(new NativeButtonRenderer<>("Удалить", doctor -> {
                Dialog dialog = new Dialog();
                Button confirm = new Button("Удалить");
                Button cancel = new Button("Отмена");
                dialog.add("Вы уверены что хотите удалить доктора?");
                dialog.add(confirm);
                dialog.add(cancel);

                confirm.addClickListener(clickEvent -> {
                    doctorServices.delete(doctor);
                    updateList();
                    dialog.close();
                    Notification notification = new Notification("Доктор удален", 2000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();

                });

                cancel.addClickListener(clickEvent -> {
                    dialog.close();
                });

                dialog.open();
            }));

            HorizontalLayout doctorContent = new HorizontalLayout(grid);
            doctorContent.setSizeFull();
            grid.setSizeFull();
            add(tabs, toolbar, doctorContent);
            setSizeFull();
            updateList();
        }

        public void updateList() { grid.setItems(doctorServices.findAll());}
}
