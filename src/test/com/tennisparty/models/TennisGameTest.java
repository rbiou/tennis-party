package com.tennisparty.models;

import com.tennisparty.constants.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TennisGameTest {

    @Test
    void showScore() {
        TennisGame aTennisGame = new TennisGame("p1Test", "p2Test");
        Set a1stSet = new Set();
        a1stSet.setPoints(new Score<>(6,4));
        Set a2ndSet = new Set();
        a2ndSet.setPoints(new Score<>(6,1));
        Set a3rdSet = new Set();
        a3rdSet.setPoints(new Score<>(7,5));
        aTennisGame.getSets().addAll(Arrays.asList(a1stSet, a2ndSet, a3rdSet));
        assertEquals("6-4 6-1 7-5",aTennisGame.showScore());
    }

    @Test
    void showCurrentPointStatus() {
        TennisGame aTennisGame = new TennisGame("p1Test", "p2Test");
        Set a1stSet = new Set();
        a1stSet.setCurrentPoint(new Score<>(Point.FOURTY, Point.FIFTEEN));
        aTennisGame.getSets().add(a1stSet);
        aTennisGame.setCurrentSet(a1stSet);
        assertEquals("40 - 15",aTennisGame.showCurrentPointStatus());
    }

    @Test
    void showCurrentPointStatusWithTieBreak() {
        TennisGame aTennisGame = new TennisGame("p1Test", "p2Test");
        Set a1stSet = new Set();
        a1stSet.getTieBreak().setScorePlayer1(9);
        a1stSet.getTieBreak().setScorePlayer2(7);
        aTennisGame.getSets().add(a1stSet);
        aTennisGame.setCurrentSet(a1stSet);
        assertEquals("Tie break : 9 - 7",aTennisGame.showCurrentPointStatus());
    }

    @Test
    void showGameStatus() {
        TennisGame aTennisGame = new TennisGame("p1Test", "p2Test");
        assertEquals("In Progress",aTennisGame.showGameStatus());
    }
}