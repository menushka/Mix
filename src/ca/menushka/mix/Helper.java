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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.event.EventHandler;
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
	
	public static Image play;
	public static Image pause;
	
	//Global Variables
	public static MusicPlayer musicPlayer;
	public static SongHolder songHolder;
	
	public static String musicPath = "/Music/";
	
	public static Media media;
	public static MediaPlayer mediaPlayer;
	
	public static boolean playing = false;
	public static File nowPlaying;
	
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
		
		play = Helper.loadResourceImage("/play.png");
		pause = Helper.loadResourceImage("/pause.png");
	}
	
	//Load font into the function
	public static void loadFont(){
		try {
			lato_light = Font.createFont(Font.TRUETYPE_FONT, Mix.class.getResourceAsStream("/Lato-Light.ttf"));
			lato_normal = Font.createFont(Font.TRUETYPE_FONT, Mix.class.getResourceAsStream("/Lato-Regular.ttf"));
			lato_bold = Font.createFont(Font.TRUETYPE_FONT, Mix.class.getResourceAsStream("/Lato-Bold.ttf"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Load JSON file into json object
	public static void loadJSON(){
		JsonParser parser = new JsonParser();
		try {
			InputStream is = Mix.class.getResourceAsStream("/color.json");
			InputStreamReader reader = new InputStreamReader(is);
			json = (JsonObject) parser.parse(reader);
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
		
		File folder = new File(System.getProperty("user.home") + musicPath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++){
			File f = listOfFiles[i];
			if (!listOfFiles[i].isDirectory()){
				if (f.getName().contains(".mp3")){
					songList.add(f.getName().replace(".mp3", ""));
				}
			}
		}
		return songList;
	}
	
	public static ArrayList<String> getFolders(){
		ArrayList<String> folderList = new ArrayList<String>();
		
		File folder = new File(System.getProperty("user.home") + musicPath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++){
			File f = listOfFiles[i];
			if (listOfFiles[i].isDirectory()){
				folderList.add(f.getName());
			}
		}
		return folderList;
	}
	
	public static void play(){
		if (mediaPlayer == null){
			if (getMusic().size() > 0){
				String path = System.getProperty("user.home") + Helper.musicPath + getMusic().get(0) + ".mp3";
				play(path);
			}
		} else {
			mediaPlayer.play();
			
			Helper.playing = true;
			Helper.musicPlayer.play.setImage(Helper.pause);
			Helper.musicPlayer.update();
		}
	}
	
	public static void play(String path){
		if (mediaPlayer != null){
			mediaPlayer.stop();
		}
		
		nowPlaying = new File(path);
		
		media = new Media(nowPlaying.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		
		Helper.playing = true;
		Helper.musicPlayer.play.setImage(Helper.pause);
		Helper.musicPlayer.update();
	}
	
	public static void pause(){
		if (mediaPlayer == null){
			
		} else {
			mediaPlayer.pause();
			
			Helper.playing = false;
			Helper.musicPlayer.play.setImage(Helper.play);
			Helper.musicPlayer.update();
		}
	}
	
	public static Image loadResourceImage(String path){
		try {
			return ImageIO.read(Mix.class.getResource(path));
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String getSongTitle(String path){
		try {
			Mp3File song = new Mp3File(path);
			if (song.hasId3v2Tag()){
				ID3v2 id3v2tag = song.getId3v2Tag();
				if (id3v2tag.getTitle() != null){
					return id3v2tag.getTitle();
				} else {
					return new File(path).getName();
				}
			} else {
				return new File(path).getName();
			}
		} catch (Exception e){
			return "Unknown";
		}
	}
	
	public static String getSongArtist(String path){
		try {
			Mp3File song = new Mp3File(path);
			if (song.hasId3v2Tag()){
				ID3v2 id3v2tag = song.getId3v2Tag();
				if (id3v2tag.getArtist() != null){
					return id3v2tag.getArtist();
				} else {
					return "Unknown";
				}
			} else {
				return "Unknown";
			}
		} catch (Exception e){
			return "Unknown";
		}
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
