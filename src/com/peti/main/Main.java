package com.peti.main;

import com.peti.screens.MainFrame;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.init();
        mainFrame.setVisible(true);
        List<String> notes = Arrays.asList("Aaaa", "Bbbb", "Cccc");
        mainFrame.updateNotes(notes);
    }
}
