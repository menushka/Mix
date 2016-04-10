package ca.menushka.mix;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import ca.menushka.mix.equalizer.Equalizer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Helper {

	//Resources
	public static Font lato_light;
	public static Font lato_normal;
	public static Font lato_bold;
	
	public static Image play;
	public static Image pause;
	
	//Global Variables
	
	public static JsonObject colorJSON;
	public static JsonArray playlistJSON;
	
	public static Mix mix;
	public static MusicPlayer musicPlayer;
	public static SongHolder songHolder;
	
	public static Equalizer equalizer;
	public static double volume = 1.0;
	
	public static String musicPath = "/Music/";
	public static int currentSongIndex = -1;
	public static ArrayList<String> currentSongList = new ArrayList<String>();
	
	public static int currentPlaylistIndex = -1;
	public static ArrayList<String> currentPlaylist = new ArrayList<String>();
	
	public static ArrayList<Playlist> playlists = new ArrayList<Playlist>();
	
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
			File colorFile = new File(System.getProperty("user.home") + "/Music/Mix/color.json");
			
			InputStream is;
			if (colorFile.exists()){
				is = new FileInputStream(colorFile);
			} else {
				is = Mix.class.getResourceAsStream("/color.json");
			}

			InputStreamReader reader = new InputStreamReader(is);
			colorJSON = (JsonObject) parser.parse(reader);
			reader.close();
			is.close();
			
			
			File playlistFile = new File(System.getProperty("user.home") + "/Music/Mix/playlists.json");
			
			if (playlistFile.exists()){
				is = new FileInputStream(playlistFile);
				reader = new InputStreamReader(is);
				playlistJSON = (JsonArray) parser.parse(reader);
				reader.close();
				is.close();
			} else {
				playlistJSON = new JsonArray();
			}
			
			for (int i = 0; i < playlistJSON.size(); i++){
				JsonObject obj = (JsonObject) playlistJSON.get(i);
				String name = obj.get("title").toString().replace('"', ' ').trim();
				String description = obj.get("description").toString().replace('"', ' ').trim();
				
				Playlist p = new Playlist(name, description);
				JsonArray songs = (JsonArray) obj.get("songs");
				for (int j = 0; j < songs.size(); j++){
					String song = songs.get(j).toString().replace('"', ' ').trim();
					p.add(song);
				}
				
				playlists.add(p);
			}
			
		} catch (Exception e) {
			
		}
	}
	
	public static void saveJSON(){
		String folderPath = System.getProperty("user.home") + "/Music/Mix/";
		
		if (!new File(folderPath).exists()){
			new File(folderPath).mkdir();
		}
		
		String colorPath = System.getProperty("user.home") + "/Music/Mix/color.json";
		try {
			Writer writer = new FileWriter(new File(colorPath));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(colorJSON, writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String playlistPath = System.getProperty("user.home") + "/Music/Mix/playlists.json";
		try {
			Writer writer = new FileWriter(new File(playlistPath));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonArray playlist = new JsonArray();
			
			for (Playlist p : playlists){
				JsonObject list = new JsonObject();
				list.addProperty("title", p.name);
				list.addProperty("description", p.description);
				
				JsonArray array = new JsonArray();
				for (String s : p.songs){
					array.add(s);
				}
				list.add("songs", array);
				
				playlist.add(list);
			}
			
			gson.toJson(playlist, writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Get value from JSON file
	public static String getJSON(String text){
		return colorJSON.get(text).toString().replace('"', ' ').trim();
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
	
	public static void getMusic(){
		ArrayList<String> songList = new ArrayList<String>();
		
		File folder = new File(System.getProperty("user.home") + musicPath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++){
			File f = listOfFiles[i];
			if (!listOfFiles[i].isDirectory()){
				if (f.getName().contains(".mp3")){
					songList.add(f.getAbsolutePath());
				}
			}
		}
		
		currentSongList = songList;
		//return songList;
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
	
	public static void getPlaylistMusic(){
		currentSongList = playlists.get(currentPlaylistIndex).songs;
		currentSongIndex = 0;
	}
	
	public static void play(){
		if (mediaPlayer == null){
			if (Helper.currentSongList.size() > 0){
				play(Helper.currentSongList.get(0));
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
		mediaPlayer.setVolume(Helper.volume);
		
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				Helper.play(Helper.getNextSong());
			}
		});
		
		mediaPlayer.play();
		
		Helper.playing = true;
		Helper.musicPlayer.play.setImage(Helper.pause);
		Helper.musicPlayer.update();
		
		currentSongIndex = currentSongList.indexOf(path);
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
	
	public static String getNextSong(){
		if (currentSongIndex == currentSongList.size() - 1){
			return Helper.currentSongList.get(0);
		}
		return Helper.currentSongList.get(Helper.currentSongIndex + 1);
	}
	
	public static String getPrevSong(){
		if (currentSongIndex == 0){
			return Helper.currentSongList.get(Helper.currentSongList.size() - 1);
		}
		return Helper.currentSongList.get(Helper.currentSongIndex - 1);
	}
	
	public static Image loadResourceImage(String path){
		try {
			return ImageIO.read(Mix.class.getResource(path));
		} catch (IOException e) {
			return null;
		}
	}
	
	public static BufferedImage changeImageColor(BufferedImage img, Color color){
		BufferedImage colored = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < img.getHeight(); y++){
			for (int x = 0; x < img.getWidth(); x++){
				int alpha = (img.getRGB(x, y) >> 24) & 0xFF;
				int rgb = (alpha << 24) & 0xFF000000 | (color.getRed() << 16) & 0x00FF0000 | (color.getGreen() << 8) & 0x0000FF00 | color.getBlue() & 0x000000FF;
				colored.setRGB(x, y, rgb);
			}
		}
		return colored;
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
	
	public static void setVolume(double value){
		volume = value;
		if (mediaPlayer != null){
			mediaPlayer.setVolume(value);
		}	
	}
}
