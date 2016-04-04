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
	
	ArrayList<String> songList;
	ArrayList<String> folderList;
	
	SongHolder(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		setBounds(x, y, width, height);
		setLayout(null);
		
		Helper.songHolder = this;
		
		createSongItems();
		
		addMouseWheelListener(this);
	}
	
	public void createSongItems(){
		holder.removeAll();
		
		songList = Helper.getMusic();
		folderList = Helper.getFolders();
		
		SongItem panel = new SongItem("Back", "Move back a directory", "/");
		panel.type = SongItem.BACK;
		panel.setBounds(0, 0, width, 40);
		panel.setBackground(Helper.colorFromHEX("#eeeeee"));
		holder.add(panel);
		
		//Draw folders
		for (int i = 0; i < folderList.size(); i++){
			String path = System.getProperty("user.home") + Helper.musicPath + folderList.get(i) + ".mp3";
					
			panel = new SongItem(folderList.get(i), "Folder", path);
			panel.setBounds(0, i * 40 + 40, width, 40);
			holder.add(panel);
		}
		
		//Draw songs
		for (int i = 0; i < songList.size(); i++){
			String path = System.getProperty("user.home") + Helper.musicPath + songList.get(i) + ".mp3";
					
			panel = new SongItem(Helper.getSongTitle(path), Helper.getSongArtist(path), path);
			panel.setBounds(0, i * 40 + 40 + 40 * folderList.size(), width, 40);
			holder.add(panel);
		}
		
		holder.setBounds(0, 0, width, 40 * songList.size() + 40 + 40 * folderList.size());
		holder.setLayout(null);
		
		add(holder);
		
		repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scroll = holder.getY() - e.getWheelRotation();
		if (scroll < height - 40 * folderList.size() - 40 - 40 * songList.size()){
			scroll = height - 40 * folderList.size() - 40 - 40 * songList.size();
		} else if (scroll > 0){
			scroll = 0;
		}
		holder.setLocation(0, scroll);
	}
}
