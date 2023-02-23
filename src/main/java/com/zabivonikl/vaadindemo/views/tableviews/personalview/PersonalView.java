package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.PersonService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import com.zabivonikl.vaadindemo.views.tableviews.TableView;

@PageTitle("Персонал | Vaadin Demo")
@Route(value = "personal/:personId?/:action?(edit)", layout = MainLayout.class)
@Tag("personal-view")
@JsModule("./views/personal/personal-view.ts")
public class PersonalView extends TableView<Person> {

    private final String PERSON_ID = "personId";
    private final String PERSON_EDIT_ROUTE_TEMPLATE = "personal/%s/edit";

    @Id
    private TextField firstName;
    @Id
    private TextField lastName;
    @Id
    private TextField email;
    @Id
    private TextField phone;
    @Id
    private DatePicker dateOfBirth;
    @Id
    private TextField role;

    public PersonalView(PersonService personService) {
        super(personService);
        addClassNames("personal-view");
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

    @Override
    protected String getEditRouteTemplate() {
        return PERSON_EDIT_ROUTE_TEMPLATE;
    }

    @Override
    protected Person getNewEntity() {
        return new Person();
    }

    @Override
    protected String getEntityId() {
        return PERSON_ID;
    }
}
