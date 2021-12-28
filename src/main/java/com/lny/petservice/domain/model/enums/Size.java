package com.lny.petservice.domain.model.enums;

import java.util.stream.Stream;

public enum Size {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large");

    private final String value;

    Size(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Stream<Size> stream(){
        return Stream.of(Size.values());
    }
}
