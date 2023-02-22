package com.zabivonikl.vaadindemo.views.inventoryview;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.InventoryService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.personalview.PersonalView;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

@PageTitle("Инвентарь")
@Route(value = "inventory/:itemId?/:action?(edit)", layout = MainLayout.class)
@Tag("inventory-view")
@JsModule("./views/inventory/inventory-view.ts")
@Uses(Icon.class)
public class InventoryView extends LitTemplate implements HasStyle, BeforeEnterObserver {
    private final String ITEM_ID = "itemId";
    private final String ITEM_EDIT_ROUTE_TEMPLATE = "inventory/%s/edit";

    @Id
    private Grid<InventoryItem> grid;

    @Id
    private TextField title;
    @Id
    private TextField vendor;
    @Id
    private TextField category;
    @Id
    private IntegerField piecesLeft;
    @Id
    private NumberField price;

    @Id
    private Button cancel;
    @Id
    private Button save;

    @Id
    private Button delete;

    private final BeanValidationBinder<InventoryItem> binder;

    private InventoryItem item;

    private final InventoryService inventoryService;

    public InventoryView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        addClassNames("personal-view");
        grid.addColumn(InventoryItem::getVendor).setHeader("Производитель").setAutoWidth(true);
        grid.addColumn(InventoryItem::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(InventoryItem::getCategory).setHeader("Категория").setAutoWidth(true);
        grid.addColumn(InventoryItem::getPrice).setHeader("Цена").setAutoWidth(true);
        grid.addColumn(InventoryItem::getPiecesLeft).setHeader("Количество").setAutoWidth(true);

        grid.setItems(query ->
                inventoryService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(),
                        VaadinSpringDataHelpers.toSpringDataSort(query))
                ).stream()
        );
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(ITEM_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(PersonalView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(InventoryItem.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e -> {
            try {
                if (this.item == null)
                    return;

                save.setText("Сохранить");
                binder.writeBean(this.item);
                inventoryService.delete(this.item.getId());
                clearForm();
                refreshGrid();
                Notification.show("Запись удалена");
                UI.getCurrent().navigate(PersonalView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Ошибка при удалении записи. Кто-то уже редактирует данные.");
                n.setPosition(Notification.Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Ошибка удаления данных. Проверьте корректность введёных данных.");
            }
        });

        save.addClickListener(e -> {
            try {
                if (this.item == null) {
                    this.item = new InventoryItem();
                }
                binder.writeBean(this.item);
                inventoryService.update(this.item);
                clearForm();
                refreshGrid();
                Notification.show("Данные обновлены");
                UI.getCurrent().navigate(PersonalView.class);
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

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> itemId = event.getRouteParameters().get(ITEM_ID).map(Long::parseLong);
        if (itemId.isPresent()) {
            Optional<InventoryItem> inventoryItemFormBackend = inventoryService.get(itemId.get());
            if (inventoryItemFormBackend.isPresent()) {
                populateForm(inventoryItemFormBackend.get());
            } else {
                Notification.show(
                        String.format("Запрашиваемый товар не найден, ID = %s", itemId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(PersonalView.class);
            }
        }
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        save.setText("Добавить");
        populateForm(null);
    }

    private void populateForm(InventoryItem value) {
        this.item = value;
        binder.readBean(this.item);
    }
}
