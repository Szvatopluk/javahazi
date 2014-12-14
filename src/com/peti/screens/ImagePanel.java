package com.peti.screens;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.security.InvalidParameterException;

/**
 * Displays an image as a swing JPanel, used for the background and icons on the screen
 */
public class ImagePanel extends JPanel {
    private Image image;

    /**
     * Creates an image panel using the file passed
     */
    public ImagePanel(String imageFileName){
        URL imageURL = getClass().getResource(imageFileName);
        if(imageURL != null){
            ImageIcon icon = new ImageIcon(imageURL);
            image = icon.getImage();
            setSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
        else {
            throw new InvalidParameterException(String.format("Image file is not found: %s", imageFileName));
        }

    }

    /**
     * Image drawing
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }
}
