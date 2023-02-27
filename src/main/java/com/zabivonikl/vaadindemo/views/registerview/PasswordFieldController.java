package com.zabivonikl.vaadindemo.views.registerview;

import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class PasswordFieldController implements Validator<String> {
    private final PasswordField password;
    private final PasswordField passwordConfirmation;

    public PasswordFieldController(PasswordField password, PasswordField passwordConfirmation) {
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    private String getPassword() {
        return password.getValue();
    }

    private String getPasswordConfirmation() {
        return passwordConfirmation.getValue();
    }

    @Override
    public ValidationResult apply(String s, ValueContext valueContext) {
        disableInvalidPasswordConfirmation();
        if (getPassword() == null || getPassword().length() < 8)
            return ValidationResult.error("Пароль должен содержать более 8 символов");

        if (getPasswordConfirmation() != null && getPassword().equals(getPasswordConfirmation()))
            return ValidationResult.ok();

        enableInvalidPasswordConfirmation();
        return ValidationResult.error("Пароли не совпадают");
    }

    private void enableInvalidPasswordConfirmation() {
        passwordConfirmation.setInvalid(true);
        passwordConfirmation.setErrorMessage("Пароли не совпадают");
    }

    private void disableInvalidPasswordConfirmation() {
        passwordConfirmation.setInvalid(false);
        passwordConfirmation.setErrorMessage(null);
    }
}
