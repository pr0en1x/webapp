package com.haulmont.application.views.recipes;

import com.haulmont.application.backend.dao.RecipeDAOImpl;
import com.haulmont.application.backend.models.Recipe;
import com.haulmont.application.backend.services.Services;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;

@Route("recipes")
public class RecipeView extends VerticalLayout {
    private Grid<Recipe> grid = new Grid<>(Recipe.class);
    private Services<Recipe> recipeServices = new Services<>(new RecipeDAOImpl());
    private ManageRecipe form = new ManageRecipe(this);

    public RecipeView() {
        Button addRecipeBtn = new Button("Добавить");
        addRecipeBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setRecipe(new Recipe());
        });

        Button updateBtn = new Button("Изменить");
        updateBtn.addClickListener(e -> {
            grid.asSingleSelect().getValue();
            form.updateRecipe();
        });

        HorizontalLayout toolbar = new HorizontalLayout(addRecipeBtn, updateBtn);

        grid.setColumns("description", "patient", "doctor", "dataCreation", "validity", "priority");


        grid.addColumn(new NativeButtonRenderer<>("Удалить", recipe -> {
            Dialog dialog = new Dialog();
            Button confirm = new Button("Удалить");
            Button cancel = new Button("Отмена");
            dialog.add("Вы уверены что хотите удалить рецепт?");
            dialog.add(confirm);
            dialog.add(cancel);

            confirm.addClickListener(clickEvent -> {
                recipeServices.delete(recipe);
                updateList();
                dialog.close();
                Notification notification = new Notification("Рецепт удален", 1000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();

            });

            cancel.addClickListener(clickEvent -> {
                dialog.close();
            });

            dialog.open();
        }));

        HorizontalLayout recipeContent = new HorizontalLayout(grid, form);
        recipeContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, recipeContent);

        setSizeFull();

        updateList();
        form.setRecipe(null);

    }

    public void updateList() { grid.setItems(recipeServices.findAll());}
}
