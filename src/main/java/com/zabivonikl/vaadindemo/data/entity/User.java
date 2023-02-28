package com.zabivonikl.vaadindemo.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    @Column(unique = true)
    private String login;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.User;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
