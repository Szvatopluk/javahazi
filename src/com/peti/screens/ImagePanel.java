package com.peti.screens;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String imageFileName){
        URL imageURL = getClass().getResource(imageFileName);
        if(imageURL != null){
            ImageIcon icon = new ImageIcon(imageURL);
            image = icon.getImage();
            setSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }
}
