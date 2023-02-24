package com.tennisparty;

import com.tennisparty.constants.GameStatus;
import com.tennisparty.constants.Point;
import com.tennisparty.models.Score;
import com.tennisparty.models.Set;
import com.tennisparty.models.TennisGame;

/**
 * Gestionnaire de match de tennis @TennisGame
 */
public class TennisGameManager {
    /**
     * Le match de tennis géré
     */
    private TennisGame game;

    /**
     * Méthode initiant un nouveau match de tennis et sa gestion : le premier set est créé et les joueurs sont
     * définis
     * @param player1 le nom du joueur 1
     * @param player2 le nom du joueur 2
     * @return le match @TennisMatch nouvellement créé
     */
    public TennisGame initGame(String player1, String player2){
        game = new TennisGame(player1, player2);
        Set initialSet = new Set();
        game.setCurrentSet(initialSet);
        game.getSets().add(initialSet);
        return game;
    }

    /**
     * Méthode d'entrée principale de gestion du match permettant de définir le joueur ayant scoré lors du point et les
     * impacts sur le score du match
     * @param game le match géré
     * @param isPlayer1WinPoint le gagnant du point : true si joueur 1, false si joueur 2
     */
    public void scorePoint(TennisGame game, boolean isPlayer1WinPoint){
        // Si tie break, on le traite simplement et on abroge les traitements
        if (game.getCurrentSet().getTieBreak().getScorePlayer1() != -1) {
            manageTieBreak(game, isPlayer1WinPoint);
            return;
        }

        // Récupération de l'état actuel de la partie
        Point scoreWinner = isPlayer1WinPoint ? game.getCurrentSet().getCurrentPoint().getScorePlayer1()
                : game.getCurrentSet().getCurrentPoint().getScorePlayer2();
        Point scoreLoser = isPlayer1WinPoint ? game.getCurrentSet().getCurrentPoint().getScorePlayer2()
                : game.getCurrentSet().getCurrentPoint().getScorePlayer1();

        // Définition du nouveau score selon quel joueur est gagnant du point
        Point newScore = Point.ZERO;
        switch (scoreWinner) {
            case ZERO:
                newScore = Point.FIFTEEN;
                break;
            case FIFTEEN:
                newScore = Point.THIRTY;
                break;
            case THIRTY:
                newScore = scoreLoser.equals(Point.FOURTY) ? Point.DEUCE : Point.FOURTY;
                break;
            case DEUCE:
                newScore = Point.ADVANTAGE;
                break;
            case FOURTY:
                // Si cas d'avantage au point précédent, alors on retourne à un état Deuce
                if (scoreLoser.equals(Point.ADVANTAGE)) newScore = Point.DEUCE;
                // Si le gagnant a 40 dans le jeu en cours (sans égalité "Deuce"), alors il gagne le jeu
                break;
            case ADVANTAGE: {
                break;
            }
        }
        // Mise à jour des score du jeu en conséquence
        setPoints(game.getCurrentSet(), isPlayer1WinPoint, newScore);
        // Si le nouveau score est zero, alors un jeu a été remporté : on met à jour le set
        if (newScore == Point.ZERO) updateCurrentSetPoint(game, isPlayer1WinPoint);
    }

    /**
     * Méthode définissant les points pour le jeu en cours
     * @param currentSet le set en cours
     * @param isPlayer1WinPoint le gagnant du point : true si joueur 1, false si joueur 2
     * @param newScore le nouveau score pour le gagnat
     */
    private static void setPoints(Set currentSet, boolean isPlayer1WinPoint, Point newScore) {
        // Le gagnant passe à un état ADVANTAGE, le perdant revient à un état Fourty (40)
        if (Point.ADVANTAGE.equals(newScore)) {
            currentSet.getCurrentPoint().setScorePlayer1(isPlayer1WinPoint ? newScore : Point.FOURTY);
            currentSet.getCurrentPoint().setScorePlayer2(!isPlayer1WinPoint ? newScore : Point.FOURTY);
        // Si le gagnant passe à un état DEUCE, le perdant y passe aussi (cas d'égalité)
        } else if (Point.DEUCE.equals(newScore)) {
            currentSet.getCurrentPoint().setScorePlayer1(newScore);
            currentSet.getCurrentPoint().setScorePlayer2(newScore);
        } else if (isPlayer1WinPoint) {
            currentSet.getCurrentPoint().setScorePlayer1(newScore);
        } else {
            currentSet.getCurrentPoint().setScorePlayer2(newScore);
        }
    }

    /**
     * Méthode définissant les points pour le set en cours : MAJ du score du set et du match en fonction du point gagné
     * @param game le match
     * @param isPlayer1WinPoint le gagnant du point : true si joueur 1, false si joueur 2
     */
    private void updateCurrentSetPoint(TennisGame game, boolean isPlayer1WinPoint){
        // On définit le prochain jeu : un nouveau jeu est créé avec 0 pour les 2 joueurs
        game.getCurrentSet().setCurrentPoint(new Score<>(Point.ZERO, Point.ZERO));

        // On définit le score des 2 joueurs dans le set
        Integer scoreWinner = (isPlayer1WinPoint) ? game.getCurrentSet().getPoints().getScorePlayer1() : game.getCurrentSet().getPoints().getScorePlayer2();
        Integer scoreLoser = (isPlayer1WinPoint) ? game.getCurrentSet().getPoints().getScorePlayer2() : game.getCurrentSet().getPoints().getScorePlayer1();

        int dif = Math.abs(scoreWinner - scoreLoser);
        // Si le gagnant du point a désormais 7 points dans le set ou 6 points avec 2 points d'écart, on créé
        // un nouveau set ou on définit la fin du match si c'est le dernier set
        if (scoreWinner == 7 || (scoreWinner == 6 && dif >= 2)) {
            // MAJ du statut du match potentiellement si gagnant
            updateMatchStatus(game, isPlayer1WinPoint);

            // Si pas de gagnant, on créé le prochain set
            if (game.getGameStatus().equals(GameStatus.IN_PROGRESS)) {
                game.setCurrentSet(new Set());
                game.getSets().add(game.getCurrentSet());
            }
        // Si le score du set est de 6-6 on déclenche le tie break en initiant le score du tie break à 0 (de base à -1
        // pour indiquer qu'aucun tie break est en cours)
        } else if (scoreWinner == 6 && scoreLoser == 6) {
            game.getCurrentSet().getTieBreak().setScorePlayer1(0);
        // Sinon on rajoute un point au score du set pour le gagnant du jeu
        } else {

            // Selon gagnant, +1 au score du set
            if (isPlayer1WinPoint) {
                game.getCurrentSet().getPoints().setScorePlayer1(scoreWinner + 1);
            } else {
                game.getCurrentSet().getPoints().setScorePlayer2(scoreWinner + 1);
            }

            // Après ajout du point, si le gagnant du jeu gagne le set ou si le match est dans une situation de
            // tie break, on rejoue la méthode pour potentiellement clore le match/passer à un nouveau set
            if (scoreWinner + 1 == 7 || (scoreWinner + 1 == 6 && dif >= 2) || (scoreWinner == 5 && scoreLoser == 6))
                updateCurrentSetPoint(game, isPlayer1WinPoint);
        }
    }

    /**
     * Méthode définissant le statut du match : toujours en cours ou définition du gagnant
     * @param game le match
     * @param isPlayer1WinPoint le gagnant du point : true si joueur 1, false si joueur 2
     */
    private void updateMatchStatus(TennisGame game, boolean isPlayer1WinPoint) {
        // Si au moins 3 sets joués, il y a un gagnant potentiel (car 3 sets gagnants)
        if (game.getSets().size() >= 3) {

            // Comptage des sets gagnés pour chaque joueur
            Score<Integer> result = new Score<>(0, 0);
            game.getSets().forEach(set -> {
                if (set.getPoints().getScorePlayer1() > set.getPoints().getScorePlayer2())
                    result.setScorePlayer1(result.getScorePlayer1() + 1);
                else
                    result.setScorePlayer2(result.getScorePlayer2() + 1);
            });

            // Si un joueur a gagné 3 sets, le match est déclaré gagné pour ce joueur
            if (result.getScorePlayer1() == 3) {
                game.setGameStatus(GameStatus.P1_WIN);
            } else if (result.getScorePlayer2() == 3) {
                game.setGameStatus(GameStatus.P2_WIN);
            }

        }
    }

    /**
     * Méthode définissant les règles de gestion du tie break : le gagnant du point doit avoir minimum 7 points et
     * 2 points d'écart pour gagner le tie break et donc le set
     * @param game le match
     * @param isPlayer1WinPoint le gagnant du point : true si joueur 1, false si joueur 2
     */
    private void manageTieBreak(TennisGame game, boolean isPlayer1WinPoint){
        // Définition des scores du tie break pour chaque joueur
        Integer scoreWinner = (isPlayer1WinPoint) ? game.getCurrentSet().getTieBreak().getScorePlayer1()
                : game.getCurrentSet().getTieBreak().getScorePlayer2();
        Integer scoreLoser = (isPlayer1WinPoint) ? game.getCurrentSet().getTieBreak().getScorePlayer2()
                : game.getCurrentSet().getTieBreak().getScorePlayer1();

        int dif = Math.abs(scoreWinner - scoreLoser);
        // Si le gagnant du point a minimum 7 points et plus de 2 points d'écart, il gagne un point dans le set et donc
        // le set
        if (scoreWinner >= 7 && dif >= 2) {
            if (isPlayer1WinPoint) {
                game.getCurrentSet().getPoints()
                        .setScorePlayer1(game.getCurrentSet().getPoints().getScorePlayer1() + 1);
            } else {
                game.getCurrentSet().getPoints()
                        .setScorePlayer2(game.getCurrentSet().getPoints().getScorePlayer2() + 1);
            }

            // MAJ du set et potentiellement du match
            updateCurrentSetPoint(game, isPlayer1WinPoint);

        // Si le joueur 1 gagne le point, on ajout un point au tie break à son compte
        } else if (isPlayer1WinPoint) {
            game.getCurrentSet().getTieBreak().setScorePlayer1(scoreWinner + 1);
            // Si après l'ajout du point, le joueur remporte le tie break, on rappelle la méthode pour qu'il gagne
            // un point dans le set et MAJ le set
            if (scoreWinner + 1 >= 7 && dif + 1 >= 2) manageTieBreak(game, isPlayer1WinPoint);
        // Sinon le joueur 1 gagne le point, on ajout un point au tie break à son compte
        } else {
            game.getCurrentSet().getTieBreak().setScorePlayer2(scoreWinner + 1);
            // Si après l'ajout du point, le joueur remporte le tie break, on rappelle la méthode pour qu'il gagne
            // un point dans le set et MAJ le set
            if (scoreWinner + 1 >= 7 && dif + 1 >= 2) manageTieBreak(game, isPlayer1WinPoint);
        }
    }
}