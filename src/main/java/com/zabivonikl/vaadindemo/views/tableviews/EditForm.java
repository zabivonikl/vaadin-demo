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
    private final Button save = createSaveButton();
    private final Button delete = createDeleteButton();
    private final Button close = craeteCloseButton();
    @SuppressWarnings("unchecked")
    protected Binder<T> binder = new BeanValidationBinder<>((Class<T>) createEntity().getClass());
    private T entity;

    protected abstract T createEntity();

    protected abstract void configureBinder();

    //region Field-components initialization

    private Button createSaveButton() {
        var save = new Button("Сохранить");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave());

        return save;
    }

    private Button createDeleteButton() {
        var delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(event -> fireEvent(new EditFormEvents.DeleteEvent(this)));

        return delete;
    }

    private Button craeteCloseButton() {
        var close = new Button("Отмена");
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        close.addClickShortcut(Key.ESCAPE);
        close.addClickListener(event -> fireEvent(new EditFormEvents.CloseEvent(this)));

        return close;
    }

    //endregion

    private void validateAndSave() {
        try {
            binder.writeBean(entity);
            fireEvent(new EditFormEvents.SaveEvent(this));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    protected HorizontalLayout createButtonsLayout() {
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
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
