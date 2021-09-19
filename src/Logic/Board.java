package Logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Entities.AI;
import Entities.Player;
import Entities.Wall;
import Entities.WallForAi;
import GUI.BoardLayout;
import GUI.GameFrame;

public class Board{
	public Graph graph;
	private boolean end = false;
	public boolean computer_game;
	public Player computer, human;
	public AI computer_Ai;
	public List<Wall> standWalls,layWalls;
	public Wall sWall, lWall;	
	public static int PATH_LENGTH_TO_WIN = 2;
	public static int PATH_DIFFERENCE_ALLOWED = 0;
	public static int PIECE_SIZE = 50, START_X_GRAPHICS = 326,START_Y_PLAYER1 = 629, START_Y_PLAYER2 = 21,CELL_SIZE=75, JUMP_ROW_EXTRA = 20;
	BoardLayout boardPanel;
	public boolean humanTurn, computerTurn, sWallNeedToPlace, lWallNeedToPlace;
	
	public Board(boolean computer_game, BoardLayout boardPanel) {
		graph = new Graph(this);
		computer = new Player("BLUE", START_X_GRAPHICS, START_Y_PLAYER2, 0, graph.N/2, graph.N-1);
		human = new Player("RED", START_X_GRAPHICS, START_Y_PLAYER1, graph.N-1, graph.N/2, 0);
		
		standWalls = new ArrayList<Wall>();
		layWalls = new ArrayList<Wall>();
		
		humanTurn = true;
		computerTurn = false;
		sWallNeedToPlace = false;
		lWallNeedToPlace = false;
		this.boardPanel = boardPanel;
		this.computer_game = computer_game;
		
		
		computer_Ai = new AI(this);
	}
	
	public void movePlayer(int operation) {
		Player player = computer;
		if(!end) {//if the game has'nt finished
			if(lWallNeedToPlace || sWallNeedToPlace) {//if the player choose to set wall
				if(humanTurn)//if it's the human turn
					player = human;
				
				if(sWallNeedToPlace)	//if the player choose to set stand wall
					placeStandWall(operation,player);
				
				else	//if the player choose to set lay wall
					placeLayWall(operation,player);
			}
			else {
				if(!computer_game) {//if the game is 1V1- NOT AGAINST THE COMPUTER!
					player = switchTurns();
					movePlayerFor1V1(player, operation);
				}
				else {
					if(humanTurn) {
						movePlayerFor1V1(human, operation);
						switchTurns();
					}
				}
			}
		}
	}
	
	
	
	/**
     * This function contains all the actions for moving the player
     * @param operation - the direction of the move
     * @param player - the player that need to move
     * @return moving the player
     */ 
	public void movePlayerFor1V1(Player player, int operation) {
		System.out.println(player);
		switch(operation) {
		case 1://RIGHT
			if(player.getyInMat()<graph.N){
				if(!(isBlocking(player.getxInMat(),player.getyInMat(), 1,1))) {
					
					if((human.getxInMat() == computer.getxInMat() && human.getyInMat()+1 == computer.getyInMat())
							||computer.getxInMat() == human.getxInMat() && computer.getyInMat()+1 == human.getyInMat()) {
						player.setX(player.getX()+CELL_SIZE*2);
						player.setyInMat(player.getyInMat()+2);
					}
					else {
						player.setX(player.getX()+CELL_SIZE);
						player.setyInMat(player.getyInMat()+1);
					}
				}
				else {
					dontSwitchTurns();
				}
				
			}
			else {
				dontSwitchTurns();
			}
			break;
		case 2://LEFT
			if(player.getyInMat()>0){
				if(!(isBlocking(player.getxInMat(),player.getyInMat(), 3,0))) {
					//player.setX(player.getX()-100);
					//player.setyInMat(player.getyInMat()-1);
					if((human.getxInMat() == computer.getxInMat() && human.getyInMat()-1 == computer.getyInMat())
							||computer.getxInMat() == human.getxInMat() && computer.getyInMat()-1 == human.getyInMat()) {
						player.setX(player.getX()-CELL_SIZE*2);
						player.setyInMat(player.getyInMat()-2);
					}
					else {
						player.setX(player.getX()-CELL_SIZE);
						player.setyInMat(player.getyInMat()-1);
					}
				}
				else {
					dontSwitchTurns();
				}
			}
			else {
				dontSwitchTurns();
			}
			break;
		case 3://UP
			if(player.getxInMat() > 0){
				if(!(isBlocking(player.getxInMat(),player.getyInMat(), 0,3))) {
					
					if((human.getxInMat()-1 == computer.getxInMat() && human.getyInMat() == computer.getyInMat())
							||computer.getxInMat()-1 == human.getxInMat() && computer.getyInMat() == human.getyInMat()) {
						player.setY(player.getY()-CELL_SIZE*2);
						player.setxInMat(player.getxInMat()-2);
						
					}
					else {
						player.setY(player.getY()-CELL_SIZE);
						player.setxInMat(player.getxInMat()-1);
					}
				}
				else {
					dontSwitchTurns();
				}
			}
			else {
				dontSwitchTurns();
			}
			//player.setY(player.getY()-CELL_SIZE);
			if(human.getxInMat() == human.getEndingRow())
				endGame(human);
			break;
		case 4://DOWN
			if(player.getxInMat()+1 < graph.N){
				if(!(isBlocking(player.getxInMat(),player.getyInMat(), 2,3))) {
					if((human.getxInMat()+1 == computer.getxInMat() && human.getyInMat() == computer.getyInMat())
							||computer.getxInMat()+1 == human.getxInMat() && computer.getyInMat() == human.getyInMat()) {
						player.setY(player.getY()+CELL_SIZE*2);
						player.setxInMat(player.getxInMat()+2);
						
					}
					else {
						player.setY(player.getY()+CELL_SIZE);
						player.setxInMat(player.getxInMat()+1);
					}
					
				}
				else {
					dontSwitchTurns();
				}
				
			}
			else {
				dontSwitchTurns();
			}
			//player.setY(player.getY()+CELL_SIZE);
			if(computer.getxInMat() == computer.getEndingRow())
				endGame(computer);
			break;
		default:
			dontSwitchTurns();
		}
	}
	
	/**
     * This function don't switch the turns
     */  
	public void dontSwitchTurns() {
		if(!humanTurn) {
			humanTurn = true;
			computerTurn = false;
		}
		else {
			humanTurn = false;
			computerTurn = true;
		}
	}
	
	/**
     * This function switch the turns
     * @return the player that the turn is his turn
     */ 
	public Player switchTurns() {
		if(humanTurn) {
			computerTurn = true;
			humanTurn = false;
			System.out.println("human");
			return human;
			//player = human;
			//movePlayerFor1V1(human, operation);
		}
		
		System.out.println("comp");
		computerTurn = false;
		humanTurn = true;
		return computer;
		//movePlayerFor1V1(computer, operation);
		
	}
	
	/**
     * This function place stand wall in the data structure
     * @param operation - the direction of the stand wall
     * @param player - the player that set the wall
     * @return place wall in the data structure
     */ 
	public void placeStandWall(int operation, Player player) {
		switch(operation) {
		case 0:
			dontSwitchTurns();
			break;
		case 1://RIGHT
			if(sWall.getyInMath() < graph.N) {//IF THE WALL IS IN THE BOARD LIMITS
				sWall.setyInMath(sWall.getyInMath()+1);
				sWall.setX(sWall.getyInMath()*CELL_SIZE);
				//switchTurns();
			}
			break;
		case 2://LEFT
			if(sWall.getyInMath() > 0) {
				sWall.setyInMath(sWall.getyInMath()-1);
				sWall.setX(sWall.getyInMath()*CELL_SIZE);
				//switchTurns();
			}
			break;
		case 3://UP
			if((sWall.getxInMath()) > 0) {
				sWall.setxInMath(sWall.getxInMath()-1);
				sWall.setY(sWall.getxInMath()*CELL_SIZE);
				//switchTurns();
			}
			break;
		case 4://DOWN
			if((sWall.getxInMath()) < graph.N-1) {
				sWall.setxInMath(sWall.getxInMath()+1);
				sWall.setY(sWall.getxInMath()*CELL_SIZE);
				//switchTurns();
			}
			break;
		case 5://ENTER
			if(isIllegal(sWall.getxInMath(), sWall.getyInMath(), 1, 0)/*!isExist(sWall.getxInMath(),sWall.getyInMath(),0)*/) {
				System.out.println("stand wall already exist! change the location!");
			}
			else {
				try {
					standWalls.add(sWall.clone());
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
				placeWallForHuman(new WallForAi(sWall.getxInMath(),sWall.getyInMath(),1,0));
				//graph.setWall(new WallForAi(sWall.getxInMath(),sWall.getyInMath(),1,0));
		 		player.setNumberOfWalls(player.getNumberOfWalls()-1);
		 		//GameFrame.number_of_walls_red.setText("number of walls red: "+player.getNumberOfWalls());
		 		changeTheNumberOfWallLabel(player);
				System.out.println("row: "+sWall.getxInMath() +" col: "+sWall.getyInMath());
		 		sWallNeedToPlace = false;
				sWall = null;
				switchTurns();
				//humanTurn = false;
				//computerTurn = true;
			}
			break;
		}
		
	}
	
	
	/**
     * This function place lay wall in the data structure
     * @param operation - the direction of the lay wall
     * @param player - the player that set the wall
     * @return place wall in the data structure
     */ 
	public void placeLayWall(int operation, Player player) {
		switch(operation) {
		case 0:
			dontSwitchTurns();
			break;
		case 1:
			if(lWall.getyInMath()+1 < graph.N-1) {
				lWall.setyInMath(lWall.getyInMath()+1);
				lWall.setX(lWall.getyInMath()*CELL_SIZE);
				
			}
			break;
		case 2:
			if(lWall.getyInMath()-1 >= 0) {
				lWall.setyInMath(lWall.getyInMath()-1);
				lWall.setX(lWall.getyInMath()*CELL_SIZE);
				
			}
			break;
		case 3:
			if((lWall.getxInMath()-1) >= 0) {
				lWall.setxInMath(lWall.getxInMath()-1);
				lWall.setY(lWall.getxInMath()*CELL_SIZE);
				
			}
			break;
		case 4:
			if((lWall.getxInMath()+1) < graph.N) {
				lWall.setxInMath(lWall.getxInMath()+1);
				lWall.setY(lWall.getxInMath()*CELL_SIZE);
				
			}
			break;
		case 5:
			if(isIllegal(lWall.getxInMath(), lWall.getyInMath(), 0, 1)/*!isExist(lWall.getxInMath(),lWall.getyInMath(),1)*/) {
				System.out.println("lay wall already exist! change the location!");
			}
			else {
				try {
					layWalls.add(lWall.clone());
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
				placeWallForHuman(new WallForAi(lWall.getxInMath(),lWall.getyInMath(),0,1));
				//graph.setWall(new WallForAi(lWall.getxInMath(),lWall.getyInMath(),0,1));
				
		 		player.setNumberOfWalls(player.getNumberOfWalls()-1);
		 		//GameFrame.number_of_walls_red.setText("number of walls red: "+player.getNumberOfWalls());
		 		changeTheNumberOfWallLabel(player);
				System.out.println("row: "+lWall.getxInMath() +" col: "+lWall.getyInMath());
				//tempDir[0] = 0;
				//tempDir[1] = 0;
				lWallNeedToPlace = false;
				lWall = null;
				switchTurns();
			}
			break;	
		}
	}
	
	
	
	/**
     * This function set the straight wall data on (sWallNeedToPlace = true, sWall), and update the next player turn
     */  
	public void sWallClikced() {
		System.out.println("sWallClikced");
		Player player;
		if(!end) {
			player = humanTurn ? human : computer;
			if(player.getNumberOfWalls() > 0) {
				sWallNeedToPlace = true;
				sWall = new Wall(CELL_SIZE,0,"S",0,1);
				lWallNeedToPlace = false;
			}
		}
	}
	
	/**
     * This function set the lay wall data on (lWallNeedToPlace = true, lWall), and update the next player turn
     */  
	public void lWallClikced() {
		Player player;
		System.out.println("lWallClikced");
		if(!end ) {	
			player = humanTurn ? human : computer;
			if(player.getNumberOfWalls() > 0) {
				lWallNeedToPlace = true;
				lWall = new Wall(5,CELL_SIZE,"L",0,0);
				sWallNeedToPlace = false;

			}
		}
	}
	
	public void changeTheNumberOfWallLabel(Player player) {
		if(player == human) {
			GameFrame.number_of_walls_red.setText("number of walls RED: "+player.getNumberOfWalls());
		}
		if(player == computer) {
			GameFrame.number_of_walls_blue.setText("number of walls BLUE: "+player.getNumberOfWalls());
		}
	}
	
	/**
     * This function place wall in the data structure
     * @param wfa - the data of the wall
     * @return place wall in the data structure
     */ 
	public void placeWallForHuman(WallForAi wfa) {
		if(wfa.getTypeOfWall() == 0) {
			graph.cells[sWall.getxInMath()][sWall.getyInMath()-1].placeWall(1);
			graph.cells[sWall.getxInMath()+1][sWall.getyInMath()-1].placeWall(1);
			if(sWall.getyInMath() != 0) {
				graph.cells[sWall.getxInMath()][sWall.getyInMath()].placeWall(3);
				graph.cells[sWall.getxInMath()+1][sWall.getyInMath()].placeWall(3);
			}
		}
		else {
			graph.cells[lWall.getxInMath()-1][lWall.getyInMath()].placeWall(2);
			graph.cells[lWall.getxInMath()-1][lWall.getyInMath()+1].placeWall(2);
			if(lWall.getxInMath() != 0) {
				graph.cells[lWall.getxInMath()][lWall.getyInMath()].placeWall(0);
				graph.cells[lWall.getxInMath()][lWall.getyInMath()+1].placeWall(0);
			}
			//graph.setWall(wfa);
			
		}
	}
	
	
	/**
     * This function check if there is a wall at the place that player want to place a wall
     * @param row - the row of the wall
     * @param col - the column of the wall
     * @param dir - the direction of the wall: 0 - up, 1 - right, 2 - down, 3 - left
     * @param typeOfWall - the type of the wall: 0 - straight wall, 1 - lay wall
     * @return boolean - true - if the place of the wall is legal and false if not
     */ 
	public boolean isIllegal(int x, int y, int dir, int typeOfWall) {
		WallForAi virtualWall = new WallForAi(x,y,dir,typeOfWall);
		Path computer_bfs, human_bfs;
		
		if((typeOfWall == 0 && x == 8 ) || (typeOfWall == 1 && y == 8))
			return true;
		
		if(!isExist(x, y, typeOfWall))
			return true;
		
		
		if(isCrossing(virtualWall))
			return true;
		
		graph.setWall(virtualWall);
		
		computer_bfs = computer_Ai.BFS(computer, null);
		human_bfs = computer_Ai.BFS(human, null);
		
		if(computer_bfs == null || human_bfs == null) {
			graph.rewmoveWall(virtualWall);
			return true;
		}
		
		
		graph.rewmoveWall(virtualWall);
		
		
		
		return false;
		
	}
	
	
	/**
     * This function check if there is a wall at the place that player want to place a wall
     * @param row - the row of the wall
     * @param col - the column of the wall
     * @param typeOfWall - the type of the wall: 0 - straight wall, 1 - lay wall
     * @return boolean - true - if there is a wall at the place that player want to place a wall and false if not
     */ 
	public boolean isExist(int row, int col, int typeOfWall) {
		boolean exist = false;
		if(typeOfWall == 0) {
			//return graph.cells[wfa.getRow()][wfa.getCol()].getNeighbor(wfa.getDir()) != null && graph.cells[wfa.getRow()+1][wfa.getCol()].getNeighbor(wfa.getDir()) != null;
			exist =  graph.cells[row][col-1].getNeighbor(1) != null && graph.cells[row][col].getNeighbor(3)  != null;
			return exist && graph.cells[row+1][col-1].getNeighbor(1) != null && graph.cells[row+1][col].getNeighbor(3)  != null;
		}
		
		//return graph.cells[wfa.getRow()][wfa.getCol()].getNeighbor(wfa.getDir()) != null && graph.cells[wfa.getRow()][wfa.getCol()+1].getNeighbor(wfa.getDir()) != null;

		exist =  graph.cells[row][col].getNeighbor(0) != null && graph.cells[row-1][col].getNeighbor(2)  != null;
		return exist &&  graph.cells[row][col+1].getNeighbor(0) != null && graph.cells[row-1][col+1].getNeighbor(2)  != null;

	}
	
	/**
	 * This function check if the player is blocking by a wall
	 * @param row - the row of the current cell
	 * @param col	the col of the current cell
	 * @param move - the direction of the move
	 * @param typeOfWall - the type of the wall - 0: stand wall, 1: lay wall
	 * @return true if blocking and false if not
	 */
	public boolean isBlocking(int row, int col, int move,int typeOfWall) {
		if(graph.cells[row][col].getNeighbor(move) != null)
			return false;
		
		return true;
	}
	
	public boolean isCrossing(WallForAi wfa) {
		int row = wfa.getRow(), col = wfa.getCol();
		if(wfa.getTypeOfWall() == 0) 
			return graph.cells[row][col].getNeighbor(2) == null && graph.cells[row][col-1].getNeighbor(2) == null;
		
		return graph.cells[row-1][col].getNeighbor(1) == null && graph.cells[row][col].getNeighbor(1) == null;
	}
	
	
	public void setEnd(boolean end) {
		this.end = end;
	}
	
	/**
	 * This function ends the game
	 * @param player - the player who won
	 */
	public void endGame(Player player) {
		this.end = true;
		System.out.println(player.getName()+" has won!!!");
		
		boardPanel.endGame(player.getName());
	}

}
