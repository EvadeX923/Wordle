package edu.wm.cs.cs301.f2024.wordle.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.f2024.wordle.controller.KeyboardButtonAction;
import edu.wm.cs.cs301.f2024.wordle.model.AppColors;
import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;

/**
 * Represents the in-game keyboard for the Wordle game.
 * Manages the keyboard layout and key actions for entering guesses.
 */
public class KeyboardPanel {

    /** The current index of the button, initialized in the constructor. */
    private int buttonIndex;

    /** The total number of keys on the keyboard panel, initialized in the constructor. */
    private final int buttonCount;

    /** An array of JButton objects representing the keyboard buttons, initialized in the constructor. */
    private final JButton[] buttons;

    /** A JPanel object that contains the keyboard layout, initialized in the createMainPanel which is called in the constructor. */
    private final JPanel panel;

    /** A KeyboardButtonAction object that waits for keyboard button presses, initialized in the constructor. */
    private final KeyboardButtonAction action;

    /** A WordleModel object that contains the game's state and logic, initialized in the constructor. */
    private final WordleModel model;

    /**
     * Constructs a new KeyboardPanel.
     *
     * @param view The WordleFrame view instance.
     * @param model The WordleModel providing data and game state.
     */
    public KeyboardPanel(WordleFrame view, WordleModel model) {
        this.model = model;
        this.buttonIndex = 0;
        this.buttonCount = firstRow().length + secondRow().length + thirdRow().length;
        this.buttons = new JButton[buttonCount];
        this.action = new KeyboardButtonAction(view, model);
        this.panel = createMainPanel();
    }

    /**
     * Creates the main keyboard panel layout with all rows of keys.
     *
     * @return A JPanel object containing the keyboard layout.
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

        panel.add(createQPanel());
        panel.add(createAPanel());
        panel.add(createZPanel());
        panel.add(createTotalPanel());

        return panel;
    }

    /**
     * Creates the first row of keys.
     *
     * @return A JPanel object containing the first row of keys.
     */
    private JPanel createQPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        Font textfont = AppFonts.getTextFont();

        String[] letters = firstRow();

        for (int index = 0; index < letters.length; index++) {
            JButton button = new JButton(letters[index]);
            setKeyBinding(button, letters[index]);
            button.addActionListener(action);
            button.setFont(textfont);
            buttons[buttonIndex++] = button;
            panel.add(button);
        }

        return panel;
    }

    /**
     * Provides the list of letters for the first row of the keyboard.
     *
     * @return An array of strings representing the first row keys.
     */
    private String[] firstRow() {
        return new String[] { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "Backspace" };
    }

    /**
     * Creates the second row of keys.
     *
     * @return A JPanel object containing the second row of keys.
     */
    private JPanel createAPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        Font textfont = AppFonts.getTextFont();

        String[] letters = secondRow();

        for (int index = 0; index < letters.length; index++) {
            JButton button = new JButton(letters[index]);
            setKeyBinding(button, letters[index]);
            button.addActionListener(action);
            button.setFont(textfont);
            buttons[buttonIndex++] = button;
            panel.add(button);
        }

        return panel;
    }

    /**
     * Provides the list of letters for the second row of the keyboard.
     *
     * @return An array of strings representing the second row keys.
     */
    private String[] secondRow() {
        return new String[] { "A", "S", "D", "F", "G", "H", "J", "K", "L", "Enter" };
    }

    /**
     * Creates the third row of keys.
     *
     * @return A JPanel object containing the third row of keys.
     */
    private JPanel createZPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        Font textfont = AppFonts.getTextFont();

        String[] letters = thirdRow();

        for (int index = 0; index < letters.length; index++) {
            JButton button = new JButton(letters[index]);
            setKeyBinding(button, letters[index]);
            button.addActionListener(action);
            button.setFont(textfont);
            buttons[buttonIndex++] = button;
            panel.add(button);
        }

        return panel;
    }

    /**
     * Provides the list of letters for the third row of the keyboard.
     *
     * @return An array of strings representing the third row keys.
     */
    private String[] thirdRow() {
        return new String[] { "Z", "X", "C", "V", "B", "N", "M" };
    }

    /**
     * Maps physical key presses to the button actions for the given button.
     *
     * @param button The JButton object representing the key.
     * @param text The label of the key.
     */
    private void setKeyBinding(JButton button, String text) {
        InputMap inputMap = button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
        if (text.equalsIgnoreCase("Backspace")) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "action");
        } else {
            inputMap.put(KeyStroke.getKeyStroke(text.toUpperCase()), "action");
        }
        ActionMap actionMap = button.getActionMap();
        actionMap.put("action", action);
    }

    /**
     * Creates a panel object showing the total possible word count and the number of columns.
     *
     * @return A JPanel object displaying the word count summary.
     */
    private JPanel createTotalPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        Font footerFont = AppFonts.getFooterFont();

        String text = String.format("%,d", model.getTotalWordCount());
        text += " possible " + model.getColumnCount() + "-letter words!";
        JLabel label = new JLabel(text);
        label.setFont(footerFont);
        panel.add(label);

        return panel;
    }

    /**
     * Sets the color of the button corresponding to the given letter.
     *
     * @param letter The letter.
     * @param backgroundColor The background color.
     * @param foregroundColor The foreground text color.
     */
    public void setColor(String letter, Color backgroundColor, Color foregroundColor) {
        for (JButton button : buttons) {
            if (button.getActionCommand().equals(letter)) {
                Color color = button.getBackground();
                if (color.equals(AppColors.GREEN)) {
                    // Do nothing
                } else if (color.equals(AppColors.YELLOW) && backgroundColor.equals(AppColors.GREEN)) {
                    button.setBackground(backgroundColor);
                    button.setForeground(foregroundColor);
                } else {
                    button.setBackground(backgroundColor);
                    button.setForeground(foregroundColor);
                }
                break;
            }
        }
    }

    /**
     * Resets all button colors to their default values.
     */
    public void resetDefaultColors() {
        for (JButton button : buttons) {
            button.setBackground(null);
            button.setForeground(null);
        }
    }

    /**
     * Retrieves the main JPanel object containing the keyboard layout.
     *
     * @return The JPanel object representing the in-game keyboard.
     */
    public JPanel getPanel() {
        return panel;
    }

}

