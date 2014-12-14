package com.peti.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Notes collection with save and load support
 */
public class Notes implements Serializable {
    private static final Logger logger = Logger.getLogger(Notes.class.getName());

    private List<Note> notes;

    /**
     * Creates an empty notes collection
     */
    public Notes(){
        notes = new ArrayList<Note>();
    }

    /**
     * Adds a note to the notes collection
     */
    public void addNote(Note n){
        notes.add(n);
    }

    /**
     * Gets the notes collection
     */
    public List<Note> getNotes(){
        return notes;
    }

    /**
     * Loads the notes collection from file
     */
    public void load(){
        try {
            FileInputStream fis = new FileInputStream("save.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            notes = (List<Note>) ois.readObject();
            ois.close();
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Exception during load()", e);
        }
    }

    /**
     * Saves the notes collection from file
     */
    public void save(){
        try {
            FileOutputStream fos = new FileOutputStream("save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(notes);
            oos.close();
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Exception during save()", e);
        }
    }
}
