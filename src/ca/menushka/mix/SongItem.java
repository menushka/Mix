package ca.menushka.mix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

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
		
		setBackground(Helper.loadColorfromJSON("list_background"));
		
		if (artist.equals("Folder")){
			this.type = FOLDER;
		} else {
			this.type = MUSIC;
		}
		
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Helper.loadColorfromJSON("list_background_hover"));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Helper.loadColorfromJSON("list_background"));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(Helper.loadColorfromJSON("list_background_onclick"));
				
				if (type == BACK){
					if (path.equals("/")){
						String[] split = Helper.musicPath.split("/");
						Helper.musicPath = "/";
						
						for (int i = 0; i < split.length - 1; i++){
							Helper.musicPath += split[i] + "/";
						}
						
						Helper.songHolder.createSongItems();
						Helper.songHolder.repaint();
					} else {
						Helper.songHolder.inPlaylist = false;
						Helper.songHolder.createPlaylistItems();
						Helper.songHolder.repaint();
					}
				} else if (type == FOLDER){
					Helper.musicPath += title + "/";
					Helper.songHolder.createSongItems();
					Helper.songHolder.repaint();
				} else {
					Helper.play(path);
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(Helper.loadColorfromJSON("list_background_hover"));
			}
		};
		
		addMouseListener(mouseAdapter);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		
		BufferedImage img;
		Color iconColor = Helper.loadColorfromJSON("list_icon");
		if (type == BACK){
			img = (BufferedImage) Helper.loadResourceImage("/back.png");
		} else if (type == FOLDER){
			img = (BufferedImage) Helper.loadResourceImage("/folder.png");
		} else {
			img = (BufferedImage) Helper.loadResourceImage("/music.png");
		}
		g2.drawImage(Helper.changeImageColor(img, iconColor), 20, 10, 20, 20, null);
		
		g2.setColor(Helper.loadColorfromJSON("list_font"));
		g2.setFont(Helper.lato_normal.deriveFont(14f));
		g2.drawString(title, 50, 22);
		
		g2.setFont(Helper.lato_light.deriveFont(10f));
		g2.drawString(artist, 50, 32);
	}
}
