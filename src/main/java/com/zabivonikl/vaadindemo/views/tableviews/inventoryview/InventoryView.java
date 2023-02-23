package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.InventoryService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;

@PageTitle("Инвентарь | Vaadin Demo")
@Route(value = "inventory", layout = MainLayout.class)
public class InventoryView extends TableView<InventoryItem> {

    public InventoryView(InventoryService inventoryService) {
        super(inventoryService);
        addClassNames("inventory-view");
    }

    @Override
    protected EditForm createForm() {
        return new InventoryItemEditForm();
    }

    @Override
    protected void addGridColumns() {
        grid.addColumn(InventoryItem::getVendor).setHeader("Производитель").setAutoWidth(true);
        grid.addColumn(InventoryItem::getTitle).setHeader("Название").setAutoWidth(true);
        grid.addColumn(InventoryItem::getCategory).setHeader("Категория").setAutoWidth(true);
        grid.addColumn(InventoryItem::getFormatedPrice).setHeader("Цена").setAutoWidth(true);
        grid.addColumn(InventoryItem::getPiecesLeft).setHeader("Количество").setAutoWidth(true);
    }
}
