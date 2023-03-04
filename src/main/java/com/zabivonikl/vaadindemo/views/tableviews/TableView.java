package com.zabivonikl.vaadindemo.views.tableviews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouteParameters;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.entity.Role;
import com.zabivonikl.vaadindemo.data.service.AbstractService;
import com.zabivonikl.vaadindemo.security.SecurityService;

import java.util.UUID;

public abstract class TableView<T extends AbstractEntity> extends VerticalLayout implements BeforeEnterObserver {
    protected final AbstractService<T> entityService;
    private final Grid<T> grid;
    private final SecurityService securityService;
    private final ConfigurableFilterDataProvider<T, Void, String> dataProvider;
    private final TextField filterText;
    private EditDialog<T> dialog;

    public TableView(AbstractService<T> entityService, SecurityService securityService) {
        this.entityService = entityService;
        this.securityService = securityService;
        this.dataProvider = getDataProvider();

        this.filterText = createFilterText();
        this.grid = createGrid();
        this.dialog = createDialog();

        setPadding(true);

        setSizeFull();

        add(
                createToolbar(),
                createContent()
        );
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

    private EditDialog<T> createDialog() {
        var dialog = createDialogProto();
        dialog.addListener(EditFormEvents.SaveEvent.class, this::saveEntity);
        dialog.addListener(EditFormEvents.DeleteEvent.class, this::deleteEntity);

        return dialog;
    }

    protected abstract EditDialog<T> createDialogProto();

    protected void resetDialog() {
        this.dialog = createDialog();
    }

    //endregion

    //region Anonymous components initialization

    private HorizontalLayout createToolbar() {
        return isUserAdmin() ?
                new HorizontalLayout(filterText, createAddButton()) :
                new HorizontalLayout(filterText);
    }

    private Component createAddButton() {
        var button = new Button("Добавить");
        button.addClickListener(click -> addEntity());

        return button;
    }

    private Component createContent() {
        return grid;
    }

    //endregion

    //region Form logic

    private void addEntity() {
        grid.asSingleSelect().clear();
        editEntity(dialog.createEntity());
    }

    protected void saveEntity(EditFormEvents.SaveEvent event) {
        if (!isUserAdmin())
            return;

        var entityToSave = dialog.getEntity();
        if (!entityService.contains(entityToSave)) {
            entityService.update(entityToSave);
            dataProvider.refreshAll();
        } else {
            entityService.update(entityToSave);
            dataProvider.refreshItem(entityToSave);
        }

        dialog.close();
    }

    public void editEntity(T entity) {
        if (!isUserAdmin() || entity == null) {
            dialog.close();
        } else {
            dialog.open();
            dialog.setEntity(entity);
        }
    }

    private void deleteEntity(EditFormEvents.DeleteEvent event) {
        if (!isUserAdmin()) return;
        var entityToDelete = dialog.getEntity();
        entityService.delete(entityToDelete);
        dataProvider.refreshItem(entityToDelete);
        dialog.close();
    }

    //endregion

    private boolean isUserAdmin() {
        var role = securityService.getAuthenticatedUser()
                .getAuthorities()
                .iterator().next()
                .getAuthority();

        return Role.valueOf(role) == Role.Admin;
    }

    //region Row selection

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        try {
            var parameterName = checkEventAndGetIdParam(beforeEnterEvent);
            var uuid = getIdParamValue(beforeEnterEvent.getRouteParameters(), parameterName);
            grid.select(entityService.findById(uuid));
        } catch (Exception ignored) {
        }
    }

    private String checkEventAndGetIdParam(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.isRefreshEvent())
            return null;

        return beforeEnterEvent
                .getRouteParameters()
                .getParameterNames()
                .stream()
                .filter(param -> param.contains("Id"))
                .findFirst()
                .orElse(null);
    }

    private UUID getIdParamValue(RouteParameters params, String parameterName) {
        if (parameterName == null)
            throw new IllegalArgumentException();

        var parameterValue = params.get(parameterName);
        if (parameterValue.isEmpty())
            throw new NullPointerException();
        return UUID.fromString(parameterValue.get());
    }

    //endregion
}
