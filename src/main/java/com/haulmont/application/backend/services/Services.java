package com.haulmont.application.backend.services;


import com.haulmont.application.backend.dao.DAO;
import com.haulmont.application.backend.models.Priority;
import com.haulmont.application.backend.models.Recipe;

import java.util.List;

public class Services<T> {

    private DAO<T> dao;

    public Services(DAO<T> dao) {
        this.dao = dao;
    }

    public T find(Long id) {
        return dao.findById(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public void save(T object) {
        dao.save(object);
    }

    public void delete(T object) {
        dao.delete(object);
    }

    public void update(T object) {
        dao.update(object);
    }

    public List<T> findAllFilter(String stringFilter) {
        return dao.findAllFilter(stringFilter);
    }

    public List<T> findAllPatients(Recipe recipe) { return dao.findAllPatients(recipe);
    }

    public List<T> findAllPriority(Priority value) { return dao.findAllPriority(value);
    }
}