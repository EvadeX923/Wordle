package edu.wm.cs.cs301.f2024.wordle.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import edu.wm.cs.cs301.f2024.wordle.model.AppColors;
import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;
import edu.wm.cs.cs301.f2024.wordle.model.WordleResponse;
import edu.wm.cs.cs301.f2024.wordle.view.StatisticsDialog;
import edu.wm.cs.cs301.f2024.wordle.view.WordleFrame;

/**
 * The KeyboardButtonAction class handles the action by a keyboard button press in the Wordle game.
 */
public class KeyboardButtonAction extends AbstractAction {
	/** Serialization ID. */
    private static final long serialVersionUID = 1L;

    /** The WordleFrame object that represents the main view of the game, initialized in the constructor. */
    private final WordleFrame view;

    /** The WordleModel object that contains the game's state and logic, initialized in the constructor. */
    private final WordleModel model;

    /**
     * Constructs a KeyboardButtonAction with the specified view and model.
     * 
     * @param view the WordleFrame that represents main view of the game.
     * @param model the WordleModel that contains the game's logic and state.
     */
    public KeyboardButtonAction(WordleFrame view, WordleModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Handles button click events for keyboard buttons in the Wordle game.
     * 
     * If the button text is "Enter":
     * 
     * Checks if the current column is the last column to make sure that a full word is submitted.
     * Sets the current row for the next guess and retrieves the responses for the current row.
     * Processes each letter in the current row and updating the letter's background color based on correctness.
     * In the case that all letters in the current row are correct, it updates the game statistics, increments the total games played,
     * updates the current streak, and displays a statistics dialog.
     * In the case that are no more rows left to guess, it resets the current streak and displays the statistics dialog.
     * In the case that there more rows left to guess, the display is refreshed.
     * 
     * If the button text is "Backspace":
     * 
     * Removes the last character from the input by calling the backspace method in the model.
     * The display is then refreshed.
     * 
     * 
     * For any other button text:
     * 
     * Treats the button text as a character and sets it in the current column of the Wordle model.
     * The display is then refreshed.
     * 
     *
     * @param event the ActionEvent object contains information about the event, including the source button.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();
        String text = button.getActionCommand();
        switch (text) {
            case "Enter":
                if (model.getCurrentColumn() >= (model.getColumnCount() - 1)) {
                    boolean moreRows = model.setCurrentRow();
                    WordleResponse[] currentRow = model.getCurrentRow();
                    int greenCount = 0;

                    for (WordleResponse wordleResponse : currentRow) {
                        view.setColor(Character.toString(wordleResponse.getChar()),
                                wordleResponse.getBackgroundColor(), 
                                wordleResponse.getForegroundColor());
                        if (wordleResponse.getBackgroundColor().equals(AppColors.GREEN)) {
                            greenCount++;
                        } 
                    }

                    if (greenCount >= model.getColumnCount()) {
                        view.repaintWordleGridPanel();
                        model.getStatistics().incrementTotalGamesPlayed();
                        int currentRowNumber = model.getCurrentRowNumber();
                        model.getStatistics().addWordsGuessed(currentRowNumber);
                        int currentStreak = model.getStatistics().getCurrentStreak();
                        model.getStatistics().setCurrentStreak(++currentStreak);
                        new StatisticsDialog(view, model);
                    } else if (!moreRows) {
                        view.repaintWordleGridPanel();
                        model.getStatistics().incrementTotalGamesPlayed();
                        model.getStatistics().setCurrentStreak(0);
                        new StatisticsDialog(view, model);
                    } else {
                        view.repaintWordleGridPanel();
                    }
                }
                break;
            case "Backspace":
                model.backspace();
                view.repaintWordleGridPanel();
                break;
            default:
                model.setCurrentColumn(text.charAt(0));
                view.repaintWordleGridPanel();
                break;
        }
    }
}

