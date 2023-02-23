package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class PersonForm extends EditForm {
    private TextField firstName = new TextField("Имя");

    private TextField lastName = new TextField("Фамилия");

    private EmailField email = new EmailField("Email");

    private IntegerField phone = new IntegerField("Телефон");

    private DatePicker dateOfBirth = new DatePicker("Дата рождения");

    private TextField role = new TextField("Должность");

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
