package com.zabivonikl.vaadindemo.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import com.zabivonikl.vaadindemo.security.SecurityService;
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
        HorizontalLayout header = new HorizontalLayout();
        header.addClassNames("text-l", "m-m");

        Button logout = new Button("Выход", e -> securityService.logout());
        Component navbar = getNavbar();

        header.add(getLayout(), navbar, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(navbar);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        return header;
    }

    private Component getLayout() {
        Div layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);
        layout.add(getAppName());

        return layout;
    }

    private Component getAppName() {
        H1 appName = new H1(new MenuItemInfo.LineAwesomeIcon("la la-vaadin"));
        appName.add("Vaadin Demo");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);
        RouterLink link = new RouterLink(WelcomeView.class);
        link.addClassNames(TextColor.BODY);
        link.add(appName);
        return link;
    }

    private Component getNavbar() {
        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);
        nav.add(getMenuItems());

        return nav;
    }

    private UnorderedList getMenuItems() {
        UnorderedList list = new UnorderedList();
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
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
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
                // Use Lumo classnames for suitable font styling
                addClassNames(FontSize.LARGE, TextColor.SECONDARY);
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }

}
