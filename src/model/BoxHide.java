package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */
public class BoxHide extends Box {

    public BoxHide(boolean containsFlag, boolean isTrapped, boolean isVisible, int numberOfNeighborTrapped, Board b) {
        super(containsFlag, isTrapped, isVisible, numberOfNeighborTrapped, b);
    }
    
    /**
     * public void revealOneBoxes
     * Reveal all boxes with one mine trapped
     */
    public void hideQuarterOfGame(){
        List<Box> boxes = this.getGame().giveQuarterOfGame(this);        
        for (Box b : boxes){
            if(b.isFlag()){
                b.setFlag(false);
                this.getGame().setNb_flags(this.getGame().getNb_flags()-1);
            } else {
                b.setVisible(false);
                this.getGame().setNb_box_discovered(this.getGame().getNb_box_discovered()-1);
            }
        }
        setChanged();
        notifyObservers();
    }
    
    @Override
    public void leftClic(){
        if(this.isFlag()){
            this.setFlag(false);
            this.getGame().setNb_flags(this.getGame().getNb_flags()-1);
        } else if(this instanceof BoxHide){
            hideQuarterOfGame();
        }
        setChanged();
        notifyObservers();
    }
    
}
