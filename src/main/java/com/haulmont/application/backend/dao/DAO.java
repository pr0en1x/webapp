package com.haulmont.application.backend.dao;

import com.haulmont.application.backend.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import java.util.List;

public abstract class DAO<T> {

        abstract public T findById(Long id);
        abstract public List<T> findAll();

        public void save(T object) {
            Session session = null;
            try {
                session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(object);
                session.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Save error:" + e);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        public void update(T object) {
            Session session = null;
            try {
                session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.update(object);
                session.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Update error: " + e);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }

        public void delete(T object) {
            Session session = null;
            try {
                session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.delete(object);
                session.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Delete error: " + e);
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
}

