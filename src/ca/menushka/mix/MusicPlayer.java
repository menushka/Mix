package ca.menushka.mix;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer extends JFXPanel {
	
	String title, artist;
	Image image, unknown;
	
	int x, y, width, height;
	JFrame parent;
	
	CircleButton fastBackward, play, fastForward;
	
	public MusicPlayer(int x, int y, int width, int height, JFrame parent) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		
		this.title = "Pikachu's Hits";
		this.artist = "Pokemon OST";
		try {
			this.image = ImageIO.read(Mix.class.getResource("/pokemon.jpg"));
			this.unknown = ImageIO.read(Mix.class.getResource("/unknown_album.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setBounds(x, y, width, height);
		setLayout(null);
		
		setBackground(Helper.colorFromHEX("#333333"));
		
		int w = 300 - 20 - height;
		int r = (w - 20) / 3;
		
		fastBackward = new CircleButton(height, 90, r, Helper.loadResourceImage("/fastbackward.png"), new MouseAdapter() {});
		play = new CircleButton(height + (r + 10), 90, r, Helper.play, new MouseAdapter() {@Override
			public void mousePressed(MouseEvent e) {
				if (Helper.playing){
					Helper.pause();
				} else {
					Helper.play();
				}
				Helper.musicPlayer.repaint();
		}});
		fastForward = new CircleButton(height + (r + 10) * 2, 90, r, Helper.loadResourceImage("/fastforward.png"), new MouseAdapter() {});
		
		add(fastBackward);
		add(play);
		add(fastForward);
	}
	
	public void update(){
		title = Helper.getSongTitle(Helper.nowPlaying.getAbsolutePath());
		artist = Helper.getSongArtist(Helper.nowPlaying.getAbsolutePath());
		image = Helper.getAlbumArt(Helper.nowPlaying.getAbsolutePath());
		
		play.setImage(Helper.pause);
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
		
		g2.setColor(Helper.loadColorfromJSON("fontcolor"));
		g2.setFont(Helper.lato_light.deriveFont(16f));
		g2.drawString(title, height, 36);
		
		g2.setFont(Helper.lato_light.deriveFont(10f));
		g2.drawString(artist, height, 50);
		
		g2.drawLine(height, 70, 300 - 20, 70);
	}
}
