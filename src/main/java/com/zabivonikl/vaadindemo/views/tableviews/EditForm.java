package com.zabivonikl.vaadindemo.views.tableviews;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.zabivonikl.vaadindemo.data.EditFormEvents;
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;

public abstract class EditForm<T extends AbstractEntity> extends FormLayout {
    private final Button save = new Button("Сохранить");
    private final Button delete = new Button("Удалить");
    private final Button close = new Button("Отмена");
    @SuppressWarnings("unchecked")
    protected Binder<T> binder = new BeanValidationBinder<>((Class<T>) createEntity().getClass());
    private T entity;

    protected abstract T createEntity();

    protected abstract void configureBinder();

    protected HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new EditFormEvents.DeleteEvent(this)));
        close.addClickListener(event -> fireEvent(new EditFormEvents.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(entity);
            fireEvent(new EditFormEvents.SaveEvent(this));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
        binder.readBean(this.entity);
    }

    @Override
    public <TEvent extends ComponentEvent<?>> Registration addListener(
            Class<TEvent> eventType,
            ComponentEventListener<TEvent> listener
    ) {
        return getEventBus().addListener(eventType, listener);
    }
}
