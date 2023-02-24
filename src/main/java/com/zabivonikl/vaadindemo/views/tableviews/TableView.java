package com.zabivonikl.vaadindemo.views.tableviews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.service.AbstractService;

@Uses(Icon.class)
public abstract class TableView<T extends AbstractEntity> extends VerticalLayout {
    private final TextField filterText = new TextField();
    protected final AbstractService<T> service;
    protected Grid<T> grid = new Grid<>();
    private EditForm<T> form;

    public TableView(AbstractService<T> service) {
        this.service = service;

        setPadding(true);
        addClassName("list-view");

        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = createForm();
        form.setWidth("25em");

//        form.addListener(EditForm.SaveEvent.class, this::saveEntity);
//        form.addListener(EditForm.DeleteEvent.class, this::deleteEntity);
//        form.addListener(EditForm.CloseEvent.class, e -> closeEditor());
    }

    protected abstract EditForm<T> createForm();

    protected void saveEntity(EditForm.SaveEvent<T> event) {
        service.add(event.getEntity());
        updateList();
        closeEditor();
    }

    private void deleteEntity(EditForm.DeleteEvent<T> event) {
        service.delete(event.getEntity());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        addGridColumns();
        grid.asSingleSelect()
                .addValueChangeListener(event -> editEntity(event.getValue()));
    }

    protected abstract void addGridColumns();

    private HorizontalLayout getToolbar() {
        initializeFilterText();
        HorizontalLayout toolbar = new HorizontalLayout(filterText, getAddButton());
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void initializeFilterText() {
        filterText.setPlaceholder("Поиск...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private Component getAddButton() {
        Button button = new Button("Добавить");
        button.addClickListener(click -> addEntity());

        return button;
    }

    private void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

    public void editEntity(T entity) {
        if (entity == null) {
            closeEditor();
        } else {
            form.setEntity(entity);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setEntity(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addEntity() {
        grid.asSingleSelect().clear();
        editEntity(form.createEntity());
    }
}
