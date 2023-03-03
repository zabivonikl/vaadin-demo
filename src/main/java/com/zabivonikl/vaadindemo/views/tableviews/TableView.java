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
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.zabivonikl.vaadindemo.data.EditFormEvents;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.entity.Role;
import com.zabivonikl.vaadindemo.data.service.AbstractService;
import com.zabivonikl.vaadindemo.security.SecurityService;

@Uses(Icon.class)
public abstract class TableView<T extends AbstractEntity> extends VerticalLayout {
    protected final AbstractService<T> entityService;
    private final SecurityService securityService;
    private final ConfigurableFilterDataProvider<T, Void, String> dataProvider;
    private final TextField filterText;
    private final Grid<T> grid;
    private final EditForm<T> form;

    public TableView(AbstractService<T> entityService, SecurityService securityService) {
        this.entityService = entityService;
        this.securityService = securityService;
        this.dataProvider = getDataProvider();

        this.filterText = createFilterText();
        this.grid = createGrid();
        this.form = createForm();

        setPadding(true);
        addClassName("list-view");

        setSizeFull();

        add(createToolbar(), createContent());
        closeEditor();
    }

    protected abstract ConfigurableFilterDataProvider<T, Void, String> getDataProvider();

    //region Field-components initialization

    private TextField createFilterText() {
        TextField filterText = new TextField();
        filterText.setPlaceholder("Поиск...");
        filterText.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> dataProvider.setFilter(e.getValue()));

        return filterText;
    }

    protected Grid<T> createGrid() {
        var grid = new Grid<T>();
        grid.setSizeFull();
        if (isUserAdmin())
            grid.asSingleSelect().addValueChangeListener(event -> editEntity(event.getValue()));
        grid.setItems(dataProvider);

        return grid;
    }

    private EditForm<T> createForm() {
        var form = createFormPrototype();
        form.setWidth("25em");

        form.addListener(EditFormEvents.SaveEvent.class, this::saveEntity);
        form.addListener(EditFormEvents.DeleteEvent.class, this::deleteEntity);
        form.addListener(EditFormEvents.CloseEvent.class, e -> closeEditor());

        return form;
    }

    protected abstract EditForm<T> createFormPrototype();

    //endregion

    //region Anonymous components initialization

    private HorizontalLayout createToolbar() {
        createFilterText();
        var toolbar = isUserAdmin() ?
                new HorizontalLayout(filterText, createAddButton()) :
                new HorizontalLayout(filterText);

        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private Component createAddButton() {
        var button = new Button("Добавить");
        button.addClickListener(click -> addEntity());

        return button;
    }

    private Component createContent() {
        var content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    //endregion

    //region Form logic

    private void addEntity() {
        grid.asSingleSelect().clear();
        editEntity(form.createEntity());
    }

    protected void saveEntity(EditFormEvents.SaveEvent event) {
        if (!isUserAdmin()) return;
        dataProvider.refreshAll();
        closeEditor();
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

    private void deleteEntity(EditFormEvents.DeleteEvent event) {
        if (!isUserAdmin()) return;
        entityService.delete(form.getEntity());
        dataProvider.refreshAll();
        closeEditor();
    }

    private void closeEditor() {
        form.setEntity(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    //endregion

    private boolean isUserAdmin() {
        var role = securityService.getAuthenticatedUser()
                .getAuthorities()
                .iterator().next()
                .getAuthority();

        return Role.valueOf(role) == Role.Admin;
    }
}
