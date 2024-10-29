package edu.wm.cs.cs301.f2024.wordle.controller;

import javax.swing.SwingUtilities;

import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;
import edu.wm.cs.cs301.f2024.wordle.view.WordleFrame;

/**
 * The Wordle class is the entry point for the Wordle game application. 
 * It implements the Runnable interface to start the GUI on the EDT.
 */
public class Wordle implements Runnable {

    /**
     * The main method that initiates the Wordle game application.
     * It schedules the Wordle instance to be executed on the 
     * EDT.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Wordle());
    }

    /**
     * The run method that is executed when the Wordle
     * instance is scheduled for execution. It creates a new instance of 
     * WordleFrame initialized with a new WordleModel.
     * This method sets up the main GUI for the Wordle game.
     */
    @Override
    public void run() {
        new WordleFrame(new WordleModel());
    }
}
