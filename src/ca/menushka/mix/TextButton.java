package ca.menushka.mix;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

public class TextButton extends JPanel {
	
	final public static int LEFT_ALIGNED = 0;
	final public static int CENTER_ALIGNED = 1;
	final public static int RIGHT_ALIGNED = 2;
	
	String text;
	int x, y;
	int width, height;

	TextButton(String text, int x, int y, int width, int height, MouseAdapter mouseAdapter){
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		setBounds(x, y, width, height);
		
		addMouseListener(mouseAdapter);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		
		FontMetrics fm = g2.getFontMetrics(Helper.lato_normal.deriveFont(12f));
		
		g2.setColor(Helper.colorFromHEX("#ffffff"));
		g2.setFont(Helper.lato_normal.deriveFont(12f));
		g2.drawString(text, width / 2 - fm.stringWidth(text) / 2, height / 2 + fm.getHeight() / 3);
	}
}
