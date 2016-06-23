package model;

import java.util.ArrayList;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public class BoxStar extends Box {
    private boolean bonus;
    
    public BoxStar(boolean containsFlag, boolean isTrapped, boolean isVisible, int numberOfNeighborTrapped, Board b, boolean isBonus) {
        super(containsFlag,isTrapped,isVisible,numberOfNeighborTrapped,b);
        this.bonus = isBonus;
    }
    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }
    
    /**
     * public void revealOneBoxes
     * Reveal all boxes with one mine trapped
     */
    public void revealOneBoxes(){
        ArrayList<Box> boxes = this.getGame().giveOneBoxes();        
        for (Box b : boxes){
            this.getGame().setNb_box_discovered(this.getGame().getNb_box_discovered()+1);
            b.setVisible(true);
        }
        setChanged();
        notifyObservers();
    }
    
    @Override
    public void leftClic(){
        if(this.isFlag()){
            this.setFlag(false);
            this.getGame().setNb_flags(this.getGame().getNb_flags()-1);
        } else if(this.isBonus()){
            revealOneBoxes();
        }
        setChanged();
        notifyObservers();
    }
}
