package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import Entities.Player;
import Entities.Wall;
import Logic.Board;

public class BoardLayout extends JPanel implements KeyListener, ActionListener, ItemListener{

	public Board board;
	private Image backG,red,blue;
	private final String backGURL = "C:\\pics\\board2.png", red_playerURL="C:\\pics\\red.png",blue_playerURL = "C:\\pics\\blue.png";
	private Timer time; 
	private int delay = 8;
	public static JOptionPane endGameMessage; 
	static JPanel panel = new JPanel();
	JPanel end_game_panel;
	JCheckBox quit,restart;
	

	public BoardLayout(boolean computer_game) {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setPreferredSize(new Dimension(700,700));
		
		
		
		board = new Board(computer_game,this);
		
		panel.add(new JLabel("Do you like to restart?"));
		backG = new ImageIcon(backGURL).getImage();
		red = new ImageIcon(red_playerURL).getImage();
		blue = new ImageIcon(blue_playerURL).getImage();
		
		
		//---------------Time---------------
		time = new Timer(delay, this);
		time.start();
	}
	
	public void paint(Graphics graphics) {
		graphics.drawImage(backG, 0, 0, 700, 700,null);
			
		graphics.drawImage(red, board.human.getX(), board.human.getY(), board.PIECE_SIZE, board.PIECE_SIZE,null);
		graphics.drawImage(blue, board.computer.getX(), board.computer.getY(),  board.PIECE_SIZE,  board.PIECE_SIZE,null);

			//player 1
		//graphics.setColor(Color.blue);
		//graphics.fillRect(board.computer.getX(), board.computer.getY(),  board.PIECE_SIZE,  board.PIECE_SIZE);
			
			
			//player 2
		//graphics.setColor(Color.red);
		//graphics.fillRect(board.human.getX(), board.human.getY(), board.PIECE_SIZE, board.PIECE_SIZE);
			
		if(board.sWallNeedToPlace || board.lWallNeedToPlace)
		{
			graphics.setColor(Color.green);
			if(board.sWall != null)
				graphics.fillRect(board.sWall.getX(), board.sWall.getY(), 10, board.CELL_SIZE+board.CELL_SIZE);
			else
				graphics.fillRect(board.lWall.getX(), board.lWall.getY(), board.CELL_SIZE+board.CELL_SIZE, 10);

		}
			
		//ALL WALLS
		graphics.setColor(Color.yellow);
		if(!(board.standWalls.isEmpty())) {
			for(Wall wall : board.standWalls) {
				graphics.fillRect(wall.getX(), wall.getY(), 10, board.CELL_SIZE+board.CELL_SIZE);
				//g.drawImage(vertical, wall.getX()-5,wall.getY(),10,100,null);
			}
		}
		if(!(board.layWalls.isEmpty())) {
			for(Wall wall : board.layWalls) {
				graphics.fillRect(wall.getX(), wall.getY(), board.CELL_SIZE+board.CELL_SIZE, 10);
				//g.drawImage(horizontal, wall.getX()-5,wall.getY(),10,100,null);
			}
		}
		
		setTurnText();
	}
	
	
	public void setTurnText() {
		if(board.computerTurn) {
			GameFrame.turn.setText("                      BLUE TURN");
			GameFrame.turn.setForeground(Color.blue);
		}
		else {
			GameFrame.turn.setText("                      RED TURN");
			GameFrame.turn.setForeground(Color.red);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Player player = computer;
		time.start();
		repaint();
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int operation = e.getKeyCode() == KeyEvent.VK_RIGHT ? 1 : e.getKeyCode() == KeyEvent.VK_LEFT ? 2 : e.getKeyCode() == KeyEvent.VK_UP ? 3 : e.getKeyCode() == KeyEvent.VK_DOWN ? 4: e.getKeyCode() == KeyEvent.VK_ENTER ? 5:0;
	
		board.movePlayer(operation);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(board.computer_game) {
			if(board.computerTurn) {
				board.computer_Ai.moveComputer();
				board.switchTurns();
			}
		}	
	}
	
	public void endGame(String won) {
		end_game_panel = new JPanel();	
		quit = new JCheckBox("quit");
		quit.setMnemonic(KeyEvent.VK_C); 
		quit.setSelected(false);
		quit.addItemListener(this);
		
		restart = new JCheckBox("retart");
		restart.setMnemonic(KeyEvent.VK_T); 
		restart.setSelected(false);
		restart.addItemListener(this);
		
		end_game_panel.add(quit);
		end_game_panel.add(restart);
		//add(end_game_panel);
		JOptionPane.showMessageDialog(null,end_game_panel,won+" HAS WON!!!",JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();

		 
		if(source == quit) 
			System.exit(0);
		
		
		if(source == restart)
			 System.out.println("bye");
			
	}
}
