package Entities;

public class WallForAi{
	public  static int [][]  directions = { {-1,0}, {0,1}, {1,0}, {0,-1} };
	private int row, col, dir, typeOfWall;
	
	public WallForAi(int row, int col, int dir, int typeOfWall) {
		this.row = row;
		this.col = col;
		this.dir = dir;
		this.typeOfWall = typeOfWall;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getTypeOfWall() {
		return typeOfWall;
	}

	public void setTypeOfWall(int typeOfWall) {
		this.typeOfWall = typeOfWall;
	}

	@Override
	public String toString() {
		return "WallForAi [row=" + row + ", col=" + col + ", dir=" + dir + ", typeOfWall=" + typeOfWall + "]";
	}
	
	

}
