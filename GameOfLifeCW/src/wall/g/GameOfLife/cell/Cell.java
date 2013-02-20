package wall.g.GameOfLife.cell;

import java.util.ArrayList;
import java.util.List;
/*
 * Author: Glen Wallace
 * Description: Conway's Game Of Life cell. This is the basic form of life and the cell is either alive or dead.
 * 
 * Rules for living:
 * 
 1)   Any live cell with fewer than two live neighbours dies, as if caused by under-population.
 2)   Any live cell with two or three live neighbours lives on to the next generation.
 3)   Any live cell with more than three live neighbours dies, as if by overcrowding.
 4)   Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 
 *
 * Architecture:
 * Each cell has a boolean value of either alive or dead.
 * Each cell contains a list of each of its neighboring cells
 */

/**
 * @author Glen
 *
 */
public class Cell {
	
	private static int BORN = 3;			//The required number of neighbors for a new cell to be born.
	private static int SURVIVAL_LOW = 2;	//One of the number of neighbors that results in a cell remaining alive
	private static int SURVIVAL_HIGH = 3;	//Another of the number of neighbors that results in a cell remaining alive. (consider making this an array)
	
	private boolean alive;
	private List<Cell> neighbors;
	
	/**
	 * Default constructor, initializes "alive" to false.
	 */
	public Cell(){
		this(false);
	}
	
	/**
	 * Overloaded constructor that takes boolean value.
	 * @param alive - Whether or not the cell is alive.
	 */
	public Cell(boolean alive){
		setAlive(alive);
		neighbors = new ArrayList<Cell>();
	}
	
	/**
	 * @param alive - Sets the boolean member "alive" to true or false.
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;		
	}
	
	/**
	 * @return alive - Determines wither or not the current cell is alive or dead.
	 */
	public boolean isAlive(){
		return this.alive;
	}
	
	/**
	 * @param neighbor - A cell that neighbors this cell.
	 */
	public void addNeighbor(Cell neighbor){
		neighbors.add(neighbor);
	}
	
	/**
	 * Algorithm determins whether or not this cell should be alive or dead.
	 * 
	 * Pseudocode:
	 * 
	 * int liveNeighbors = 0
	 * boolean shouldLive = false
	 * Iterate through each neighbor
	 * 		if that neighbor is alive 
	 * 			increment liveNeighbors
	 * 		if liveNeighbors = SURVIVAL_HIGH
	 * 			set shouldLive to true
	 * 		else if this cell is alive
	 * 			if liveNeighbors = SURVIVAL_LOW
	 * 				set shouldLive to true
	 * set this cell's "alive" member to whatever shouldLive is
	 * 		
	 * 
	 * 		
	 */
	public boolean update(){//changed it to return a boolean to prevent changes within a single update from affecting the results
		int liveNeighbors = 0;			//Number of neighbors that are alive
		boolean shouldLive = false;		//Whether or not the cell should be alive
		int iterations = 0;				//Iteration for indexing
		while(iterations < this.neighbors.size()){//Iterate through the neighbors
			if(neighbors.get(iterations).isAlive()){//If the neighbor at said index is alive then increment liveNeighbors
				liveNeighbors++;
			}//end if
			iterations++;
		}//end while
		if(liveNeighbors == SURVIVAL_HIGH){
			shouldLive = true;
		}//end if
		else if(this.isAlive()){
			if (liveNeighbors == SURVIVAL_LOW){
				shouldLive = true;
			}//end if
		}//end else if
		//this.setAlive(shouldLive);
		
//		System.out.println("Neighbors: " + neighbors.size());
//		System.out.println("Live neighbors: " + liveNeighbors);
//		
//		System.out.println("Should live: " + shouldLive);
		
		return shouldLive;
	}//end update
	
		
	public void printNumberOfNeighbors(){
		System.out.println(neighbors.size());
	}
	
	public Cell copy(){
		Cell temp = new Cell(this.alive);
		for(Cell neighbor : this.neighbors){
			temp.addNeighbor(new Cell(neighbor.isAlive()));//for each neighbor create a dummy-copy of it and add it to the temp's neighbor list
		}
		return temp;
	}
	
	public static void main(String[] args){
		
		//testing
		
		Cell cell1 = new Cell(false);
		Cell cell2 = new Cell(false);
		cell1.addNeighbor(cell2);
		Cell cell3 = new Cell(true);
		cell1.addNeighbor(cell3);
		Cell cell4 = new Cell(false);
		cell1.addNeighbor(cell4);
		Cell cell5 = new Cell(true);
		cell1.addNeighbor(cell5);
		Cell cell6 = new Cell(true);
		cell1.addNeighbor(cell6);
		
		System.out.print("Before: " + cell1.isAlive());
		cell1.update();
		System.out.println(" | After: "+ cell1.isAlive());
		
	}
}
