package com.tennisparty.model;

import com.tennisparty.constant.Point;

public class Set {

    private Score<Integer> points = new Score<>(0, 0);
    private Score<Point> currentPoint = new Score<>(Point.ZERO, Point.ZERO);
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

    public Score<Point> getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Score<Point> currentPoint) {
        this.currentPoint = currentPoint;
    }

    public Score<Integer> getTieBreak() {
        return tieBreak;
    }

    public void setTieBreak(Score<Integer> tieBreak) {
        this.tieBreak = tieBreak;
    }

    public String getSetScore() {
        return tieBreak.getScorePlayer1().equals(-1) ?
                points.getScorePlayer1() + "-" + points.getScorePlayer2() : points.getScorePlayer1() + "-" + points.getScorePlayer2() + "(" + tieBreak.getScorePlayer1() + "-" + tieBreak.getScorePlayer2() +")";
    }
}
