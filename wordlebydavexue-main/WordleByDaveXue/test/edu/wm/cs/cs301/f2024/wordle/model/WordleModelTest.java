package edu.wm.cs.cs301.f2024.wordle.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordleModelTest {

    private WordleModel wordleModel;

    @BeforeEach
    public void setUp() {
        wordleModel = new WordleModel();
    }

    @Test
    public void testSetWordListAndGetTotalWordCount() {
        wordleModel.setWordList(Arrays.asList());
        assertEquals(0, wordleModel.getTotalWordCount());
        wordleModel.setWordList(Arrays.asList("apple", "grape"));
        assertEquals(2, wordleModel.getTotalWordCount());
        wordleModel.setWordList(Arrays.asList("apple", "grape", "check", "plane"));
        assertEquals(4, wordleModel.getTotalWordCount());
    }
    
    @Test
    public void testGetMaximumRows() {
        assertEquals(6, wordleModel.getMaximumRows());
    }
    
    @Test
    public void testGetColumnCount() {
        assertEquals(5, wordleModel.getColumnCount());
    }

    @Test
    public void testSetCurrentColumn() {
        wordleModel.setCurrentColumn('p');
        assertEquals('p', wordleModel.getWordleGrid()[0][0].getChar());
        assertEquals(0, wordleModel.getCurrentColumn());

        wordleModel.setCurrentColumn('e');
        assertEquals('e', wordleModel.getWordleGrid()[0][1].getChar());
        assertEquals(1, wordleModel.getCurrentColumn());
    }

    @Test
    public void testBackspaceOnOneCharacter() {
        wordleModel.setCurrentColumn('p');
        wordleModel.backspace();
        assertNull(wordleModel.getWordleGrid()[0][0]);
        assertEquals(-1, wordleModel.getCurrentColumn());
    }
    
    @Test
    public void testBackspaceOnThreeCharacter() {
        wordleModel.setCurrentColumn('p');
        wordleModel.setCurrentColumn('e');
        wordleModel.backspace();
        assertNull(wordleModel.getWordleGrid()[0][1]);
        assertEquals(0, wordleModel.getCurrentColumn());
    }
    
    @Test
    public void testBackspaceOnFiveCharacters() {
        wordleModel.setCurrentColumn('p');
        wordleModel.setCurrentColumn('e');
        wordleModel.setCurrentColumn('a');
        wordleModel.setCurrentColumn('c');
        wordleModel.setCurrentColumn('h');
        wordleModel.backspace();
        wordleModel.backspace();
        wordleModel.backspace();
        assertNull(wordleModel.getWordleGrid()[0][2]);
        assertNull(wordleModel.getWordleGrid()[0][3]);
        assertNull(wordleModel.getWordleGrid()[0][4]);
        assertEquals(1, wordleModel.getCurrentColumn());
    }
    
    @Test
    public void testSetCurrentRowCorrectGuess() {
        wordleModel.setWordList(Arrays.asList("apple"));
        wordleModel.setCurrentWord();

        wordleModel.setCurrentColumn('a');
        wordleModel.setCurrentColumn('p');
        wordleModel.setCurrentColumn('p');
        wordleModel.setCurrentColumn('l');
        wordleModel.setCurrentColumn('e');

        wordleModel.setCurrentRow();
        WordleResponse[] currentRow = wordleModel.getCurrentRow();
        for (WordleResponse response : currentRow) {
            assertEquals(AppColors.GREEN, response.getBackgroundColor());
        }
    }

    @Test
    public void testSetCurrentRowPartialCorrectGuess() {
        wordleModel.setWordList(Arrays.asList("apple"));
        wordleModel.setCurrentWord();

        wordleModel.setCurrentColumn('a');
        wordleModel.setCurrentColumn('p');
        wordleModel.setCurrentColumn('p');
        wordleModel.setCurrentColumn('l');
        wordleModel.setCurrentColumn('y');

        wordleModel.setCurrentRow();
        WordleResponse[] currentRow = wordleModel.getCurrentRow();

        assertEquals(AppColors.GREEN, currentRow[0].getBackgroundColor());
        assertEquals(AppColors.GREEN, currentRow[1].getBackgroundColor());
        assertEquals(AppColors.GREEN, currentRow[2].getBackgroundColor());
        assertEquals(AppColors.GREEN, currentRow[3].getBackgroundColor());
        assertEquals(AppColors.GRAY, currentRow[4].getBackgroundColor());
    }

    @Test
    public void testGetWordleGrid() {
        WordleResponse[][] grid = wordleModel.getWordleGrid();
        assertNotNull(grid);
        assertEquals(6, grid.length);
        assertEquals(5, grid[0].length);
    }

    @Test
    public void testSetCurrentWordGeneratesRandomWord() {
        wordleModel.setWordList(Arrays.asList("apple", "grape", "peach"));
        wordleModel.setCurrentWord();

        String currentWordStr = String.valueOf(wordleModel.getCurrentRow());
        assertTrue(Arrays.asList("APPLE", "GRAPE", "PEACH").contains(currentWordStr));
    }

    @Test
    public void testWordleGridInitializesCorrectly() {
        WordleResponse[][] grid = wordleModel.getWordleGrid();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                assertNull(grid[row][col]);
            }
        }
    }

    @Test
    public void testGetStatistics() {
        assertNotNull(wordleModel.getStatistics());
    }


    @Test
    public void testInitializeResetsModel() {
        wordleModel.setCurrentColumn('p');
        wordleModel.setCurrentColumn('l');
        wordleModel.setCurrentRow();

        wordleModel.initialize();

        assertEquals(0, wordleModel.getCurrentRowNumber());
        assertEquals(-1, wordleModel.getCurrentColumn());
        assertNotNull(wordleModel.getWordleGrid());
    }

}

