package com.gabs.rpggame;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	private Clip clip;
	public static Sound bg = new Sound("Snowy.wav"); 
	
	public Sound(String path) {
		try {
			File file = new File(Thread.currentThread().getContextClassLoader().getResource(path).getFile());
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			this.clip = AudioSystem.getClip();
			this.clip.open(audioStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play() {
		new Thread() {
			public void start() {
				clip.start();
			}
		}.start();
	}
	
	public void loop() {
		new Thread() {
			public void start() {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}.start();
	}
	
	public void stop() {
		clip.stop();
	}
}	
