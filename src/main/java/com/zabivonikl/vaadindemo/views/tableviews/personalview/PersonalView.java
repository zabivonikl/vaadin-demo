package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.PersonalService;
import com.zabivonikl.vaadindemo.security.SecurityService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;

import javax.annotation.security.PermitAll;

@PageTitle("Персонал | Vaadin Demo")
@Route(value = "personal/:personId?", layout = MainLayout.class)
@PermitAll
public class PersonalView extends TableView<Person> {
    public PersonalView(PersonalService personalService, SecurityService securityService) {
        super(personalService, securityService);
    }

    @Override
    protected PersonEditDialog createDialogProto() {
        return new PersonEditDialog();
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
