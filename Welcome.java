// =============================================================================
/**
 * The Welcome class provides the display for the Welcome screen. It has a button
 * to show instructions and also a button to begin the entire simulation.
 * 
 *
 * @author Sophie Koh and Chloe Wohlgemuth
 **/
// =============================================================================
 
 
 
// =============================================================================
// IMPORTS 
// =============================================================================
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

// =============================================================================
public class Welcome extends Display{
	// ==========================================================================
	//CONSTRUCTOR
	public Welcome(JFrame frame){
		super();
		frame.setLayout(new BorderLayout());
		//instructionsPanel holds the instructions button
		JPanel instructionsPanel = setInstructionsPanel();
		frame.add(instructionsPanel);
		//beginPanel holds the begin button to start the program
		JPanel beginPanel = setBeginPanel(frame);
		instructionsPanel.add(beginPanel);
		frame.setVisible(true); 
	}
	// =============================================================================
	//METHODS
	//setInstructionsPanel() sets the labels, panels, and buttons in the instructionsPanel
	public JPanel setInstructionsPanel(){
		//The title/top of Welcome screen
		JLabel title = new JLabel("Dance Simulator!", SwingConstants.CENTER); 
		 //The panel to hold title/top and the instructions button
		JPanel instructionsPanel = new JPanel(); 
		JButton bInstructions = new JButton("INSTRUCTIONS");
		//Format stuff for the instructions panel
		title.setFont(new Font("Lucida Handwriting", Font.PLAIN, 60));
		title.setForeground(Color.MAGENTA);
		instructionsPanel.setBackground(Color.BLACK);
		instructionsPanel.setOpaque(true);
		instructionsPanel.setLayout(new GridLayout(3,1));
		bInstructions.setBackground(Color.BLACK);
		bInstructions.setFont(new Font("Lucida Handwriting", Font.PLAIN, 30));
		bInstructions.setForeground(Color.WHITE);
		bInstructions.setOpaque(true);
		//Adding to screen
		instructionsPanel.add(title);
		instructionsPanel.add(bInstructions);
		//Actions that occur when button is clicked:		
		bInstructions.addActionListener(new ActionListener(){  
    		public void actionPerformed(ActionEvent e){
				String instructions; //the instructions to be displayed when instruction button is clicked
				instructions = ("<html>INSTRUCTIONS:<br><br>Input the stage dimensions and number of dancers when "+
					"prompted. <br><br>Then, click on the spaces (representing positions on your stage) where you want your dancers "+
					"to initially be.<br><br>When you do this, you need to establish their name and color. **IF YOU REPEAT NAMES and/or "+
					"COLORS, this will be confusing for you (DUH!). You CANNOT have multiple dancers in the same location!** <br><br>Once you input all of the dancers, you will be notified "+
					"and moved to input the next formation.<br> Here, you will click the places where you want the dancers to be next."+
					"<br><br>Continue this process for as many formations as you would like. <br><br>When you are finished (with at least 2 "+
					"inputted formations), click NO when prompted if you are done. **PLEASE be patient while the regression algorithm runs, "+
					"especially if you have a lot of dancers :) <br><br>The animation window will open automatically and you can see your formations in action!");
					JOptionPane.showMessageDialog(null, instructions);} });
		return instructionsPanel; 
	}//setInstructionsPanel()
	
	//setBeginPanel sets the panel and button that you click to begin the simulation
	public JPanel setBeginPanel(JFrame frame){
		JPanel beginPanel = new JPanel();
		beginPanel.setBackground(Color.BLACK);
		JButton beginButton = new JButton("Click to Begin!");
		beginButton.setBackground(new Color(73,210,200));
		beginButton.setPreferredSize(new Dimension(300,100));
		beginButton.setFont(new Font("Lucida Handwriting", Font.BOLD, 25));
		beginButton.addActionListener(new ActionListener(){  
    		public void actionPerformed(ActionEvent e){
				frame.dispose();
				JFrame screen = new JFrame();
				screen.setSize(1000, 1000);
				screen.setLocation(0, 0);
				screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				screen.add(Input.topPanel(),BorderLayout.BEFORE_FIRST_LINE);
				screen.add(new Input(screen), BorderLayout.CENTER); 
				screen.setVisible(true); } }); 
		beginPanel.add(beginButton,BorderLayout.CENTER);
		return beginPanel; 
	}//setBeginPanel()
// =============================================================================
} // class Welcome
// =============================================================================