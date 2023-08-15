package com.jvel.edify.entity.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    NON_BINARY("Non-binary"),
    NOT_SPECIFIED("Not specified");

    public final String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender valueOfLabel(String label) {
        for (Gender g : values())
            if (g.label.equalsIgnoreCase(label))
                return g;
        return null;
    }
}
