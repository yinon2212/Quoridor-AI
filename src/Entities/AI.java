package Entities;

import java.beans.DesignMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;

import GUI.GameFrame;
import Logic.Board;
import Logic.Cell;
import Logic.Path;

public class AI{
	private Board board;
	private WallForAi blockingWallWeakSpot;
	
	public AI(Board board) {
		this.board = board;
		blockingWallWeakSpot = null;
		
	}
	
	/**
	 * This function is on charge to create the AI algorithm process
	 */
	public void moveComputer() {
		if(board.computer.getxInMat() == board.graph.N-1) {
			board.setEnd(true);
			board.endGame(board.computer);
		}else {
			//System.out.println(BFS(graph.cells[computer.getxInMat()][computer.getyInMat()], null));
			Path shortestPathComp = BFS(board.computer, null);
			Path shortestPathPlayer = BFS(board.human, null);
			
		    // If the opponent's shortest path is shorter than the computer by more than one
		    // depending on the turn number
		    // Only try to place a wall if the next move is not a winning move
			//System.out.println("Shortest path player: "+shortestPathPlayer.toString());
		    if (shortestPathComp.cells.size() > board.PATH_LENGTH_TO_WIN && shortestPathComp.cells.size() - board.PATH_DIFFERENCE_ALLOWED > shortestPathPlayer.cells.size()){
		    	if(board.computer.getNumberOfWalls() > 0) {//if the computer has walls
		    		if(!blockOpponent(shortestPathPlayer.cells)) {//if the idea for placing wall failed
		    			doStepAi(shortestPathComp);//do move
		    		}
		    	}
		    	else {
		    		doStepAi(shortestPathComp);
		    	}
		        	/*
		        	WallCheck();
		            if (this.wallsLeft == 0 || !blockOpponent(opponentPath, opMoveOptions, false))
		                tryToMove(moveOptions, shortestPath, opMoveOptions, opponentPath);
		                */
		    }
		    else {
		    	//checkPathWidth(shortestPathComp);
		    	doStepAi(shortestPathComp);
		       // if(human.getNumberOfWalls() == 0)//if the human player has no walls left
		        	//doStep(shortestPathComp);//make move
		        //else
		        	//checkPathWidth(shortestPathComp);//check the width of the path and act in the correct way
		    }
		}	
	}
	
	private void doStepAi(Path shortestPathComp) {
		board.computer.setxInMat(shortestPathComp.cells.get(0).getX());
		board.computer.setyInMat(shortestPathComp.cells.get(0).getY());
		System.out.println("X: "+board.computer.getxInMat()+" Y:" +board.computer.getyInMat());

		board.computer.setY(((board.computer.getxInMat()+(board.CELL_SIZE*board.computer.getxInMat()))) + board.JUMP_ROW_EXTRA);
		board.computer.setX((board.computer.getyInMat()+(board.CELL_SIZE*board.computer.getyInMat()))+board.START_Y_PLAYER2);		
	}
	
	

	/**
     * This function return the best short path for the player
     * @param player - the current player
     * @param destination - the end cell for the BFS
     * @return  Path - the best short path for the player
     */  
	 public Path BFS(Player player, Cell destination)
     {
         Queue<Cell> queue = new ArrayDeque<Cell>();
         HashSet<Cell> visited = new HashSet<Cell>();
         Map<Cell, Cell> parentNodes = new HashMap<Cell, Cell>(); // Key - node, value - parent
         Path shortestPathsEnds = new Path();
         queue.add(board.graph.cells[player.getxInMat()][player.getyInMat()]);
         parentNodes.put(board.graph.cells[player.getxInMat()][player.getyInMat()], null);

         shortestPathsEnds.cells.add(new Cell(player.getxInMat(),player.getyInMat()));
         // this loop will stop when:
         // we have gone over all of the reachable nodes and did not reach the end line
         // we have reached all possible paths and its start will be in the ShortestPathEnds
         while (queue.size() > 0)
         {
       	  Cell nextNode = queue.poll();
             visited.add(nextNode);
             if ( (destination == null && nextNode.getX() == player.getEndingRow() //|| nextNode.col== player.getEndingCol())
                   || destination != null && nextNode==destination))
                 shortestPathsEnds.cells.add(nextNode);
             else
             {
                  for (Cell neighbor : nextNode.getNeighbors())
                     if (neighbor != null && !visited.contains(neighbor))
                     {
                         if (parentNodes.containsKey(neighbor)==false)
                             parentNodes.put(neighbor, nextNode);
                         queue.add(neighbor);
                     }
             }
         }
         if (shortestPathsEnds.cells.size()-1 == 0)
             return null;
         return  determineShortestPath(shortestPathsEnds, parentNodes,board.graph.cells[player.getxInMat()][player.getyInMat()]);
     }
	 
	   /**
	  * This function gets the parentNodes from the BFS and the shortest paths' possible ends.
	  * It determines which path is indeed the shortest one.
	  * @param shortestPathsEnds  the shortest paths possible ends
	  * @param parentNodes  the parent Nodes HashMap created by the BFS
	  * @return an ArrayList of the shortest path
	  */  
	     public Path determineShortestPath(Path shortestPathsEnds, Map<Cell, Cell> parentNodes, Cell playerCell)
	     {
	         Path shortestPath = new Path();
	         Path currentPath = new Path();
	         for (Cell shortestPathsEnd : shortestPathsEnds.cells)
	         {
	             currentPath.cells.clear();
	             Cell cell = shortestPathsEnd;
	             while (cell != null)
	             {
	                 if ( !(cell.getX() == playerCell.getX() && cell.getY() == playerCell.getY()))
	                     currentPath.cells.add(cell);
	                 cell = parentNodes.get(cell);
	             }
	             if (shortestPath.cells.size() == 0 || shortestPath.cells.size() > currentPath.cells.size())
	             {
	                 shortestPath.cells.clear();
	                 shortestPath.cells.addAll(currentPath.cells);
	             }
	         }
	         Collections.reverse(shortestPath.cells);
	         return shortestPath;
	     }
	     

	     /**
	     * This function try to block the opponent
	     * @param opponentPath  ArrayList of the path of the human player
	     * @return  boolean if the block succeed
	     */  
	 	public  boolean blockOpponent(ArrayList<Cell> opponentPath) {
	 		int bestDiffrenece = Integer.MAX_VALUE;
	 		Path myBfs = null, opBfs = null;
	 		int bestRow = -1, bestCol = -1, dir = -1,typeOfWall = -1;
	 		WallForAi[] wfa = new WallForAi[2];
	 		
	 		for(int i = 0; i<opponentPath.size()-1; i++) {
	 			wfa[0] = determineRowAndColOfWall(opponentPath.get(i),opponentPath.get(i+1));
	 			System.out.println("First available wall: "+wfa[0]);
	 			wfa[1] = getOppositeWall(wfa[0]);
	 			System.out.println("Second available wall: "+wfa[1]);

	 			for(WallForAi wfa2 : wfa) {
	 				if(wfa2 != null) {
	 					if(board.isIllegal(wfa2.getRow(),wfa2.getCol(),wfa2.getDir(),wfa2.getTypeOfWall()))
	 						continue;
	 					
		 				if(board.graph.cells[wfa2.getRow()][wfa2.getCol()].getNeighbor(wfa2.getDir()) != null) {
		 					board.graph.setWall(wfa2);//place the virtual wall	
		 		 			
		 		 			myBfs  = (BFS(board.computer, null));
		 		 			opBfs = (BFS(board.human, null));		
		 		 			
		 		 			if(myBfs == null || opBfs == null) {
		 		 				board.graph.rewmoveWall(wfa2);
		 		 			}
		 		 			else {
		 		 				if((myBfs.cells.size())-opBfs.cells.size() < bestDiffrenece) {
		 		 					bestDiffrenece = (myBfs.cells.size())-opBfs.cells.size();
		 		 					bestRow = wfa2.getRow();
		 		 					bestCol = wfa2.getCol();
		 		 					dir = wfa2.getDir();
		 		 					typeOfWall = wfa2.getTypeOfWall();
		 		 				}
		 		 				board.graph.rewmoveWall(wfa2);
		 		 			}
		 		 		}
	 				}
	 			}
	 		}
	 		if(wfa == null || myBfs == null || opBfs == null)
	 			return false;
	 		else {
	 			placeWallInGraph(new WallForAi(bestRow, bestCol, dir, typeOfWall));
	 			/*
	 			graph.cells[bestRow][bestCol].placeWall(dir);
	 			if(typeOfWall == 0)
	 				standWalls.add(new Wall(bestRow*10,bestCol*10, "",bestRow, bestCol));
	 			else
	 				layWalls.add(new Wall(bestCol*100,bestRow*100, "",bestRow, bestCol));
	 				*/
	 			return true;
	 		}
	 			
	 		//return new WallForAi(bestRow,bestCol,dir,typeOfWall);
	 		
	 	}
	 	/**
	     * This function determine the best wall to block the opponent
	     * @param start - the current cell of the opponent
	     * @param nextMove - the next cell of the opponent
	     * @return  returns WallForAi that contains the data for the blocking wall
	     */  
	 	public WallForAi determineRowAndColOfWall(Cell start ,Cell nextMove) {
			if(start.getX() > nextMove.getX())  //Up
				return new WallForAi (start.getX(),start.getY(), 0,1);
	 		
			
			if(start.getX() < nextMove.getX()) //Down
				return new WallForAi(start.getX(),start.getY(), 2,1);
			
			if(start.getY() < nextMove.getY())  //Right
				return new WallForAi(start.getX(),start.getY(), 1,0);
			
			
			if(start.getY() > nextMove.getY()) { //Left
				return new WallForAi(start.getX(),start.getY(), 3,0);
			}
			
			return null;	
		}
	 
	 	public void placeWallInGraph(WallForAi wfa) {
			board.graph.setWall(wfa);
			board.computer.setNumberOfWalls(board.computer.getNumberOfWalls()-1);
	 		GameFrame.number_of_walls_blue.setText("number of walls blue: "+board.computer.getNumberOfWalls());
	 		if(wfa.getDir() == 0) {
	 			//graph.cells[bestRow-1][bestCol].placeWall(2);
	 			board.layWalls.add(new Wall(wfa.getCol()*board.CELL_SIZE,(wfa.getRow()*board.CELL_SIZE)/*-CELL_SIZE*/, "",wfa.getRow(), wfa.getCol()));
	 			System.out.println("placed wall in dir: "+wfa.getDir()+". X: "+wfa.getRow()+" Y: "+wfa.getCol());
	 			return;
	 		}
	 		
	 		if(wfa.getDir() == 1) {
	 			//graph.cells[bestRow][bestCol+1].placeWall(3);
	 			board.standWalls.add(new Wall(wfa.getCol()*board.CELL_SIZE+board.CELL_SIZE,(wfa.getRow()*board.CELL_SIZE)/*-CELL_SIZE*/, "",wfa.getRow(), wfa.getCol()));
	 			System.out.println("placed wall in dir: "+wfa.getDir()+". X: "+wfa.getRow()+" Y: "+wfa.getCol());

	 			return;
	 		}
	 		
	 		if(wfa.getDir() == 2) {
	 			//graph.cells[bestRow+1][bestCol].placeWall(0);
	 			board.layWalls.add(new Wall(wfa.getCol()*board.CELL_SIZE,(wfa.getRow()*board.CELL_SIZE)/*-CELL_SIZE*/, "",wfa.getRow(), wfa.getCol()));
	 			System.out.println("placed wall in dir: "+wfa.getDir()+". X: "+wfa.getRow()+" Y: "+wfa.getCol());

	 			return;
	 		}
	 		
	 		if(wfa.getDir() == 3) {
	 			//graph.cells[bestRow][bestCol-1].placeWall(1);
	 			board.standWalls.add(new Wall(wfa.getCol()*board.CELL_SIZE,(wfa.getRow()*board.CELL_SIZE)/*-CELL_SIZE*/, "",wfa.getRow(), wfa.getCol()));
	 			System.out.println("placed wall in dir: "+wfa.getDir()+". X: "+wfa.getRow()+" Y: "+wfa.getCol());

	 			return;
	 		}
			
		}
	 	
	 	 /**
	     * This function returns the second available wall to block the cell
	     * @param wfa - the data of the first wall 
	     * @return  WallForAi - the second available wall to block the cell
	     */  
	 	public WallForAi getOppositeWall(WallForAi wfa) {
	 		if(wfa == null)
	 			return null;
	 		
	 		if(wfa.getTypeOfWall() == 1) {
	 			if(wfa.getCol() == 0)
	 				return null;
	 			return new WallForAi(wfa.getRow(),wfa.getCol()-1,wfa.getDir(),wfa.getTypeOfWall());
	 		}
	 		
	 		if(wfa.getRow() == 0)
					return null;
				return new WallForAi(wfa.getRow()-1,wfa.getCol(),wfa.getDir(),wfa.getTypeOfWall());
	 	}
	 	
	 	/**
	 	 * This function check the width of the computer path and if there is a need to block a weak spot it does that
	 	 * @param computerPath - the shortest path of the computer
	 	 */
	 	public void checkPathWidth(Path computerPath) {
	 		
	 		boolean decisionMade = false;
	 		
	 		for(int i = 0; i<computerPath.cells.size() && !decisionMade; i++) {
	 			int width = getWidhtSize(computerPath.cells.get(i));
	 			
	 			if(width<=2) {
	 				System.out.println("Weak spot at: X: "+computerPath.cells.get(i).getX()+" Y: "+computerPath.cells.get(i).getY());
	 				try {
	 					decisionMade = handleWeakSpot(computerPath, computerPath.cells.get(i),computerPath.cells.get(i+1));
	 				}
	 				catch (IndexOutOfBoundsException ioobe) {
	 					System.out.println("checkPathWidth: end of path! ");
	 				}	
	 			}
	 		}
	 			
	 	}
	 	
	 	/**
	 	 *  This function calculates and returns the width of the cell (the number of available neighbors)
	 	 * @param cell - the cell that we want to calculate it width
	 	 * @return the width of the cell (the number of available neighbors)
	 	 */
	 	public int getWidhtSize(Cell cell) {
	 		int width = 0;
	 		for(int i=0 ;i<Cell.NUM_NEIGHBORS; i++) {
	 			if(i == 2)
	 				continue;
	 			width += cell.getNeighbor(i) == null ? 0:1;
	 		}
	 		 
	 		return width;
	 	}
	 	
	 	public boolean handleWeakSpot(Path myPath, Cell weakSpot, Cell neighborWeakSpot) {
	 		WallForAi[] walls = new WallForAi[2];
	 		walls[0] = determineRowAndColOfWall(weakSpot, neighborWeakSpot);
	 		walls[1] = getOppositeWall(walls[0]);
	 		WallForAi blockWeak = null;
	 		Path compBfs, humanBfs;
	 		int comLen, humanLen;
	 		
	 		for(WallForAi wall:walls) {
	 			if(wall == null)
	 				continue;
	 			compBfs = BFS(board.computer, null);
	 			humanBfs = BFS(board.human, null);
	 			board.graph.setWall(wall); //virtually!
	 			if(BFS(board.computer, null) == null || BFS(board.human, null) == null) {
	 				board.graph.rewmoveWall(wall);
	 				continue;
	 			}
	 			int width = getWidhtSize(weakSpot);
	 			
	 			if(width == 0) {
	 				//TO DO!!!
	 			}
	 			board.graph.rewmoveWall(wall);
	 		}
	 		compBfs = BFS(board.computer, null);
 			humanBfs = BFS(board.human, null);
 			comLen = compBfs.cells.size();
 			humanLen = humanBfs.cells.size();
	 		if(blockWeak == null || comLen - humanLen <= 0 || ((comLen - humanLen <=2) && humanLen > 5)) 
	 			return false;
	 		
	 		setBlockingWallWeakSpot(blockWeak);
	 		return true;
	 		
	 	}

		public WallForAi getBlockingWallWeakSpot() {
			return blockingWallWeakSpot;
		}

		public void setBlockingWallWeakSpot(WallForAi blockingWallWeakSpot) {
			this.blockingWallWeakSpot = blockingWallWeakSpot;
		}
}
