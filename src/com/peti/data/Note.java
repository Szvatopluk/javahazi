package com.peti.data;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Note data record
 */
public class Note implements Serializable {
    private	String text;
    private	Date warning;
    private	Color background;
    private	Color textcolor;
    private	Dimension size;
    private	Point position;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Date getWarning() {
        return warning;
    }
    public void setWarning(Date warning) {
        this.warning = warning;
    }
    public Color getBackground() {
        return background;
    }
    public void setBackground(Color background) {
        this.background = background;
    }
    public Color getTextcolor() {
        return textcolor;
    }
    public void setTextcolor(Color textcolor) {
        this.textcolor = textcolor;
    }
    public Dimension getSize() {
        return size;
    }
    public void setSize(Dimension size) {
        this.size = size;
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
}
