package ca.menushka.mix.equalizer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.menushka.mix.Helper;

public class Equalizer extends JFrame {
	
	JFrame parent;
	double volume;
	
	public Equalizer(JFrame parent, double volume) {
		super();
		
		Helper.equalizer = this;
		this.parent = parent;
		this.volume = volume;
		
		setSize(30, 150);
		setUndecorated(true);
		setLocation(parent.getX(), parent.getY() + parent.getHeight() - getHeight() - 30);
		setVisible(true);
		
		repaint();
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getY() >= 10 && e.getY() <= Helper.equalizer.getHeight() - 10){
					update(e);
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (e.getY() >= 10 && e.getY() <= Helper.equalizer.getHeight() - 10){
					update(e);
				}
			}
		};
		
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		
		WindowAdapter windowAdapter = new WindowAdapter() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				Helper.equalizer.dispose();
				Helper.equalizer = null;
			}
		};
		
		addWindowFocusListener(windowAdapter);
		
		add(new Canvas(this));
	}
	
	public void update(MouseEvent e){
		volume = (double)(e.getY() - 10) / (double)(getHeight() - 20);
		volume = 1.0 - volume;
		Helper.setVolume(volume);
		revalidate();
		repaint();
	}
}

class Canvas extends JPanel {
	
	Equalizer equalizer;
	
	Canvas(Equalizer equalizer){
		super();
		
		this.equalizer = equalizer;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		
		g2.setColor(Helper.loadColorfromJSON("player_background"));
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		g2.setColor(Helper.colorFromHEX("#ffffff"));
		
		g2.drawLine(getWidth() / 2, 10, getWidth() / 2, getHeight() - 10);
		
		int radius = 10;
		g2.fillOval(getWidth() / 2 - radius / 2, (int) (10 + (getHeight() - 20) * (1.0 - equalizer.volume)) - radius  / 2, radius, radius);
	}
}
