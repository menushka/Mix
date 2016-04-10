package ca.menushka.mix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import javafx.embed.swing.JFXPanel;

public class MusicPlayer extends JFXPanel {
	
	String title, artist;
	Image image, unknown;
	
	int x, y, width, height;
	JFrame parent;
	
	SongProgress songProgress;
	CircleButton fastBackward, play, fastForward;
	
	public MusicPlayer(int x, int y, int width, int height, JFrame parent) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		
		this.title = "Not selected";
		this.artist = "Not selected";
		try {
			this.unknown = ImageIO.read(Mix.class.getResource("/unknown_album.png"));
			this.image = this.unknown;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setBounds(x, y, width, height);
		setLayout(null);
		
		setBackground(Helper.loadColorfromJSON("player_background"));
		
		songProgress = new SongProgress(height, 60, 300 - 20 - height, 20, Helper.colorFromHEX("#ffffff"), this);
		add(songProgress);
		
		int w = 300 - 20 - height;
		int r = (w - 20) / 3;
		
		Color iconColor = Helper.loadColorfromJSON("player_icon");
		fastBackward = new CircleButton(height, 90, r, Helper.loadResourceImage("/fastbackward.png"), iconColor, new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (Helper.mediaPlayer != null){
					Helper.play(Helper.getPrevSong());
				}
			}
		});
		play = new CircleButton(height + (r + 10), 90, r, Helper.play, iconColor, new MouseAdapter() {@Override
			public void mousePressed(MouseEvent e) {
				if (Helper.playing){
					Helper.pause();
				} else {
					Helper.play();
				}
				Helper.musicPlayer.repaint();
		}});
		fastForward = new CircleButton(height + (r + 10) * 2, 90, r, Helper.loadResourceImage("/fastforward.png"), iconColor, new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (Helper.mediaPlayer != null){
					Helper.play(Helper.getNextSong());
				}
			}
		});
		
		add(fastBackward);
		add(play);
		add(fastForward);
	}
	
	public void update(){
		title = Helper.getSongTitle(Helper.nowPlaying.getAbsolutePath());
		artist = Helper.getSongArtist(Helper.nowPlaying.getAbsolutePath());
		image = Helper.getAlbumArt(Helper.nowPlaying.getAbsolutePath());
		
		songProgress.update();
		
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		
		//g2.setColor(Helper.colorFromHEX("#333333"));
		//g2.fillRect(0, 0, width, height);
		
		if (image != null) {
			g2.drawImage(image, 20, 20, height - 40, height - 40, null);
		} else {
			g2.drawImage(unknown, 20, 20, height - 40, height - 40, null);
		}
		
		g2.setColor(Helper.loadColorfromJSON("player_font"));
		g2.setFont(Helper.lato_light.deriveFont(16f));
		g2.drawString(title, height, 36);
		
		g2.setFont(Helper.lato_light.deriveFont(10f));
		g2.drawString(artist, height, 50);
		
		//g2.drawLine(height, 70, 300 - 20, 70);
	}
}
