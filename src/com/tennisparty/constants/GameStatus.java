package com.tennisparty.constants;

public enum GameStatus {
    IN_PROGRESS("In Progress"),
    P1_WIN("Player 1 wins"),
    P2_WIN("Player 2 wins");

    public final String label;

    GameStatus(String label) {
        this.label = label;
    }

    }
