package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameFrame extends JFrame implements ItemListener{
	public BoardLayout boardPanel;
	public InfoPanel infoPanel;
	JPanel main_panel = new JPanel();
	JPanel popUpMenu = new JPanel();
	private Image backG;
	private final String backGURL = "C:\\pics\\frame_backg.jpg";
	public static JLabel number_of_walls_blue, number_of_walls_red, turn; 
	JCheckBox playerVsPlayer,playerVsComp, quit, restart;
	JPanel end_game_panel;
	
	public GameFrame() {
		setLayout(new GridLayout());
		setSize(1000, 1000);
		setTitle("Quoridor game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		playerVsPlayer = new JCheckBox("Player vs Player");
		playerVsPlayer.setMnemonic(KeyEvent.VK_C); 
		playerVsPlayer.setSelected(false);
		playerVsPlayer.addItemListener(this);
		
		playerVsComp = new JCheckBox("Player vs Computer");
		playerVsComp.setMnemonic(KeyEvent.VK_T); 
		playerVsComp.setSelected(false);
		playerVsComp.addItemListener(this);
		
		
		popUpMenu.add(playerVsPlayer);
		popUpMenu.add(playerVsComp);
		
		JOptionPane.showMessageDialog(null,popUpMenu,"CHOOSE GAME MODE",JOptionPane.INFORMATION_MESSAGE);
		//add(popUpMenu);
		
		//initComponents();
		
		
		
		
	}
	
	public void initComponents(boolean computer_game) {
		boardPanel = new GUI.BoardLayout(computer_game);
		infoPanel = new GUI.InfoPanel(boardPanel);
		main_panel.setPreferredSize(new Dimension(700,700));
		//main_panel.add(boardPanel);
		main_panel.add(boardPanel);
		main_panel.add(infoPanel);
		System.out.println(infoPanel.getWidth()+" "+infoPanel.getHeight());
		number_of_walls_blue=new JLabel ("number of walls blue: 10");  
		number_of_walls_blue.setBounds(100, 10, 10, 5);
		number_of_walls_blue.setForeground(Color.blue);
		
		number_of_walls_red=new JLabel ("number of walls red: 10");  
		number_of_walls_red.setBounds(930, 10, 10, 5);
		number_of_walls_red.setForeground(Color.red);
		
		turn = new JLabel("                      RED TURN");
		main_panel.add(number_of_walls_blue);
		main_panel.add(number_of_walls_red);
		main_panel.add(turn);
		main_panel.setBackground(new Color(155,97,66));
		
		add(main_panel);
		setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		GameFrame g = new GameFrame();

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		 Object source = e.getItemSelectable();
		 
		 if(source == playerVsPlayer)
			 initComponents(false);
		 else {
			 if(source == playerVsComp)
				 initComponents(true);
			
		 }
	}
	

}
