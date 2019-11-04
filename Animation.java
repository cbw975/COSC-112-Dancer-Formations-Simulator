// =============================================================================
/**
 * The Animation class provides the code for reading the txt file from the Input class
 * as well as the code for the panel that displays the formations discretely
 * and animations.
 *
 * @author Sophie Koh and Chloe Wohlgemuth
 **/
// =============================================================================
 
 
 
// =============================================================================
// IMPORTS
 
// =============================================================================
 
 
// =============================================================================
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
 
 
 
// =============================================================================
public class Animation extends Display{
// =============================================================================
    //FIELDS
    public static int numButtonPresses;
    public static ArrayList<Stage> stages,initStages;
    public int num, numberOfFormations;
    public Dancer[] d, animatedDancers; 
    public JPanel bottomPanel;
    public JLabel formationNumber;
    // ==========================================================================
    //CONSTRUCTOR
    public Animation(int fNum){
        super();
        this.setLayout(new BorderLayout());

        //topPanel from Display
        topPanel = new JPanel();
        
        numberOfFormations = fNum; //fNum given by Input

        formationNumber=new JLabel("");  //formationNumber tells user which formation is currently displayed
        formationNumber.setText("Formation: "+1);

        //BUTTONS
        //next button changes the positions of the animated dancers to the next formation
        JButton next = new JButton("Next Formation");
        next.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                    if(numButtonPresses<numberOfFormations-1){ //if the number of button presses is greater than the number of formations, it doesn't update
                        repaint();
                        for(int i=0;i<stages.get(0).dancers.length;i++){
                            animatedDancers[i]=stages.get(numButtonPresses+1).dancers[i];
                            //System.out.println(animatedDancers[i]);
                            }
                        
                    }
                    numButtonPresses++;
                    if(numButtonPresses<numberOfFormations){ 
                        formationNumber.setText("Formation: "+(numButtonPresses+1));
                    }
                    //System.out.println("Next button pressed"); 
                }  
            }
            ); 
        //fullAnimation button shows the dancers travelling through all of the stages
        JButton fullAnimation = new JButton("Full Animation"); //full animation 
        fullAnimation.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                    formationNumber.setText("Running...");
                    animate();
                    //System.out.println("Full animation button pressed");
                    formationNumber.setText("Done");
                }  
            }
            ); 

        //reset button resets the dancers to the first formation
        JButton reset=new JButton("Reset");
        //reset.setFont(new Font("Arial", Font.BOLD, 25));
        reset.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                    reset();
                    //System.out.println("Reset button pressed");

                }  
            }
            ); 
        
        //bottomPanel will hold several JLabels with a key
        bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Key: "));

        topPanel.add(formationNumber);
        topPanel.add(next);
        topPanel.add(fullAnimation);
        topPanel.add(reset);

        this.add(topPanel, BorderLayout.PAGE_START);
        this.add(bottomPanel, BorderLayout.PAGE_END);
        // =============================================================================
        //* Sets the format of the panel. topPanel displays the buttons next, reset, and 
        //* full animationas well as the label with the formation number.
        //* The bottom panel stores the Key with Dancers names color-coded, which will
        //* be added to the JLabel later
        // =============================================================================

    } //Animation()

    // =============================================================================
    // METHODS
    // =============================================================================

    //readStates will read all of the information from the dancer files and instantiate dancers in the correct stages, and runs the regression
    public void readStates (String coordinates, String names, String colors) {
        // Creates the readers for these files.
        Scanner reader = null; 
        Scanner nameReader = null;
        Scanner colorReader = null;

        //holds the initial stages
        initStages = new ArrayList();

        //loads in the three files: coordinates, names, and colors
        try {
            reader = new Scanner(new File(coordinates));
        } 
        catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found: " + coordinates);
        }
        try {
            nameReader = new Scanner(new File(names));
        } 
        catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found: " + names);
        }
        try {
            colorReader = new Scanner(new File(colors));
        } 
        catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found: " + colors);
        }

        // Read the first line, which contains the number of dancers
        try {
            num = reader.nextInt();
            d = new Dancer[num];
        }
        catch(InputMismatchException e) {
            System.out.println("ERROR: Invalid number of dancers"); 
        }

        //instantiates all of the dancers in an array d
        for(int i=0; i<num; i++){   
                int BEFORE_X = reader.nextInt();
                int BEFORE_Y = reader.nextInt();
                String name = nameReader.nextLine();
                int r =colorReader.nextInt();
                int g =colorReader.nextInt();
                int b =colorReader.nextInt();
                Color c = new Color(r,g,b);
                d[i] = new Dancer(BEFORE_X,BEFORE_Y,name,c);
            }
        //adds a new stage that holds the d array
        initStages.add(0,new Stage(WIDTH,LENGTH,d)); 

        //adds JLabel of the color and name to the bottomPanel key for all of the dancers
        for(int i=0; i<num; i++){
                JLabel nameColors = new JLabel(initStages.get(0).dancers[i].getName());
                nameColors.setForeground(initStages.get(0).dancers[i].getColor());
                bottomPanel.add(nameColors);
            }

        //adds other values as dancers to the stage for the rest of the formations
        for(int j=1;j<numberOfFormations;j++) 
        {
            Stage s = new Stage(WIDTH,LENGTH);
            s.dancers=new Dancer[num];
            for(int i=0; i<num; i++){ 
                int AFTER_X = reader.nextInt();
                int AFTER_Y = reader.nextInt();
                String n =initStages.get(0).dancers[i].getName();
                Color c =initStages.get(0).dancers[i].getColor();
                s.dancers[i]=new Dancer(AFTER_X,AFTER_Y,n,c);
            }
             initStages.add(j,s); //adds next formations to initStages
        }

        //putting the dancers in the right spot
        stages = new ArrayList();
        stages.add(0,initStages.get(0));

        //for all of the formations, uses initStages to find paths of which dancer should go where
        for(int k=1;k<numberOfFormations;k++)
        {
            Stage sInitial=stages.get(k-1); 
            Dancer[] nextDancers =initStages.get(k).dancers;
            Dancer[] dArray = sInitial.nextFormation(nextDancers); //nextFormation contains the algorithm for determining who goes where
            Stage s = new Stage(WIDTH,LENGTH,dArray);
            stages.add(k,s);
        }
        //prints out information
        for(int k=0;k<numberOfFormations;k++){
            for(int i=0;i<stages.get(0).dancers.length;i++){
                //System.out.println("Stage "+k+", Dancer: "+i+" "+stages.get(k).dancers[i]);

            }
        }
        //sets nextPositions of dancers for animation
        for(int k=0;k<numberOfFormations-1;k++){
            for(int i=0;i<stages.get(0).dancers.length;i++){
                stages.get(k).dancers[i].setNextPositionX(stages.get(k+1).dancers[i].getPositionX());
                stages.get(k).dancers[i].setNextPositionY(stages.get(k+1).dancers[i].getPositionY());
            }
        }
        
        //since all of the information is stored in the ArrayList stages, the dancers that show up on screen are in the animatedDancers array
        animatedDancers=new Dancer[stages.get(0).dancers.length];
        for(int i=0;i<stages.get(0).dancers.length;i++){
            animatedDancers[i]=stages.get(0).dancers[i];
        }

        repaint(); // once animated dancers are set, they are displayed on the screen

    }// readStates()

    // =============================================================================

    //paintComponent will set the background to white and draw the animatedDancers as their colors
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, LENGTH);
        for (int i=0; i<stages.get(0).dancers.length; i++)
            {
                g.setColor(animatedDancers[i].getColor());
                animatedDancers[i].draw(g);
                //System.out.println(animatedDancers[i]);
            }
    
    } //paintComponent()

    // =============================================================================

    //animate will display all of the transitions through all of the stages
    public void animate(){
        for(int i=0;i<stages.get(0).dancers.length;i++){ //create new dancers to display so original positions do not change
            animatedDancers[i]=new Dancer(stages.get(0).dancers[i].getPositionX(),stages.get(0).dancers[i].getPositionY(),stages.get(0).dancers[i].getName(),stages.get(0).dancers[i].getColor());
        }
        //set next positions and updates the velocity
        for(int i=0;i<stages.get(0).dancers.length;i++){ 
            animatedDancers[i].setNextPositionX(stages.get(1).dancers[i].getPositionX());
            animatedDancers[i].setNextPositionY(stages.get(1).dancers[i].getPositionY());
            animatedDancers[i].updateVelocity();
            //System.out.println("Next x: "+animatedDancers[i].getNextPositionX()+" Next y:"+animatedDancers[i].getNextPositionY());
        }
        //for all of the formations, it will animate the transition
        for(int k=1;k<numberOfFormations;k++){
            animateTransition(k);
            }   
    }//animate()

    // =============================================================================

    //animateTransition will animate transition k and call nextAnimation
    public void animateTransition(int k){
        for(int j=0;j<60;j++){
            for(int i=0;i<stages.get(0).dancers.length;i++){
                animatedDancers[i].update();
            }
            paintImmediately(this.getBounds()); //paintImmediately because called in a looping method
            try{Thread.sleep(10);}
            catch(InterruptedException e){}
        }
        nextAnimation(k);

    }//animateTransition()
    // =============================================================================

    // nextAnimation sets the positions and next positions for the next animation
    public void nextAnimation(int k){
        for(int i=0;i<stages.get(0).dancers.length;i++){ 
            animatedDancers[i].setPositionX(stages.get(k).dancers[i].getPositionX());
            animatedDancers[i].setPositionX(stages.get(k).dancers[i].getPositionX());
            if(k<numberOfFormations-1){
                animatedDancers[i].setNextPositionX(stages.get(k).dancers[i].getNextPositionX());
                animatedDancers[i].setNextPositionY(stages.get(k).dancers[i].getNextPositionY());
                //System.out.println("Stage: "+k+" Dancer "+i+" Next x: "+animatedDancers[i].getNextPositionX()+" Next y:"+animatedDancers[i].getNextPositionY());
                }
            animatedDancers[i].updateVelocity();
            }
    }//nextAnimation()

    // =============================================================================

    //reset method resets the screen to the first formation
    public void reset(){
        for(int i=0;i<stages.get(0).dancers.length;i++){
            animatedDancers[i]=stages.get(0).dancers[i];
        }
        formationNumber.setText("Formation: "+1);
        numButtonPresses=0;
        repaint();
    }//reset()
// =============================================================================
} // class Animation
// =============================================================================

