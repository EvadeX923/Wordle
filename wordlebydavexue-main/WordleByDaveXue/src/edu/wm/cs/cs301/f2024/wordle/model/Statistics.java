package edu.wm.cs.cs301.f2024.wordle.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Storing and managing player's performance data in the Wordle game.
 */
public class Statistics {
    
    /** The current win streak of the player, initialized in the readStatistics method which is called in the constructor. */
    private int currentStreak;
    
    /** The longest win streak of the player, initialized in the readStatistics method which is called in the constructor. */
    private int longestStreak;
    
    /** The total number of games played by the player, initialized in the readStatistics method which is called in the constructor. */
    private int totalGamesPlayed;
    
    /** A list of integers representing the number of words guessed in each game, initialized in the constructor. */
    private List<Integer> wordsGuessed;
    
    /** The path where the statistics log is stored, including the user's home directory, initialized in the constructor. */
    private String path;
    
    /** The log file name for storing statistics, initialized in the constructor. */
    private String log;

    /**
     * Constructs a Statistics object, initializes the file path, 
     * log file name, and loads previous statistics from the log file.
     * Initializes the fields to default values if the log file does not exist.
     */
    public Statistics() {
        this.wordsGuessed = new ArrayList<>();
        String fileSeparator = System.getProperty("file.separator");
        this.path = System.getProperty("user.home") + fileSeparator + "Wordle";
        this.log = fileSeparator + "statistics.log";
        readStatistics();
    }

    /**
     * Reads statistics data from the log file, including the current streak, 
     * longest streak, total games played, and words guessed.
     * Initializes the fields to default values if the log file does not exist.
     */
    private void readStatistics() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + log));
            this.currentStreak = Integer.valueOf(br.readLine().trim());
            this.longestStreak = Integer.valueOf(br.readLine().trim());
            this.totalGamesPlayed = Integer.valueOf(br.readLine().trim());
            int totalWordsGuessed = Integer.valueOf(br.readLine().trim());
            
            for (int index = 0; index < totalWordsGuessed; index++) {
                wordsGuessed.add(Integer.valueOf(br.readLine().trim()));
            }
            br.close();
        } catch (FileNotFoundException e) {
            this.currentStreak = 0;
            this.longestStreak = 0;
            this.totalGamesPlayed = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the current statistics to the log file, including the current streak, 
     * longest streak, total games played, and the number of words guessed.
     */
    public void writeStatistics() {
        try {
            File file = new File(path);
            file.mkdir(); // Create the directory if it doesn't exist
            file = new File(path + log);
            file.createNewFile(); // Create the log file if it doesn't exist

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(Integer.toString(currentStreak));
            bw.write(System.lineSeparator());
            bw.write(Integer.toString(longestStreak));
            bw.write(System.lineSeparator());
            bw.write(Integer.toString(totalGamesPlayed));
            bw.write(System.lineSeparator());
            bw.write(Integer.toString(wordsGuessed.size()));
            bw.write(System.lineSeparator());
            
            for (Integer value : wordsGuessed) {
                bw.write(Integer.toString(value));
                bw.write(System.lineSeparator());
            }
            
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the current win streak of the player.
     * 
     * @return the current win streak
     */
    public int getCurrentStreak() {
        return currentStreak;
    }

    /**
     * Sets the current win streak of the player. If the current streak exceeds 
     * the longest streak, the longest streak is updated.
     * 
     * @param currentStreak the new current win streak
     */
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
        if (currentStreak > longestStreak) {
            this.longestStreak = currentStreak;
        }
    }

    /**
     * Gets the longest win streak of the player.
     * 
     * @return the longest win streak
     */
    public int getLongestStreak() {
        return longestStreak;
    }

    /**
     * Gets the total number of games played by the player.
     * 
     * @return the total number of games played
     */
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    /**
     * Increments the total number of games played by the player.
     */
    public void incrementTotalGamesPlayed() {
        this.totalGamesPlayed++;
    }

    /**
     * Gets the list of the number of words guessed for each game played.
     * 
     * @return a list of integers representing the number of words guessed in each game
     */
    public List<Integer> getWordsGuessed() {
        return wordsGuessed;
    }

    /**
     * Adds the number of words guessed in the current game to the list of guesses.
     * 
     * @param wordCount the number of words guessed in the current game
     */
    public void addWordsGuessed(int wordCount) {
        this.wordsGuessed.add(wordCount);
    }
}

