package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.PersonService;
import com.zabivonikl.vaadindemo.data.service.dataproviders.PersonsProvider;
import com.zabivonikl.vaadindemo.security.SecurityService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;

import javax.annotation.security.PermitAll;

@PageTitle("Персонал | Vaadin Demo")
@Route(value = "personal", layout = MainLayout.class)
@PermitAll
public class PersonalView extends TableView<Person> {
    public PersonalView(PersonService personService, SecurityService securityService) {
        super(personService, securityService);
    }

    @Override
    protected PersonEditForm createFormPrototype() {
        return new PersonEditForm();
    }

    @Override
    protected ConfigurableFilterDataProvider<Person, Void, String> getDataProvider() {
        return new PersonsProvider(entityService).withConfigurableFilter();
    }

    @Override
    protected Grid<Person> createGrid() {
        var grid = super.createGrid();

        grid.addColumn(Person::getFirstName).setHeader("Имя");
        grid.addColumn(Person::getLastName).setHeader("Фамилия");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getPhone).setHeader("Телефон");
        grid.addColumn(Person::getDateOfBirth).setHeader("Дата рождения");
        grid.addColumn(Person::getRole).setHeader("Должность");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        return grid;
    }
}
