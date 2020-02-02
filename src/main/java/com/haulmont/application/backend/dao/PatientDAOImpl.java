package com.haulmont.application.backend.dao;

import com.haulmont.application.backend.models.Patient;
import com.haulmont.application.backend.models.Priority;
import com.haulmont.application.backend.models.Recipe;
import com.haulmont.application.backend.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl extends DAO<Patient> {
    @Override
    public Patient findById(Long id) {
        Session session = null;
        Patient patient = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            patient = session.load(Patient.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return patient;
    }


    @Override
    public List<Patient> findAll() {
        Session session = null;
        List<Patient> patients = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Patient> criteriaQuery = session.getCriteriaBuilder().createQuery(Patient.class);
            criteriaQuery.from(Patient.class);
            patients = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.out.println("findAll error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return patients;
    }

    @Override
    public List<Patient> findAllFilter(String stringFilter) {
        return null;
    }

    @Override
    public List<Patient> findAllPatients(Recipe recipe) { return null; }

    @Override
    public List<Patient> findAllPriority(Priority value) { return null; }
}
