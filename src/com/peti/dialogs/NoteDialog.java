package com.peti.dialogs;

import com.peti.data.Note;
import com.peti.data.PrefAccess;
import com.peti.data.Prefs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Random;

/**
 * Note data editor dialog
 */
public class NoteDialog extends JDialog {

    private Note note;

    JTextArea textArea = new JTextArea();
    JButton textColorButton = new JButton();
    JButton backgroundColorButton = new JButton();
    JSpinner warningSpinner;

    /**
     * Creates a note editor dialog, initializing the dialog components
     */
    public NoteDialog() {
        setModal(true);
        setSize(new Dimension(500, 400));
        setLocation(0, 0);
        Container contentPane = getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        JLabel textLabel = new JLabel("Note text:");
        textLabel.setPreferredSize(new Dimension(120,30));
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, textLabel, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, textLabel, 10, SpringLayout.WEST, this);
        contentPane.add(textLabel);

        JScrollPane scrollPane = new JScrollPane(textArea);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, textLabel);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, 120, SpringLayout.SOUTH, textLabel);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.EAST, textLabel);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, contentPane);
        contentPane.add(scrollPane);

        JLabel textColorLabel = new JLabel("Text color:");
        textColorLabel.setPreferredSize(new Dimension(120,30));
        textColorLabel.setVerticalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, textColorLabel, 10, SpringLayout.SOUTH, scrollPane);
        layout.putConstraint(SpringLayout.WEST, textColorLabel, 10, SpringLayout.WEST, this);
        contentPane.add(textColorLabel);

        textColorButton.setText(" ");
        textColorButton.setBackground(Color.BLACK);
        textColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(textColorButton, "Text Color", textColorButton.getBackground());
                if(color != null){
                    textColorButton.setBackground(color);
                }
            }
        });
        layout.putConstraint(SpringLayout.NORTH, textColorButton, 0, SpringLayout.NORTH, textColorLabel);
        layout.putConstraint(SpringLayout.WEST, textColorButton, 10, SpringLayout.EAST, textColorLabel);
        contentPane.add(textColorButton);

        JLabel backgroundColorLabel = new JLabel("Background color:");
        backgroundColorLabel.setPreferredSize(new Dimension(120,30));
        backgroundColorLabel.setVerticalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, backgroundColorLabel, 10, SpringLayout.SOUTH, textColorLabel);
        layout.putConstraint(SpringLayout.WEST, backgroundColorLabel, 0, SpringLayout.WEST, textColorLabel);
        contentPane.add(backgroundColorLabel);

        backgroundColorButton.setText(" ");
        backgroundColorButton.setBackground(Color.YELLOW);
        backgroundColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(backgroundColorButton, "Text Color", backgroundColorButton.getBackground());
                if(color != null){
                    backgroundColorButton.setBackground(color);
                }
            }
        });
        layout.putConstraint(SpringLayout.NORTH, backgroundColorButton, 0, SpringLayout.NORTH, backgroundColorLabel);
        layout.putConstraint(SpringLayout.WEST, backgroundColorButton, 10, SpringLayout.EAST, backgroundColorLabel);
        contentPane.add(backgroundColorButton);

        JLabel warningLabel = new JLabel("Warning date/time:");
        warningLabel.setPreferredSize(new Dimension(120,30));
        warningLabel.setVerticalAlignment(SwingConstants.CENTER);
        layout.putConstraint(SpringLayout.NORTH, warningLabel, 10, SpringLayout.SOUTH, backgroundColorLabel);
        layout.putConstraint(SpringLayout.WEST, warningLabel, 0, SpringLayout.WEST, backgroundColorLabel);
        contentPane.add(warningLabel);

        warningSpinner = new JSpinner(new SpinnerDateModel());
        warningSpinner.setEditor(new JSpinner.DateEditor(warningSpinner, "yyyy-MM-dd HH:mm:ss"));
        layout.putConstraint(SpringLayout.NORTH, warningSpinner, 0, SpringLayout.NORTH, warningLabel);
        layout.putConstraint(SpringLayout.WEST, warningSpinner, 10, SpringLayout.EAST, warningLabel);
        contentPane.add(warningSpinner);

        final JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonAction();
            }
        });
        layout.putConstraint(SpringLayout.SOUTH, okButton, -30, SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, okButton, -10, SpringLayout.HORIZONTAL_CENTER, contentPane);
        contentPane.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelButtonAction();
            }
        });
        layout.putConstraint(SpringLayout.SOUTH, cancelButton, -30, SpringLayout.SOUTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, cancelButton, 10, SpringLayout.HORIZONTAL_CENTER, contentPane);
        contentPane.add(cancelButton);

    }

    /**
     * Should be called to display the dialog, updates the components based on the received note data
     */
    public void showDialog(Note note){
        this.note = note;
        setTitle(note != null ? "Edit note" : "Add note");
        if(note != null) {
            textArea.setText(note.getText());
            textColorButton.setBackground(note.getTextcolor());
            backgroundColorButton.setBackground(note.getBackground());
            warningSpinner.setValue(note.getWarning());
        }
        else {
            textArea.setText("");
            textColorButton.setBackground(Color.BLACK);
            backgroundColorButton.setBackground(Color.YELLOW);
            warningSpinner.setValue(new Date(System.currentTimeMillis() + 60000));
        }
        setVisible(true);
    }

    /**
     * Getter to access the dialogs note data
     */
    public Note getNote(){
        return note;
    }

    /**
     * Ok button handler, updates the note data of the dialog
     */
    private void okButtonAction(){
        if(note == null){
            note = new Note();
            int size = PrefAccess.getPreferences().getInt(Prefs.NOTE_SIZE, Prefs.DEFAULT_NOTE_SIZE);
            note.setSize(new Dimension(size, size));
            Random rnd = new Random();
            note.setPosition(new Point(100 + rnd.nextInt(5) * 10, 100 + rnd.nextInt(5) * 10));
        }
        note.setText(textArea.getText());
        note.setTextcolor(textColorButton.getBackground());
        note.setBackground(backgroundColorButton.getBackground());
        note.setWarning((Date)warningSpinner.getValue());
        setVisible(false);
    }

    /**
     * Cancel button handler, just hides the dialog
     */
    private void cancelButtonAction(){
        setVisible(false);
    }

}
