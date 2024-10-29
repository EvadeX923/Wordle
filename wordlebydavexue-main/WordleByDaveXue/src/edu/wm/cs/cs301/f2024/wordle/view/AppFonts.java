package edu.wm.cs.cs301.f2024.wordle.view;

import java.awt.Font;

/**
 * The AppFonts class provides static methods that returns different font styles 
 * used in the Wordle application.
 */
public class AppFonts {
    
    /**
     * Returns a bold title font with a size of 36 points.
     *
     * @return a Font object representing the title font
     */
    public static Font getTitleFont() {
        return new Font("Dialog", Font.BOLD, 36);
    }
    
    /**
     * Returns a plain text font with a size of 16 points.
     *
     * @return a Font object representing the text font
     */
    public static Font getTextFont() {
        return new Font("Dialog", Font.PLAIN, 16);
    }
    
    /**
     * Returns a plain footer font with a size of 12 points.
     *
     * @return a Font object representing the footer font
     */
    public static Font getFooterFont() {
        return new Font("Dialog", Font.PLAIN, 12);
    }
}
