package com.tennisparty.models;

/**
 * Modèle de score générique définissant une paire de valeur associé à un jeu ou un @Set selon le contexte
 * @param <X> selon le contexte, une paire de @Point dans le cadre d'un jeu
 *           ou une paire d'entiers dans le cadre d'un @Set
 */
public class Score<X> {
    /**
     * Le score du joueur 1
     */
    private X scorePlayer1;
    /**
     * Le score du joueur 2
     */
    private X scorePlayer2;

    public Score(X scorePlayer1, X scorePlayer2) {
        super();
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
    }

    public X getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(X scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public X getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(X scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }
}
