package ca.menushka.mix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DragMenu extends JPanel {
	
	int x, y, width, height;
	Color color;
	JFrame parent;
	
	Point onCliked = new Point(0,0);
	
	public DragMenu(int x, int y, int width, int height, Color color, JFrame parent) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.parent = parent;
		
		setBounds(x, y, width, height);
		setVisible(true);
		
		setBackground(Helper.loadColorfromJSON("menubar"));
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				onCliked = new Point(e.getX(), e.getY());
			}
		
			
			@Override
			public void mouseDragged(MouseEvent e) {
				Point mouse = MouseInfo.getPointerInfo().getLocation();
				parent.setLocation(mouse.x - onCliked.x, mouse.y - onCliked.y);
			}
		};
		
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		
		g2.setColor(Helper.colorFromHEX("#ffffff"));
		g2.setFont(Helper.lato_normal.deriveFont(12f));
		
		g2.drawString("Mix Player", 120, 15);
	}
}
