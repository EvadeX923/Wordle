package edu.wm.cs.cs301.f2024.wordle.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wm.cs.cs301.f2024.wordle.model.WordleModel;

/**
 * The ReadWordsRunnable class reads a list of words from a resource file and 
 * updates the WordleModel object with this word list. The process is then logged.
 */
public class ReadWordsRunnable implements Runnable {
    /** A Logger object used to log the execution of the word reading process. */
    private final static Logger LOGGER = Logger.getLogger(ReadWordsRunnable.class.getName());
    
    /** The WordleModel object that contains the game's state and logic, initialized in the constructor. */
    private final WordleModel model;

    /**
     * Constructs a ReadWordsRunnable object, initializes the logger, 
     * and prepares the word list for the given WordleModel.
     *
     * @param model The WordleModel object that contains the game's state and logic
     */
    public ReadWordsRunnable(WordleModel model) {
        LOGGER.setLevel(Level.INFO);

        try {
            FileHandler fileTxt = new FileHandler("./logging.txt");
            LOGGER.addHandler(fileTxt);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.model = model;
    }

    /**
     * Starts the word reading process in a separate thread. 
     * Creates a word list from the resource file and updates the 
     * WordleModel with this list. 
     * If an exception occurs during reading, it logs the error 
     * and initializes an empty word list in the model.
     */
    @Override
    public void run() {
        List<String> wordlist;

        try {
            wordlist = createWordList();
            LOGGER.info("Created word list of " + wordlist.size() + " words.");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
            wordlist = new ArrayList<>();
        }

        model.setWordList(wordlist);
        model.generateCurrentWord();
    }

    /**
     * Delivers the input stream for the resource file containing words.
     * 
     * @return An InputStream object to read the resource file.
     * 
     */
    private InputStream deliverInputStream() {
        String text = "/resources/usa.txt";

        InputStream stream = Wordle.class.getResourceAsStream(text);
        
        if (null == stream) {
            System.out.println("Failed to open stream with " + text);
            System.exit(0);
        } else {
            System.out.println("Successfully opened inputstream for " + text);
        }
        
        return stream;
    }

    /**
     * Creates a list of words from the input stream by reading the 
     * resource file line by line. If the lines match the minimum 
     * length  are added, then they are added to the list.
     * 
     * @return A List<String> containing words of the required length.
     * 
     */
    private List<String> createWordList() throws IOException {
        int minimum = model.getColumnCount();
        List<String> wordlist = new ArrayList<>();

        InputStream stream = deliverInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line = reader.readLine();
        while (line != null) {
            line = line.trim();
            if (line.length() == minimum) {
                wordlist.add(line);
            }
            line = reader.readLine();
        }
        reader.close();

        return wordlist;
    }
}