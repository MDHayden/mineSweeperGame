package model;

import java.util.Observable;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public abstract class Board extends Observable {
    
    /**
     * public void discover
     * @param posX
     * @param posY
     * Discover neighbors for given box at posX and posY position in the board
     */
    public void discover(int posX, int posY){}
    
    /**
     * public void search
     * @param posX
     * @param posY
     * Called by discover method (recursion)
     */
    public void search(int posX, int posY){}
    
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
     * public void addNumbersOfMinesTrapped
     * Generates and adds number of neighbors trapped for each box
     */
    public void addNumbersOfMinesTrapped(){}
    
    /**
     * public void findNumberOfMinesTrapped
     * @param posX
     * @param posY
     * Finds numbers of mines trapped around given box at posX and posY position in the board
     * @return number of mines trapped around the box
     */
    public int findNumberOfMinesTrapped(int posX, int posY){
       return 0; 
    }
    
    /**
     * public void rightClic
     * @param b
     * Handles left user clic on given box b : adds a flag
     */
    public void rightClic(Box b){}
    
    /**
     * public void leftClic
     * @param b
     * Handles right user clic on given box b : removes a flag
     */
    public void leftClic(Box b){}
}
