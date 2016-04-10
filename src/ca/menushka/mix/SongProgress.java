package ca.menushka.mix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SongProgress extends JPanel implements ActionListener{
	int x, y, width, height;
	Color color;
	MusicPlayer parent;
	
	float progress = 0;
	
	Timer timer;
	
	public SongProgress(int x, int y, int width, int height, Color color, MusicPlayer parent) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.parent = parent;
		
		setBounds(x, y, width, height);
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				update(e);
				
				Helper.mediaPlayer.seek(Helper.media.getDuration().multiply(progress));
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				update(e);
				
				Helper.mediaPlayer.seek(Helper.media.getDuration().multiply(progress));
			}
		};
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		
		startTimer();
	}
	
	public void startTimer(){
		timer = new Timer(100, this);
		timer.start();
	}
	
	public void stopTimer(){
		timer.stop();
	}
	
	public void update(){
		progress = 0;
	}
	
	public void update(MouseEvent e){
		progress = (float) e.getX() / (float) (width - 1);
		if (progress < 0){
			progress = 0f;
		} else if (progress > 1f){
			progress = 1f;
		}
		parent.repaint();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = Helper.getSmoothedGraphics(g);
		
		g2.setColor(Helper.colorFromHEX("#ffffff"));
		g2.drawLine(0, height/4, width, height/4);
		
		if (Helper.mediaPlayer != null && Helper.media != null){
			progress = (float) (Helper.mediaPlayer.getCurrentTime().toMillis() / Helper.media.getDuration().toMillis());
		}
		g2.drawLine((int) ((width - 1) * progress), 0, (int) ((width - 1) * progress), height / 2);
		
		int currentTime = 0;
		int duration = 0;
		if (Helper.mediaPlayer != null && Helper.media != null){
			currentTime = (int)(Helper.mediaPlayer.getCurrentTime().toSeconds());
			duration = (int)(Helper.media.getDuration().toSeconds());
		}
		
		g2.setFont(Helper.lato_light.deriveFont(10f));
		g2.drawString(currentTime / 60 + ":" + String.format("%02d", currentTime % 60) + " / " + duration / 60 + ":" + String.format("%02d", duration % 60), 0, (int)(height));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(timer)){
			if (Helper.mediaPlayer != null && Helper.media != null && Helper.playing){
				progress = (float) (Helper.mediaPlayer.getCurrentTime().toMillis() / Helper.media.getDuration().toMillis());
				parent.repaint();
				repaint();
			}
		}
	}
}
