/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author brobi
 */
public class DraggableCircle extends Circle implements Draggable{
    
    static final String CIRCLE = "CIRCLE";
    String point;
    DraggableLine parentLine;
    LineTo nodeLine;
    MoveTo nodePoint;
    DraggableCircle previousNode;
    
    public DraggableCircle (DraggableLine pLine) {
        parentLine = pLine;
    }
    
    public DraggableLine getParentLine() {
        return parentLine;
    }
    
    public void setPreviousNode(DraggableCircle node) {
        previousNode = node;
    }
    
    public DraggableCircle getPreviousNode() {
        return previousNode;
    }
    
    public void setNodeLine(LineTo newLineNode) {
        nodeLine = newLineNode;
    }
    
    public void setNodePoint(MoveTo newPointNode) {
        nodePoint = newPointNode;
    }
    
    public MoveTo getNodePoint() {
        return nodePoint;
    }
    
    public LineTo getNodeLine() {
        return nodeLine;
    }
    
    @Override
    public void drag(int x, int y) {
        
    }
    
    public void setPoint(String newPoint) {
        point = newPoint;
    }
    
    public String getPoint() {
        return point;
    }
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        
    }
    
    @Override
    public String getType() {
        return CIRCLE;
    }
    
    @Override
    public DesignerState getStartingState() {
        return DesignerState.DRAGGING_NOTHING;
    } 
}
