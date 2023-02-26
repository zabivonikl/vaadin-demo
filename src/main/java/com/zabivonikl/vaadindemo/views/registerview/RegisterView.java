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

    private final PasswordField password = new PasswordField("Пароль");

    private final Select<String> role = new Select<>();

    private final Button register = new Button("Зарегистрироваться");

    public RegisterView(UserService userService) {
        this.userService = userService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(
                header,
                login,
                password,
                role,
                register
        );

        configureBinder();
        configureRegisterButton();
        configureRoleSelector();
    }

    private void configureBinder() {
        binder.forField(login)
                .withValidator(value -> !value.isEmpty(), "Поле должно быть заполнено")
                .bind(User::getLogin, User::setLogin);
        binder.forField(password)
                .withValidator(value -> !value.isEmpty(), "Поле должно быть заполнено")
                .bind(User::getPassword, User::setPassword);
        binder.forField(role)
                .bind(User::getRoleString, User::setRoleString);
        binder.addStatusChangeListener(e -> register.setEnabled(binder.isValid()));
    }

    private void configureRegisterButton() {
        register.addClickListener(e -> onCreateUser());
    }

    private void configureRoleSelector() {
        role.setLabel("Роль");
        role.setItems(Role.getNames());
        role.setPlaceholder("Роль...");
    }

    private void onCreateUser() {
        try {
            User user = new User();
            binder.writeBean(user);
            userService.add(user);
            UI.getCurrent().navigate(LoginView.class);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
