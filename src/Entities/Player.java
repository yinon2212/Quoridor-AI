package Entities;

public class Player {
	private int x,xInMat;
	private int y,yInMat;
	private String name;
	private int numberOfWalls;
	private int endingRow;
	
	public Player(String name ,int x, int y, int xInMat, int yInMat, int endingRow) {
		this.name = name;
		this.x = x;
		this.y = y;
		numberOfWalls = 10;
		this.xInMat = xInMat;
		this.yInMat = yInMat;
		this.endingRow = endingRow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getNumberOfWalls() {
		return numberOfWalls;
	}

	public void setNumberOfWalls(int numberOfWalls) {
		this.numberOfWalls = numberOfWalls;
	}

	public int getxInMat() {
		return xInMat;
	}

	public void setxInMat(int xInMat) {
		this.xInMat = xInMat;
	}

	public int getyInMat() {
		return yInMat;
	}

	public void setyInMat(int yInMat) {
		this.yInMat = yInMat;
	}

	public int getEndingRow() {
		return endingRow;
	}

	public void setEndingRow(int endingRow) {
		this.endingRow = endingRow;
	}

	@Override
	public String toString() {
		return "Player [x=" + x + ", xInMat=" + xInMat + ", y=" + y + ", yInMat=" + yInMat + ", name=" + name
				+ ", numberOfWalls=" + numberOfWalls + ", endingRow=" + endingRow + "]";
	}

		

	
	
	
	

}
