package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import edu.wm.cs.cs301.f2024.wordle.controller.ReadWordsRunnable;

/**
 * Represents the model for the Wordle game, managing the game state, word list,
 * and user interactions.
 */
public class WordleModel {

    /** The word that the player needs to guess, initialized by the generateCurrentWord or the setCurrentWord method.*/
    private char[] currentWord;
    
    /** The player's current guess, initialized by the constructor. */
    private char[] guess;

    /** The number of columns representing letters in a word, initialized by the constructor. */
    private final int columnCount;

    /** The maximum number of rows representing the number of attempts, initialized by the constructor. */
    private final int maximumRows;

    /** The current column (letter) being guessed, initialized by the constructor. */
    private int currentColumn;

    /** The current row (attempt) number, initialized by the constructor. */
    private int currentRow;

    /** The list of available words for the game, initialized by the setWordList method. */
    private List<String> wordList;

    /** Random instance for selecting words randomly, initialized by the constructor. */
    private final Random random;

    /** The statistics object tracking game performance, initialized by the constructor. */
    private final Statistics statistics;

    /** The grid of WordleResponse objects representing the state of the game, initialized by the initializeWordleGrid method in the constructor. */
    private WordleResponse[][] wordleGrid;

    /**
     * Constructs a new WordleModel instance, initializing the grid, word list, 
     * and statistics, and generating a random word to guess.
     */
    public WordleModel() {
        this.currentColumn = -1;
        this.currentRow = 0;
        this.columnCount = 5;
        this.maximumRows = 6;
        this.random = new Random();

        createWordList();
        this.wordleGrid = initializeWordleGrid();
        this.guess = new char[columnCount];
        this.statistics = new Statistics();
    }

    /**
     * Starts the process of creating the word list in a separate thread.
     */
    private void createWordList() {
        ReadWordsRunnable runnable = new ReadWordsRunnable(this);
        new Thread(runnable).start();
    }

    /**
     * Initializes the model, resetting the grid, rows, columns, and generating
     * a new word to guess.
     */
    public void initialize() {
        this.wordleGrid = initializeWordleGrid();
        this.currentColumn = -1;
        this.currentRow = 0;
        generateCurrentWord();
        this.guess = new char[columnCount];
    }

    /**
     * Generates a new current word to be guessed by selecting randomly from the
     * word list.
     */
    public void generateCurrentWord() {
        String word = getCurrentWord();
        this.currentWord = word.toUpperCase().toCharArray();
    }

    /**
     * Gets a random word from the word list.
     *
     * @return A random word from the word list.
     */
    private String getCurrentWord() {
        return wordList.get(getRandomIndex());
    }

    /**
     * Generates a random index based on the size of the word list.
     *
     * @return A random index for selecting a word.
     */
    private int getRandomIndex() {
        int size = wordList.size();
        return random.nextInt(size);
    }

    /**
     * Initializes the Wordle grid, setting all positions to null.
     *
     * @return A 2D array representing the empty Wordle grid.
     */
    private WordleResponse[][] initializeWordleGrid() {
        WordleResponse[][] wordleGrid = new WordleResponse[maximumRows][columnCount];

        for (int row = 0; row < wordleGrid.length; row++) {
            for (int column = 0; column < wordleGrid[row].length; column++) {
                wordleGrid[row][column] = null;
            }
        }

        return wordleGrid;
    }

    /**
     * Sets the word list to be used by the model.
     *
     * @param wordList The list of words to be used in the game.
     */
    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }

    /**
     * Sets the current word by selecting a random word from the word list.
     */
    public void setCurrentWord() {
        int index = getRandomIndex();
        currentWord = wordList.get(index).toCharArray();
    }

    /**
     * Sets the current column with the guessed character and updates the grid.
     *
     * @param c The character guessed for the current column.
     */
    public void setCurrentColumn(char c) {
        currentColumn++;
        currentColumn = Math.min(currentColumn, (columnCount - 1));
        guess[currentColumn] = c;
        wordleGrid[currentRow][currentColumn] = new WordleResponse(c, Color.WHITE, Color.BLACK);
    }

    /**
     * Removes the last guessed character and moves back one column.
     * 
     * TODO Potential bug as the code doesn't check if currentColumn is a negative index.
     */
    public void backspace() {
        wordleGrid[currentRow][currentColumn] = null;
        guess[currentColumn] = ' ';
        this.currentColumn--;
        this.currentColumn = Math.max(currentColumn, 0);
    }

    /**
     * Returns the current row of Wordle responses.
     *
     * @return An array of WordleResponse objects for the current row.
     */
    public WordleResponse[] getCurrentRow() {
        return wordleGrid[getCurrentRowNumber()];
    }

    /**
     * Gets the current row number.
     *
     * @return The current row number.
     */
    public int getCurrentRowNumber() {
        return currentRow - 1;
    }

    /**
     * Updates the grid with the colors representing correct and incorrect guesses
     * and moves to the next row.
     *
     * @return True if there are remaining rows for guessing; false otherwise.
     */
    public boolean setCurrentRow() {
        for (int column = 0; column < guess.length; column++) {
            Color backgroundColor = AppColors.GRAY;
            Color foregroundColor = Color.WHITE;
            if (guess[column] == currentWord[column]) {
                backgroundColor = AppColors.GREEN;
            } else if (contains(currentWord, guess, column)) {
                backgroundColor = AppColors.YELLOW;
            }

            wordleGrid[currentRow][column] = new WordleResponse(guess[column],
                    backgroundColor, foregroundColor);
        }

        currentColumn = -1;
        currentRow++;
        guess = new char[columnCount];

        return currentRow < maximumRows;
    }

    /**
     * Checks if the guessed character exists in the current word at any position 
     * other than the current column.
     *
     * @param currentWord The word being guessed.
     * @param guess       The current guess.
     * @param column      The current column being checked.
     * @return True if the character exists in another position; false otherwise.
     */
    private boolean contains(char[] currentWord, char[] guess, int column) {
        for (int index = 0; index < currentWord.length; index++) {
            if (index != column && guess[column] == currentWord[index]) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the current state of the Wordle grid.
     *
     * @return A 2D array of WordleResponse objects representing the grid.
     */
    public WordleResponse[][] getWordleGrid() {
        return wordleGrid;
    }

    /**
     * Gets the maximum number of rows (attempts) allowed.
     *
     * @return The maximum number of rows.
     */
    public int getMaximumRows() {
        return maximumRows;
    }

    /**
     * Gets the number of columns (letters in the word).
     *
     * @return The number of columns.
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Gets the current column number.
     *
     * @return The current column number.
     */
    public int getCurrentColumn() {
        return currentColumn;
    }

    /**
     * Gets the total number of words in the word list.
     *
     * @return The size of the word list.
     */
    public int getTotalWordCount() {
        return wordList.size();
    }

    /**
     * Gets the statistics for the game.
     *
     * @return The statistics object tracking performance.
     */
    public Statistics getStatistics() {
        return statistics;
    }
}

