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
import com.zabivonikl.vaadindemo.data.entity.AbstractEntity;

public abstract class EditForm<T extends AbstractEntity> extends FormLayout {
    private T entity;

    @SuppressWarnings("unchecked")
    protected Binder<T> binder = new BeanValidationBinder<>((Class<T>) createEntity().getClass());

    private final Button save = new Button("Сохранить");

    private final Button delete = new Button("Удалить");

    private final Button close = new Button("Отмена");

    protected abstract T createEntity();

    protected abstract void configureBinder();

    public void setEntity(T entity) {
        this.entity = entity;
        binder.readBean(this.entity);
    }

    protected HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent<>(this, entity)));
        close.addClickListener(event -> fireEvent(new CloseEvent<>(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(entity);
            fireEvent(new SaveEvent<>(this, entity));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class EditFormEvent<TEntity extends AbstractEntity> extends ComponentEvent<EditForm<TEntity>> {
        private final TEntity entity;

        protected EditFormEvent(EditForm<TEntity> source, TEntity entity) {
            super(source, false);
            this.entity = entity;
        }

        public TEntity getEntity() {
            return entity;
        }
    }

    public static class SaveEvent<TEntity extends AbstractEntity> extends EditFormEvent<TEntity> {
        SaveEvent(EditForm<TEntity> source, TEntity entity) {
            super(source, entity);
        }
    }

    public static class DeleteEvent<TEntity extends AbstractEntity> extends EditFormEvent<TEntity> {
        DeleteEvent(EditForm<TEntity> source, TEntity entity) {
            super(source, entity);
        }
    }

    public static class CloseEvent<TEntity extends AbstractEntity> extends EditFormEvent<TEntity> {
        CloseEvent(EditForm<TEntity> source) {
            super(source, null);
        }
    }

    @Override
    public <TEvent extends ComponentEvent<?>> Registration addListener(
            Class<TEvent> eventType,
            ComponentEventListener<TEvent> listener
    ) {
        return getEventBus().addListener(eventType, listener);
    }
}
