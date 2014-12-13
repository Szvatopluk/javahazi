package com.peti.screens;

import com.peti.data.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NotePanel extends JTextArea implements MouseListener, MouseMotionListener {

    private Note note;

    private int clickedX;
    private int clickedY;
    private int clickedWidth;
    private int clickedHeight;
    private int lastX;
    private int lastY;

    public NotePanel(Note note){
        this.note = note;
        setSize(note.getSize());
        setLocation(note.getPosition());
        setForeground(note.getTextcolor());
        setBackground(note.getBackground());
        setText(note.getText());
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

    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getClickCount() >= 2) {
            firePropertyChange("edit", note, null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        getParent().setComponentZOrder(this, 0);
        getParent().repaint();
        clickedX = e.getX();
        clickedY = e.getY();
        lastX = e.getX();
        lastY = e.getY();
        clickedWidth = getWidth();
        clickedHeight = getHeight();
    }

    @Override
    public void mouseReleased(MouseEvent e){
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
        if(clickedWidth - clickedX < 10 && clickedHeight - clickedY < 10){
            setSize(getWidth() + e.getX() - lastX, getHeight() + e.getY() - lastY);
            lastX = e.getX();
            lastY = e.getY();
        }
        else {
            Point location = getLocation();
            setLocation(location.x + e.getX() - clickedX, location.y + e.getY() - clickedY);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e){}


}
