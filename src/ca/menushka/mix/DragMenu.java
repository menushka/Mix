package ca.menushka.mix;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
}
