package com.musicplayer.main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.media.MediaPlayer;

public class MusicPlayerMain {
	ArrayList<File> playList;
	MusicPlayer mp;
	Scanner sc;
	Boolean autoPlay;
	Boolean autoLoop;

	String commands;
	int currentSong = -1;

	public MusicPlayerMain() {

		commands = "play file";

		autoLoop = false;
		autoPlay = true;
		playList = new ArrayList<File>();
		mp = new MusicPlayer();
		sc = new Scanner(System.in);

	}

	public void startPlayer(){
		System.out.print("\r>>");
		String command = sc.next();

		while (!command.equals("quit") && !command.equals("q")) {

			if (command.equals("help")) {
				System.out.println(commands);
			}

			if (command.equals("load") || command.equals("l")) {
				System.out.print("\r>");
				command = sc.next();
				
				loadFiles(command);
				
				if(!playList.isEmpty()){
					currentSong = 0;
				}
			}

			if (command.equalsIgnoreCase("play")) {
				playMusic();
			}
			
			if(command.equalsIgnoreCase("resume") || command.equalsIgnoreCase("r")){
				resumeMusic();
			}
			
			if (command.equalsIgnoreCase("stop") || command.equalsIgnoreCase("s")) {
				stopMusic();
			}

			if (command.equalsIgnoreCase("pause") || command.equalsIgnoreCase("p")) {
				pauseMusic();
			}
			
			if (command.equalsIgnoreCase("currentSong") || command.equalsIgnoreCase("songStats")){
				printCurrentSong();
			}
			
			if(command.equalsIgnoreCase("playlist") || command.equalsIgnoreCase("pl")){
				printPlaylist();
			}
			
			if(command.equalsIgnoreCase("volume") || command.equalsIgnoreCase("v")){
				System.out.print("\r>");
				try{
					int i = sc.nextInt();
					setMusicVolume(i);
				}catch(Exception e){
					System.out.println("Pleace enter a valid [int]");
				}
			}
			
			if(command.equalsIgnoreCase("select")){
				System.out.print("\r>");
				try{
					int i = sc.nextInt();
					setCurrentSong(i);
				}catch(Exception e){
					System.out.println("Pleace enter a valid [int]");
				}
			}

			System.out.print("\r>>");
			command = sc.next();
		}
		
		
		//sc.close();
		//mp.dispose();
		System.out.println("Bye Bye!");
	}
	
	private void setCurrentSong(int i){
		
		if(playList.isEmpty() || i > (playList.size()-1)){
			System.out.println("Please enter a valid playlist number");
		}else{
			currentSong = i;
			//playMusic();
		}
	}
	
	private void setMusicVolume(int i){
		if (mp.getStatus() != null) {
			mp.volume(i);
		} else {
			System.out.println("First you have to [load] some music!");
		}
	}
	
	private void printCurrentSong(){
		if((mp.getStatus() == MediaPlayer.Status.PLAYING && mp.getStatus() != null) && currentSong > -1){
			printSeparator();
			System.out.println();
			System.out.println("Name: " + playList.get(currentSong).getName());
			System.out.println("Time: [" + (int)mp.getCurrentTime()/1000 + "s / " + (int)mp.getDuartion()/1000 + "s]\n");
			System.out.println(mp.getVisualTime() + "\n");
			printSeparator();
		}else{
			System.out.println("There is no loaded song, please load a song first!");
		}
	}
	
	private void printPlaylist(){
		printSeparator();
		
		for(int i = 0; i < playList.size(); i++){
			if(i == currentSong){
				System.out.println("* " + i + ": " + playList.get(i).getName());
			}else{
				System.out.println("  " + i + ": " + playList.get(i).getName());
			}
		}
		printSeparator();
	}

	private void pauseMusic() {
		if ((mp.getStatus() == MediaPlayer.Status.PLAYING && mp.getStatus() != null)) {
			mp.pause();
		} else {
			System.out.println("First you have to [play] the music!");
		}
	}

	private void stopMusic() {
		if ((mp.getStatus() == MediaPlayer.Status.PLAYING && mp.getStatus() != null)) {
			mp.stop();
		} else {
			System.out.println("First you have to [play] the music!");
		}
	}

	private void resumeMusic() {
		if ((mp.getStatus() == MediaPlayer.Status.PAUSED && mp.getStatus() != null)) {
			mp.play();
		} else {
			System.out.println("First you have to [pause] the music!");
		}
	}

	private void playMusic() {
		if (currentSong > -1) {
			try {
				if(mp.getMP() != null){
					mp.dispose();
				}
				
				mp.load(playList.get(currentSong).toURI().toURL());
				//System.out.println(playList.get(currentSong).getName());

				mp.getMP().setOnEndOfMedia(new Runnable() {
					@Override
					public void run() {
						if (autoPlay) {
							if (playList.isEmpty() || currentSong == playList.size() - 1) {
								if (autoLoop) {
									currentSong = 0;
								} else {
									currentSong = -1;
								}
							
							}else{
								currentSong++;
							} 
						} // autoPlay

						playMusic();
					}
				});
			} catch (MalformedURLException e) {
				System.out.println("Something went wrong :(, are you sure the file is not deleted or moved?");
				e.printStackTrace();
			}
			mp.play();
		} else {
			System.out.println("The playlist is empty");
		}
	}

	private void loadFiles(String path) {
		try {
			Files.walk(Paths.get(path)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					String[] sl = filePath.toString().split("\\.");
					String s = sl[sl.length - 1];

					if (s.equals("mp3")) {
						playList.add(new File(filePath.toString()));
					}
				}
			});
		} catch (IOException e) {
			System.out.println("Wrong path to files!");
			e.printStackTrace();
		}
	}
	
	
	private void printSeparator(){
		System.out.println("-------------------------------");
	}
}