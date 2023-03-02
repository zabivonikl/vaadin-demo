package com.zabivonikl.vaadindemo.views.loginview;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.zabivonikl.vaadindemo.views.registerview.RegistrationView;

@Route("login")
@PageTitle("Вход | Vaadin Demo")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private final H1 header = createHeader();
    private final LoginForm login = createLoginForm();
    private final Button registration = createRegistrationButton();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(header, login, registration);
    }

    //region Field-component initialization

    private H1 createHeader() {
        return new H1("Vaadin Demo");
    }

    private LoginForm createLoginForm() {
        var loginForm = new LoginForm();
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);

        return loginForm;
    }

    private Button createRegistrationButton() {
        var registration = new Button(
                "Регистрация",
                e -> UI.getCurrent()
                        .navigate(RegistrationView.class)
        );
        registration.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        registration.addClassName(LumoUtility.Margin.Top.NONE);

        return registration;
    }

    //endregion

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}