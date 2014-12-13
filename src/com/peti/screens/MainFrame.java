package com.peti.screens;

import com.peti.data.Note;
import com.peti.data.Notes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainFrame extends JFrame implements PropertyChangeListener {

    private ImagePanel background = new ImagePanel("/images/parafa.jpg");
    private ImagePanel trashBin = new ImagePanel("/images/szemetes.png");
    private ImagePanel addNote = new ImagePanel("/images/uj.png");
    private ImagePanel settings = new ImagePanel("/images/szemetes.png");
    private JPanel notesPanel = new JPanel();
    private NoteDialog noteDialog = new NoteDialog();

    public void init(){
        setTitle("Java Notes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Container contentPane = getContentPane();
        getContentPane().setLayout(null);

        background.setLocation(0, 0);
        contentPane.add(background, 0);

        trashBin.setLocation(10, 10);
        contentPane.add(trashBin, 0);

        addNote.setLocation(80, 10);
        addNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addNoteMouseClicked();
            }
        });
        contentPane.add(addNote, 0);

        notesPanel.setLocation(0,0);
        notesPanel.setSize(getSize());
        notesPanel.setOpaque(false);
        notesPanel.setLayout(null);
        contentPane.add(notesPanel, 0);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadData();
            }
            @Override
            public void componentHidden(ComponentEvent e) {
                saveData();
                System.exit(0);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                notesPanel.setSize(getSize());
            }
        });

    }

    private void loadData(){
        Notes notes = new Notes();
        notes.load();
        notesPanel.removeAll();
        for(Note note : notes.getNotes()){
            NotePanel notePanel = new NotePanel(note);
            notePanel.addPropertyChangeListener(this);
            notesPanel.add(notePanel);
        }
        notesPanel.repaint();
    }

    private void saveData(){
        Notes notes = new Notes();
        for(Component component : notesPanel.getComponents()){
            notes.addNote(((NotePanel)component).getNote());
        }
        notes.save();
    }

    private void addNoteMouseClicked(){
        noteDialog.showDialog(null);
        NotePanel notePanel = new NotePanel(noteDialog.getNote());
        notePanel.addPropertyChangeListener(this);
        notesPanel.add(notePanel,0);
        notePanel.repaint();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt){
        if(evt.getPropertyName().equals("remove")){
            notesPanel.remove((NotePanel)evt.getSource());
            notesPanel.repaint();
        }
        if(evt.getPropertyName().equals("edit")){
            noteDialog.showDialog((Note)evt.getOldValue());
        }
    }

}
