package Logic;

import java.util.ArrayList;

public class Path {
	public ArrayList<Cell>   cells;
	
	 public Path( )
    {
        cells = new ArrayList<Cell>();
    }
	 public Path(Cell start)
    {
        cells = new ArrayList<Cell>();
        cells.add(start);
    }
	 
	 public String toString() {
		 return cells.toString();
	 }
}
