package com.zabivonikl.vaadindemo.views.welcomeview;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.zabivonikl.vaadindemo.views.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Добро пожаловать! | Vaadin Demo")
public class WelcomeView extends Div implements HasStyle {
    public WelcomeView() {
        addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.AROUND
        );
        H1 header = new H1("Добро пожаловать!");
        header.addClassNames(
                LumoUtility.Margin.Vertical.NONE,
                LumoUtility.TextAlignment.CENTER
        );
        add(header);
    }
}
