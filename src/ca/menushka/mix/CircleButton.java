package ca.menushka.mix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

public class CircleButton extends JPanel {
	
	int x, y, diameter;
	Color color;
	
	CircleButton(int x, int y, int diameter, Color color, MouseAdapter mouseAdapter){
		super();
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.color = color;
		
		setBounds(x, y, diameter, diameter);
		setVisible(true);
		
		addMouseListener(mouseAdapter);
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints hints = new RenderingHints(null);
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(hints);
		g2.setColor(color);
		g2.fillOval(0, 0, diameter, diameter);
	}
}
