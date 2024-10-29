package edu.wm.cs.cs301.f2024.wordle.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;

/**
 * WordleFrame is the main frame for the Wordle game GUI. It creates the game
 * window, sets up the components including the grid, keyboard panel, and menu,
 * and handles the game shutdown process.
 */
public class WordleFrame {

    /** The JFrame for the Wordle game window. */
    private final JFrame frame;
    
    /** The KeyboardPanel object that represents the on-screen keyboard for Wordle input, initialized in the constructor. */
    private final KeyboardPanel keyboardPanel;
    
    /** The WordleModel object that contains the game's state and logic, initialized in the constructor. */
    private final WordleModel model;
    
    /** The WordleGridPanel object that represents the Wordle grid, initialized in the constructor. */
    private final WordleGridPanel wordleGridPanel;

    /**
     * Constructs a WordleFrame using the specified Wordle model. It initializes
     * the frame, keyboard panel, and wordle grid, and then sets up and shows the
     * GUI.
     * 
     * @param model the WordleModel that contains the game logic and state
     */
    public WordleFrame(WordleModel model) {
        this.model = model;
        this.keyboardPanel = new KeyboardPanel(this, model);
        int width = keyboardPanel.getPanel().getPreferredSize().width;
        this.wordleGridPanel = new WordleGridPanel(this, model, width);
        this.frame = createAndShowGUI();
    }

    /**
     * Creates and configures the main game window, adding the necessary components
     * such as the title panel, grid panel, and keyboard panel.
     * 
     * @return the initialized JFrame object that represents the main window
     */
    private JFrame createAndShowGUI() {
        JFrame frame = new JFrame("Wordle");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                shutdown();
            }
        });

        frame.add(createTitlePanel(), BorderLayout.NORTH);
        frame.add(wordleGridPanel, BorderLayout.CENTER);
        frame.add(keyboardPanel.getPanel(), BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        System.out.println("Frame size: " + frame.getSize());

        return frame;
    }

    /**
     * Creates the menu bar for the game, which includes options for viewing
     * instructions and "about" information.
     * 
     * @return the JMenuBar object containing the Help menu
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem instructionsItem = new JMenuItem("Instructions...");
        instructionsItem.addActionListener(event -> new InstructionsDialog(this));
        helpMenu.add(instructionsItem);

        JMenuItem aboutItem = new JMenuItem("About...");
        aboutItem.addActionListener(event -> new AboutDialog(this));
        helpMenu.add(aboutItem);

        return menuBar;
    }

    /**
     * Creates the title panel with a JLabel displaying the "Wordle" game title.
     * and sets up key bindings for handling the Escape key.
     * 
     * @return the JPanel object containing the game title
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
        ActionMap actionMap = panel.getActionMap();
        actionMap.put("cancelAction", new CancelAction());

        JLabel label = new JLabel("Wordle");
        label.setFont(AppFonts.getTitleFont());
        panel.add(label);

        return panel;
    }

    /**
     * Shuts down the game, writing any statistics to file, disposing of the
     * frame, and exiting the application.
     */
    public void shutdown() {
        model.getStatistics().writeStatistics();
        frame.dispose();
        System.exit(0);
    }

    /**
     * Resets the colors of the on-screen keyboard to the default colors.
     */
    public void resetDefaultColors() {
        keyboardPanel.resetDefaultColors();
    }

    /**
     * Sets the color of a specific letter.
     * 
     * @param letter the letter to change color for
     * @param backgroundColor the background color to set
     * @param foregroundColor the foreground color to set
     */
    public void setColor(String letter, Color backgroundColor, Color foregroundColor) {
        keyboardPanel.setColor(letter, backgroundColor, foregroundColor);
    }

    /**
     * Repaints the Wordle grid panel to reflect the current state of the game.
     */
    public void repaintWordleGridPanel() {
        wordleGridPanel.repaint();
    }

    /**
     * Returns the main JFrame object for the Wordle game.
     * 
     * @return the JFrame object representing the game window
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * The CancelAction class defines the action for closing the dialog 
     * when the user presses the Escape key.
     */
    private class CancelAction extends AbstractAction {
    	/** Serialization ID. */
        private static final long serialVersionUID = 1L;

        /**
         * Shuts down the game when the action is performed.
         *
         * @param event the ActionEvent object triggered by the button click or key press
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            shutdown();
        }
    }
}

