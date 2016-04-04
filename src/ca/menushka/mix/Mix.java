package ca.menushka.mix;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Mix extends JFrame {
	
	public final String VERSION = "0.1";
	public final int WIDTH = 300;
	public final int HEIGHT = 420;
	JFrame frame = this;
	
	CircleButton closeButton = new CircleButton(5, 5, 10, Helper.colorFromHEX("#fc625d"), new MouseAdapter() {public void mousePressed(java.awt.event.MouseEvent e) {
		System.exit(0);
	};});
	CircleButton minimizeButton = new CircleButton(20, 5, 10, Helper.colorFromHEX("#fdbc40"), new MouseAdapter() {public void mousePressed(java.awt.event.MouseEvent e) {
		frame.setState(ICONIFIED);
	};});
	/*CircleButton maximizeButton = new CircleButton(35, 5, 10, Helper.colorFromHEX("#34c749"), new MouseAdapter() {public void mousePressed(java.awt.event.MouseEvent e) {
		System.exit(0);
	};});*/
	
	DragMenu menuBar = new DragMenu(0, 0, WIDTH, 20, Helper.colorFromHEX("#555555"), this);
	
	MusicPlayer player = new MusicPlayer(0, 20, 300, 150, this);
	
	SongHolder holder = new SongHolder(0, 170, 300, 250);
	
	Mix(){
		super();
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		getContentPane().setBackground(Helper.loadColorfromJSON("background"));
		
		add(closeButton);
		add(minimizeButton);
		add(menuBar);
		
		add(player);
		Helper.musicPlayer = player;
		add(holder);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Helper.init();
		Mix mix = new Mix();
	}
}
