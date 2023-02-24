package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class InventoryItemEditForm extends EditForm<InventoryItem> {

    public final TextField title = new TextField("Название");

    public final TextField vendor = new TextField("Производитель");

    public final TextField category = new TextField("Категория");

    public final TextField piecesLeft = new TextField("Осталось шт.");

    public final TextField price = new TextField("Цена");

    @Override
    protected InventoryItem createEntity() {
        return new InventoryItem();
    }

    public InventoryItemEditForm() {
        super();
        add(
                title,
                vendor,
                category,
                piecesLeft,
                price,
                createButtonsLayout()
        );
        configureBinder();
    }

    @Override
    protected void configureBinder() {
        binder.forField(title)
                .bind(InventoryItem::getTitle, InventoryItem::setTitle);
        binder.forField(vendor)
                .bind(InventoryItem::getVendor, InventoryItem::setVendor);
        binder.forField(category)
                .bind(InventoryItem::getCategory, InventoryItem::setCategory);
        binder.forField(piecesLeft)
                .withConverter(new StringToIntegerConverter("Введённое значение не является числом"))
                .bind(InventoryItem::getPiecesLeft, InventoryItem::setPiecesLeft);
        binder.forField(price)
                .withConverter(new StringToDoubleConverter("Введённое значение не является числом"))
                .bind(InventoryItem::getPrice, InventoryItem::setPrice);
    }

}
