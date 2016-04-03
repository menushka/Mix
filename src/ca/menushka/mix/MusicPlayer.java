package ca.menushka.mix;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MusicPlayer extends JPanel {
	
	int x, y, width, height;
	JFrame parent;
	
	public MusicPlayer(int x, int y, int width, int height, JFrame parent) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		
		setBounds(x, y, width, height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Helper.colorFromHEX("#333333"));
		g.fillRect(0, 0, width, height);
		
		try {
			g.drawImage(ImageIO.read(Mix.class.getResource("/pokemon.jpg")), 20, 20, height - 40, height - 40, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			g.setColor(Helper.colorFromHEX("#999999"));
			g.fillRect(20, 20, height - 40, height - 40);
		}
		
		g.setColor(Helper.loadColorfromJSON("fontcolor"));
		g.setFont(Helper.lato.deriveFont(16f));
		g.drawString("Pikachu's Hits", height, 36);
		
		g.setFont(Helper.lato.deriveFont(10f));
		g.drawString("Pokemon OST", height, 50);
	}
}
