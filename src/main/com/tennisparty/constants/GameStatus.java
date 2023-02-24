package com.tennisparty.constants;

/**
 * Enumération listant l'état du match : IN_PROGRESS si le match est en cours, P1_WIN si le joueur 1 a remporté le match
 * et P2_WIN si le joueur 2 a remporté le match
 */
public enum GameStatus {
    IN_PROGRESS("In Progress"),
    P1_WIN("Player 1 wins"),
    P2_WIN("Player 2 wins");

    /**
     * Libellé associé à l'état du match
     */
    public final String label;

    GameStatus(String label) {
        this.label = label;
    }

}
