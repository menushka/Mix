package ca.menushka.mix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import javafx.embed.swing.JFXPanel;

public class SongItem extends JPanel {
	
	String title, artist, path;
	
	final public static int BACK = 0;
	final public static int MUSIC = 1;
	final public static int FOLDER = 2;
	int type;
	
	SongItem(String title, String artist, String path) {
		
		this.title = title;
		this.artist = artist;
		this.path = path;
		
		setBackground(Helper.colorFromHEX("#eeeeee"));
		
		if (artist.equals("Folder")){
			this.type = FOLDER;
		} else {
			this.type = MUSIC;
		}
		
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Helper.colorFromHEX("#dddddd"));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Helper.colorFromHEX("#eeeeee"));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(Helper.colorFromHEX("#cccccc"));
				
				if (type == BACK){
					String[] split = Helper.musicPath.split("/");
					Helper.musicPath = "/";
					
					for (int i = 0; i < split.length - 1; i++){
						Helper.musicPath += split[i] + "/";
					}
					
					Helper.songHolder.createSongItems();
					Helper.songHolder.repaint();
				} else if (type == FOLDER){
					Helper.musicPath += title + "/";
					Helper.songHolder.createSongItems();
					Helper.songHolder.repaint();
				} else {
					Helper.play(path);
					Helper.musicPlayer.update();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(Helper.colorFromHEX("#dddddd"));
			}
		};
		
		addMouseListener(mouseAdapter);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		
		if (type == BACK){
			g2.drawImage(Helper.loadResourceImage("/back.png"), 20, 10, 20, 20, null);
		} else if (type == FOLDER){
			g2.drawImage(Helper.loadResourceImage("/folder.png"), 20, 10, 20, 20, null);
		} else {
			g2.drawImage(Helper.loadResourceImage("/music.png"), 20, 10, 20, 20, null);
		}
		
		
		g2.setColor(Helper.colorFromHEX("#222222"));
		g2.setFont(Helper.lato_normal.deriveFont(14f));
		g2.drawString(title, 50, 22);
		
		g2.setFont(Helper.lato_light.deriveFont(10f));
		g2.drawString(artist, 50, 32);
	}
}
