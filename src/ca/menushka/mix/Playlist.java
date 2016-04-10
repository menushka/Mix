package ca.menushka.mix;

import java.util.ArrayList;

public class Playlist {
	
	String name, description;
	ArrayList<String> songs = new ArrayList<String>();
	
	public Playlist(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public void add(String song){
		songs.add(song);
	}
}
