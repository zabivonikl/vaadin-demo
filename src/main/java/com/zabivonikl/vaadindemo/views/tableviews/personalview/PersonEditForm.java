package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class PersonEditForm extends EditForm<Person> {
    private final TextField firstName = new TextField("Имя");

    private final TextField lastName = new TextField("Фамилия");

    private final EmailField email = new EmailField("Email");

    private final TextField phone = new TextField("Телефон");

    private final DatePicker dateOfBirth = new DatePicker("Дата рождения");

    private final TextField role = new TextField("Должность");

    public PersonEditForm() {
        add(
                firstName,
                lastName,
                email,
                phone,
                dateOfBirth,
                role,
                createButtonsLayout()
        );
        configureBinder();
    }

    @Override
    protected Person createEntity() {
        return new Person();
    }

    @Override
    protected void configureBinder() {
        binder.forField(firstName)
                .asRequired("Поле должно быть заполнено")
                .bind(Person::getFirstName, Person::setFirstName);
        binder.forField(lastName)
                .asRequired("Поле должно быть заполнено")
                .bind(Person::getLastName, Person::setLastName);
        binder.forField(email)
                .asRequired("Поле должно быть заполнено")
                .withValidator(new EmailValidator("Неверный формат почты"))
                .bind(Person::getEmail, Person::setEmail);
        binder.forField(phone)
                .withValidator(new RegexpValidator(
                        "Неверный формат номера",
                        "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$"
                ))
                .bind(Person::getPhone, Person::setPhone);
        binder.forField(dateOfBirth)
                .bind(Person::getDateOfBirth, Person::setDateOfBirth);
        binder.forField(role)
                .asRequired("Поле должно быть заполнено")
                .bind(Person::getRole, Person::setRole);
    }

}
