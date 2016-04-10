package ca.menushka.mix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Button extends JPanel {
	int x, y, width, height;
	Image image;
	Color color;
	
	
	public Button(int x, int y, int width, int height, Image image, Color color, MouseAdapter mouseAdapter) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.color = color;
		
		setBounds(x, y, width, height);
		
		addMouseListener(mouseAdapter);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		g2.drawImage(Helper.changeImageColor((BufferedImage) image, color), 0, 0, width, height, null);
	}
}
