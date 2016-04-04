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
	
	SongItem(String title, String artist, String path) {
		
		this.title = title;
		this.artist = artist;
		this.path = path;
		
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
				Helper.musicPlayer.title = title;
				Helper.musicPlayer.artist = artist;
				Helper.musicPlayer.image = Helper.getAlbumArt(path);
				Helper.play(path);
				Helper.musicPlayer.repaint();
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
		
		g2.setColor(Helper.colorFromHEX("#222222"));
		g2.setFont(Helper.lato_normal.deriveFont(14f));
		g2.drawString(title, 20, 22);
		
		g2.setFont(Helper.lato_light.deriveFont(10f));
		g2.drawString(artist, 20, 32);
	}
}
