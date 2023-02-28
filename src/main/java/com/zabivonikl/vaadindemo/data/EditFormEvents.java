package com.zabivonikl.vaadindemo.data;

import com.vaadin.flow.component.ComponentEvent;
import com.zabivonikl.vaadindemo.views.tableviews.EditForm;

public class EditFormEvents {
    public abstract static class EditFormEvent extends ComponentEvent<EditForm<?>> {
        protected EditFormEvent(EditForm source) {
            super(source, false);
        }
    }

    public static class SaveEvent extends EditFormEvent {
        public SaveEvent(EditForm source) {
            super(source);
        }
    }

    public static class DeleteEvent extends EditFormEvent {
        public DeleteEvent(EditForm source) {
            super(source);
        }
    }

    public static class CloseEvent extends EditFormEvent {
        public CloseEvent(EditForm source) {
            super(source);
        }
    }
}
