package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.InventoryService;
import com.zabivonikl.vaadindemo.data.service.dataproviders.InventoryItemsProvider;
import com.zabivonikl.vaadindemo.security.SecurityService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;

import javax.annotation.security.PermitAll;

@PageTitle("Инвентарь | Vaadin Demo")
@Route(value = "inventory", layout = MainLayout.class)
@PermitAll
public class InventoryView extends TableView<InventoryItem> {
    public InventoryView(InventoryService inventoryService, SecurityService securityService) {
        super(inventoryService, securityService);
    }

    @Override
    protected InventoryItemEditDialog createDialogProto() {
        return new InventoryItemEditDialog();
    }

    @Override
    protected ConfigurableFilterDataProvider<InventoryItem, Void, String> getDataProvider() {
        return new InventoryItemsProvider(entityService).withConfigurableFilter();
    }

    @Override
    protected Grid<InventoryItem> createGrid() {
        var grid = super.createGrid();

        grid.addColumn(InventoryItem::getVendor).setHeader("Производитель");
        grid.addColumn(InventoryItem::getTitle).setHeader("Название");
        grid.addColumn(InventoryItem::getCategory).setHeader("Категория");
        grid.addColumn(this::getFormattedPrice).setHeader("Цена");
        grid.addColumn(InventoryItem::getPiecesLeft).setHeader("Количество");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        return grid;
    }

    private String getFormattedPrice(InventoryItem item) {
        return String.format("%.2f₽", item.getPrice());
    }
}
