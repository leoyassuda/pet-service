package com.lny.petservice.domain.model.enums;

import java.util.List;

public enum Size {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large");

    private final String value;

    Size(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<Size> list() {
        return List.of(Size.values());
    }
}
