package com.zabivonikl.vaadindemo.data.entity;

import java.util.List;
import java.util.Vector;

public enum Role {
    User, Admin;

    public static List<String> getNames() {
        List<String> names = new Vector<>(Role.values().length);
        for (Role role : Role.values()) names.add(role.name());
        return names;
    }
}
