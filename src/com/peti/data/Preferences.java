package com.peti.data;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Preferences {
	private String sound;
	private String background;
	private int maxNotes;
    // default size

    private static Clip warningClip;


	public void load(){
		
		
	}
	
	public void save(){
		
		
		
	}
	
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public int getMaxNotes() {
		return maxNotes;
	}
	public void setMaxNotes(int maxNotes) {
		this.maxNotes = maxNotes;
	}


    public static void playWarningSound(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Preferences.class.getResource("/sounds/ding.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

}
