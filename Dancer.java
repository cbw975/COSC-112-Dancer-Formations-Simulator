// =============================================================================
/**
 * The Dancer class holds information for each dancer such as position, color,
 * name, velocity, nextPosition
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
import javax.swing.event.*;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;

// =============================================================================
public class Dancer{
// =============================================================================
    //FIELDS
    // =============================================================================
    private String name;
    private double velocityX, velocityY;
    private double nextPositionX, nextPositionY;
    private Color color;
    private double positionX, positionY;
    // =============================================================================
    //CONSTRUCTORS
    // =============================================================================
    public Dancer(int x, int y, Color col){
        this.color = col; this.positionX=x; this.positionY=y; }
    public Dancer(int x, int y, String n, Color col){
        this.color = col; this.positionX=x; this.name=n; this.positionY=y; }
    public Dancer(int x, int y){
        positionX=1.0*x;
        positionY=1.0*y;
        velocityX=0.0;
        velocityY=0.0;
        name = null;
    }
    // =============================================================================
    // METHODS
    // =============================================================================

    //getters and setters, positions are stored as doubles
    public void setName(String x){
        this.name=x; 
    }
    public void setPositionX(int x){
        positionX = 1.0*x; 
    }
    public void setNextPositionX(int x){
        nextPositionX = 1.0*x; 
    }
    public void setNextPositionY(int y){
        nextPositionY = 1.0*y; 
    }
    public void setPositionY(int y){
        positionY = 1.0*y;
    }
    public void setColor(Color c){ 
        this.color=c; 
    }
    public String getName(){ 
        return this.name; 
    }
    public int getPositionX(){ 
        return (int)positionX; 
    }
    public int getPositionY(){
        return (int)positionY; 
    }
    public int getNextPositionX(){
        return (int)nextPositionX;
    }
    public int getNextPositionY(){
        return (int)nextPositionY;
    }
    public double getVelocityX(){
        return velocityX;
    }
    public double getVelocityY(){
        return velocityY;
    }
    public Color getColor(){ 
        return this.color; 
    }

    //toString converts dancer information into a string
    public String toString(){
        String s="Name: "+name+" X: "+positionX+" Y: "+positionY;
        return s; 
    }

    //draw fills a circle adjusted to the width and length of the input frame with the color of the dancer
    public void draw(Graphics g){
        //g.setColor(this.color);
        double pixelX = Display.scaleX*positionX;
        double pixelY = Display.scaleY*positionY;
        g.fillOval((int)(pixelX),(int)(pixelY)+10,20,20);
    }

    //updates velocity relative to nextPositions
    public void updateVelocity(){
        velocityX = 1.0*(nextPositionX-positionX);
        velocityY = 1.0*(nextPositionY-positionY);
    }

    //updates positions based on velocity
    public void update(){
        double dx = 1.0*(velocityX)/60;
        double dy = 1.0*(velocityY)/60;
        this.positionX+=dx;
        this.positionY+=dy; 
    }
}