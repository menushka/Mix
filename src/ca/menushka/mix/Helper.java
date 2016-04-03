package ca.menushka.mix;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Helper {
	
	public static JsonObject json;
	public static Font lato;
	
	public static Color colorFromHEX(String hex){
		int r = Integer.valueOf(hex.substring(1, 3), 16);
		int g = Integer.valueOf(hex.substring(3, 5), 16);
		int b = Integer.valueOf(hex.substring(5, 7), 16);
		return new Color(r, g, b);
	}
	
	public static void init(){
		loadFont();
		loadJSON();
	}
	
	public static void loadFont(){
		try {
			lato = Font.createFont(Font.TRUETYPE_FONT, new File(Mix.class.getResource("/Lato-Light.ttf").toURI()));
		} catch (FontFormatException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadJSON(){
		JsonParser parser = new JsonParser();
		try {
			FileReader fileReader = new FileReader(new File(Mix.class.getResource("/color.json").toURI()));
			json = (JsonObject) parser.parse(fileReader);
		} catch (Exception e) {
			
		}
	}
	
	public static String getJSON(String text){
		return json.get(text).toString().replace('"', ' ').trim();
	}
	
	public static Color loadColorfromJSON(String text){
		return colorFromHEX(getJSON(text));
	}
}
