package com.peti.dialogs;

import com.peti.data.PrefAccess;
import com.peti.data.Prefs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * Preferences data editor dialog
 */
public class PreferencesDialog extends JDialog {

    JFormattedTextField noteSizeField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
    JFormattedTextField noteCountField = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
    JTextField soundFileField = new JTextField();
    JTextField imageFileField = new JTextField();

    /**
     * Creates a preferences editor dialog, initializing the dialog components
     */
    public PreferencesDialog() {
        setModal(true);
        setSize(new Dimension(500, 300));
        setLocation(0, 0);
        setTitle("Edit user preferences");

        Container contentPane = getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        JLabel noteSizeLabel = new JLabel("Default note size:");
        layout.putConstraint(SpringLayout.NORTH, noteSizeLabel, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, noteSizeLabel, 10, SpringLayout.WEST, this);
        contentPane.add(noteSizeLabel);

        noteSizeField.setPreferredSize(new Dimension(100, 20));
        layout.putConstraint(SpringLayout.NORTH, noteSizeField, 0, SpringLayout.NORTH, noteSizeLabel);
        layout.putConstraint(SpringLayout.WEST, noteSizeField, 200, SpringLayout.EAST, this);
        contentPane.add(noteSizeField);

        JLabel noteCountLabel = new JLabel("Maximum note count:");
        layout.putConstraint(SpringLayout.NORTH, noteCountLabel, 10, SpringLayout.SOUTH, noteSizeLabel);
        layout.putConstraint(SpringLayout.WEST, noteCountLabel, 10, SpringLayout.WEST, this);
        contentPane.add(noteCountLabel);

        noteCountField.setPreferredSize(new Dimension(100, 20));
        layout.putConstraint(SpringLayout.NORTH, noteCountField, 0, SpringLayout.NORTH, noteCountLabel);
        layout.putConstraint(SpringLayout.WEST, noteCountField, 200, SpringLayout.EAST, this);
        contentPane.add(noteCountField);

        JLabel soundFileLabel = new JLabel("Sound file:");
        layout.putConstraint(SpringLayout.NORTH, soundFileLabel, 10, SpringLayout.SOUTH, noteCountLabel);
        layout.putConstraint(SpringLayout.WEST, soundFileLabel, 10, SpringLayout.WEST, this);
        contentPane.add(soundFileLabel);

        soundFileField.setPreferredSize(new Dimension(250, 20));
        layout.putConstraint(SpringLayout.NORTH, soundFileField, 0, SpringLayout.NORTH, soundFileLabel);
        layout.putConstraint(SpringLayout.WEST, soundFileField, 200, SpringLayout.EAST, this);
        contentPane.add(soundFileField);

        JLabel imageFileLabel = new JLabel("Background image:");
        layout.putConstraint(SpringLayout.NORTH, imageFileLabel, 10, SpringLayout.SOUTH, soundFileLabel);
        layout.putConstraint(SpringLayout.WEST, imageFileLabel, 10, SpringLayout.WEST, this);
        contentPane.add(imageFileLabel);

        imageFileField.setPreferredSize(new Dimension(250, 20));
        layout.putConstraint(SpringLayout.NORTH, imageFileField, 0, SpringLayout.NORTH, imageFileLabel);
        layout.putConstraint(SpringLayout.WEST, imageFileField, 200, SpringLayout.EAST, this);
        contentPane.add(imageFileField);

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
     * Should be called to display the dialog, updates the components based on the preferences
     */
    public void showDialog(){
        noteSizeField.setValue(PrefAccess.getPreferences().getInt(Prefs.NOTE_SIZE, Prefs.DEFAULT_NOTE_SIZE));
        noteCountField.setValue(PrefAccess.getPreferences().getInt(Prefs.MAX_NOTE_COUNT, Prefs.DEFAULT_NOTE_COUNT));
        soundFileField.setText(PrefAccess.getPreferences().get(Prefs.SOUND_FILE, Prefs.DEFAULT_SOUND_FILE));
        imageFileField.setText(PrefAccess.getPreferences().get(Prefs.BACKGROUND_IMAGE, Prefs.DEFAULT_BACKGROUND_IMAGE));
        setVisible(true);
    }

    /**
     * Ok button handler, saves the preferences from the dialog components
     */
    private void okButtonAction(){
        PrefAccess.getPreferences().putInt(Prefs.NOTE_SIZE, (Integer)noteSizeField.getValue());
        PrefAccess.getPreferences().putInt(Prefs.MAX_NOTE_COUNT, (Integer)noteCountField.getValue());
        PrefAccess.getPreferences().put(Prefs.SOUND_FILE, soundFileField.getText());
        PrefAccess.getPreferences().put(Prefs.BACKGROUND_IMAGE, imageFileField.getText());
        setVisible(false);
    }

    /**
     * Cancel button handler, just hides the dialog
     */
    private void cancelButtonAction(){
        setVisible(false);
    }

}