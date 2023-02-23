package com.tennisparty.constant;

public enum Point {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FOURTY("40"),
    DEUCE("Deuce"),
    ADVANTAGE("Advantage");

    public final String label;

    Point(String label) {
        this.label = label;
    }

    }
