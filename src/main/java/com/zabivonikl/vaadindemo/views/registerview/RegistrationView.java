package com.zabivonikl.vaadindemo.views.registerview;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.zabivonikl.vaadindemo.data.entity.Role;
import com.zabivonikl.vaadindemo.data.entity.User;
import com.zabivonikl.vaadindemo.data.service.UserService;
import com.zabivonikl.vaadindemo.views.loginview.LoginView;


@Route("register")
@PageTitle("Регистрация | Vaadin Demo")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {
    private final UserService userService;

    private final Binder<User> binder = createBinder();

    private final H1 header = createHeader();

    private final TextField login = createLoginField();

    private final PasswordField password = createPasswordField();

    private final PasswordField passwordConfirmation = createPasswordConfirmationField();

    private final Select<Role> role = createRoleSelector();

    private final Button register = createRegisterButton();

    public RegistrationView(UserService userService) {
        this.userService = userService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(
                header,
                login,
                password,
                passwordConfirmation,
                role,
                register
        );

        configureBinder();
    }

    //region Field-component initialization

    private Binder<User> createBinder() {
        return new BeanValidationBinder<>(User.class);
    }

    private H1 createHeader() {
        return new H1("Регистрация");
    }

    private TextField createLoginField() {
        return new TextField("Логин");
    }

    private PasswordField createPasswordField() {
        return new PasswordField("Пароль");
    }

    private PasswordField createPasswordConfirmationField() {
        var passwordField = new PasswordField("Пароль ещё раз");
        passwordField.addValueChangeListener(e -> binder.validate());
        passwordField.setRequired(true);
        return passwordField;
    }

    private Button createRegisterButton() {
        var button = new Button("Зарегистрироваться");
        button.addClickListener(e -> onCreateUser());
        return button;
    }

    private Select<Role> createRoleSelector() {
        var select = new Select<Role>();
        select.setLabel("Роль");
        select.setItems(Role.values());
        select.setPlaceholder("Роль...");
        return select;
    }

    //endregion

    private void configureBinder() {
        binder.forField(login)
                .asRequired("Поле должно быть заполнено")
                .withValidator(userService::isLoginAvailable, "Логин уже занят")
                .bind(User::getLogin, User::setLogin);
        binder.forField(password)
                .asRequired("Поле должно быть заполнено")
                .withValidator(new PasswordFieldsController(password, passwordConfirmation))
                .bind(User::getPassword, User::setPassword);
        binder.forField(role)
                .asRequired("Поле должно быть заполнено")
                .bind(User::getRole, User::setRole);
        binder.addStatusChangeListener(e -> register.setEnabled(binder.isValid()));
    }

    private void onCreateUser() {
        try {
            var user = new User();
            binder.writeBean(user);
            userService.add(user);
            UI.getCurrent().navigate(LoginView.class);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
