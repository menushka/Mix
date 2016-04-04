package ca.menushka.mix;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.scene.media.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;


public class Helper {
	
	//Resources
	public static JsonObject json;
	public static Font lato_light;
	public static Font lato_normal;
	public static Font lato_bold;
	
	//Global Variables
	public static Media media;
	public static MediaPlayer mediaPlayer;
	
	public static MusicPlayer musicPlayer;
	
	//Color from HEX
	public static Color colorFromHEX(String hex){
		int r = Integer.valueOf(hex.substring(1, 3), 16);
		int g = Integer.valueOf(hex.substring(3, 5), 16);
		int b = Integer.valueOf(hex.substring(5, 7), 16);
		return new Color(r, g, b);
	}
	
	//Initalize static helper function
	public static void init(){
		loadFont();
		loadJSON();
		//media = new Media("");
		//mediaPlayer = new MediaPlayer(media);
	}
	
	//Load font into the function
	public static void loadFont(){
		try {
			lato_light = Font.createFont(Font.TRUETYPE_FONT, new File(Mix.class.getResource("/Lato-Light.ttf").toURI()));
			lato_normal = Font.createFont(Font.TRUETYPE_FONT, new File(Mix.class.getResource("/Lato-Regular.ttf").toURI()));
			lato_bold = Font.createFont(Font.TRUETYPE_FONT, new File(Mix.class.getResource("/Lato-Bold.ttf").toURI()));
		} catch (FontFormatException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Load JSON file into json object
	public static void loadJSON(){
		JsonParser parser = new JsonParser();
		try {
			FileReader fileReader = new FileReader(new File(Mix.class.getResource("/color.json").toURI()));
			json = (JsonObject) parser.parse(fileReader);
		} catch (Exception e) {
			
		}
	}
	
	//Get value from JSON file
	public static String getJSON(String text){
		return json.get(text).toString().replace('"', ' ').trim();
	}
	
	//Load color directly from JSON file
	public static Color loadColorfromJSON(String text){
		return colorFromHEX(getJSON(text));
	}
	
	//Enable Anti-aliasing, Interpolation and corrected rendering
	public static Graphics2D getSmoothedGraphics(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints hints = new RenderingHints(null);
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(hints);
		return g2;
	}
	
	public static ArrayList<String> getMusic(){
		ArrayList<String> songList = new ArrayList<String>();
		songList.add("PSY - Gangnam Style");
		songList.add("Justin Bieber - Love Yourself");
		songList.add("Twenty One Pilots - Stressed Out");
		songList.add("Flo Rida - My House");
		songList.add("Charlie Puth - One Call  Away");
		songList.add("Ariana Grande - Love Me Harder");
		songList.add("Mike Posner - I Took A Pill In Ibiza");
		songList.add("PSY - DADDY");
		songList.add("Major Lazer - Lean On");
		songList.add("Rachel Platten - Fight Song");
		songList.add("Selena Gomez - Good For You");
		return songList;
	}
	
	public static ArrayList<String> getMusicReal(){
		ArrayList<String> songList = new ArrayList<String>();
		
		File folder = new File(System.getProperty("user.home") + "/Music/Pop");
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isDirectory()){
				
			} else {
				File f = listOfFiles[i];
				if (f.getName().contains(".mp3")){
					songList.add(f.getName().replace(".mp3", ""));
				}
			}
		}
		return songList;
	}
	
	public static void play(String path){
		if (mediaPlayer != null){
			mediaPlayer.stop();
		}
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
	
	public static Image getAlbumArt(String path){
		try {
			Mp3File song = new Mp3File(path);
			if (song.hasId3v2Tag()){
				ID3v2 id3v2tag = song.getId3v2Tag();
			    byte[] imageData = id3v2tag.getAlbumImage();
			    //converting the bytes to an image
			    BufferedImage img;
			    try {
			    	img = ImageIO.read(new ByteArrayInputStream(imageData));
			    } catch (Exception e){
			    	return null;
			    }
			    return img;
			}
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
