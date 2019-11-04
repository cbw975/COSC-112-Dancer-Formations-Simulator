// =============================================================================
/**
 * The Main class runs the main method. This creates a JFrame and sets the content
 * to a Welcome screen. The button on the Welcome screen will create an Input,
 * beginning the rest of the simulation.
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
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
// =============================================================================
public class Main{
	// ===========================================================================
	//MAIN METHOD
	//main() creates a JFrame and an instance of Welcome
	public static void main(String[] args){
		JFrame frame = new JFrame("Dancing Simulation!");
		frame.setSize(750, 1000);
        frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Welcome(frame), BorderLayout.CENTER); 
		frame.setVisible(true); 
    }
// =============================================================================
} // class Main
// =============================================================================