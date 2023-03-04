package com.zabivonikl.vaadindemo.data;

import com.vaadin.flow.component.ComponentEvent;
import com.zabivonikl.vaadindemo.views.tableviews.EditDialog;

public class EditFormEvents {
    public abstract static class EditFormEvent extends ComponentEvent<EditDialog<?>> {
        protected EditFormEvent(EditDialog source) {
            super(source, false);
        }
    }

    public static class SaveEvent extends EditFormEvent {
        public SaveEvent(EditDialog source) {
            super(source);
        }
    }

    public static class DeleteEvent extends EditFormEvent {
        public DeleteEvent(EditDialog source) {
            super(source);
        }
    }

    public static class CloseEvent extends EditFormEvent {
        public CloseEvent(EditDialog source) {
            super(source);
        }
    }
}
