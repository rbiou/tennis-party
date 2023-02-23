package com.tennisparty.model;

public class Score<X> {
    private X scorePlayer1;
    private X scorePlayer2;

    public Score() {
        super();
    }

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
