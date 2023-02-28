package com.zabivonikl.vaadindemo.views.welcomeview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.TextAlignment;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.zabivonikl.vaadindemo.views.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Добро пожаловать! | Vaadin Demo")
@AnonymousAllowed
public class WelcomeView extends VerticalLayout implements HasStyle {
    public WelcomeView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(getLogo(), getHeader(), getDescription());
    }

    private Component getLogo() {
        var icon = new Icon(VaadinIcon.ACADEMY_CAP);
        icon.addClassNames(FontSize.XXXLARGE, TextColor.PRIMARY);
        return icon;
    }

    private Component getHeader() {
        var header = new H1("Добро пожаловать!");
        header.addClassNames(
                Margin.Vertical.NONE,
                TextAlignment.CENTER
        );

        return header;
    }

    private Component getDescription() {
        var description = new Paragraph("Тестовый проект на Vaadin. Github: ");
        description.add(new Anchor("https://github.com/zabivonikl/vaadin-demo", "https://github.com/zabivonikl/vaadin-demo"));
        description.add(".");

        return description;
    }
}
