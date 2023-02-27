package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class InventoryItemEditForm extends EditForm<InventoryItem> {
    public final TextField title = new TextField("Название");

    public final TextField vendor = new TextField("Производитель");

    public final TextField category = new TextField("Категория");

    public final IntegerField piecesLeft = getPiecesLeftField();

    public final NumberField price = getPriceField();

    public InventoryItemEditForm() {
        add(
                title,
                vendor,
                category,
                price,
                piecesLeft,
                createButtonsLayout()
        );
        configureBinder();
    }

    private IntegerField getPiecesLeftField() {
        IntegerField field = new IntegerField("Осталось шт.");
        field.setMin(0);
        field.setMax(Integer.MAX_VALUE);
        field.setErrorMessage("Количество должно быть больше 0");
        return field;
    }

    private NumberField getPriceField() {
        NumberField field = new NumberField("Цена");
        field.setMin(0);
        field.setMax(Integer.MAX_VALUE);
        field.setStep(.01);
        field.setErrorMessage("Цена должна быть больше 0,00₽");
        return field;
    }

    @Override
    protected InventoryItem createEntity() {
        return new InventoryItem();
    }

    @Override
    protected void configureBinder() {
        binder.forField(title)
                .asRequired("Поле должно быть заполнено")
                .bind(InventoryItem::getTitle, InventoryItem::setTitle);
        binder.forField(vendor)
                .asRequired("Поле должно быть заполнено")
                .bind(InventoryItem::getVendor, InventoryItem::setVendor);
        binder.forField(category)
                .asRequired("Поле должно быть заполнено")
                .bind(InventoryItem::getCategory, InventoryItem::setCategory);
        binder.forField(price)
                .asRequired("Поле должно быть заполнено")
                .bind(InventoryItem::getPrice, InventoryItem::setPrice);
        binder.forField(piecesLeft)
                .asRequired("Поле должно быть заполнено")
                .bind(InventoryItem::getPiecesLeft, InventoryItem::setPiecesLeft);
    }
}
