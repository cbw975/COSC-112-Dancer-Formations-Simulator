// =============================================================================
/**
 * The Display class extends JPanel, and is an abstract class of what will be displayed.
 * There are three subclasses, Welcome, Input, and Animation. This class holds constants, 
 * scale factors, and a field for the top panel, which is used in both Animation and Input. 
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
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.Graphics;
// =============================================================================
public abstract class Display extends JPanel {
    // =============================================================================
    //FIELDS
    public static final int WIDTH = 1000;
    public static final int LENGTH = 1000;
    public static double scaleX;
    public static double scaleY;
    public JPanel topPanel;
    // =============================================================================
    //CONSTRUCTOR 
    public Display(){
        this.setPreferredSize(new Dimension(1000, 1000));
    }
    // =============================================================================
    //METHODS
    //mutator methods to set scale
    public static void setScaleX(double x){ 
        scaleX=x; 
    }
    public static void setScaleY(double y){
        scaleY=y; 
    }

} 