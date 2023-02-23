package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class PersonForm extends EditForm {
    private final TextField firstName = new TextField("Имя");

    private final TextField lastName = new TextField("Фамилия");

    private final EmailField email = new EmailField("Email");

    private final IntegerField phone = new IntegerField("Телефон");

    private final DatePicker dateOfBirth = new DatePicker("Дата рождения");

    private final TextField role = new TextField("Должность");

    public PersonForm() {
        super();
        add(
                firstName,
                lastName,
                email,
                phone,
                dateOfBirth,
                role,
                createButtonsLayout()
        );
    }
}
