package model;

import java.util.ArrayList;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public abstract class Board {
    private int nb_mine_trapped, nb_box_discovered, nb_flags;
      
    Board(int mines){
        this.nb_mine_trapped = mines;
    }
    
    public int getNbMineTrapped() {
        return nb_mine_trapped;
    }
    
    public int getNb_flags() {
        return nb_flags;
    }
    
    public int getNb_box_discovered() {
        return nb_box_discovered;
    }
    
     public void setNbMineTrapped(int nb) {
        this.nb_mine_trapped = nb;
    }

    public void setNb_box_discovered(int nb_box_discovered) {
        this.nb_box_discovered = nb_box_discovered;
    }

    public void setNb_flags(int nb_flags) {
        this.nb_flags = nb_flags;
    }
    
    /**
     * public void generateBoard
     * Generate board's game
     */
    public void generateBoard(){}
    
    /**
     * public void generateMineTrapped
     * Called by generateBoard method to generate trapped mines
     */
    public void generateMineTrapped(){}
    
    /**
     * public void generateMineBonus
     * Called by generateBoard method to generate a bonus box
     */
    public void generateBonusBox(){}
    
    /**
     * public void addNumberMineTrapped
     * Finds number of mines trapped for each box of the game
     */
    public void addNumberMineTrapped(){}
        
    /**
     * public ArrayList giveNeighbors
     * @param b Given box for which we have to give its neighbors
     * @return an ArrayList containing all the neighbors
     */
    public ArrayList<Box> giveNeighbors(Box b){return null;}
    
    /**
     * public ArrayList giveEmptyBoxes
     * @return an ArrayList containing all the empty boxes of the game
     */
    public ArrayList<Box> giveOneBoxes(){return null;}
    
}
