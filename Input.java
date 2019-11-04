// =============================================================================
/**
 * The Input class provides the code for inputting the user-defined variables 
 * (stage width and length (in # of locations) and number of dancers), and the
 * dancer placements for each formation. In the initial formation, they input 
 * dancer information, which is only used when sent to animation. It exports
 * the dancer and coordinate information to txt files that are then used in
 * the Animation class.
 * 
 * @author Sophie Koh and Chloe Wohlgemuth
 **/
// =============================================================================
 
 
 
// =============================================================================
// IMPORTS
 
// =============================================================================
 
 
// =============================================================================
import java.util.*;
//GUI related imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
//FILE related imports
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
// =============================================================================
 
 
 
// =============================================================================
public class Input extends Display implements ActionListener{
// =============================================================================
    //FIELDS
    //JFrames
    public JFrame inFrame;
    public JFrame anFrame;
    //The board of buttons
    private static Button<Dancer>[][] positions;  //The positions on stage
    //Defines the Dimensions of the Buttons on the stage/Board
    private static int B_WIDTH;
    private static int B_LENGTH;
    //number of positions horizontally and vertically
    private static int horzPos;
    private static int vertPos;
    //For Color stuff
    private final static Color BACKGROUND = new Color(254,254,254);
    private final static Color PRESSED = new Color(95,95,95);
    private static Color lineColor; // The color of the lines between buttons
    //Buttons and sliders/changers
    public static int lineThick = 2; // How thick the lines between buttons are.
    public static JButton bScatter;
    //COUNTERS
    public static int fNum=0; //number of formations that have been set
    public static int dMax; // Number of dancers --> will be constant in each formation
    public static int dNum; // number of dancers that have been placed so far
    //FIELDS relevant to the TXT file writing stuff:
    public static File colors;
    public static File formations; //create the file to write in information in
    public static File dancers; //create the file to write in information in
    public static BufferedWriter db; //writer for the dancers txt file
    public static BufferedWriter fb; //writer for the formations txt file
    public static BufferedWriter cb; //writer for the colors txt file
    //Needed/Used in ActionPerforms stuff:
    public static Random rand = new Random();
    private static boolean firstPick = true;  // if true, we are selecting a dancer
    private int tmpV=-1; private int tmpH=-1;//temporary variables to store the 
            //vertical-coord and horizontal-coord for button PRESSED in actionPerformed
    // ==========================================================================
    //CONSTRUCTOR
    // =============================================================================
    public Input(JFrame frame){
        this.inFrame = frame;
        setFocusable(true);
        setChoices();
        try{setFileStuff(); }catch (IOException e){ 
            System.out.println("\n\n\n UH OH \n\n\n\n"); }
        initBoard(); 
        // =============================================================================
        //* Sets up the Input panel; see called methods' descriptions
        // =============================================================================
        } //Input()
        
    // =============================================================================
    // METHODS
    // =============================================================================
        
    /** setChoices() deals with the code (functional and visual) for setting all the
    user specified values: stage dimensions and number of dancers */
    public void setChoices(){
        Integer[] choices1 = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
            26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,
            47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,
            68,69,70,71,72,73,74,75};
        Integer[] choices2 = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
            26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,
            47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,
            68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,
            89,90,91,92,93,94,95,96,97,98,99,100};
        Integer[] choices3 = {1,2,3,4,5,6,7,8,9,10,11,12};
        //temporary variables
        Integer in1=0; 
        Integer in2=0; 
        Integer in3=0;
    
        //DIMENSIONS
            boolean isValid = false;
        while(!isValid){//Getting the vertPos or HEIGHT
            in1 = (Integer) JOptionPane.showInputDialog(null, "Stage (vertical) Width: ",
                "Stage Dimensions", JOptionPane.QUESTION_MESSAGE, null, choices1,choices1[0]); 
            if (in1 != 0) 
                vertPos = in1; 
            else 
                System.exit(0); //If they type cancel or type in nothing
            isValid=true; 
            in1 = 0; 
        }
        isValid = false;
        while(!isValid){//Getting the horzPos or LENGTH
            in2 = (Integer)JOptionPane.showInputDialog(null, "Stage (horizontal) Length: ",
                "Stage Dimensions", JOptionPane.QUESTION_MESSAGE, null, choices2,choices2[0]); 
            if (in2 != 0) 
                horzPos = in2; 
            else 
                System.exit(0); //If they type cancel or type in nothing
            isValid=true; 
            in2 = 0; 
        }
        isValid = false;
        while(!isValid){ //Getting the MAX DANCERS
            in3 = (Integer) JOptionPane.showInputDialog(null, "Dancer Count: ",
                "# of Dancers", JOptionPane.QUESTION_MESSAGE, null, choices3,choices3[0]); 
            if (in3 != 0) 
                dMax = in3; 
            else 
                System.exit(0); //If they type cancel or type in nothing
            isValid=true; 
            in3 = 0; }//end While()
        isValid = false;
        }//setChoices()

    /**setFileStuff() resets/clears and initializes the variables necessary for producing the 
    //txt files to hold the dancer, color and coordinate information (sent to Animation class)*/
    public void setFileStuff() throws IOException{
        //instantiating files
        formations = new File("coordinates.txt");
        dancers = new File("names.txt");
        colors = new File("colors.txt");
        //if these files exist with contents already, remove them
        if(formations.exists()) 
            formations.delete();
        if(dancers.exists()) 
            dancers.delete();
        if(colors.exists()) 
            colors.delete();
        //Make the writers -->  //1st par: file to be writing to. True specifies that we are 
        //appending each time, not replacing all content
        db = new BufferedWriter(new FileWriter(dancers, true));             
        fb = new BufferedWriter(new FileWriter(formations, true));
        cb = new BufferedWriter(new FileWriter(colors, true));
        //Write the max number of dancers ar the top of the formations file:
        fb.write(dMax+"\n"); fb.flush(); } //setFileStuff()

    /**initBoard() sets up the board of buttons that the user interacts with to specify dancer 
    //locations for each formation.*/
    public void initBoard() {   
        //Animation pixel and positon conversion factor:
        Display.setScaleX((double)(Display.LENGTH/horzPos));
        Display.setScaleY((double)(Display.WIDTH/vertPos));
        //Button Dimensions:
        B_WIDTH=WIDTH/vertPos;
        B_LENGTH=LENGTH/vertPos;
        //Initializing the button array
        positions = new Button[vertPos][horzPos]; 
        //create the positions
        this.setLayout(new GridLayout(vertPos, horzPos));
        //System.out.println(vertPos+" and horz: "+horzPos);
        //Initialize buttons:
        initButton(positions, true); 
        //Setup the bScatter button function
        bScatter.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e){
                int temp=-1; int hInd=0; int vInd=-1;
                while(dNum<dMax){
                        if(dNum != 0) 
                            bScatter.setVisible(false);
                        hInd=(int)(rand.nextInt(horzPos));
                        vInd = (int)(rand.nextInt(vertPos));
                        while(hasDancer(vInd,hInd)){
                            vInd = (int)(rand.nextInt(vertPos)); 
                        } 
                        if(dNum<dMax) addDancer(vInd,hInd);
                    }
            checkdMaxReset(); 
        } });//ActionListener()
    }//initBoard()
    
    /**initButton() initializes the @param Button<Dancer>[][] board/array of buttons that the user interacts 
    and inputs information with */
    public void initButton(Button<Dancer>[][] a, boolean addVisible){
        for (int w = 0; w < vertPos; w++) {
            for (int l = 0; l < horzPos; l++) { //in EACH button of the array:
                //To display the coordinates as default format/display
                a[w][l] = new Button<Dancer>(w+","+l);
                //Creating an "empty"/default dancer
                a[w][l].setObj((Dancer) new Dancer(w,l,BACKGROUND));
                a[w][l].setPreferredSize(new Dimension(B_WIDTH,B_WIDTH)); //doesn't do anything
                //set the background to the background color
                a[w][l].setBackground(BACKGROUND);
                //Give each button a border, so user can differentiate between positions
                a[w][l].setBorder(BorderFactory.createLineBorder(lineColor, lineThick));
                //give each actioHandler so can make it do something when clicked
                a[w][l].addActionListener(this); 
                if(addVisible) this.add(a[w][l]); 
            }
        } 
    }//initButton()

    /**actionPerformed() is invoked when a button @param ActionEvent action occurs; when button containing a dancer
    //is clicked, this determines what occurs*/
    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof Button) {
            for(int v=0; v<vertPos;v++) for(int h=0;h<horzPos;h++)
                if(positions[v][h]==e.getSource()){ tmpV=v; tmpH=h; } } //buttpn coordinates
            //System.out.println("clicked: "+tmpV+" "+tmpH);
            try{
                if(!firstPick) nextFormation();//OTHERWISE, PICKING A POSITION FOR NEXT FORMATION
                else initialFormation();//IF PICKING FOR INITIAL STARTING POSITIONS
            }catch(IOException exception) { System.out.println("\n\n\n\n CHLOE FIX ME \n\n\n\n\n"); }
        repaint(); //repaint whole board after each formation
        //Check if they have not yet put in stuff, because then they have option to randomize
        if((dNum==0) && (!firstPick)) bScatter.setVisible(true);
        else if((firstPick) || (dNum!=0)) bScatter.setVisible(false);
        }//actionPerformed()

    /** @return true if there is a dancer at this @param row and @param column, 
    false if the position is empty */
    public static boolean hasDancer(int w, int l) { 
        if(firstPick) 
            return (positions[w][l].getObj().getName() != null); 
        else 
            return (positions[w][l].getObj().getColor() == BACKGROUND); 
    }//hasDancer()

    /**initialFormation() is the action (called in actionPerformed) for setting the initial formation 
     * as well as the dancer and color information. */
    public void initialFormation() throws IOException{
        //Check that the coordinates will be on stage
            if(!(tmpV>-1) || !(tmpH>-1)) 
                System.out.println("\n\n\n CHLOE FIX ME \n\n\n\n");
            int w=tmpV; 
            int l=tmpH; 
            //System.out.println("inital formation: "+tmpV+" "+tmpH);
            addDancer(w,l); //process of creating new dancer
            checkdMaxReset(); //Check if you have reached the max number of dancers yet
        }//initialFormation()

    /**nextFormation() is the action (called in actionPerformed) for handling non-intial formations*/
    public void nextFormation(){
        if(!(tmpV>-1) || !(tmpH>-1)) 
            System.out.println("\n\n\n CHLOE fix pls MEEEEEEEE \n\n\n\n");
        int w=tmpV; 
        int l=tmpH; 
        addDancer(w, l);
        checkdMaxReset(); ////Check if you have reached the max number of dancers yet
    }//nextFormation()

    /**toTxt() stores the coordinates of an added position/dancer at given @param w and @param l
    in the txt files from the initial formation, all three files are written in. after the initial, only coordinates */
    public void toTxt(int w, int l) throws IOException{
        if(firstPick){
            //System.out.println("toTxt x and y: "+tmpV+" "+tmpH);
            db.write(positions[w][l].getObj().getName()+"\n");
            db.flush();
            cb.write(((positions[w][l].getObj().getColor().getRed()))+" "
                +(positions[w][l].getObj().getColor().getGreen())+" "
                +(positions[w][l].getObj().getColor().getBlue())+"\n");
            cb.flush(); }
        fb.write(l+" "+w+"\n"); //Because the Animation treats the x as horz, y as vert == opposite for this Class
        fb.flush(); 
    }//toTxt()
        
    /** Adds a dancer to the stage at the desired location at @param w along width and @param l along length*/
    public void addDancer(int w, int l) {  
        //l_coord is from leftside of panel=0 to right side=LENGTH ; w_coord is from top=0 to bottom=WIDTH
        Dancer dancer;
        if(firstPick){
        if((hasDancer(w,l))){ 
            JOptionPane.showMessageDialog(null, "There is already a dancer here!!");
         }
            else{
                String name; Color inColor;
                //-----JColorChooser Stuff:-----
                inColor = JColorChooser.showDialog(null,"Select a Color\nwhite=cancelling",Color.WHITE);
                if((inColor != null) && (inColor != Color.WHITE)){ 
                    name = JOptionPane.showInputDialog(null, "Enter Dancer name");
                    if((name != null) && (name != "")){ //successfully placed dancer
                        dancer = new Dancer(w,l,name,inColor);
                        positions[w][l].setObj(dancer); 
                        positions[w][l].setBackground(inColor);
                        //System.out.println("in addDancer: "+w+" "+l);
                            //Send dancer to text
                            try{toTxt(w,l); }catch (IOException e){ System.out.println("\n\n UH OH \n\n"); }
                            dNum++;
                    }else JOptionPane.showMessageDialog(null, "You cancelled without selecting");
                }else JOptionPane.showMessageDialog(null, "You cancelled without selecting"); }
        }else{ //non first pick dancers
            if(hasDancer(w,l)) JOptionPane.showMessageDialog(null, "There is already a dancer here");
            else{ //set the dancer in the button
                if(positions[w][l].getObj().getName() != ""){
                positions[w][l].setObj((Dancer) new Dancer(w,l,"",PRESSED));
                positions[w][l].setBackground(PRESSED);
                //Send dancer coordinates to text
                try{toTxt(w,l); }catch (IOException e){ System.out.println("\n\n UH OH \n\n"); }
                dNum++; } } } }

    /**checkdMaxReset() does nothing unless the max number of dancers have been placed in a given formation
     * If max number has been placed, then will go through process to go to next formation or end if at least 
     * 2 formations have occured */
    public void checkdMaxReset(){
    int result = 9; //value that will never occur in regular expected instances
    if(dNum == dMax){//If have selected all the dancers
        firstPick=false;
        fNum++; //another formation has been added
        if(fNum>1){
            result = JOptionPane.showConfirmDialog(null, 
            "Is your choreography done?",null, JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){ 
                String endInput = "You inputted "+fNum+" formations.";
                JOptionPane.showMessageDialog(null, endInput); //message letting user know how many formations the set
                this.setVisible(false); 
                JFrame anFrame = inFrame;
                anFrame.setSize(1000,1010); //plus ten to height to account for top panel and botton panel
                inFrame.dispose();
                //SETTING UP ANIMATION:
                Animation a = new Animation(fNum);
                a.readStates("coordinates.txt","names.txt","colors.txt");
                anFrame.setContentPane(a);
                anFrame.setVisible(true);
            } }
        if((result == JOptionPane.NO_OPTION) || (result == 9))
            JOptionPane.showMessageDialog(null, "Inputting the next formation now", "To Next Formation!", JOptionPane.INFORMATION_MESSAGE);
        //Clear out the (now for next formation) positions array
        for (int v = 0; v < vertPos; v++) for (int h = 0; h < horzPos; h++){
            //Clear out the positions dancers:
            positions[v][h].setObj((Dancer) new Dancer(v,h)); 
            positions[v][h].setBackground(BACKGROUND); } 
        dNum=0; //reset counter
        bScatter.setVisible(true); //make button available again right before dancers begin placement in next formation
    } }//checkdMaxReset()

    /**topPanel() sets up the top panel, a @return JPanel, containing the buttons and sliders for the user to adjust the 
     * visual format/display preferences*/
    public static JPanel topPanel(){ //Will have change color option, slider for line thickness, slider for button sizes
        JPanel top = new JPanel(new FlowLayout());    
        //Randomize button -- invis until nextFormations
        bScatter = new JButton("Randomize");
        bScatter.setVisible(false);
        top.add(bScatter);
        //LINE THICKNESS slider
        JSlider lineThickSlide;
        top.add(new JLabel("Line Thickness:"));
        top.add(lineThickSlide=new JSlider(SwingConstants.HORIZONTAL, 1, 5, lineThick));
        lineThickSlide.setMajorTickSpacing(1);   lineThickSlide.setPaintTicks(true);   
        lineThickSlide.addChangeListener((ChangeListener) new ChangeListener() { 
            public void stateChanged(ChangeEvent event) {
                lineThick = lineThickSlide.getValue();
                for(int v=0; v<vertPos;v++) for(int h=0;h<horzPos;h++)
                    positions[v][h].setBorder(BorderFactory.createLineBorder(lineColor,lineThick));}});
        JButton bCoord = new JButton("Coordinates");
        bCoord.setVisible(true);
        top.add(bCoord);
        bCoord.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e){
                if((positions[0][0].getText()==null) || (positions[0][0].getText()==""))
                    for(int v=0; v<vertPos;v++) for(int h=0;h<horzPos;h++)
                        positions[v][h].setText(v+","+h);
                else{
                    for(int v=0; v<vertPos;v++) for(int h=0;h<horzPos;h++)
                        positions[v][h].setText(""); 
                } } });
        return top; }//topPanel()
// =============================================================================
} //end class Input
// =============================================================================