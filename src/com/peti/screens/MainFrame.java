package com.peti.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class MainFrame extends JFrame implements PropertyChangeListener {

    private ImagePanel background = new ImagePanel("/images/parafa.jpg");
    private ImagePanel trashBin = new ImagePanel("/images/szemetes.png");
    private JPanel notesPanel = new JPanel();

    public void init(){
        setTitle("Java Notes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contentPane = getContentPane();

        getContentPane().setLayout(null);

        background.setLocation(0, 0);
        contentPane.add(background, 0);

        trashBin.setLocation(10, 10);
        contentPane.add(trashBin, 0);

        notesPanel.setLocation(0,0);
        notesPanel.setSize(getSize());
        notesPanel.setOpaque(false);
        notesPanel.setLayout(null);
        contentPane.add(notesPanel, 0);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                notesPanel.setSize(getSize());
            }
        });

    }

    public void updateNotes(List<String> notes){
        notesPanel.removeAll();
        for(String text : notes){
            NotePanel notePanel = new NotePanel(text);
            notePanel.addPropertyChangeListener(this);
            notesPanel.add(notePanel);
        }
        notesPanel.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        if(evt.getPropertyName().equals("remove")){
            notesPanel.remove((NotePanel)evt.getSource());
            notesPanel.repaint();
        }
        if(evt.getPropertyName().equals("edit")){
            System.out.println("edit");
        }
    }

}
