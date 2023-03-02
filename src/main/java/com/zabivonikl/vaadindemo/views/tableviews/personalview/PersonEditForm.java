package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class PersonEditForm extends EditForm<Person> {
    private final TextField firstName = createFirstName();

    private final TextField lastName = createLastName() ;

    private final EmailField email = createEmail() ;

    private final TextField phone = createPhone() ;

    private final DatePicker dateOfBirth = createDateOfBirth();

    private final TextField role = createRole() ;

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

    //region Field-components initialization

    private TextField createFirstName() {
        return new TextField("Имя");
    }

    private TextField createLastName() {
        return new TextField("Фамилия");
    }

    private EmailField createEmail() {
        return new EmailField("Email");
    }

    private TextField createPhone() {
        return new TextField("Телефон");
    }

    private DatePicker createDateOfBirth() {
        return new DatePicker("Дата рождения");
    }

    private TextField createRole() {
        return new TextField("Должность");
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
