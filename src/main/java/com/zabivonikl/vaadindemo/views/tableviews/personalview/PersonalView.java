package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.PersonService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;

import javax.annotation.security.PermitAll;

@PageTitle("Персонал | Vaadin Demo")
@Route(value = "personal", layout = MainLayout.class)
@PermitAll
public class PersonalView extends TableView<Person> {
    public PersonalView(PersonService personService) {
        super(personService);
    }

    @Override
    protected PersonEditForm createForm() {
        return new PersonEditForm();
    }

    @Override
    protected void addGridColumns() {
        grid.addColumn(Person::getFirstName).setHeader("Имя");
        grid.addColumn(Person::getLastName).setHeader("Фамилия");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getPhone).setHeader("Телефон");
        grid.addColumn(Person::getDateOfBirth).setHeader("Дата рождения");
        grid.addColumn(Person::getRole).setHeader("Должность");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }
}
