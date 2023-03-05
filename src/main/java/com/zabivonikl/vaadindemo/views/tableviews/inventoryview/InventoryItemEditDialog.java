package com.zabivonikl.vaadindemo.views.tableviews.inventoryview;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.zabivonikl.vaadindemo.data.entity.InventoryItem;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.PersonalService;
import com.zabivonikl.vaadindemo.views.tableviews.EditDialog;


public class InventoryItemEditDialog extends EditDialog<InventoryItem> {
    public final TextField title;

    public final TextField vendor;

    public final TextField category;

    public final IntegerField piecesLeft;

    public final NumberField price;

    public final ComboBox<Person> creator;

    private final PersonalService personalService;

    public InventoryItemEditDialog(PersonalService personalService) {
        this.personalService = personalService;
        this.title = createTitleField();
        this.vendor = createVendorField();
        this.category = createCategoryField();
        this.piecesLeft = createPiecesLeftField();
        this.price = createPriceField();
        this.creator = createComboBox();

        add(
                title,
                vendor,
                category,
                creator,
                price,
                piecesLeft
        );

        configureBinder();
    }

    //region Field-components initialization

    private TextField createTitleField() {
        var field = new TextField("Название");
        field.setSizeFull();
        return field;
    }

    private TextField createVendorField() {
        var field = new TextField("Производитель");
        field.setSizeFull();
        return field;
    }

    private TextField createCategoryField() {
        var field = new TextField("Категория");
        field.setSizeFull();
        return field;
    }

    private IntegerField createPiecesLeftField() {
        var field = new IntegerField("Осталось шт.");
        field.setValue(0);
        field.setMin(0);
        field.setMax(Integer.MAX_VALUE);
        field.setSizeFull();
        field.setErrorMessage("Количество должно быть больше 0");
        return field;
    }

    private NumberField createPriceField() {
        var field = new NumberField("Цена");
        field.setValue(0d);
        field.setMin(0);
        field.setMax(Integer.MAX_VALUE);
        field.setStep(.01);
        field.setSizeFull();
        field.setErrorMessage("Цена должна быть больше 0,00₽");
        return field;
    }

    private ComboBox<Person> createComboBox() {
        ComboBox<Person> comboBox = new ComboBox<>("Добавлен");
        if (personalService != null) comboBox.setItems(personalService);
        comboBox.setItemLabelGenerator(Person::getFullName);
        comboBox.setPlaceholder("Поиск...");
        comboBox.setSizeFull();
        comboBox.setClearButtonVisible(true);
        return comboBox;
    }

    //endregion

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
        binder.forField(creator)
                .bind(InventoryItem::getCreator, InventoryItem::setCreator);
        binder.forField(price)
                .asRequired("Поле должно быть заполнено")
                .bind(InventoryItem::getPrice, InventoryItem::setPrice);
        binder.forField(piecesLeft)
                .asRequired("Поле должно быть заполнено")
                .bind(InventoryItem::getPiecesLeft, InventoryItem::setPiecesLeft);
    }
}
