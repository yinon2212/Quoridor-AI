package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class InfoPanel extends JPanel implements ActionListener, ItemListener{
	private Icon horizontal;
	private Icon linear; 
	private JButton straightWall;
	private JButton standWall;
	private BoardLayout boardPanel;
	JPanel end_game_panel;
	JCheckBox quit, restart;
	
	public InfoPanel(BoardLayout boardPanel) {
		setSize(10, 10);
		this.boardPanel = boardPanel;
		horizontal = new ImageIcon("C:\\pics\\meuzan.png");
		linear = new ImageIcon("C:\\pics\\meunach.png"); 
		straightWall = new JButton(linear);
		standWall = new JButton(horizontal);
		
		straightWall.setVisible(true);
		straightWall.setText("s");
		straightWall.setFocusable(false);
		straightWall.addActionListener(this);
		
		
		standWall.setVisible(true);
		standWall.setText("l");
		standWall.setFocusable(false);
		standWall.addActionListener(this);
		
		
		
		add(standWall);
		add(straightWall);
		
		setBackground(new Color(155,97,66));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(straightWall.getText())) {
			boardPanel.board.sWallClikced();
		}
		else{
			if(e.getActionCommand().equals(standWall.getText())) {
				boardPanel.board.lWallClikced();
			}		
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
}
