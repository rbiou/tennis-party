package com.tennisparty.models;

import com.tennisparty.constants.Point;

/**
 * Modèle définissant un set dans les règles du tennis composé du score du set @points, du jeu en cours @currentPoint
 * et du tie break le cas échéant
 */
public class Set {

    /**
     * Le score du set, initié à 0 pour le joueur 1, correpondant à la première valeur et pour le joueur 2
     * correpondant à la seconde valeur
     */
    private Score<Integer> points = new Score<>(0, 0);

    /**
     * Le score du jeu actuel, compris dans les valeurs définis dans @Point, la première valeur étant celle du
     * joueur 1 et la seconde celle du joueur 2
     */
    private Score<Point> currentPoint = new Score<>(Point.ZERO, Point.ZERO);

    /**
     * Le score du tie break pour le set, la première valeur étant celle du joueur 1 et la seconde celle
     * du joueur 2. Par défaut, la première valeur est initié à -1 pour indiquer qu'aucun tie break est en cours
     * pour ce set
     */
    private Score<Integer> tieBreak = new Score<>(-1, 0);
    public Set() {
        super();
    }

    public Score<Integer> getPoints() {
        return points;
    }

    public void setPoints(Score<Integer> points) {
        this.points = points;
    }

    public void setCurrentPoint(Score<Point> currentPoint) {
        this.currentPoint = currentPoint;
    }

    public Score<Point> getCurrentPoint() {
        return currentPoint;
    }

    public Score<Integer> getTieBreak() {
        return tieBreak;
    }

    /**
     * Méthode permettant de retourner le score du set dans le langage standard d'affichage des scores du tennis, y
     * compris le score du tie break
     * @return le score du set
     */
    public String getSetScore() {
        return tieBreak.getScorePlayer1().equals(-1) ?
                points.getScorePlayer1() + "-" + points.getScorePlayer2() : points.getScorePlayer1() + "-" + points.getScorePlayer2() + "(" + tieBreak.getScorePlayer1() + "-" + tieBreak.getScorePlayer2() +")";
    }
}
