package com.tennisparty.models;

import com.tennisparty.constants.GameStatus;
import com.tennisparty.constants.Point;
import com.tennisparty.models.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modèle définissant un modèle de match de tennis composé du score du noms des joueurs, de la liste des sets @Set,
 * du set @Set en cours (correspondant à un des sets de la liste des sets) et le statut du match
 */
public class TennisGame {

    /**
     * Nom du joueur 1
     */
    private String player1;

    /**
     * Nom du joueur 2
     */
    private String player2;

    /**
     * Liste des sets @Set composant le match
     */
    private List<Set> sets = new ArrayList<>();

    /**
     * Set en cours, correspondant à un set de la liste des sets @Set du match
     */
    private Set currentSet = new Set();

    private GameStatus gameStatus = GameStatus.IN_PROGRESS;

    public TennisGame(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public TennisGame(){}

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
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

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Méthode permettant l'affichage du score du match dans les standards d'affichage des scores de tennis
     * @return le score du match standardisé
     */
    public String showScore(){
        return sets.stream()
                .map(Set::getSetScore)
                // Chaque score de chaque set est joint pour former le score global de la partie
                .collect(Collectors.joining(" "));
    }

    /**
     * Méthode retournant le score du jeu en cours dans le cadre du set et du match en cours, avec le score des 2
     * joueurs
     * @return le score standardisé des 2 joueurs
     */
    public String showCurrentPointStatus(){
        // Si pas de tie break, on affiche le score du P1 et le score du P2 simplement
        if (currentSet.getTieBreak().getScorePlayer1().equals(-1)){
            return currentSet.getCurrentPoint().getScorePlayer1().equals(Point.DEUCE) ?
                    Point.DEUCE.label : currentSet.getCurrentPoint().getScorePlayer1().label + " - " + currentSet.getCurrentPoint().getScorePlayer2().label;
        // Sinon, on affiche le score des 2 joueurs suffixé du score du tie break
        } else {
            return "Tie break : " + currentSet.getTieBreak().getScorePlayer1() + " - " + currentSet.getTieBreak().getScorePlayer2();
        }
    }

    /**
     * Méthode retournant le statut du match
     * @return le libellé du statut du match
     */
    public String showGameStatus(){
        return gameStatus.label;
    }
}
