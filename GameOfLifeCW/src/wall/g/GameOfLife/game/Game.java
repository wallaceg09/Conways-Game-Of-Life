package wall.g.GameOfLife.game;

import java.util.Random;

import wall.g.GameOfLife.cell.Cell;

/**
 * @author Glen
 * 
 * The actual game. This creates a 2d array, then iterates through the array linking each cell with its closest neighbors.
 *
 */
public class Game {
	private int gameWidth;
	private int gameHeight;
	
	private Cell[][] cellMatrix;
	private Cell[][] cellMatrixBuffer;//this allows the processing of the cells update method to not be affected by changes via the neighbor cells update calls
	
	public static Random rand = new Random(999999999L);//seed 999999999L
	
	public Game(){
		this(100, 100);
	}
	public Game(int width, int height){
		setWidth(width);
		setHeight(height);
		
		cellMatrix = new Cell[height][width];
		cellMatrixBuffer = new Cell[height][width];
		
		initialize();
	}
	
	private void initialize(){
		randomizeCells();
		connectCells();
	}
	
	private void randomizeCells(){
		for(int height = 0; height < gameHeight; height++ ){
			for (int width = 0; width < gameWidth; width++){
				if(rand.nextInt(100)%2 == 0){//give each cell a 50/50 chance of being alive

					cellMatrix[height][width] = new Cell(true);
//					cellMatrixBuffer[height][width] = new Cell(true);
				}
				else{
					cellMatrix[height][width] = new Cell(false);
//					cellMatrixBuffer[height][width] = new Cell(true);
				}
			}
		}
	}
	
	private void connectCells(){
		for(int row = 0; row < gameHeight; row++){//iterate through each row
			for(int col = 0; col < gameWidth; col++){//iterate through each column
				
				//start from the neighbor that is one left and one above the cell, then iterate through to one right and one below the cell


				for(int neighRow = row-1; neighRow <= row+1; neighRow++){
					for(int neighCol = col-1; neighCol <= col+1; neighCol++){
						
						if((neighRow != row) || (neighCol != col)){

							try{
								cellMatrix[row][col].addNeighbor(cellMatrix[neighRow][neighCol]);
//								cellMatrixBuffer[row][col].addNeighbor(cellMatrixBuffer[neighRow][neighCol]);
							}catch (IndexOutOfBoundsException ioobe){//just ignore any indexing problems

							}
							
						}
					}
				}

				
			}
		}
	}

	public void update(){
		//FIXME: I need to find a way to stop the updating of cells in the same update call from affecting the update algorithm of other cells. --FIXED--
		cellMatrixBuffer = deepCopy(cellMatrix);
		for(int row = 0; row < gameHeight; row++){
			for(int col = 0; col < gameWidth; col++){
				boolean live = cellMatrixBuffer[row][col].update();
				cellMatrix[row][col].setAlive(live);
			}
		}
	}
	
	public boolean isAlive(int row, int col){
		return cellMatrix[row][col].isAlive();
	}
	
	public int getWidth() {
		return gameWidth;
	}
	private void setWidth(int width) {
		this.gameWidth = width;
	}
	public int getHeight() {
		return gameHeight;
	}
	private void setHeight(int height) {
		this.gameHeight = height;
	}
	
	public void printCellMatrix(){
		for(Cell[] row : cellMatrix){
			for(Cell element : row){
				System.out.print(element.isAlive() + "\t");
			}
			System.out.println();
		}
	}
	
	private Cell[][] deepCopy(Cell[][] array){
		Cell[][] temp = new Cell[array.length][array[0].length];
		for(int row = 0; row < array.length; row++){
			for(int col = 0; col < array[0].length; col++){
				temp[row][col] = array[row][col].copy();
			}
		}
		return temp;
	}
	
	public static void main(String[] args){
		Game testGame = new Game(10, 10);
		testGame.printCellMatrix();
		
		testGame.update();
		
		System.out.println("-------------------------------");
		
		testGame.printCellMatrix();
		
//		int count = 0;
//		for(int i = 0; i < 100; i++){
//			if(i%20 == 0){
//				count++;
//			}
//		}
//		System.out.printf("%d/100\t:\t%f\n", count, count/100.0);
	}
}
