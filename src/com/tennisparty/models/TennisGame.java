package com.tennisparty.models;

import com.tennisparty.constants.GameStatus;
import com.tennisparty.models.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TennisGame {

    private String player1;

    private String player2;

    private List<Set> sets = new ArrayList<>();

    private Set currentSet = new Set();

    private GameStatus gameStatus = GameStatus.IN_PROGRESS;

    public TennisGame(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Set getCurrentSet() {
        return currentSet;
    }

    public void setCurrentSet(Set currentSet) {
        this.currentSet = currentSet;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String showScore(){
        return sets.stream()
                .map(Set::getSetScore)
                // Chaque score de chaque set est joint pour former le score global de la partie
                .collect(Collectors.joining(" "));
    }

    public String showCurrentPointStatus(){
        // Si pas de tie break, on affiche le score du P1 et le score du P2 simplement
        if (currentSet.getTieBreak().getScorePlayer1().equals(-1)){
            return currentSet.getCurrentPoint().getScorePlayer1() + " - " + currentSet.getCurrentPoint().getScorePlayer2();
        // Sinon, on affiche le score des 2 joueurs suffixé du score du tie break
        } else {
            return "Tie break : " + currentSet.getTieBreak().getScorePlayer1() + " - " + currentSet.getTieBreak().getScorePlayer2();
        }
    }

    public String showGameStatus(){
        return gameStatus.label;
    }
}
