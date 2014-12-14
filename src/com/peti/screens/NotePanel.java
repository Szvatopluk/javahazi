package com.peti.screens;

import com.peti.data.Note;
import com.peti.data.PrefAccess;
import com.peti.data.Prefs;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This panel displays a single note on the screen (added to notes panel of the main frame)
 */
public class NotePanel extends JTextArea implements MouseListener, MouseMotionListener, ActionListener {
    private static final Logger logger = Logger.getLogger(NotePanel.class.getName());

    private Note note;

    private int clickedX;
    private int clickedY;
    private int clickedWidth;
    private int clickedHeight;
    private int lastX;
    private int lastY;

    private Timer timer;
    private int timerCount;
    private boolean isWarning;

    /**
     * Creates a note panel, note data is received as parameter
     */
    public NotePanel(Note note){
        this.note = note;
        setSize(note.getSize());
        setLocation(note.getPosition());
        updateNote();
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setEditable(false);
        setLineWrap(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Getter to access the note data of this panel
     */
    public Note getNote(){
        note.setPosition(getLocation());
        note.setSize(getSize());
        return note;
    }

    /**
     * Updates the note on the screen: colors, text, warning timer
     */
    public void updateNote(){
        setForeground(note.getTextcolor());
        setBackground(note.getBackground());
        setText(note.getText());
        // Setup warning timer
        int warningDelay = (int)(note.getWarning().getTime() -  System.currentTimeMillis());
        // Only in future
        if(warningDelay > 0) {
            if (timer != null) {
                timer.setInitialDelay(warningDelay);
            } else {
                timer = new Timer(500, this);
                timer.setInitialDelay(warningDelay);
                timer.setRepeats(true);
            }
            timer.start();
            timerCount = 0;
            isWarning = false;
        }
    }

    /**
     * Mouse click event handler to process edit (double click) or turning off warning (single click)
     */
    @Override
    public void mouseClicked(MouseEvent e){
        logger.log(Level.FINE, "mouseClicked()");
        // Edit note on double click
        if(e.getClickCount() >= 2) {
            firePropertyChange("edit", note, null);
        }
        // Stop warning on click
        if(isWarning) {
            timer.stop();
            isWarning = false;
            setBackground(note.getBackground());
        }
    }

    /**
     * Mouse press event handler to handle pop up, resize and move.
     */
    @Override
    public void mousePressed(MouseEvent e){
        logger.log(Level.FINE, "mousePressed()");
        // Move to top if clicked
        getParent().setComponentZOrder(this, 0);
        getParent().repaint();
        // Save coordinates for resize and relocate
        clickedX = e.getX();
        clickedY = e.getY();
        lastX = e.getX();
        lastY = e.getY();
        clickedWidth = getWidth();
        clickedHeight = getHeight();
    }

    /**
     * Mouse released event handler to process note removal
     */
    @Override
    public void mouseReleased(MouseEvent e){
        logger.log(Level.FINE, "mouseReleased()");
        // Remove note when dragged to top/left corner (trash bin icon)
        if(getX() < 60 && getY() < 70){
            if(timer != null){
                timer.stop();
            }
            firePropertyChange("remove", note, null);
        }
    }

    /**
     * Empty mouse event handler to implement MouseListener
     */
    @Override
    public void mouseEntered(MouseEvent e){}

    /**
     * Empty mouse event handler to implement MouseListener
     */
    @Override
    public void mouseExited(MouseEvent e){}

    /**
     * Mouse drag event handler to process move and resize, based on first click location
     */
    @Override
    public void mouseDragged(MouseEvent e){
        // Resize if bottom/right corner is dragged
        if(clickedWidth - clickedX < 10 && clickedHeight - clickedY < 10){
            setSize(getWidth() + e.getX() - lastX, getHeight() + e.getY() - lastY);
            lastX = e.getX();
            lastY = e.getY();
        }
        // Relocate if dragged somewhere else
        else {
            Point location = getLocation();
            setLocation(location.x + e.getX() - clickedX, location.y + e.getY() - clickedY);
        }
    }

    /**
     * Empty mouse event handler to implement MouseMotionListener
     */
    @Override
    public void mouseMoved(MouseEvent e){}


    /**
     * Warning timer action handler, changes the background color and plays sound
     */
    @Override
    public void actionPerformed(ActionEvent e){
        logger.log(Level.FINE, "timer - actionPerformed()");
        isWarning = true;
        if(timerCount++ < 20){
            if(timerCount % 2 == 0){
                playWarningSound();
                setBackground(Color.RED);
            }
            else {
                setBackground(note.getBackground());
            }

        }
        else {
            timer.stop();
            isWarning = false;
            setBackground(note.getBackground());
        }
    }

    /**
     * Helper method to play the warning sound
     */
    private void playWarningSound(){
        try {
            String soundFileName = PrefAccess.getPreferences().get(Prefs.SOUND_FILE, Prefs.DEFAULT_SOUND_FILE);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundFileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Exception during playWarningSound()", e);
            return;
        }

    }


}
