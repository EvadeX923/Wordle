package edu.wm.cs.cs301.f2024.wordle.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

/** The InstructionsDialog class is a dialog that displays the game instructions. */
public class InstructionsDialog extends JDialog {
	/** Serialization ID. */
    private static final long serialVersionUID = 1L;

    /** An object that closes the dialog, initialized in the constructor. */
    private final CancelAction cancelAction;

    /** An object that loads and displays the instructions in HTML file, initialized by the createMainPanel method which is called in the constructor. */
    private JEditorPane editorPane;

    /**
     * Constructs an InstructionsDialog object for the Wordle game.
     * The dialog loads instructions from an HTML file and displays them.
     *
     * @param view the WordleFrame object associated with this dialog
     */
    public InstructionsDialog(WordleFrame view) {
        super(view.getFrame(), "Instructions", true);
        this.cancelAction = new CancelAction();

        add(createMainPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(view.getFrame());
        setVisible(true);
    }

    /**
     * Creates and sets up the main panel that contains the JEditorPane 
     * displaying the instructions which are loaded in from an HTML file.
     *
     * @return the JPanel object containing the editor pane and scroll pane
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        URL url = InstructionsDialog.class.getResource("/resources/instructions.htm");

        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setPreferredSize(new Dimension(600, 480));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates the button panel that contains a "Cancel" button 
     * for closing the dialog and  checks for the Escape key 
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
         * Closes the dialog when the action is triggered.
         *
         * @param event the ActionEvent object triggered by the cancel button or Escape key
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            dispose();
        }
    }
}

