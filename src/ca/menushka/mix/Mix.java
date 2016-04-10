package ca.menushka.mix;

import java.awt.event.MouseAdapter;

import javax.swing.JFrame;

import ca.menushka.mix.equalizer.Equalizer;

public class Mix extends JFrame {
	
	public static final String VERSION = "0.8";
	public static final int WIDTH = 300;
	public static final int HEIGHT = 450;
	JFrame frame = this;
	
	boolean playlist = false;
	
	CircleButton closeButton = new CircleButton(5, 5, 10, Helper.colorFromHEX("#fc625d"), new MouseAdapter() {public void mousePressed(java.awt.event.MouseEvent e) {
		Helper.saveJSON();
		System.exit(0);
	};});
	CircleButton minimizeButton = new CircleButton(20, 5, 10, Helper.colorFromHEX("#fdbc40"), new MouseAdapter() {public void mousePressed(java.awt.event.MouseEvent e) {
		frame.setState(ICONIFIED);
	};});
	
	DragMenu menuBar = new DragMenu(0, 0, WIDTH, 20, Helper.colorFromHEX("#555555"), this);
	
	MusicPlayer player = new MusicPlayer(0, 20, 300, 150, this);
	
	SongHolder holder = new SongHolder(0, 170, 300, 250);
	
	TextButton mymusic = new TextButton("MY MUSIC", WIDTH / 2 - 50, HEIGHT - 30, 100, 30, new MouseAdapter() {
		public void mousePressed(java.awt.event.MouseEvent e) {
			if (playlist){
				mymusic.setText("MY MUSIC");
				playlist = false;
				
				Helper.songHolder.createSongItems();
			} else {
				mymusic.setText("PLAYLISTS");
				playlist = true;
				
				Helper.songHolder.createPlaylistItems();
			}
			repaint();
		};
	});
	
	Button volume = new Button(0, HEIGHT - 30, 30, 30, Helper.loadResourceImage("/volume.png"), Helper.colorFromHEX("#ffffff"), new MouseAdapter() {
		public void mousePressed(java.awt.event.MouseEvent e) {
			Equalizer equalizer = new Equalizer(frame, Helper.volume);
		};
	});
	Button settings = new Button(WIDTH - 30, HEIGHT - 30, 30, 30, Helper.loadResourceImage("/settings.png"), Helper.colorFromHEX("#ffffff"), new MouseAdapter() {});
	
	Mix(){
		super();
		
		Helper.mix = this;
		
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		getContentPane().setBackground(Helper.loadColorfromJSON("player_background"));
		
		add(closeButton);
		add(minimizeButton);
		add(menuBar);
		
		add(player);
		Helper.musicPlayer = player;
		add(holder);
		
		add(mymusic);
		
		add(volume);
		add(settings);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Helper.init();
		Mix mix = new Mix();
	}
}
