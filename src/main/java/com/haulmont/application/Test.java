package com.haulmont.application;

import com.haulmont.application.backend.dao.PatientDAOImpl;
import com.haulmont.application.backend.models.Patient;
import com.haulmont.application.backend.services.Services;

import java.sql.SQLException;

public class Test {
        public static void main(String[] args) throws SQLException {

            Services<Patient> patientServices = new Services<>(new PatientDAOImpl());
            Patient patient = new Patient("Masha","sasha", "safq", "1234567890");
            patientServices.save(patient);

        }
}

