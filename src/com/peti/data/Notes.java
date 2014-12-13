package com.peti.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Notes implements Serializable {

	private List<Note> notes;
	
	public Notes(){
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note n){
		notes.add(n);
	}
	
	public void removeNote(int i){
		if(i>=0 && i<notes.size()){
			notes.remove(i);
		}
	}
	
	public List<Note> getNotes(){
		return notes;
	}
	
	public void load(){
		try{
		FileInputStream fis = new FileInputStream("save.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		notes = (List<Note>) ois.readObject();
		ois.close();
		} catch(Exception e){
			e.printStackTrace();	
		}	
	}

	public void save(){
		try{
		FileOutputStream fos = new FileOutputStream("save.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(notes);
		oos.close();
		} catch(Exception e){
			e.printStackTrace();	
		}	
	}
	
	

}
