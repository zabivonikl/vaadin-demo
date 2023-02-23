package com.zabivonikl.vaadindemo.views.tableviews;

import com.vaadin.flow.component.Component;
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
    protected Grid<T> grid = new Grid<>();

    private final TextField filterText = new TextField();

    private EditForm form;

    private final AbstractService<T> service;

    public TableView(AbstractService<T> service) {
        this.service = service;

        setPadding(true);
        addClassName("list-view");

        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
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
    }

    protected abstract EditForm createForm();

    private void configureGrid() {
        grid.setSizeFull();
        addGridColumns();
    }

    protected abstract void addGridColumns();

    private HorizontalLayout getToolbar() {
        initializeFilterText();

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void initializeFilterText() {
        filterText.setPlaceholder("Поиск...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }
}
