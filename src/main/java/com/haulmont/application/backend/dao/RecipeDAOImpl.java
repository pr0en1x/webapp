package com.haulmont.application.backend.dao;

import com.haulmont.application.backend.models.Recipe;
import com.haulmont.application.backend.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeDAOImpl extends DAO<Recipe> {

    @Override
    public Recipe findById(Long id) {
        Session session = null;
        Recipe recipe = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            recipe = session.load(Recipe.class, id);
        } catch (Exception e) {
            System.out.println("findById error: " + e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return recipe;
    }

    @Override
    public List<Recipe> findAll() {
        Session session = null;
        List<Recipe> recipes = new ArrayList<>();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            CriteriaQuery<Recipe> criteriaQuery = session.getCriteriaBuilder().createQuery(Recipe.class);
            criteriaQuery.from(Recipe.class);
            recipes = session.createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return recipes;
    }

    @Override
    public List<Recipe> findAllFilter(String stringFilter) {
        Session session = null;
        List<Recipe> recipes = new ArrayList<>();
        List<Recipe> contacts = new ArrayList<>();
        try {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaQuery<Recipe> criteriaQuery = session.getCriteriaBuilder().createQuery(Recipe.class);
        criteriaQuery.from(Recipe.class);
        recipes = session.createQuery(criteriaQuery).getResultList();
        HashMap<Long, Recipe> contactsMap = new HashMap<>();
            for (Recipe recipe : recipes) {
                contactsMap.put(recipe.getId(), recipe);
            }
        for (Recipe contact : contactsMap.values()) {

            boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                    || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
            if (passesFilter) {
                contacts.add(contact);
            }
        }} finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        return contacts;
    }
}