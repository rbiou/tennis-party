package com.tennisparty;

import com.tennisparty.constants.Point;
import com.tennisparty.models.Score;
import com.tennisparty.models.Set;
import com.tennisparty.models.TennisGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TennisGameManagerTest {

    @Test
    void scorePoint() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("15 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("0-0", aTennisGame.showScore());
    }

    @Test
    void scorePointFifteenToThirty() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FIFTEEN, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("30 - 0", aTennisGame.showCurrentPointStatus());
    }

    @Test
    void scorePointToDeuce() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.THIRTY));
        aTennisGameManager.scorePoint(aTennisGame, false);
        assertEquals("Deuce", aTennisGame.showCurrentPointStatus());
    }

    @Test
    void scorePointAdvantageP2() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.DEUCE, Point.DEUCE));
        aTennisGameManager.scorePoint(aTennisGame, false);
        assertEquals("40 - Advantage", aTennisGame.showCurrentPointStatus());
    }

    @Test
    void scorePointAdvantageP2ToDeuce() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ADVANTAGE));
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("Deuce", aTennisGame.showCurrentPointStatus());
    }

    @Test
    void scorePointAdvantageP2ToWinPoint() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ADVANTAGE));
        aTennisGameManager.scorePoint(aTennisGame, false);
        assertEquals("0 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("0-1", aTennisGame.showScore());
    }

    @Test
    void scorePointThirtyToFourty() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.THIRTY, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("40 - 0", aTennisGame.showCurrentPointStatus());
    }

    @Test
    void scorePointWinAPoint() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("0 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("1-0", aTennisGame.showScore());
    }

    @Test
    void scorePointWinAPointAndASet() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        aTennisGame.getCurrentSet().setPoints(new Score<>(5, 2));
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("0 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("6-2 0-0", aTennisGame.showScore());
    }

    @Test
    void scorePointP1WinAPointAndASetAndAMatch() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        Set a1StSet = aTennisGame.getCurrentSet();
        a1StSet.setPoints(new Score<>(6,2));
        Set a2ndSet = new Set();
        a2ndSet.setPoints(new Score<>(6,2));
        aTennisGame.getSets().add(a2ndSet);
        Set a3rdSet = new Set();
        a3rdSet.setPoints(new Score<>(5,2));
        aTennisGame.getSets().add(a3rdSet);
        aTennisGame.setCurrentSet(a3rdSet);
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("0 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("6-2 6-2 6-2", aTennisGame.showScore());
        assertEquals("Player 1 wins", aTennisGame.showGameStatus());
    }

    @Test
    void scorePointP2WinAPointAndASetAndAMatch() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        Set a1StSet = aTennisGame.getCurrentSet();
        a1StSet.setPoints(new Score<>(2,6));
        Set a2ndSet = new Set();
        a2ndSet.setPoints(new Score<>(2,6));
        aTennisGame.getSets().add(a2ndSet);
        Set a3rdSet = new Set();
        a3rdSet.setPoints(new Score<>(2,5));
        aTennisGame.getSets().add(a3rdSet);
        aTennisGame.setCurrentSet(a3rdSet);
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.ZERO, Point.FOURTY));
        aTennisGameManager.scorePoint(aTennisGame, false);
        assertEquals("0 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("2-6 2-6 2-6", aTennisGame.showScore());
        assertEquals("Player 2 wins", aTennisGame.showGameStatus());
    }

    @Test
    void scorePointWinAPointAndGoToTieBreak() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        Set a1StSet = aTennisGame.getCurrentSet();
        a1StSet.setPoints(new Score<>(5,6));
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("Tie break : 0 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("6-6(0-0)", aTennisGame.showScore());
    }

    @Test
    void scorePointP1WinAPointInTieBreak() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        Set a1StSet = aTennisGame.getCurrentSet();
        a1StSet.setPoints(new Score<>(5,6));
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("Tie break : 1 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("6-6(1-0)", aTennisGame.showScore());
    }

    @Test
    void scorePointP2WinAPointInTieBreak() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        Set a1StSet = aTennisGame.getCurrentSet();
        a1StSet.setPoints(new Score<>(6,5));
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.ZERO, Point.FOURTY));
        aTennisGameManager.scorePoint(aTennisGame, false);
        aTennisGameManager.scorePoint(aTennisGame, false);
        assertEquals("Tie break : 0 - 1", aTennisGame.showCurrentPointStatus());
        assertEquals("6-6(0-1)", aTennisGame.showScore());
    }

    @Test
    void scorePointP1WinASetInTieBreak() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        Set a1StSet = aTennisGame.getCurrentSet();
        a1StSet.setPoints(new Score<>(5,6));
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        aTennisGame.getCurrentSet().getTieBreak().setScorePlayer1(6);
        aTennisGame.getCurrentSet().getTieBreak().setScorePlayer2(2);
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("0 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("7-6(7-2) 0-0", aTennisGame.showScore());
    }

    @Test
    void scorePointP2WinASetInTieBreak() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        Set a1StSet = aTennisGame.getCurrentSet();
        a1StSet.setPoints(new Score<>(6,5));
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.ZERO, Point.FOURTY));
        aTennisGameManager.scorePoint(aTennisGame, false);
        aTennisGame.getCurrentSet().getTieBreak().setScorePlayer1(2);
        aTennisGame.getCurrentSet().getTieBreak().setScorePlayer2(6);
        aTennisGameManager.scorePoint(aTennisGame, false);
        assertEquals("0 - 0", aTennisGame.showCurrentPointStatus());
        assertEquals("6-7(2-7) 0-0", aTennisGame.showScore());
    }

    @Test
    void scorePointP1WinASetAndAMatchInTieBreak() {
        TennisGameManager aTennisGameManager = new TennisGameManager();
        TennisGame aTennisGame = aTennisGameManager.initGame("p1Test", "p2Test");
        Set a1StSet = aTennisGame.getCurrentSet();
        a1StSet.setPoints(new Score<>(6,2));
        Set a2ndSet = new Set();
        a2ndSet.setPoints(new Score<>(6,2));
        aTennisGame.getSets().add(a2ndSet);
        Set a3rdSet = new Set();
        a3rdSet.setPoints(new Score<>(5,6));
        aTennisGame.getSets().add(a3rdSet);
        aTennisGame.setCurrentSet(a3rdSet);
        aTennisGame.getCurrentSet().setCurrentPoint(new Score<>(Point.FOURTY, Point.ZERO));
        aTennisGameManager.scorePoint(aTennisGame, true);
        aTennisGame.getCurrentSet().getTieBreak().setScorePlayer1(6);
        aTennisGame.getCurrentSet().getTieBreak().setScorePlayer2(2);
        aTennisGameManager.scorePoint(aTennisGame, true);
        assertEquals("Tie break : 7 - 2", aTennisGame.showCurrentPointStatus());
        assertEquals("6-2 6-2 7-6(7-2)", aTennisGame.showScore());
        assertEquals("Player 1 wins", aTennisGame.showGameStatus());
    }
}