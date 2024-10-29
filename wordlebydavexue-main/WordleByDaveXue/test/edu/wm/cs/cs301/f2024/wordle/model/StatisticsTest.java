package edu.wm.cs.cs301.f2024.wordle.model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class StatisticsTest {

    private Statistics statistics;

    @Before
    public void setUp() {
        statistics = new Statistics();

        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Wordle" + System.getProperty("file.separator") + "statistics.log");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testGetCurrentStreak() {
        assertEquals(0, statistics.getCurrentStreak());
        statistics.setCurrentStreak(3);
        assertEquals(3, statistics.getCurrentStreak());
    }

    @Test
    public void testSetCurrentStreak() {
        statistics.setCurrentStreak(5);
        assertEquals(5, statistics.getCurrentStreak());
        assertEquals(5, statistics.getLongestStreak());

        statistics.setCurrentStreak(3);
        assertEquals(3, statistics.getCurrentStreak());
        assertEquals(5, statistics.getLongestStreak());
    }

    @Test
    public void testGetLongestStreak() {
        statistics.setCurrentStreak(4);
        assertEquals(4, statistics.getLongestStreak());
        
        statistics.setCurrentStreak(6);
        assertEquals(6, statistics.getLongestStreak());
    }

    @Test
    public void testGetTotalGamesPlayed() {
        assertEquals(0, statistics.getTotalGamesPlayed());

        statistics.incrementTotalGamesPlayed();
        assertEquals(1, statistics.getTotalGamesPlayed());

        statistics.incrementTotalGamesPlayed();
        assertEquals(2, statistics.getTotalGamesPlayed());
    }

    @Test
    public void testIncrementTotalGamesPlayed() {
        statistics.incrementTotalGamesPlayed();
        assertEquals(1, statistics.getTotalGamesPlayed());
    }

    @Test
    public void testGetWordsGuessedAndAddWordsGuessed() {
        List<Integer> wordsGuessed = statistics.getWordsGuessed();
        assertNotNull(wordsGuessed);
        assertEquals(0, wordsGuessed.size());

        statistics.addWordsGuessed(3);
        wordsGuessed = statistics.getWordsGuessed();
        assertEquals(1, wordsGuessed.size());
        assertEquals(3, (int) wordsGuessed.get(0));

        statistics.addWordsGuessed(4);
        wordsGuessed = statistics.getWordsGuessed();
        assertEquals(2, wordsGuessed.size());
        assertEquals(4, (int) wordsGuessed.get(1));
    }

    @Test
    public void testAddWordsGuessed() {
        statistics.addWordsGuessed(2);
        statistics.addWordsGuessed(5);
        
        List<Integer> wordsGuessed = statistics.getWordsGuessed();
        assertEquals(4, wordsGuessed.size());
        assertEquals(3, (int) wordsGuessed.get(0));
        assertEquals(5, (int) wordsGuessed.get(1));
    }

    @Test
    public void testWriteAndReadStatistics() {
        statistics.setCurrentStreak(5);
        statistics.setCurrentStreak(3);
        statistics.incrementTotalGamesPlayed();
        statistics.incrementTotalGamesPlayed();
        statistics.addWordsGuessed(3);
        statistics.addWordsGuessed(5);
        statistics.writeStatistics();

        Statistics newStatistics = new Statistics();
        
        assertEquals(3, newStatistics.getCurrentStreak());
        assertEquals(5, newStatistics.getLongestStreak());
        assertEquals(2, newStatistics.getTotalGamesPlayed());
        List<Integer> wordsGuessed = newStatistics.getWordsGuessed();
        assertEquals(2, wordsGuessed.size());
        assertEquals(3, (int) wordsGuessed.get(0));
        assertEquals(5, (int) wordsGuessed.get(1));
    }
}

