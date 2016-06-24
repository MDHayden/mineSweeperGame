package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public abstract class Board {
    private int nb_mine_trapped, nb_box_discovered, nb_flags, nb_special_boxes;
      
    Board(int mines){
        this.nb_mine_trapped = mines;
        this.nb_special_boxes = 3;
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

    public int getNb_special_boxes() {
        return nb_special_boxes;
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
    public void generateSpecialBoxes(){}
    
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
    
    /**
     * public ArrayList giveDiagonalBoxes
     * @param b Given box for which we wil reveal the diagonal
     * @return an ArrayList containing all boxes in the diagonal of a box
     */
    public ArrayList<Box> giveDiagonalBoxes(Box b){return null;}
    
    /**
     * public ArrayList giveQuarterOfGame
     * @param b Given box for which we wil hide quarter of the game
     * @return an ArrayList containing all boxes we have to hide
     */
    public List<Box> giveQuarterOfGame(Box b){return null;}
    
    /**
     * public int[] generateIndexes
     * @return an array containing random indexes
     */
    public int[] generateIndexes(){return null;}
    
}
