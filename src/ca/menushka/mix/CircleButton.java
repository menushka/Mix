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
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		
		g2.setColor(color);
		g2.fillOval(0, 0, diameter, diameter);
	}
}
