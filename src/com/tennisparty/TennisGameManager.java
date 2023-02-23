package com.tennisparty;

import com.tennisparty.constants.GameStatus;
import com.tennisparty.constants.Point;
import com.tennisparty.models.Score;
import com.tennisparty.models.Set;
import com.tennisparty.models.TennisGame;

public class TennisGameManager {

    private TennisGame game;

    public void initGame(String player1, String player2){
        game = new TennisGame(player1, player2);
    }

    public void scorePoint(TennisGame game, boolean isPlayer1WinPoint){
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
                newScore = Point.FOURTY;
                break;
            case DEUCE:
                newScore = Point.ADVANTAGE;
                break;
            case FOURTY:
                // Si le gagnant a 40 dans le jeu en cours (sans égalité "Deuce"), alors il gagne le jeu
            case ADVANTAGE: {
                calculateCurrentSetPoint(game, isPlayer1WinPoint);
                break;
            }
        }
        // Mise à jour des score du jeu en conséquence
        setPoints(game.getCurrentSet(), isPlayer1WinPoint, newScore);
    }

    private static void setPoints(Set currentSet, boolean isPlayer1WinPoint, Point newScore) {
        if (Point.ADVANTAGE.equals(newScore)) {
            currentSet.getCurrentPoint().setScorePlayer1(newScore);
            currentSet.getCurrentPoint().setScorePlayer2(Point.FOURTY);
        } else if (Point.DEUCE.equals(newScore)) {
            currentSet.getCurrentPoint().setScorePlayer1(newScore);
            currentSet.getCurrentPoint().setScorePlayer2(newScore);
        } else if (isPlayer1WinPoint) {
            currentSet.getCurrentPoint().setScorePlayer1(newScore);
        } else {
            currentSet.getCurrentPoint().setScorePlayer2(newScore);
        }
    }

    private void calculateCurrentSetPoint(TennisGame game, boolean isPlayer1WinPoint){
        game.getCurrentSet().setCurrentPoint(new Score<>(Point.ZERO, Point.ZERO));
        Integer scoreWinner = (isPlayer1WinPoint) ? game.getCurrentSet().getPoints().getScorePlayer1() : game.getCurrentSet().getPoints().getScorePlayer2();
        Integer scoreLoser = (isPlayer1WinPoint) ? game.getCurrentSet().getPoints().getScorePlayer2() : game.getCurrentSet().getPoints().getScorePlayer1();

        int dif = Math.abs(scoreWinner - scoreLoser);
        if (scoreWinner == 7 || (scoreWinner == 6 && dif >= 2)) {
            game.getSets().add(game.getCurrentSet());

            // calculateMatchStatus()
            calculateMatchStatus(game, isPlayer1WinPoint);

            game.setCurrentSet(new Set());
            if (isPlayer1WinPoint) {
                game.getCurrentSet().getPoints().setScorePlayer1(1);
            } else {
                game.getCurrentSet().getPoints().setScorePlayer2(1);
            }

        } else if (scoreWinner == 6 && scoreLoser == 6) {
            tieBreak(game, isPlayer1WinPoint);
        } else {
            if (isPlayer1WinPoint) {
                game.getCurrentSet().getPoints().setScorePlayer1(scoreWinner + 1);
            } else {
                game.getCurrentSet().getPoints().setScorePlayer2(scoreWinner + 1);
            }
        }
    }

    private void calculateMatchStatus(TennisGame game, boolean isPlayer1WinPoint) {
        if (game.getSets().size() >= 3) {
            Score<Integer> result = new Score<>(0, 0);
            game.getSets().forEach(set -> {
                if (set.getPoints().getScorePlayer1() > set.getPoints().getScorePlayer2())
                    result.setScorePlayer1(result.getScorePlayer1() + 1);
                else
                    result.setScorePlayer2(result.getScorePlayer2() + 1);
            });

            if ((isPlayer1WinPoint && (result.getScorePlayer1() == 3)) || (result.getScorePlayer2() == 3)) {
                game.setGameStatus(GameStatus.P1_WIN);
            } else if (result.getScorePlayer2() == 3) {
                game.setGameStatus(GameStatus.P2_WIN);
            }

        }
    }

    private void tieBreak(TennisGame game, boolean isPlayer1WinPoint){

        Integer scoreWinner = (isPlayer1WinPoint) ? game.getCurrentSet().getTieBreak().getScorePlayer1()
                : game.getCurrentSet().getTieBreak().getScorePlayer2();
        Integer scoreLoser = (isPlayer1WinPoint) ? game.getCurrentSet().getTieBreak().getScorePlayer2()
                : game.getCurrentSet().getTieBreak().getScorePlayer1();

        if (isPlayer1WinPoint) {
            game.getCurrentSet().getTieBreak().setScorePlayer1(scoreWinner + 1);
        } else {
            game.getCurrentSet().getTieBreak().setScorePlayer2(scoreWinner + 1);
        }

        int dif = Math.abs(scoreWinner - scoreLoser);
        if (scoreWinner >= 7 && dif >= 2) {
            if (isPlayer1WinPoint) {
                game.getCurrentSet().getPoints()
                        .setScorePlayer1(game.getCurrentSet().getPoints().getScorePlayer1() + 1);
            } else {
                game.getCurrentSet().getPoints()
                        .setScorePlayer2(game.getCurrentSet().getPoints().getScorePlayer2() + 1);
            }
        }
    }
}