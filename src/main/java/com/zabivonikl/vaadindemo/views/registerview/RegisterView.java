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
public class RegisterView extends VerticalLayout {
    private final UserService userService;

    private final Binder<User> binder = new BeanValidationBinder<>(User.class);

    private final H1 header = new H1("Регистрация");

    private final TextField login = new TextField("Логин");

    private final PasswordField password = getPasswordField();

    private final PasswordField passwordConfirmation = getPasswordConfirmationField();

    private final Select<Role> role = getRoleSelector();

    private final Button register = getRegisterButton();

    public RegisterView(UserService userService) {
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

    private PasswordField getPasswordField() {
        return new PasswordField("Пароль");
    }

    private PasswordField getPasswordConfirmationField() {
        var passwordField = new PasswordField("Пароль ещё раз");
        passwordField.addValueChangeListener(e -> binder.validate());
        passwordField.setRequired(true);
        return passwordField;
    }

    private Button getRegisterButton() {
        var button = new Button("Зарегистрироваться");
        button.addClickListener(e -> onCreateUser());
        return button;
    }

    private Select<Role> getRoleSelector() {
        var select = new Select<Role>();
        select.setLabel("Роль");
        select.setItems(Role.values());
        select.setPlaceholder("Роль...");
        return select;
    }

    private void configureBinder() {
        binder.forField(login)
                .asRequired("Поле должно быть заполнено")
                .withValidator(userService::isLoginAvailable, "Логин уже занят")
                .bind(User::getLogin, User::setLogin);
        binder.forField(password)
                .asRequired("Поле должно быть заполнено")
                .withValidator(new PasswordFieldController(password, passwordConfirmation))
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
