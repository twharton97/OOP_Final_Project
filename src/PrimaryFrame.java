
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.swing.*;

public class PrimaryFrame extends JFrame {
	
	private static final long serialVersionUID = 8822067539558631513L;
	private BoardPanel board;
	
	public PrimaryFrame() {

		//Initialize the window (JFrame)
		//tells the application to close when you close this window
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1920, 1080);// width, height
		//see https://docs.oracle.com/javase/7/docs/api/java/awt/BorderLayout.html
		this.setLayout(new BorderLayout()); 
		this.setTitle("BattleGround");
		
		//we'll place two panels in our window
		//one at the top to hold the button (topPanel)
		//the other to hold all the painted shapes (body)
		
		/*
		 * This is one example of organizing code to build a panel: using 
		 * a separate method.
		 *  
		 * I could have put all buildTopPanel's code into this constructor
		 * but it would have made for a long method and made less clear 
		 * what the constructor is actually doing (setting its own layout 
		 * and adding two panels)
		 * 
		 * This way if I want to mess with that panel I know to go to the
		 * buildTopPanel method
		 */
		JPanel topPanel = buildTopPanel();
		this.add(topPanel, BorderLayout.NORTH);
		
	
		/*
		 * BoardPanel is responsible for painting all the shapes
		 * 
		 * Here's another way to organize building a panel: using 
		 * a separate class that inherits from JPanel.
		 * 
		 * This option protects that panel's data and code a little
		 * more than buildTopPanel, but it also takes more code
		 * for that panel to communicate with other panels.  In this
		 * case, BoardPanel only needs to receive instructions, not 
		 * send them, so we don't need to worry about two-way communication
		 * between separate classes.
		 * 
		 * Because topPanel and its action listener has visibility 
		 * to the BoardPanel, the button's listener can call board.moveShapes 
		 * (Yes, you can separate concerns more than this, but let's leave
		 * that for another assignment.)
		 */
		
		ShapeController controller1 = new ShapeController();
		Shape[] s;
		controller1.createObjects();
		s = controller1.getShape();
		
		board = new BoardPanel(s);
		this.add(board, BorderLayout.CENTER);
		
		this.setVisible(true);// making the frame visible
	
	}

	private JPanel buildTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.decode("#96BACF"));
		
		JButton tickButton = new JButton("Increment Time...");
		tickButton.setActionCommand("Move"); //to differentiate buttons in case we add more of them later
		tickButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("actionPerformed: " + e.getActionCommand());
				System.out.println("Button clicked, handled in anonymous inner class: " + LocalDateTime.now());
				board.moveShapes();
				
			}
		});
		panel.add(tickButton);
		return panel;
	}
	
	
}
