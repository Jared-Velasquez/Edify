package com.jvel.edify.entity.enums;

public enum Department {
    COMPUTER_SCIENCE("Computer Science"),
    ELECTRICAL_ENGINEERING("Electrical Engineering"),
    ASIAN_AMERICAN_STUDIES("Asian American Studies"),
    CLASSICS("Classics"),
    MUSICOLOGY("Musicology");

    public final String label;

    Department(String label) {
        this.label = label;
    }

    public static Department valueOfLabel(String label) {
        for (Department d : values())
            if (d.label.equalsIgnoreCase(label))
                return d;
        return null;
    }
}
