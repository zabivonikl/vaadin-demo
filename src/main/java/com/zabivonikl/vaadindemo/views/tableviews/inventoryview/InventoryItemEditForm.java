package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class InventoryItemEditForm extends EditForm {

    public final TextField title = new TextField("Название");

    public final TextField vendor = new TextField("Производитель");

    public final TextField category = new TextField("Категория");

    public final IntegerField piecesLeft = new IntegerField("Осталось шт.");

    public final NumberField price = new NumberField("Цена");

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
    }
}
