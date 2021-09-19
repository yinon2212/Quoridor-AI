package Logic;

import Entities.WallForAi;

public class Graph {
	public final int N=9;
	public Cell[][] cells;
	public enum DIR  {                      UP,     RIGHT,      DOWN,     LEFT     };
    public static int[][] directions = { { -1, 0 }, { 0, 1 },  { 1, 0 }, { 0, -1 }, };
	Board board;
    
    public Graph(Board board) {
    	cells = new Cell[N][N];
		for(int i = 0; i<N; i++) {
			for(int j=0; j<N; j++) {
				cells[i][j] = new Cell(i,j);
			}
		}
		ConnectCells();
    }
    
    private void ConnectCells()
    {
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    int row_neighbor = cells[i][j].getX() + directions[k][0];
                    int col_neighbor = cells[i][j].getY() + directions[k][1];
                    if (IsInBoard(row_neighbor, col_neighbor))
                        cells[i][j].getNeighbors()[k] = cells[row_neighbor][col_neighbor];
                }
            }
        }
    }
	
	public  boolean IsInBoard(int rowtry, int coltry)
    {
        return rowtry >= 0 && rowtry < N && coltry >= 0 && coltry < N;
    }

	
	/**
     * This function place wall in the data structure
     * @param wfa - the data of the wall
     * @return place wall in the data structure
     */ 
	public void setWall(WallForAi wfa) {
		int dir = wfa.getDir();
		int row_n = wfa.getRow() + WallForAi.directions[dir][0];
		int col_n = wfa.getCol() + WallForAi.directions[dir][1];
		
		if(col_n == 9)
			col_n--;
		
		if(row_n == 9)
			row_n--;
		
		cells[wfa.getRow()][wfa.getCol()].placeWall(dir);                ///0  1 2 3
		int dir_n = dir-2;
		if ( dir_n < 0 )
			dir_n +=4;
		cells[row_n][col_n].placeWall(dir_n);
		//2  3 0  1
		if(wfa.getTypeOfWall() == 0) {
			cells[wfa.getRow()+1][wfa.getCol()].placeWall(dir);                ///0  1 2 3
			cells[row_n+1][col_n].placeWall(dir_n);                           //2  3 0  1
		}
		else {
			cells[wfa.getRow()][wfa.getCol()+1].placeWall(dir);                ///0  1 2 3
			cells[row_n][col_n+1].placeWall(dir_n);                           //2  3 0  1
		}
	}

	/**
     * This function remove wall in the data structure
     * @param wfa - the data of the wall
     * @return remove the wall in the data structure
     */ 
	public void rewmoveWall(WallForAi wfa) {
		int dir = wfa.getDir();
		int row_n = wfa.getRow() + WallForAi.directions[dir][0];
		int col_n = wfa.getCol() + WallForAi.directions[dir][1];
		
		if(col_n == 9)
			col_n--;
		
		if(row_n == 9)
			row_n--;
		
		cells[wfa.getRow()][wfa.getCol()].getNeighbors()[dir] = cells[row_n][col_n]; 
		
		int dir_n = dir-2;
		if ( dir_n < 0 )
			dir_n +=4;
		

		cells[row_n][col_n].getNeighbors()[dir_n]=cells[wfa.getRow()][wfa.getCol()];
		
		if(wfa.getTypeOfWall() == 0) {
			cells[row_n+1][col_n].getNeighbors()[dir_n]=cells[wfa.getRow()+1][wfa.getCol()];
			cells[wfa.getRow()+1][wfa.getCol()].getNeighbors()[wfa.getDir()]=cells[row_n+1][col_n];
		}
		else {
			cells[row_n][col_n+1].getNeighbors()[dir_n]=cells[wfa.getRow()][wfa.getCol()+1];
			cells[wfa.getRow()][wfa.getCol()+1].getNeighbors()[wfa.getDir()]=cells[row_n][col_n+1];

		}
		
	}

	
	
	/**
     * This function place wall in the data structure
     * @param wfa - the data of the wall
     * @return place wall in the data structure
     */ 
	public void placeWallForHuman(WallForAi wfa) {
		int dir = wfa.getDir();
		int row_n = wfa.getRow() + WallForAi.directions[dir][0];
		int col_n = wfa.getCol() + WallForAi.directions[dir][1];
		
		cells[wfa.getRow()][wfa.getCol()].placeWall(dir);                ///0  1 2 3
		int dir_n = dir-2;
		if ( dir_n < 0 )
			dir_n +=4;
		cells[row_n][col_n].placeWall(dir_n); 
		
		if(wfa.getTypeOfWall() == 0) {
			cells[wfa.getRow()+1][wfa.getCol()].placeWall(dir);                ///0  1 2 3
			cells[row_n+1][col_n].placeWall(dir_n);                           //2  3 0  1
		}
		else {
			cells[wfa.getRow()][wfa.getCol()+1].placeWall(dir);                ///0  1 2 3
			cells[row_n][col_n+1].placeWall(dir_n);                           //2  3 0  1
		}
	}

	
}
