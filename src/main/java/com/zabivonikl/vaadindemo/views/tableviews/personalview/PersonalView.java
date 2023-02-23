package com.zabivonikl.vaadindemo.views.tableviews.personalview;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.PersonService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import java.util.Optional;

import com.zabivonikl.vaadindemo.views.tableviews.TableView;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Персонал | Vaadin Demo")
@Route(value = "personal/:personId?/:action?(edit)", layout = MainLayout.class)
@Tag("personal-view")
@JsModule("./views/personal/personal-view.ts")
@Uses(Icon.class)
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
