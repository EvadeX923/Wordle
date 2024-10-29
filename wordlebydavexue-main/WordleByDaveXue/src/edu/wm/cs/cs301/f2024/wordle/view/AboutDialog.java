package edu.wm.cs.cs301.f2024.wordle.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * The AboutDialog class is a dialog that displays information about the Wordle game, 
 * including its creator, date of creation, and version.
 */
public class AboutDialog extends JDialog {
	/** Serialization ID. */
    private static final long serialVersionUID = 1L;

    /** The object to close the dialog, initialized in the constructor. */
    private final CancelAction cancelAction;

    /**
     * Constructs an AboutDialog object with the specified WordleFrame.
     *
     * @param view the WordleFrame object that this dialog is associated with
     */
    public AboutDialog(WordleFrame view) {
        super(view.getFrame(), "About", true);
        this.cancelAction = new CancelAction();
        
        add(createMainPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(view.getFrame());
        setVisible(true);
    }
    
    /**
     * Creates a panel containing information about Wordle.
     *
     * @return a JPanel object containing the information about Wordle
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        Font titleFont = AppFonts.getTitleFont();
        Font textFont = AppFonts.getTextFont();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 30);
        
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label = new JLabel("About Wordle");
        label.setFont(titleFont);
        label.setHorizontalAlignment(JLabel.CENTER);
        Color backgroundColor = label.getBackground();
        panel.add(label, gbc);
        
        gbc.gridy++;
        String text = "Wordle was created by software engineer "
                + "and former Reddit employee, Josh Wardle, and "
                + "was launched in October 2021.";
        JTextArea textArea = new JTextArea(4, 25);
        textArea.setBackground(backgroundColor);
        textArea.setEditable(false);
        textArea.setFont(textFont);
        textArea.setLineWrap(true);
        textArea.setText(text);
        textArea.setWrapStyleWord(true);
        panel.add(textArea, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy++;
        label = new JLabel("Author:");
        label.setFont(textFont);
        panel.add(label, gbc);
        
        gbc.gridx++;
        label = new JLabel("Gilbert G. Le Blanc");
        label.setFont(textFont);
        panel.add(label, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        label = new JLabel("Date Created:");
        label.setFont(textFont);
        panel.add(label, gbc);
        
        gbc.gridx++;
        label = new JLabel("31 March 2022");
        label.setFont(textFont);
        panel.add(label, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        label = new JLabel("Version:");
        label.setFont(textFont);
        panel.add(label, gbc);
        
        gbc.gridx++;
        label = new JLabel("1.0");
        label.setFont(textFont);
        panel.add(label, gbc);
        
        return panel;
    }
    
    /**
     * Creates the button panel that contains a "Cancel" button 
     * for closing the dialog and checks for the Escape key 
     * to trigger the cancel action.
     *
     * @return the JPanel object containing the cancel button
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        
        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
        ActionMap actionMap = panel.getActionMap();
        actionMap.put("cancelAction", cancelAction);
        
        JButton button = new JButton("Cancel");
        button.addActionListener(cancelAction);
        panel.add(button);
        
        return panel;
    }
    
    /**
     * The CancelAction class defines the action for closing the dialog 
     * when the user clicks the "Cancel" button or presses the Escape key.
     */
    private class CancelAction extends AbstractAction {
    	/** Serialization ID. */
        private static final long serialVersionUID = 1L;

        /**
         * Closes the AboutDialog when the action is performed.
         *
         * @param event the ActionEvent object triggered by the button click or key press
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            dispose();
        }
        
    }
}