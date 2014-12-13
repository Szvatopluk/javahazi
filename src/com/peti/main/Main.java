package com.peti.main;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello Worlds!");
//        Note n = new Note();
//        n.setText("Kakamaki");
//        Notes lista = new Notes();
//        lista.addNote(n);
//        lista.save();
    	Notes lista = new Notes();
    	lista.load();
    	for(Note n : lista.getNotes()){
    		System.out.println(n.getText());
    	}
    	System.out.println("Hello World!");
    }
}
