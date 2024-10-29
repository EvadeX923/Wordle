package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;

/**
 * Represents a single response for a guessed character in the Wordle game, 
 * containing the character and its corresponding background and foreground colors.
 */
public class WordleResponse {
    
    /** The character guessed by the player, initialized by the constructor. */
    private final char c;
    
    /** The response containing the background and foreground colors for the character, initialized by the constructor. */
    private final ColorResponse colorResponse;

    /**
     * Constructs a WordleResponse object for a guessed character with specific colors.
     * 
     * @param c The guessed character.
     * @param backgroundColor The background color for the character.
     * @param foregroundColor The text color of the character.
     */
    public WordleResponse(char c, Color backgroundColor, Color foregroundColor) {
        this.c = c;
        this.colorResponse = new ColorResponse(backgroundColor, foregroundColor);
    }

    /**
     * Returns the guessed character.
     *
     * @return The character guessed by the player.
     */
    public char getChar() {
        return c;
    }

    /**
     * Returns the background color associated with the guessed character.
     *
     * @return the background color for the guessed character.
     */
    public Color getBackgroundColor() {
        return colorResponse.getBackgroundColor();
    }

    /**
     * Returns the foreground color of the guessed character.
     *
     * @return the text color of the guessed character.
     */
    public Color getForegroundColor() {
        return colorResponse.getForegroundColor();
    }
}

