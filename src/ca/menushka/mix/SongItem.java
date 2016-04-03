package ca.menushka.mix;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class SongItem extends JPanel {
	SongItem() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Helper.colorFromHEX("#dddddd"));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Helper.colorFromHEX("#eeeeee"));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(Helper.colorFromHEX("#cccccc"));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(Helper.colorFromHEX("#dddddd"));
			}
		};
		
		addMouseListener(mouseAdapter);
	}
}
