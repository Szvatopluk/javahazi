package com.peti.screens;

import com.peti.data.Note;
import com.peti.data.Notes;
import com.peti.data.PrefAccess;
import com.peti.data.Prefs;
import com.peti.dialogs.NoteDialog;
import com.peti.dialogs.PreferencesDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Program main window
 */
public class MainFrame extends JFrame implements PropertyChangeListener {
    private static final Logger logger = Logger.getLogger(MainFrame.class.getName());

    private ImagePanel background;
    private ImagePanel trashBin = new ImagePanel("/images/szemetes.png");
    private ImagePanel addNote = new ImagePanel("/images/uj.png");
    private ImagePanel preferences = new ImagePanel("/images/fogaskerek.png");
    private JPanel notesPanel = new JPanel();
    private NoteDialog noteDialog = new NoteDialog();
    private PreferencesDialog preferencesDialog = new PreferencesDialog();

    /**
     * Initializing the screen components
     */
    public void init(){
        logger.log(Level.INFO, "Java Notes started");

        setTitle("Java Notes");
        int width = PrefAccess.getPreferences().getInt(Prefs.WINDOW_WIDTH, Prefs.DEFAULT_WINDOW_WIDTH);
        int heigth = PrefAccess.getPreferences().getInt(Prefs.WINDOW_HEIGHT, Prefs.DEFAULT_WINDOW_HEIGHT);
        setSize(width, heigth);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Container contentPane = getContentPane();
        getContentPane().setLayout(null);

        String imageFileName = PrefAccess.getPreferences().get(Prefs.BACKGROUND_IMAGE, Prefs.DEFAULT_BACKGROUND_IMAGE);
        background = new ImagePanel(imageFileName);
        background.setLocation(0, 0);
        contentPane.add(background, 0);

        trashBin.setLocation(10, 10);
        contentPane.add(trashBin, 0);

        addNote.setLocation(80, 10);
        addNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addNoteMouseClicked();
            }
        });
        contentPane.add(addNote, 0);

        preferences.setLocation(150, 10);
        preferences.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                preferencesMouseClicked();
            }
        });
        contentPane.add(preferences, 0);

        notesPanel.setLocation(0,0);
        notesPanel.setSize(getSize());
        notesPanel.setOpaque(false);
        notesPanel.setLayout(null);
        contentPane.add(notesPanel, 0);

        noteDialog.setLocationRelativeTo(this);

        preferencesDialog.setLocationRelativeTo(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                logger.log(Level.FINE, "componentShown()");
                loadData();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                logger.log(Level.FINE, "componentHidden()");
                saveData();
                Dimension size = getSize();
                PrefAccess.getPreferences().putInt(Prefs.WINDOW_WIDTH, (int)size.getWidth());
                PrefAccess.getPreferences().putInt(Prefs.WINDOW_HEIGHT, (int)size.getHeight());
                logger.log(Level.INFO, "Java Notes stopped");
                System.exit(0);
            }

            @Override
            public void componentResized(ComponentEvent e) {
                notesPanel.setSize(getSize());
            }
        });

    }

    /**
     * Loads the notes data and adds the notes to the note panel
     */
    private void loadData(){
        Notes notes = new Notes();
        notes.load();
        notesPanel.removeAll();
        for(Note note : notes.getNotes()){
            NotePanel notePanel = new NotePanel(note);
            notePanel.addPropertyChangeListener(this);
            notesPanel.add(notePanel);
        }
        notesPanel.repaint();
    }

    /**
     * Saves the notes data from notes panel
     */
    private void saveData(){
        Notes notes = new Notes();
        for(Component component : notesPanel.getComponents()){
            notes.addNote(((NotePanel)component).getNote());
        }
        notes.save();
    }

    /**
     * Add note icon click handler, invokes the note editor dialog
     */
    private void addNoteMouseClicked(){
        logger.log(Level.FINE, "addNoteMouseClicked()");
        if(notesPanel.getComponents().length < PrefAccess.getPreferences().getInt(Prefs.MAX_NOTE_COUNT, Prefs.DEFAULT_NOTE_COUNT)){
            noteDialog.showDialog(null);
            Note note = noteDialog.getNote();
            if(note != null){
                NotePanel notePanel = new NotePanel(note);
                notePanel.addPropertyChangeListener(this);
                notesPanel.add(notePanel,0);
                notePanel.repaint();
            }
        }
    }

    /**
     * Preferences icon click handler, invokes the preferences dialog
     */
    private void preferencesMouseClicked(){
        logger.log(Level.FINE, "preferencesMouseClicked()");
        preferencesDialog.showDialog();
    }

    /**
     * Property change handler. Events in notes panel (remove and edit) are sent as property change events
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt){
        NotePanel notePanel = (NotePanel)evt.getSource();

        if(evt.getPropertyName().equals("remove")){
            logger.log(Level.FINE, "propertyChange - remove");
            notesPanel.remove(notePanel);
            notesPanel.repaint();
        }
        if(evt.getPropertyName().equals("edit")){
            logger.log(Level.FINE, "propertyChange - edit");
            noteDialog.showDialog((Note)evt.getOldValue());
            notePanel.updateNote();
        }
    }

}
