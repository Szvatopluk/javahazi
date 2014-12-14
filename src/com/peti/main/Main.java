package com.peti.main;

import com.peti.screens.MainFrame;

/**
 * Main class, program entry point
 */
public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.init();
        mainFrame.setVisible(true);
    }
}
