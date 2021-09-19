package Logic;
import java.util.Arrays;

public class Cell implements Cloneable{
	public static int NUM_NEIGHBORS = 4;
	private Cell [] neighbors;
    private int x,y,endingRow;
	
    public Cell(int x, int y) {
    	this.x = x;
		this.y = y;
		this.neighbors = new Cell[NUM_NEIGHBORS];		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void placeWall(int dir) {
		this.neighbors[dir] = null;
	}
	
	public Cell getNeighbor(int dir) {
		return this.neighbors[dir];
	}
	
	public int getEndingRow() {
		return endingRow;
	}
	
	public void setEndingRow(int endingRow) {
		this.endingRow = endingRow;
	}

	public Cell[] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Cell[] neighbors) {
		this.neighbors = neighbors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public Cell clone() throws CloneNotSupportedException	{

		Cell temp = (Cell)super.clone();
		
		for(int i = 0;i<NUM_NEIGHBORS; i++)
			temp.neighbors[i] = this.neighbors[i].clone();
		
		return temp;
	}
	
	
	
	
	/*
	public String toString() {
		String str = "";
		for(int i=0;i<4; i++) {
			str+=neighbors[i]+",";
		}
		return str;
	}
	*/
		
}
