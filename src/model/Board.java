package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public abstract class Board extends Observable {
    
    // Hashmap containing all game's boxes
    HashMap <Object, Box> boxes;
    
    /**
     *
     * @param posX
     * @param posY
     * @return
     */
    public void discover(int posX, int posY){
    }
    
    public void search(int posX, int posY){
    }
    
    // Generates board's gamme
    public void generateBoard(){
        
    }
    
    // Generates mine's trapped
    public void generateMineTrapped(){
    }
    
    // Finds the number of mines trapped around the box
    public int findNumberOfMinesTrapped(int posX, int posY){
       return 0; 
    }
    
    public void rightClic(Box b){        
    }
    
    public void leftClic(Box b){
        
    }
}
