package Entities;

public class Wall implements Cloneable{
	private int x,xInMath;
	private int y,yInMath;
	private String id;
	
	public Wall(int xInMath, int yInMath) {
		this.id = "";
		this.x = 0;
		this.y = 0;
		this.id = id;
		this.xInMath=xInMath;
		this.yInMath=yInMath;
	}
	
	public Wall(int x, int y, String id,int xInMath, int yInMath) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.xInMath=xInMath;
		this.yInMath=yInMath;
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

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public int getxInMath() {
		return xInMath;
	}

	public void setxInMath(int xInMath) {
		this.xInMath = xInMath;
	}

	
	public int getyInMath() {
		return yInMath;
	}

	public void setyInMath(int yInMath) {
		this.yInMath = yInMath;
	}

	public Wall clone() throws CloneNotSupportedException {
		return (Wall)super.clone();
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wall other = (Wall) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	

}
