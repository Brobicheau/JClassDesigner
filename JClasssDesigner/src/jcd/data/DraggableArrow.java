/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.scene.shape.Polygon;

/**
 *
 * @author brobi
 */
public class DraggableArrow extends Polygon implements Draggable {
    
    static final String ARROW = "ARROW";

    DraggableLine parentLine;
    boolean implementArrow;
    boolean useArrow;
    boolean aggregateArrow;


    public DraggableArrow (DraggableLine pLine, String arrowType) {

        parentLine = pLine;
        if(arrowType.equals("AGGREGATE")) {
            aggregateArrow = true;
            useArrow = false;
            implementArrow = false;
        }
        else if(arrowType.equals("USE")) {
            aggregateArrow = false;
            useArrow = true;
            implementArrow = false;            
        }
        else if(arrowType.equals("IMPLEMENT")) {
            aggregateArrow = true;
            useArrow = false;
            implementArrow = false;            
        }
        
    }
    

    
    
    public void makeArrow() {
        
       
    }
    
    public DraggableLine getParentLine() {
        return parentLine;
    }
    
    @Override
    public void drag(int x, int y) {
        
    }
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        
    }
    
    @Override
    public String getType() {
        return ARROW;
    }
    
    @Override
    public DesignerState getStartingState() {
        return DesignerState.DRAGGING_NOTHING;
    } 
}
