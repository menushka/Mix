package ca.menushka.mix;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SongHolder extends JPanel implements MouseWheelListener {
	
	ArrayList<SongItem> songs = new ArrayList<SongItem>();
	
	int x, y, width, height;
	JPanel holder = new JPanel();
	
	SongHolder(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		setBounds(x, y, width, height);
		setLayout(null);
		
		for (int i = 0; i < 10; i++){
			SongItem panel = new SongItem();
			panel.setBounds(0, i * 40, width, 40);
			panel.setBackground(Helper.colorFromHEX("#eeeeee"));
			holder.add(panel);
		}
		holder.setBounds(0, 0, width, 40 * 10);
		holder.setLayout(null);
		
		add(holder);
		
		addMouseWheelListener(this);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scroll = holder.getY() - e.getWheelRotation();
		if (scroll < 250 - 400){
			scroll = -150;
		} else if (scroll > 0){
			scroll = 0;
		}
		holder.setLocation(0, scroll);
	}
}
