package model;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public class Board2D extends Board {    
    private int nb_row_columns;
    private int nb_mine_trapped, nb_box_discovered, nb_flags;
    private final Box[][] board2D;
    
    /* Constructor */
    public Board2D(int mine_trapped,int board_size) {
        this.nb_mine_trapped = mine_trapped;
        this.nb_row_columns = board_size;
        this.board2D = new Box[nb_row_columns][nb_row_columns];
    }
    
    /* Getters */
    public int getNbRowColumns() {
        return nb_row_columns;
    }

    public int getNbMineTrapped() {
        return nb_mine_trapped;
    }
    
    public Box getBox(int i, int j) {
        Box b = board2D[i][j];
        return b;
    }

    public int getNb_flags() {
        return nb_flags;
    }
    
    /* Setters */
    public void setNbRowColumns(int nb) {
        this.nb_row_columns = nb;
    }

    public void setNbMineTrapped(int nb) {
        this.nb_mine_trapped = nb;
    }

    public int getNb_box_discovered() {
        return nb_box_discovered;
    }

    public void setNb_box_discovered(int nb_box_discovered) {
        this.nb_box_discovered = nb_box_discovered;
    }

    public void setNb_flags(int nb_flags) {
        this.nb_flags = nb_flags;
    }
    
    
    /*
    * public void generateBoard
    * @return 
    * Generates board's game
    */
    @Override
    public void generateBoard(){
        
        generateMineTrapped();
        
        // Since we already filled some boxes with trapped mine, we just have to fill the rest of the board
        for (int i = 0; i < this.nb_row_columns; i++){
            for (int j = 0 ; j < this.nb_row_columns; j++){
                if (this.board2D[i][j] == null){  
                    Box b = new Box(false,false,false,0);
                    this.board2D[i][j] = b;
                }
            }
        }
        addNumbersOfMinesTrapped();
    }
    
    public void addNumbersOfMinesTrapped(){        
        for (int i = 0; i < this.nb_row_columns; i++){
            for (int j = 0 ; j < this.nb_row_columns; j++){
                if (!this.board2D[i][j].isTrapped()){
                    // We have to check if the box is surronded by trapped box
                    // We will update numbers of mine trapped
                    int nbMinesTrapped = findNumberOfMinesTrapped(i,j);
                    this.board2D[i][j].setNumberOfNeighborTrapped(nbMinesTrapped);
                }
            }
        }
    }
    
    /*
    * public int[][] generatePositionMineTrapped()
    * @return 
    * Initialize board's game with randomly created mines
    */
    @Override
    public void generateMineTrapped() {   
        
        for (int i=0; i<this.nb_mine_trapped;i++){
            
            int random_row = (int)(Math.random() * (this.nb_row_columns-0)) + 0;
            int random_column = (int)(Math.random() * (this.nb_row_columns-0)) + 0;
            // we check if there's no mine already : if this is the case, we have to generate new indexes
            while(this.board2D[random_row][random_column] != null){
                random_row = (int)(Math.random() * (this.nb_row_columns-0)) + 0;
                random_column = (int)(Math.random() * (this.nb_row_columns-0)) + 0;
            }
            Box b = new Box(false,true,false,0);
            this.board2D[random_row][random_column] = b;
        }
    }
    
    @Override
    public void search(int posX, int posY){
        Box b = this.board2D[posX][posY];
        
        if(!b.isFlag() && !b.isVisible()) {
            b.setVisible(true);
            this.setNb_box_discovered(nb_box_discovered+1);            
            if(!b.isTrapped()) {
                if((b.getNumberOfNeighborTrapped() == 0)){
                    // Top box
                    if (posY-1>-1) {
                        search(posX,posY-1);
                    }
                    // Left box
                    if (posX-1>-1) {
                        search(posX-1,posY);
                    }

                    // Right box
                    if (posX+1<this.getNbRowColumns()) {
                        search(posX+1,posY);
                    }

                    // Bottom box
                    if (posY+1<this.getNbRowColumns()) {
                        search(posX,posY+1);
                    }         
                }
            }
        }
        
        
    }
    
    
    @Override
    public void discover(int posX, int posY ){
        Box b = this.board2D[posX][posY]; 
        
        search(posX,posY);
        setChanged();
        notifyObservers();
    }
    
    @Override
    public int findNumberOfMinesTrapped(int posX, int posY){
       int numberOfMinesTrapped=0;
       
       // Right-top corner box
       if (posX-1>-1 && posY-1>-1) {
           if(board2D[posX-1][posY-1].isTrapped()){
                numberOfMinesTrapped++;
            }
       }
       
       // Top box
       if (posY-1>-1) {
           if(board2D[posX][posY-1].isTrapped()){
                numberOfMinesTrapped++;
            }
       }
       
       // Left-top corner box
       if (posY-1>-1 && posX+1<this.getNbRowColumns()) {
           if(board2D[posX+1][posY-1].isTrapped()){
                numberOfMinesTrapped++;
            }
       }
       
       // Left box
       if (posX-1>-1) {
           if(board2D[posX-1][posY].isTrapped()){
                numberOfMinesTrapped++;
            }
       }
       
       // Right box
       if (posX+1<this.getNbRowColumns()) {
           if(board2D[posX+1][posY].isTrapped()){
                numberOfMinesTrapped++;
            }
       }
       
       // Right-bottom corner box
       if (posY+1<this.getNbRowColumns() && posX+1<this.getNbRowColumns()) {
           if(board2D[posX+1][posY+1].isTrapped()){
                numberOfMinesTrapped++;
            }
       }
       
       // Bottom box
       if (posY+1<this.getNbRowColumns()) {
           if(board2D[posX][posY+1].isTrapped()){
                numberOfMinesTrapped++;
            }
       }
       
       // Left-bottom corner box
       if (posX-1>-1 && posY+1<this.getNbRowColumns()) {
           if(board2D[posX-1][posY+1].isTrapped()){
                numberOfMinesTrapped++;
            }
       }
       return numberOfMinesTrapped;
    }
    
    @Override
    public void rightClic(Box b){
        b.setFlag(true);
        this.setNb_flags(nb_flags+1);
        setChanged();
        notifyObservers();
    }
    
    @Override
    public void leftClic(Box b){
        b.setFlag(false);
        this.setNb_flags(nb_flags-1);
        setChanged();
        notifyObservers();
    }
}
