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

@customElement('inventory-view')
export class InventoryView extends LitElement {
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
              <vaadin-text-field label="Название" id="title"></vaadin-text-field>
              <vaadin-text-field label="Производитель" id="vendor"></vaadin-text-field>
              <vaadin-text-field label="Категория" id="category"></vaadin-text-field>
              <vaadin-integer-field label="Осталось шт." id="piecesLeft"></vaadin-integer-field>
              <vaadin-number-field label="Цена" id="price"></vaadin-number-field>
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
