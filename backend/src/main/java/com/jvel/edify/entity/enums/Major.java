package com.jvel.edify.entity.enums;

public enum Major {
    COMPUTER_SCIENCE("Computer Science"),
    COMPUTER_SCIENCE_ENGINEERING("Computer Science Engineering"),
    COMPUTER_ENGINEERING("Computer Engineering"),
    LINGUISTICS_COMPUTER_SCIENCE("Linguistics Computer Science"),
    ASIAN_AMERICAN_STUDIES("Asian American Studies"),
    MUSICOLOGY("Musicology"),
    CLASSICS("Classics");

    public final String label;

    Major(String label) {
        this.label = label;
    }

    public static Major valueOfLabel(String label) {
        for (Major m : values())
            if (m.label.equalsIgnoreCase(label))
                return m;
        return null;
    }
}
