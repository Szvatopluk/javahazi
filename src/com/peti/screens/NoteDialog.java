package com.peti.screens;

import com.peti.data.Note;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NoteDialog extends JDialog {

  private Note note;

  public NoteDialog(){
      setModal(true);
      setSize(new Dimension(640,480));
  }

  public void showDialog(Note note){
      this.note = note;
      setTitle(note != null ? "Edit note" : "Add note");
      setVisible(true);
  }

  public Note getNote(){
      if(note == null){
          note = new Note();
      }
      note.setSize(new Dimension(100, 200));
      Random rnd = new Random();
      note.setSize(new Dimension(100, 200));
      note.setPosition(new Point(50 + rnd.nextInt(200), 50 + rnd.nextInt(200)));
      return note;
  }

}
