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
	
	private static final int CELL_SIZE = 10;//The size of a single cell. Play around with this to make the cells different sizes. -NOTE: making this smaller increases the number of cells displayed but doesn't change the size of the panel (bug)
	private static final Color ALIVE = Color.white;
	private static final Color DEAD = Color.black;
	
//	private Rectangle2D[][] rectangleMap;//ommitting
	
	private int columns;//basically the horizontal number of cells from the Game object
	private int rows;//basically the vertical number of cells from the Game object
	
//	private Graphics graphics;//omitting
	
	private Game game;//Game object recieved from the GUI calling it
	
	public GamePanel(Game game){

		setGame(game);
		setHeight(game.getHeight());
		setWidth(game.getWidth());
		
		initialize();
		this.setVisible(true);
	}
	
	public void initialize(){
		Dimension gameDimension = new Dimension(columns * CELL_SIZE, rows * CELL_SIZE);//created as a dimension to make it easier to see this value in the debugger. 
		this.setSize(gameDimension);//FIXME: This value displays correctly in the debugger however the size doesn't change no matter what.
//		this.setLayout(new FlowLayout());//thought adding a layout manager might help. It did not
//		this.setPreferredSize(gameDimension);//this changed nothing.
		
	}
	@Override
	public void paint(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		
		for(int row = 0; row < rows; row++){//iterate through each cell in the game and create a rectangle 
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
