package com.peti.screens;

import com.peti.data.Note;
import com.peti.data.Preferences;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class NotePanel extends JTextArea implements MouseListener, MouseMotionListener, ActionListener {

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

    public Note getNote(){
        note.setPosition(getLocation());
        note.setSize(getSize());
        return note;
    }

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

    @Override
    public void mouseClicked(MouseEvent e){
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

    @Override
    public void mousePressed(MouseEvent e){
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

    @Override
    public void mouseReleased(MouseEvent e){
        // Remove note when dragged to top/left corner (trash bin icon)
        if(getX() < 60 && getY() < 70){
            firePropertyChange("remove", note, null);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseDragged(MouseEvent e){
        // Resize if bottom/right corner is dragged
        if(clickedWidth - clickedX < 10 && clickedHeight - clickedY < 10){
            setSize(getWidth() + e.getX() - lastX, getHeight() + e.getY() - lastY);
            lastX = e.getX();
            lastY = e.getY();
        }
        // Relocate if dragged
        else {
            Point location = getLocation();
            setLocation(location.x + e.getX() - clickedX, location.y + e.getY() - clickedY);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e){}


    /**
     * Warning timer action handler
     */
    @Override
    public void actionPerformed(ActionEvent e){
        isWarning = true;
        if(timerCount++ < 20){
            if(timerCount % 2 == 0){
                Preferences.playWarningSound();
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

}
