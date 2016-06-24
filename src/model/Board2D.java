package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public class Board2D extends Board {
    private int nb_row_columns;
    private final Box[][] board2D;
    private HashMap<Box, int[]> boxPosition;
    
    public Board2D(int mine_trapped,int board_size) {
        super(mine_trapped);
        this.nb_row_columns = board_size;
        this.board2D = new Box[nb_row_columns][nb_row_columns];
        this.boxPosition = new HashMap<>();
    }
    
    /* Getters */
    public int getNbRowColumns() {
        return nb_row_columns;
    }
    
    public Box getBox(int i, int j) {
        Box b = board2D[i][j];
        return b;
    }

    public HashMap<Box, int[]> getBoxPosition() {
        return boxPosition;
    }
    
    /* Setters */
    public void setNbRowColumns(int nb) {
        this.nb_row_columns = nb;
    }

    public void setBoxPosition(HashMap<Box, int[]> boxPosition) {
        this.boxPosition = boxPosition;
    }
    
    @Override
    public void generateBoard(){
        // We first generate mines trapped
        generateMineTrapped();
        // Since we already filled some boxes with trapped mine, we just have to fill the rest of the board
        for (int i = 0; i < this.nb_row_columns; i++){
            for (int j = 0 ; j < this.nb_row_columns; j++){
                if (this.board2D[i][j] == null){  
                    Box b = new Box(false,false,false,0,this);
                    this.board2D[i][j] = b;
                    int[] position = new int[2];
                    position[0] = i; position [1] = j;
                    this.boxPosition.put(b, position);
                }
            }
        }
        // We finally update number of neighbors trapped for each box
        addNumberMineTrapped();
        // we then generate the bonus box
        generateSpecialBoxes();
    }
    
    @Override
    public void generateMineTrapped() {
        for (int i=0; i<this.getNbMineTrapped();i++){
            int random_row = (int)(Math.random() * (this.nb_row_columns-0)) + 0;
            int random_column = (int)(Math.random() * (this.nb_row_columns-0)) + 0;
            
            // We check if there's no mine already : if this is the case, we have to generate new indexes
            while(this.board2D[random_row][random_column] != null){
                random_row = (int)(Math.random() * (this.nb_row_columns-0)) + 0;
                random_column = (int)(Math.random() * (this.nb_row_columns-0)) + 0;
            }
            
            Box b = new Box(false,true,false,0,this);
            this.board2D[random_row][random_column] = b;
            int[] position = new int[2];
            position[0] = random_row; position [1] = random_column;
            this.boxPosition.put(b, position);
        }
    }
    
    @Override
    public void generateSpecialBoxes(){
        int[] indexes;
        
        indexes = this.generateIndexes();
        // We check if there's no mine already : if this is the case, we have to generate new indexes
        while((this.board2D[indexes[0]][indexes[1]].getNumberOfNeighborTrapped() != 1) || this.board2D[indexes[0]][indexes[1]].isTrapped()){
            indexes = this.generateIndexes();
        }
        BoxStar bonus = new BoxStar(false, false , false, 1, this);
        addInLists(bonus, indexes);
        
        // We check if there's no mine already : if this is the case, we have to generate new indexes
        while((this.board2D[indexes[0]][indexes[1]].isTrapped()) || this.board2D[indexes[0]][indexes[1]] instanceof BoxStar){
            indexes = this.generateIndexes();
        }
        BoxDiagonal bonus2 = new BoxDiagonal(false, false , false, this.board2D[indexes[0]][indexes[1]].getNumberOfNeighborTrapped(), this);
        addInLists(bonus2, indexes);
        
        // We check if there's no mine already : if this is the case, we have to generate new indexes
        while((this.board2D[indexes[0]][indexes[1]].isTrapped()) || this.board2D[indexes[0]][indexes[1]] instanceof BoxStar
                || this.board2D[indexes[0]][indexes[1]] instanceof BoxDiagonal){
            indexes = this.generateIndexes();
        }
        BoxHide bonus3 = new BoxHide(false, false , false, this.board2D[indexes[0]][indexes[1]].getNumberOfNeighborTrapped(), this);
        addInLists(bonus3, indexes);
        
    }
    
    @Override
    public int[] generateIndexes(){
        int[] indexes = new int[2];
        indexes[0] = (int)(Math.random() * this.nb_row_columns);
        indexes[1] = (int)(Math.random() * this.nb_row_columns);        
        return indexes;
    }
    
    /**
     * public void addInLists()
     * Adds an box and its position in hash map
     * @param b Box
     * @param position x and y indexes
     */
    public void addInLists(Box b, int[] position){
        this.board2D[position[0]][position[1]] = b;
        this.boxPosition.put(b, position);
    }
    
    @Override
    public void addNumberMineTrapped(){
        for (int i = 0; i < this.nb_row_columns; i++){
            for (int j = 0 ; j < this.nb_row_columns; j++){
                Box b = this.board2D[i][j];
                if (!b.isTrapped()){
                    b.findNumberOfMinesTrapped();
                }
            }
        }
    }
    
    @Override
    public ArrayList<Box> giveNeighbors(Box b){
        ArrayList<Box> neighborList = new ArrayList<>();
        int[] position = this.getBoxPosition().get(b);
        int posX = position[0], posY = position[1];
        
        // Right-top corner box
       if (posX-1>-1 && posY-1>-1) {
           neighborList.add(this.board2D[posX-1][posY-1]);
       }
       
       // Top box
       if (posY-1>-1) {
           neighborList.add(this.board2D[posX][posY-1]);
       }
       
       // Left-top corner box
       if (posY-1>-1 && posX+1<this.getNbRowColumns()) {
           neighborList.add(this.board2D[posX+1][posY-1]);
       }
       
       // Left box
       if (posX-1>-1) {
           neighborList.add(this.board2D[posX-1][posY]);
       }
       
       // Right box
       if (posX+1<this.getNbRowColumns()) {
           neighborList.add(this.board2D[posX+1][posY]);
       }
       
       // Right-bottom corner box
       if (posY+1<this.getNbRowColumns() && posX+1<this.getNbRowColumns()) {
           neighborList.add(this.board2D[posX+1][posY+1]);
       }
       
       // Bottom box
       if (posY+1<this.getNbRowColumns()) {
           neighborList.add(this.board2D[posX][posY+1]);
       }
       
       // Left-bottom corner box
       if (posX-1>-1 && posY+1<this.getNbRowColumns()) {
           neighborList.add(this.board2D[posX-1][posY+1]);
       }
        return neighborList;
    }
    
    @Override
    public ArrayList<Box> giveOneBoxes(){
        ArrayList<Box> emptyBoxes = new ArrayList<>();
        for (int i = 0; i < this.nb_row_columns; i++){
            for (int j = 0 ; j < this.nb_row_columns; j++){
                Box b = this.board2D[i][j];
                if (b.getNumberOfNeighborTrapped() == 1){
                    emptyBoxes.add(b);
                }
            }
        }
        return emptyBoxes;
    }
    
    @Override
    public ArrayList<Box> giveDiagonalBoxes(Box b){
        ArrayList<Box> diagonalBoxes = new ArrayList<>();
        diagonalBoxes.add(b);        
        int[] currentBox = boxPosition.get(b);
        int x = currentBox[0], y = currentBox[1];
        
        // Boxes back
        if(x-1>0 && y>0){
            x --; y --;
            while(x>-1 && y>-1){
                diagonalBoxes.add(this.board2D[x][y]);
                x--; y--;
            }
        }
        
        // return in the center
        x = currentBox[0]; y = currentBox[1];
        
        // Boxes forward
        if(x+1<this.getNbRowColumns() && y+1<=this.getNbRowColumns()){
            x ++; y ++;
            while((x<this.getNbRowColumns()) && (y<this.getNbRowColumns())){
                diagonalBoxes.add(this.board2D[x][y]);
                x++; y++;
            }
        }
        return diagonalBoxes;
    }
    
    @Override
    public List<Box> giveQuarterOfGame(Box b){
        List<Box> quarterGame = new ArrayList<>();      
        int[] currentBox = boxPosition.get(b);
        int x = currentBox[0], y = currentBox[1], i=0, j=0;
        int endi=0, endj=0, temp;
        
        if(x<=4 && y<=4){
            i = 0; j = 0;
            endi = 5; endj = 5;
        } else if (x<=4 && y>4){
            i = 0; j = 5;
            endi = 5; endj = 10;
        } else if (x>4 && y<=4) {
            i = 5; j = 0;
            endi = 10; endj = 5;
        } else {
            i = 5; j = 5;
            endi = 10; endj = 10;
        }
        
        temp = j;
        
        while (i < endi){
            j = temp;
            while (j < endj){
                quarterGame.add(this.board2D[i][j]);
                j++;
            }
            i++;
        }
        return quarterGame;
    }
}
