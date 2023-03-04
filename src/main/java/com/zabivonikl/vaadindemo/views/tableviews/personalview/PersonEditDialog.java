package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.views.tableviews.EditDialog;

public class PersonEditDialog extends EditDialog<Person> {
    private final TextField firstName = createFirstName();

    private final TextField lastName = createLastName();

    private final EmailField email = createEmail();

    private final TextField phone = createPhone();

    private final DatePicker dateOfBirth = createDateOfBirth();

    private final TextField role = createRole();

    public PersonEditDialog() {
        add(
                firstName,
                lastName,
                email,
                phone,
                dateOfBirth,
                role
        );
        configureBinder();
    }

    //region Field-components initialization

    private TextField createFirstName() {
        var field = new TextField("Имя");
        field.setSizeFull();
        return field;
    }

    private TextField createLastName() {
        TextField field = new TextField("Фамилия");
        field.setSizeFull();
        return field;
    }

    private EmailField createEmail() {
        EmailField field = new EmailField("Email");
        field.setSizeFull();
        return field;
    }

    private TextField createPhone() {
        TextField field = new TextField("Телефон");
        field.setSizeFull();
        return field;
    }

    private DatePicker createDateOfBirth() {
        DatePicker picker = new DatePicker("Дата рождения");
        picker.setSizeFull();
        return picker;
    }

    private TextField createRole() {
        TextField field = new TextField("Должность");
        field.setSizeFull();
        return field;
    }

    //endregion

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
                .withValidator(createPhoneValidator())
                .bind(Person::getPhone, Person::setPhone);
        binder.forField(dateOfBirth)
                .bind(Person::getDateOfBirth, Person::setDateOfBirth);
        binder.forField(role)
                .asRequired("Поле должно быть заполнено")
                .bind(Person::getRole, Person::setRole);
    }

    private Validator<String> createPhoneValidator() {
        var regex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        return new RegexpValidator("Неверный формат номера", regex);
    }
}
