package com.example.schoolsystem.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Attendancestatus {
    Present,
    Absent,
    Halfday,
    Leave,
    Holiday,
    Late;

    @JsonCreator
    public static Attendancestatus fromString(String value) {
        if (value == null) return null;
        for (Attendancestatus status : Attendancestatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown Attendancestatus: " + value);
    }
}
