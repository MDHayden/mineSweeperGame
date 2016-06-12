package model;
import java.util.Observable;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public class Box extends Observable{
    private boolean flag;
    private boolean trapped;
    private boolean visible;
    private int numberOfNeighborTrapped;
    
    // Simple constructor
    public Box() {
    }
    
    // Full constructor
    public Box(boolean containsFlag, boolean isTrapped, boolean isVisible, int numberOfNeighborTrapped) {
        this.flag = containsFlag;
        this.trapped = isTrapped;
        this.visible = isVisible;
        this.numberOfNeighborTrapped = numberOfNeighborTrapped;
    }
    
    // Getters and setters
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
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
    
    public void updateBox(){
        // TODO
        this.setVisible(true);
    }
    
    public void clic() {
        updateBox();
        
//        ArrayList<> al = ;
//	for(int i = 0; i < al.size(); i++) {
//            ArrayList<Box> b = Board.getNeighbors(this)
//            al<1>.maj();
//	}
//	notifyObservers();
    }
    
    
    
}
