package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class InventoryItemEditForm extends EditForm<InventoryItem> {
    public final TextField title = new TextField("Название");

    public final TextField vendor = new TextField("Производитель");

    public final TextField category = new TextField("Категория");

    public final IntegerField piecesLeft = new IntegerField("Осталось шт.");

    public final NumberField price = new NumberField("Цена");

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
                .withValidator(
                        value -> !value.isEmpty(),
                        "Поле должно быть заполнено"
                )
                .bind(InventoryItem::getTitle, InventoryItem::setTitle);
        binder.forField(vendor)
                .withValidator(
                        value -> !value.isEmpty(),
                        "Поле должно быть заполнено"
                )
                .bind(InventoryItem::getVendor, InventoryItem::setVendor);
        binder.forField(category)
                .withValidator(
                        value -> !value.isEmpty(),
                        "Поле должно быть заполнено"
                )
                .bind(InventoryItem::getCategory, InventoryItem::setCategory);
        binder.forField(piecesLeft)
                .withValidator(new IntegerRangeValidator(
                        "Количество должно быть больше 0",
                        0,
                        Integer.MAX_VALUE
                ))
                .bind(InventoryItem::getPiecesLeft, InventoryItem::setPiecesLeft);
        binder.forField(price)
                .withValidator(new DoubleRangeValidator(
                        "Цена должна быть больше 0",
                        0d,
                        Double.MAX_VALUE
                ))
                .bind(InventoryItem::getPrice, InventoryItem::setPrice);
    }

}
