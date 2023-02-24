package com.tennisparty.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetTest {

    @Test
    void getSetScore() {
        Set aSet = new Set();
        assertEquals("0-0",aSet.getSetScore());
    }

    @Test
    void getSetScoreWithTieBreak() {
        Set aSet = new Set();
        aSet.getTieBreak().setScorePlayer1(7);
        aSet.getTieBreak().setScorePlayer2(2);
        assertEquals("0-0(7-2)",aSet.getSetScore());
    }
}