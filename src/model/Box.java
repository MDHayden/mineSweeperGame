package model;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public class Box extends Observable{
    private boolean flag, trapped, visible;
    private int numberOfNeighborTrapped;
    private final Board game;
    
    public Box(boolean containsFlag, boolean isTrapped, boolean isVisible, int numberOfNeighborTrapped, Board b) {
        this.flag = containsFlag;
        this.trapped = isTrapped;
        this.visible = isVisible;
        this.numberOfNeighborTrapped = numberOfNeighborTrapped;
        this.game = b;
    }
    
    // Getters & setters
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        // gérer clic droit
        this.flag = flag;
    }

    public boolean isTrapped() {
        return trapped;
    }

    public void setTrapped(boolean trapped) {
        this.trapped = trapped;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getNumberOfNeighborTrapped() {
        return numberOfNeighborTrapped;
    }

    public void setNumberOfNeighborTrapped(int numberOfNeighborTrapped) {
        this.numberOfNeighborTrapped = numberOfNeighborTrapped;
    }

    public Board getGame() {
        return game;
    }
    
    /**
     * public void discover
     * @param b Given box we will discover
     */
    public void discover(Box b){
        updateBox(b);        
        setChanged();
        notifyObservers();
    }
    
    /**
     * public void discover
     * called by discover() method
     * @param b Given box we will discover
     */
    public void updateBox(Box b){        
        if(!b.isFlag() && !b.isVisible()) {
            b.setVisible(true);
            this.getGame().setNb_box_discovered(this.getGame().getNb_box_discovered()+1);
            if(!b.isTrapped() && (b.getNumberOfNeighborTrapped() == 0)) {
                ArrayList<Box> neighbors = b.getGame().giveNeighbors(b);
                for (Box n : neighbors) {
                    if(!n.isTrapped()){
                        updateBox(n);
                    }
                }
            }
        }
    }
    
    /**
     * public void findNumberOfMinesTrapped
     * finds and set number of mines around the box
     */
    public void findNumberOfMinesTrapped(){
        int numberOfMinesTrapped=0;
        ArrayList<Box> neighbors = this.getGame().giveNeighbors(this);

        for (Box n : neighbors) {
            if(n.isTrapped()){
                numberOfMinesTrapped++;
            }
        }
        this.setNumberOfNeighborTrapped(numberOfMinesTrapped);
    }
    
    /**
     * public void rightClic
     * handles right clic and updates model
     */
    public void rightClic(){
        if(!this.isFlag()){
            this.setFlag(true);
            this.getGame().setNb_flags(this.getGame().getNb_flags()+1);
        } else {
            this.setFlag(false);
            this.getGame().setNb_flags(this.getGame().getNb_flags()-1);
        }
        setChanged();
        notifyObservers();
    }
    
    /**
     * public void leftClic
     * handles left clic and updates model
     */
    public void leftClic(){
        if(this.isFlag()){
            this.setFlag(false);
            this.getGame().setNb_flags(this.getGame().getNb_flags()-1);
        }
        setChanged();
        notifyObservers();
    }
}
