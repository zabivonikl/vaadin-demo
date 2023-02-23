package com.zabivonikl.vaadindemo;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


@SpringBootApplication
@Theme(value = "VaadinDemo")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(
            DataSource dataSource,
            SqlInitializationProperties properties
    ) {
        return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
            @Override
            public boolean initializeDatabase() {
                return false;
            }
        };
    }
}
