import '@vaadin/button';
import '@vaadin/checkbox';
import '@vaadin/date-picker';
import '@vaadin/date-time-picker';
import '@vaadin/form-layout';
import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-column';
import '@vaadin/horizontal-layout';
import '@vaadin/split-layout';
import '@vaadin/text-field';
import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('personal-view')
export class PersonalView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`
      <vaadin-split-layout>
        <div class="grid-wrapper">
          <vaadin-grid id="grid"></vaadin-grid>
        </div>
        <div class="editor-layout">
          <div class="editor">
            <vaadin-form-layout>
              <vaadin-text-field label="Имя" id="firstName"></vaadin-text-field>
              <vaadin-text-field label="Фамилия" id="lastName"></vaadin-text-field>
              <vaadin-text-field label="Email" id="email"></vaadin-text-field>
              <vaadin-text-field label="Телефон" id="phone"></vaadin-text-field>
              <vaadin-date-picker label="Дата рождения" id="dateOfBirth"></vaadin-date-picker>
              <vaadin-text-field label="Должность" id="role"></vaadin-text-field>
            </vaadin-form-layout>
          </div>
          <vaadin-horizontal-layout class="button-layout">
            <vaadin-button theme="primary" id="save">Сохранить</vaadin-button>
            <vaadin-button theme="primary" id="delete">Удалить</vaadin-button>
            <vaadin-button theme="tertiary" slot="" id="cancel">Отмена</vaadin-button>
          </vaadin-horizontal-layout>
        </div>
      </vaadin-split-layout>`;
  }
}
