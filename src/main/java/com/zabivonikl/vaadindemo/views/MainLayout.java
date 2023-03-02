package com.zabivonikl.vaadindemo.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import com.zabivonikl.vaadindemo.security.SecurityService;
import com.zabivonikl.vaadindemo.views.loginview.LoginView;
import com.zabivonikl.vaadindemo.views.registerview.RegistrationView;
import com.zabivonikl.vaadindemo.views.tableviews.inventoryview.InventoryView;
import com.zabivonikl.vaadindemo.views.tableviews.personalview.PersonalView;
import com.zabivonikl.vaadindemo.views.welcomeview.WelcomeView;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        var header = new HorizontalLayout();
        header.addClassNames("text-l", "m-m");

        var navbar = createNavbar();

        header.add(createLayout(), navbar, isUserLoggedIn() ? createLogoutButton() : createLoginButton());
        if (!isUserLoggedIn())
            header.add(createRegisterButton());

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(navbar);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        return header;
    }

    private boolean isUserLoggedIn() {
        return securityService.getAuthenticatedUser() != null;
    }

    private Button createLogoutButton() {
        var button = new Button(new Icon(VaadinIcon.EXIT_O), e -> securityService.logout());
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return button;
    }

    private Button createLoginButton() {
        var button = new Button("Вход", e -> UI.getCurrent().navigate(LoginView.class));
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    private Button createRegisterButton() {
        return new Button("Регистрация", e -> UI.getCurrent().navigate(RegistrationView.class));
    }

    private Component createLayout() {
        var layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);
        layout.add(createAppName());

        return layout;
    }

    private Component createAppName() {
        var icon = new Icon(VaadinIcon.VAADIN_H);
        var title = new H1("Vaadin Demo");
        title.addClassNames(FontSize.XLARGE, Margin.Vertical.MEDIUM);

        var appName = new HorizontalLayout(icon, title);
        appName.addClassNames(Display.FLEX, AlignItems.CENTER);
        var logo = new RouterLink(WelcomeView.class);
        logo.add(appName);

        return logo;
    }

    private Component createNavbar() {
        var nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);
        nav.add(createMenuItemsWrapper());

        return nav;
    }

    private UnorderedList createMenuItemsWrapper() {
        var list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        for (MenuItemInfo menuItem : createMenuItems())
            list.add(menuItem);

        return list;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{
                new MenuItemInfo("Персонал", "la la-user", PersonalView.class),
                new MenuItemInfo("Товары", "la la-barcode", InventoryView.class)
        };
    }

    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            var link = new RouterLink();
            link.addClassNames(
                    Display.FLEX,
                    Gap.XSMALL,
                    Height.MEDIUM,
                    AlignItems.CENTER,
                    Padding.Horizontal.SMALL,
                    TextColor.BODY
            );
            link.setRoute(view);

            var text = new Span(menuTitle);
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * <a href="https://icons8.com/line-awesome">https://icons8.com/line-awesome</a>
         */
        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            public LineAwesomeIcon(String lineawesomeClassnames) {
                addClassNames(FontSize.LARGE, TextColor.SECONDARY);
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }
}
