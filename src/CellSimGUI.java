

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * Knowledge of the code in here is not required for the class or tests.
 */
public class CellSimGUI extends JFrame {
	/**
	 * Version 2 of the GUI.
	 */
	private static final long serialVersionUID = 2L;
	int delay;
	
	/**
	 * The size of the buttons displayed. You can change this constant to make
	 * the buttons larger or smaller.
	 */
	private static final int BUTTON_SIZE = 80;
	
	private JButton[][] buttons;
	private JPanel gamePanel;
	private boolean isEnabled; 
	public CellSimGUI(int size, int delay) {
		super("CellSimGUI");
		this.isEnabled = true;
		this.delay = delay;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(size * BUTTON_SIZE, size * BUTTON_SIZE);
		
		gamePanel = new JPanel(new GridLayout(size, size));
		buttons = new JButton[size][size];

		for (int row = 0; row < size; row++){
			 for (int col = 0; col < size; col++) {
				JButton b = new JButton(" ");
				b.setBackground(Color.WHITE);
				buttons[row][col] = b;
				gamePanel.add(b);
			}
		}

		this.add(gamePanel);
		this.setVisible(true);
	}

	/**
	* Sets the color of the cell located at a particular position
	* 
	* @param row the row position of target cell
	* @param col the col position of target cell
	* @param c the color you wish to change the cell to
	*
	**/
	public void setCell(int row, int col, Color c){
		try{
		Thread.sleep(delay);}catch(Exception e){}
		buttons[row][col].setBackground(c);
		buttons[row][col].setOpaque(true);
		buttons[row][col].setBorderPainted(true);
		buttons[row][col].repaint();
	
	}
	
	public static void setColor(char[][] tissue, CellSimGUI grid){
		for(int l =0;l<tissue.length;l++){
			for (int k = 0;k<tissue[0].length;k++){
				if (tissue[l][k]== 'X'){
					
				 grid.setCell(l,k,Color.red);
				}
				else if (tissue[l][k]=='O'){
					grid.setCell(l, k, Color.blue);
					
				}
				else if (tissue[l][k]=='Y'){
					grid.setCell(l,k,Color.yellow);
				}
				else {
					grid.setCell(l, k, Color.white);
				}
			}
		}
		
	}

}