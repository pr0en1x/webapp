package com.haulmont.application.backend.dao;

import com.haulmont.application.backend.models.Doctor;
import com.haulmont.application.backend.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl extends DAO<Doctor> {

    @Override
    public Doctor findById(Long id) {
        Session session = null;
        Doctor doctor = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            doctor = session.load(Doctor.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return doctor;
    }

    @Override
    public List<Doctor> findAll() {
        Session session = null;
        List<Doctor> doctors = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Doctor> criteriaQuery = session.getCriteriaBuilder().createQuery(Doctor.class);
            criteriaQuery.from(Doctor.class);
            doctors = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.out.println("findAll error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return doctors;
    }
}
