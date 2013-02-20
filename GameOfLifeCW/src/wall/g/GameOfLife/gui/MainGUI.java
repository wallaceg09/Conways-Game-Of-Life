package wall.g.GameOfLife.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import wall.g.GameOfLife.game.Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {
	
	private static final Color ALIVE = Color.white;
	private static final Color DEAD = Color.black;
	
	private static final Dimension CELL_SIZE = new Dimension(2,2);
	
	private int width, height;//game variables for determining how many cells to have
	
	private JPanel cellGridPanel;//This holds the grid-layout to display the cells
	private JPanel bufferPanel;//this is a buffer to keep the layout manager of the contentpane from messing with the cellGridLayout
	
	private JPanel[][] cellMatrix;//this holds the colored JPanels that represent the cells
	
	private JPanel buttonPanel;//holds the buttons in a group
	
	private GamePanel gamePanel;
	
	private JButton iterate;//allows the user to calculate just one iteration 
	
	private JButton start;//begins running the game continuously
	
	private JButton stop;//stops the game from iterating further.
	
	
	private Game game;
	
	private Timer timer;
	
	Thread gameLogic;
	
	
	private boolean paused;
	
	private MainGUI(String name){
		this(name, 10, 10);
		
	}
	private MainGUI(String name, int gameWidth, int gameHeight){
		super(name);
		
		paused = true;
		
		timer = new Timer(1000, this);
		
		setWidth(gameWidth);
		setHeight(gameHeight);
		
		initializeGame();
		
		initComponents();
		this.setVisible(true);
		
		gameLogic = new Thread(new Runnable() {//creates a new thread for the game AI to allow it to work in the background and not affect the GUI
			
			@Override
			public void run() {
			
//				for(int i = 0; i < 100000; i++){
				while(!paused){//
					updateGame();
//					game.update();
					
					try {
						gameLogic.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		
	}
	
	private void initComponents(){//initialize all the components and their layouts.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
	
		
//		initCellGridPanel();
		gamePanel = new GamePanel(this.game);//FIXME: something is wrong with this. Probably in the GamePanel.java file
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.red));//FIXME: The border isn't even showing up
		
		
		bufferPanel = new JPanel();//bufferPanel prevents the frame's layout manager from interfering with the sizing of the gamePanel
		bufferPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
//		bufferPanel.add(cellGridPanel);//remnant of a previous attempt at displaying the game
		
		bufferPanel.add(gamePanel);
		
		
//		bufferPanel.setPreferredSize(new Dimension((width*CELL_SIZE.width)+5, (height*CELL_SIZE.height)+5));
		
		this.getContentPane().add(bufferPanel);
		
		iterate = new JButton("Iterate once");
		iterate.addActionListener(this);
		iterate.setEnabled(false);
		
		start = new JButton("Start");
		start.addActionListener(this);
		start.setEnabled(false);
		
		stop = new JButton("Stop");
		stop.addActionListener(this);
		stop.setEnabled(false);
		
		buttonPanel = new JPanel();//holds all my buttons
		
		buttonPanel.add(iterate);
		buttonPanel.add(start);
		buttonPanel.add(stop);
		
		this.getContentPane().add(buttonPanel);
		
		
		
		
		
	}
	
	private void initCellGridPanel(){//older way of displaying the game using a grid of jpanels. Slow and sloppy, just the way I like it.
		
		cellGridPanel = new JPanel();
		cellGridPanel.setLayout(new GridLayout(height, width));
		
		cellMatrix = new JPanel[height][width];
		
		for(int row = 0; row < height; row++){//add a new JPanel to each of the grid indices 
			for(int col = 0; col < width; col++){
				cellMatrix[row][col] = new JPanel();
				cellMatrix[row][col].setBackground(ALIVE);
				cellMatrix[row][col].setBorder(BorderFactory.createLineBorder(Color.black, 1));
				cellMatrix[row][col].setSize(CELL_SIZE);
				
				cellGridPanel.add(cellMatrix[row][col]);
				
//				cellGridPanel.add
//				cellGridPanel.getComponentAt(col, row).setBackground(DEAD);
			}
		}
		
		updateVisual();
		
	}
	
	private void updateVisual(){//iterates through each of the JPanel's and determines if the cell in this position is alive or dead. --Won't be used--
		for(int row = 0; row < height; row++){
			for( int col = 0; col < width; col++){
				if(game.isAlive(row, col)){
					cellMatrix[row][col].setBackground(ALIVE);
					
				}else{
					cellMatrix[row][col].setBackground(DEAD);
					
				}
				cellMatrix[row][col].revalidate();//testing to see if this will update the panels
			}
		}
		
	}
	
	private void updateGame(){
		game.update();
		updateVisual();
	
	}
	
	private void initializeGame(){
		game = new Game(width, height);
	}
	
//	private void run(){//locking up the thread --not going to be used--
//		
//		while(!paused){
//			timer.start();
//			updateGame();
//		
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		
		if(source == iterate){//update the game once.
			if(paused){
				updateGame();
				
			}
		}else if(source == start){//update the game until the stop button is pressed
			paused = false;
//			run();
//			Runnable gameLoop = new Runnable() {

			gameLogic.start();
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					updateVisual();
					
				}
			});
		}else if(source == stop){
			paused = true;
		
		}
		
		
		
	}
	
	private void setWidth( int width){
		this.width = width;
	}
	
	private void setHeight(int height){
		this.height = height;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MainGUI mainGame = new MainGUI("Conway's game of life.", 50, 50);
	}


}
