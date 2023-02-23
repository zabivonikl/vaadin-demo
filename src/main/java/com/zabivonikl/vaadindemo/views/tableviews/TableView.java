package com.zabivonikl.vaadindemo.views.tableviews;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;
import com.zabivonikl.vaadindemo.data.service.AbstractService;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

@Uses(Icon.class)
public abstract class TableView<T extends AbstractEntity> extends LitTemplate implements HasStyle, BeforeEnterObserver {
    @Id
    protected Grid<T> grid;

    @Id
    protected Button cancel;
    @Id
    protected Button save;

    @Id
    protected Button delete;

    protected BeanValidationBinder<T> binder;

    protected T entity;

    protected final AbstractService<T> inventoryService;

    protected TableView(AbstractService<T> dataProvider) {
        this.inventoryService = dataProvider;

        initGrid();
        initBinder();
        initCancel();
        initDelete();
        initSave();
    }

    private void initGrid() {
        addGridColumns();
        grid.setItems(query ->
                inventoryService.list(
                        PageRequest.of(
                                query.getPage(), query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query)
                        )
                ).stream()
        );
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(getEditRouteTemplate(), event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(this.getClass());
            }
        });
    }

    protected abstract void addGridColumns();

    protected abstract String getEditRouteTemplate();

    private void initBinder() {
        this.entity = getNewEntity();
        binder = new BeanValidationBinder<T>((Class<T>) entity.getClass());
        binder.bindInstanceFields(this);
        this.entity = null;
    }

    protected abstract T getNewEntity();

    private void initCancel() {
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
    }

    private void initDelete() {
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e -> {
            try {
                deleteEntity();
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Ошибка при удалении записи. Кто-то уже редактирует данные.");
                n.setPosition(Notification.Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Ошибка удаления данных. Проверьте корректность введённых данных.");
            }
        });
    }

    private void deleteEntity() throws ValidationException {
        if (this.entity == null)
            return;

        binder.writeBean(this.entity);
        inventoryService.delete(this.entity.getId());

        clearForm();
        refreshGrid();

        Notification.show("Запись удалена");
        UI.getCurrent().navigate(this.getClass());
    }

    private void initSave() {
        save.addClickListener(e -> {
            try {
                saveEntity();
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Ошибка обновления данных. Кто-то уже редактирует данные.");
                n.setPosition(Notification.Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Ошибка обновления данных. Проверьте корректность введёных данных.");
            }
        });
    }

    private void saveEntity() throws ValidationException {
        if (this.entity == null) this.entity = getNewEntity();

        binder.writeBean(this.entity);
        inventoryService.update(this.entity);

        clearForm();
        refreshGrid();

        Notification.show("Данные обновлены");
        UI.getCurrent().navigate(this.getClass());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> itemId = event
                .getRouteParameters()
                .get(getEntityId())
                .map(Long::parseLong);
        if (itemId.isPresent()) {
            Optional<T> inventoryItemFormBackend = inventoryService.get(itemId.get());
            if (inventoryItemFormBackend.isPresent()) {
                populateForm(inventoryItemFormBackend.get());
            } else {
                Notification.show(
                        String.format("Запрашиваемый объект не найден, ID = %s", itemId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(this.getClass());
            }
        }
    }

    protected abstract String getEntityId();

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(T value) {
        this.entity = value;
        binder.readBean(this.entity);
    }
}
