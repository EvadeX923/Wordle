package edu.wm.cs.cs301.f2024.wordle.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;

/**
 * The StatisticsDialog class is a dialog that displays statistics about the player's performance in the Wordle game.
 * It includes information like the number of games played, win percentage, current streak, and longest streak,
 * and a guess distribution chart.
 */
public class StatisticsDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    /** An object that handles the action when the user decides to exit the game, initialized in the constructor. */
    private final ExitAction exitAction;

    /** An object that handles the action when the user decides to continue playing, initialized in the constructor. */
    private final NextAction nextAction;

    /** The WordleFrame object that represents the main view of the game, initialized in the constructor. */
    private final WordleFrame view;

    /** The WordleModel object that contains the game's state and logic, initialized in the constructor. */
    private final WordleModel model;

    /**
     * Constructs a new StatisticsDialog that shows the game's statistics.
     * 
     * @param view  the WordleFrame object that representing the main view of the game.
     * @param model the WordleModel object that contains the game's state and logic.
     */
    public StatisticsDialog(WordleFrame view, WordleModel model) {
        super(view.getFrame(), "Statistics", true);
        this.view = view;
        this.model = model;
        this.exitAction = new ExitAction();
        this.nextAction = new NextAction();

        add(createMainPanel(), BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(view.getFrame());
        setVisible(true);
    }

    /**
     * Creates the main panel that holds the top and bottom panels of the statistics dialog.
     * 
     * @return JPanel containing the statistics summary and guess distribution chart
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        panel.add(createTopPanel(), BorderLayout.NORTH);
        panel.add(createBottomPanel(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the top panel that contains the title and summary statistics.
     * 
     * @return JPanel containing the game statistics title and summary
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        panel.add(createTitlePanel(), BorderLayout.NORTH);
        panel.add(createSummaryPanel(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the title panel that shows the "Statistics" heading.
     * 
     * @return JPanel with the title label
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JLabel label = new JLabel("Statistics");
        label.setFont(AppFonts.getTitleFont());
        panel.add(label);

        return panel;
    }

    /**
     * Creates the bottom panel that has the guess distribution chart.
     * 
     * @return JPanel with guess distribution statistics
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        panel.add(createSubtitlePanel(), BorderLayout.NORTH);
        panel.add(new DistributionPanel(view, model), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the subtitle panel for the guess distribution section.
     * 
     * @return JPanel with the "Guess Distribution" subtitle
     */
    private JPanel createSubtitlePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JLabel label = new JLabel("Guess Distribution");
        label.setFont(AppFonts.getTitleFont());
        panel.add(label);

        return panel;
    }

    /**
     * Creates the summary panel that displays statistics like the number of games played,
     * win percentage, current streak, and longest streak.
     * 
     * @return JPanel with summary statistics
     */
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        int totalGamesPlayed = model.getStatistics().getTotalGamesPlayed();
        int currentStreak = model.getStatistics().getCurrentStreak();
        int longestStreak = model.getStatistics().getLongestStreak();
        List<Integer> wordsGuessed = model.getStatistics().getWordsGuessed();
        int percent = (wordsGuessed.size() * 1000 + 5) / (totalGamesPlayed * 10);

        panel.add(createStatisticsPanel(totalGamesPlayed, "Played", ""));
        panel.add(createStatisticsPanel(percent, "Win %", ""));
        panel.add(createStatisticsPanel(currentStreak, "Current", "Streak"));
        panel.add(createStatisticsPanel(longestStreak, "Longest", "Streak"));

        return panel;
    }

    /**
     * Creates a panel to display a single statistic with two text lines below the value.
     * 
     * @param value the numerical value to display
     * @param line1 the first line of text below the value
     * @param line2 the second line of text below the value
     * @return JPanel displaying the statistic and text
     */
    private JPanel createStatisticsPanel(int value, String line1, String line2) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        Font textFont = AppFonts.getTextFont();

        JLabel valueLabel = new JLabel(String.format("%,d", value));
        valueLabel.setFont(AppFonts.getTitleFont());
        valueLabel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(valueLabel);

        JLabel label = new JLabel(line1);
        label.setFont(textFont);
        label.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label);

        label = new JLabel(line2);
        label.setFont(textFont);
        label.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label);

        return panel;
    }

    /**
     * Creates the button panel that contains the "Next Word" and "Exit Wordle" buttons.
     * 
     * @return JPanel with buttons for next word and exit
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exitAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "nextAction");
        ActionMap actionMap = panel.getActionMap();
        actionMap.put("nextAction", nextAction);
        actionMap.put("exitAction", exitAction);

        JButton nextButton = new JButton("Next Word");
        nextButton.addActionListener(nextAction);
        panel.add(nextButton);

        JButton exitButton = new JButton("Exit Wordle");
        exitButton.addActionListener(exitAction);
        panel.add(exitButton);

        Dimension nextDimension = nextButton.getPreferredSize();
        Dimension exitDimension = exitButton.getPreferredSize();
        int maxWidth = Math.max(nextDimension.width, exitDimension.width) + 10;
        nextButton.setPreferredSize(new Dimension(maxWidth, nextDimension.height));
        exitButton.setPreferredSize(new Dimension(maxWidth, exitDimension.height));

        return panel;
    }

    /**
     * ExitAction handles the event of the user pressing the "Exit Wordle" button or pressing the Escape key.
     */
    private class ExitAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        /**
         * Closes the dialog when the action is triggered.
         *
         * @param event the ActionEvent object triggered by the cancel button or Escape key
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            dispose();
            view.shutdown();
        }
    }

    /**
     * NextAction handles the event of the user pressing the "Next Word" button or pressing the Enter key.
     */
    private class NextAction extends AbstractAction {
    	/** Serialization ID. */
        private static final long serialVersionUID = 1L;
        
        /**
         * Closes the dialog, prepares the model and view for a new word when the action is triggered.
         *
         * @param event the ActionEvent object triggered by the cancel button or Escape key
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            dispose();
            model.initialize();
            view.repaintWordleGridPanel();
            view.resetDefaultColors();
        }
    }
}
