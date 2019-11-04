// =============================================================================
/**
 * The Button class extends the JButton, and is used in storing dancer information
 * in the input class. Each button holds an object. 
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

// =============================================================================
class Button<T> extends JButton{
    // =============================================================================
        private static final long serialVersionUID = 1L;
        T obj;
    
    //CONSTRUCTORS
        public Button(String n){ 
            super(n);
            setFocusPainted(false);
            //Make visible/changeable with color
            setContentAreaFilled(false);
            setOpaque(true);
        }//Button(String)
    
        public Button(String n, T par){ 
            super(n);
            this.obj = par; 
            setFocusPainted(false);
            //Make visible/changeable with color
            setContentAreaFilled(false);
            setOpaque(true);
        }//Button(String, object)
    
    //METHODS for buttons
        //getters and setters
        public T getObj(){ 
            return this.obj; 
        }//getObj()
        public void setObj(T o){
            this.obj = o; 
        }//setObj()
        public String getType(){
            return obj.getClass().getName(); 
        }//setType()
    }