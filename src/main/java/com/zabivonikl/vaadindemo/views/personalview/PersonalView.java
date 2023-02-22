package com.zabivonikl.vaadindemo.views.personalview;

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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.zabivonikl.vaadindemo.data.entity.Person;
import com.zabivonikl.vaadindemo.data.service.PersonService;
import com.zabivonikl.vaadindemo.views.MainLayout;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Персонал")
@Route(value = "personal/:personId?/:action?(edit)", layout = MainLayout.class)
@Tag("personal-view")
@JsModule("./views/personal/personal-view.ts")
@Uses(Icon.class)
public class PersonalView extends LitTemplate implements HasStyle, BeforeEnterObserver {

    private final String PERSON_ID = "personId";
    private final String PERSON_EDIT_ROUTE_TEMPLATE = "personal/%s/edit";

    @Id
    private Grid<Person> grid;

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

    @Id
    private Button cancel;
    @Id
    private Button save;

    @Id
    private Button delete;

    private BeanValidationBinder<Person> binder;

    private Person person;

    private final PersonService personService;

    public PersonalView(PersonService personService) {
        this.personService = personService;
        addClassNames("personal-view");
        grid.addColumn(Person::getFirstName).setHeader("Имя").setAutoWidth(true);
        grid.addColumn(Person::getLastName).setHeader("Фамилия").setAutoWidth(true);
        grid.addColumn(Person::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(Person::getPhone).setHeader("Телефон").setAutoWidth(true);
        grid.addColumn(Person::getDateOfBirth).setHeader("Дата рождения").setAutoWidth(true);
        grid.addColumn(Person::getRole).setHeader("Должность").setAutoWidth(true);

        grid.setItems(query -> personService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream()
        );
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(PersonalView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Person.class);

        // Bind fields. This is where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e -> {
            try {
                if (this.person == null)
                    return;

                binder.writeBean(this.person);
                personService.delete(this.person.getId());
                clearForm();
                refreshGrid();
                Notification.show("Запись удалена");
                UI.getCurrent().navigate(PersonalView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Ошибка при удалении записи. Кто-то уже редактирует данные.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Ошибка удаления данных. Проверьте корректность введёных данных.");
            }
        });

        save.addClickListener(e -> {
            try {
                if (this.person == null) {
                    this.person = new Person();
                }
                binder.writeBean(this.person);
                personService.update(this.person);
                clearForm();
                refreshGrid();
                Notification.show("Данные обновлены");
                UI.getCurrent().navigate(PersonalView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Ошибка обновления данных. Кто-то уже редактирует данные.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Ошибка обновления данных. Проверьте корректность введёных данных.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> personId = event.getRouteParameters().get(PERSON_ID).map(Long::parseLong);
        if (personId.isPresent()) {
            Optional<Person> personFromBackend = personService.get(personId.get());
            if (personFromBackend.isPresent()) {
                populateForm(personFromBackend.get());
            } else {
                Notification.show(
                        String.format("Запрашиваемый работник не найден, ID = %s", personId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(PersonalView.class);
            }
        }
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Person value) {
        this.person = value;
        binder.readBean(this.person);

    }
}
