package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;

/**
 * The ColorResponse class represents a color response for a character
 * in the Wordle game, including both the background and foreground colors.
 */
public class ColorResponse {
	/** The background color for the character, initialized by the constructor. */
	private final Color backgroundColor;
	
	/** The text color of the character, initialized by the constructor. */
	private final Color foregroundColor;

	/**
	 * Constructs a ColorResponse object with the specified background and foreground colors.
	 * 
	 * @param backgroundColor the background color for the character.
	 * @param foregroundColor the text color of the character.
	 */
	public ColorResponse(Color backgroundColor, Color foregroundColor) {
		this.backgroundColor = backgroundColor; 
		this.foregroundColor = foregroundColor;
	}

	/**
	 * Returns the background color stored in this ColorResponse.
	 * 
	 * @return the background color for the character.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Returns the foreground color stored in this ColorResponse.
	 * 
	 * @return the text color of the character.
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

}
