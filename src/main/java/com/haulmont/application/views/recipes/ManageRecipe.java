package com.haulmont.application.views.recipes;

import com.haulmont.application.backend.dao.RecipeDAOImpl;
import com.haulmont.application.backend.models.Recipe;
import com.haulmont.application.backend.services.Services;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("manageRecipe")
public class ManageRecipe extends Dialog {
    private TextField name = new TextField("Имя");
    private TextField surname = new TextField("Фамилия");
    private TextField patronymic = new TextField("Отчество");
    private TextField phone = new TextField("Телефон");
    private Button save = new Button("Ок");
    private Button cancel = new Button("Отменить");

    private Binder<Recipe> binder = new Binder<>(Recipe.class);
    RecipeView recipeView;
    private Services<Recipe> recipeServices = new Services<>(new RecipeDAOImpl());

    public ManageRecipe(RecipeView recipeView) {
        this.recipeView = recipeView;

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(name, surname, patronymic, phone, buttons);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> save());
        cancel.addClickListener(event -> cancel());
    }

    public void setRecipe(Recipe recipe) {
        binder.setBean(recipe);

        if (recipe == null) {
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
        Recipe recipe = binder.getBean();
        recipeServices.save(recipe);
        recipeView.updateList();
        setRecipe(null);
    }

    public void updateRecipe() {
        Recipe recipe = binder.getBean();
        recipeServices.update(recipe);
        recipeView.updateList();
        setRecipe(null);
    }

}
