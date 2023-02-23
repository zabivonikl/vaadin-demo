package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.InventoryService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;

@PageTitle("Инвентарь | Vaadin Demo")
@Route(value = "inventory/:itemId?/:action?(edit)", layout = MainLayout.class)
@Tag("inventory-view")
@JsModule("./views/inventory/inventory-view.ts")
public class InventoryView extends TableView<InventoryItem> {
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

    public InventoryView(InventoryService inventoryService) {
        super(inventoryService);
        addClassNames("inventory-view");
    }

    @Override
    protected void addGridColumns() {
        grid.addColumn(InventoryItem::getVendor).setHeader("Производитель").setAutoWidth(true);
        grid.addColumn(InventoryItem::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(InventoryItem::getCategory).setHeader("Категория").setAutoWidth(true);
        grid.addColumn(InventoryItem::getFormatedPrice).setHeader("Цена").setAutoWidth(true);
        grid.addColumn(InventoryItem::getPiecesLeft).setHeader("Количество").setAutoWidth(true);
    }

    @Override
    protected String getEditRouteTemplate() {
        return ITEM_EDIT_ROUTE_TEMPLATE;
    }

    @Override
    protected InventoryItem getNewEntity() {
        return new InventoryItem();
    }

    @Override
    protected String getEntityId() {
        return ITEM_ID;
    }
}
