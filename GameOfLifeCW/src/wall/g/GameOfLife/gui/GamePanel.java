package wall.g.GameOfLife.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import wall.g.GameOfLife.game.Game;

public class GamePanel extends JPanel{
	
	private static final int CELL_SIZE = 2;
	private static final Color ALIVE = Color.white;
	private static final Color DEAD = Color.black;
	
//	private Rectangle2D[][] rectangleMap;//ommitting
	private int columns;
	private int rows;
	
//	private Graphics graphics;//omitting
	
	private Game game;
	
	public GamePanel(Game game){
		//tits
		setGame(game);
		setHeight(game.getHeight());
		setWidth(game.getWidth());
		
		initialize();
		this.setVisible(true);
	}
	
	public void initialize(){
		Dimension gameDimension = new Dimension(columns * CELL_SIZE, rows * CELL_SIZE);
		this.setSize(gameDimension);
		this.setLayout(new FlowLayout());
//		this.setPreferredSize(gameDimension);
		
	}
	@Override
	public void paint(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < columns; col++){
				if(game.isAlive(row, col)){
					g2.setColor(ALIVE);
					
				}else{
					g2.setColor(DEAD);
				}
								
				g2.fillRect(	col*CELL_SIZE, 	//x position
								row*CELL_SIZE,	//y position
								CELL_SIZE,		//width
								CELL_SIZE);		//height
			}
		}
		
	}
	
	
	//Setters and getters
	private void setWidth(int width){
		this.columns = width;
	}
	
	public int getWidth(){
		return columns;
	}
	
	private void setHeight(int height){
		this.rows = height;
	}
	
	public int getHeight(){
		return rows;
	}
	
	private void setGame(Game game){
		this.game=game;
	}
	public Game getGame(){
		return game;
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.add(new GamePanel(new Game()));
//
//	}

}
