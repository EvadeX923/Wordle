package edu.wm.cs.cs301.f2024.wordle.model;

import java.awt.Color;

/**
 *  The AppColors class defines the color constants used in the Wordle game.
 */
public class AppColors {
	/** The color for incorrect letters in the game (gray). */
	public static Color GRAY = new Color(120, 124, 126);
	
    /** The color for correctly guessed letters in the game (green). */
	public static Color GREEN = new Color(106, 170, 100);
	
    /** The color for letters present but in the wrong position (yellow). */
	public static Color YELLOW = new Color(201, 180, 88);
	
    /** The color used for outlining the grid cells (light gray). */
	public static Color OUTLINE = new Color(211, 214, 218);

}
