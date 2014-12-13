package com.peti.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NotePanel extends JTextArea implements MouseListener, MouseMotionListener {

    private int clickedX;
    private int clickedY;
    private int clickedWidth;
    private int clickedHeight;
    private int lastX;
    private int lastY;

    public NotePanel(String text){
        setSize(200, 200);
        Random rnd = new Random();
        setLocation(rnd.nextInt(300), rnd.nextInt(300));
        setBackground(Color.YELLOW);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setEditable(false);
        setLineWrap(true);
        setText(text);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getClickCount() >= 2) {
            firePropertyChange("edit", null, null);
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
            firePropertyChange("remove", null, null);
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
