package com.jvel.edify.entity.enums;

public enum Position {
    PROFESSOR("Professor"),
    TEACHING_PROFESSOR("Teaching Professor"),
    LECTURER("Lecturer"),
    ADJUNCT_PROFESSOR("Adjunct Professor"),
    ASSISTANT_PROFESSOR("Assistant Professor");

    public final String label;

    Position(String label) {
        this.label = label;
    }

    public static Position valueOfLabel(String label) {
        for (Position p : values())
            if (p.label.equalsIgnoreCase(label))
                return p;
        return null;
    }
}
