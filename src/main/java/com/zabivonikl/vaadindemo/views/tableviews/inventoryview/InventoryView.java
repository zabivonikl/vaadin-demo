package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.service.InventoryService;
import com.zabivonikl.vaadindemo.data.service.dataproviders.InventoryItemsProvider;
import com.zabivonikl.vaadindemo.security.SecurityService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;
import com.zabivonikl.vaadindemo.views.tableviews.personalview.PersonalView;

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
        grid.addColumn(this.getCreatorRenderer()).setHeader("Добавил");
        grid.addColumn(this::getFormattedPrice).setHeader("Цена");
        grid.addColumn(InventoryItem::getPiecesLeft).setHeader("Количество");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        return grid;
    }

    private ComponentRenderer<Component, InventoryItem> getCreatorRenderer() {
        return new ComponentRenderer<>(item -> {
            var creator = item.getCreator();
            if (creator == null)
                return new Paragraph("—");
            else
                return new RouterLink(
                        creator.getFullName(),
                        PersonalView.class,
                        new RouteParameters("personId", creator.getId().toString())
                );
        });
    }

    private String getFormattedPrice(InventoryItem item) {
        return String.format("%.2f₽", item.getPrice());
    }
}
