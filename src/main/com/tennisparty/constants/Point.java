package com.tennisparty.constants;

/**
 * Enumération listant les points possibles lors d'un jeu de tennis
 */
public enum Point {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FOURTY("40"),
    DEUCE("Deuce"),
    ADVANTAGE("Advantage");

    /**
     * Libellé associé au point dans le jeu
     */
    public final String label;

    Point(String label) {
        this.label = label;
    }

    }
