package com.zabivonikl.vaadindemo.views.tableviews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.zabivonikl.vaadindemo.data.EditFormEvents;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.entity.Role;
import com.zabivonikl.vaadindemo.data.service.AbstractService;
import com.zabivonikl.vaadindemo.security.SecurityService;

@Uses(Icon.class)
public abstract class TableView<T extends AbstractEntity> extends VerticalLayout {
    protected final AbstractService<T> entityService;
    private final TextField filterText = new TextField();
    private final SecurityService securityService;
    protected Grid<T> grid = new Grid<>();
    private EditForm<T> form;

    public TableView(AbstractService<T> entityService, SecurityService securityService) {
        this.entityService = entityService;
        this.securityService = securityService;

        setPadding(true);
        addClassName("list-view");

        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private boolean isUserAdmin() {
        String role = securityService.getAuthenticatedUser()
                .getAuthorities()
                .iterator().next()
                .getAuthority();

        return Role.valueOf(role) == Role.Admin;
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

        form.addListener(EditFormEvents.SaveEvent.class, this::saveEntity);
        form.addListener(EditFormEvents.DeleteEvent.class, this::deleteEntity);
        form.addListener(EditFormEvents.CloseEvent.class, e -> closeEditor());
    }

    protected abstract EditForm<T> createForm();

    protected void saveEntity(EditFormEvents.SaveEvent event) {
        if (!isUserAdmin()) return;
        entityService.add(form.getEntity());
        updateList();
        closeEditor();
    }

    private void deleteEntity(EditFormEvents.DeleteEvent event) {
        if (!isUserAdmin()) return;
        entityService.delete(form.getEntity());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        addGridColumns();
        if (isUserAdmin())
            grid.asSingleSelect()
                    .addValueChangeListener(event -> editEntity(event.getValue()));
    }

    protected abstract void addGridColumns();

    private HorizontalLayout getToolbar() {
        initializeFilterText();
        HorizontalLayout toolbar = isUserAdmin() ?
                new HorizontalLayout(filterText, getAddButton()) :
                new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private void initializeFilterText() {
        filterText.setPlaceholder("Поиск...");
        filterText.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
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
        grid.setItems(entityService.findAll(filterText.getValue()));
    }

    public void editEntity(T entity) {
        if (!isUserAdmin() || entity == null) {
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
