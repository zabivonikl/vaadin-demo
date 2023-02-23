package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.PersonService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;

@PageTitle("Персонал | Vaadin Demo")
@Route(value = "personal", layout = MainLayout.class)
public class PersonalView extends TableView<Person> {

    private TextField firstName;

    private TextField lastName;

    private TextField email;

    private TextField phone;

    private DatePicker dateOfBirth;

    private TextField role;

    public PersonalView(PersonService personService) {
        super(personService);
        addClassNames("personal-view");
    }

    @Override
    protected EditForm createForm() {
        return new PersonForm();
    }

    @Override
    protected void addGridColumns() {
        grid.addColumn(Person::getFirstName).setHeader("Имя").setAutoWidth(true);
        grid.addColumn(Person::getLastName).setHeader("Фамилия").setAutoWidth(true);
        grid.addColumn(Person::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(Person::getPhone).setHeader("Телефон").setAutoWidth(true);
        grid.addColumn(Person::getDateOfBirth).setHeader("Дата рождения").setAutoWidth(true);
        grid.addColumn(Person::getRole).setHeader("Должность").setAutoWidth(true);
    }
}
