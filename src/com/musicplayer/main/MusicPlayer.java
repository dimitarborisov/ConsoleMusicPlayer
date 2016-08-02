package com.musicplayer.main;

import java.net.URL;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MusicPlayer {
	MediaPlayer mediaPlayer;
	
	public MusicPlayer(){
		new JFXPanel();
	}
	
	public void load(URL url){
		mediaPlayer = new MediaPlayer(new Media(url.toString()));
	}
	
	public void play(){
		mediaPlayer.play();
	}
	
	public void pause(){
		mediaPlayer.pause();
	}
	
	public void stop(){
		mediaPlayer.stop();
	}
	
	public void volume(double i){
		mediaPlayer.setVolume(i / 100);
	}

	public void seek(double time){
		mediaPlayer.seek(new Duration(time));
	}
	
	public double getCurrentTime(){
		return mediaPlayer.getCurrentTime().toMillis();
	}
	
	public double getDuartion(){
		return mediaPlayer.getTotalDuration().toMillis();
	}
	
	public MediaPlayer getMP(){
		return mediaPlayer;
	}
	
	public String getVisualTime(){
		return drawCursor((getCurrentTime() / getDuartion()) * 100);
	}
	
	public Status getStatus(){
		if(mediaPlayer == null){
			return null;
		}
		return mediaPlayer.getStatus();
	}
	
	public void dispose(){
		mediaPlayer.dispose();
	}
	
	private String drawCursor(double time) {
		// 100% [==================================================]
		// 0% [ ]

		StringBuilder s = new StringBuilder();
		int p = (int) Math.floor(time);
		
		
		s.append("[");
		
		int i = 0;
		for (i = 0; i < p / 2; i++) {
			s.append("=");
		}

		for (int x = 0; x < 50 - i; x++) {
			s.append(" ");
		}

		s.append("] ");
		s.append(p);
		s.append("% ");

		return s.toString();
	}
}
