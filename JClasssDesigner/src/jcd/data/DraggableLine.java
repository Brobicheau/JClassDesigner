/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import jcd.controller.LineController;
import static jcd.data.PositionState.EAST;
import static jcd.data.PositionState.EQUAL;
import static jcd.data.PositionState.NORTH;
import static jcd.data.PositionState.NORTH_EAST;
import static jcd.data.PositionState.NORTH_WEST;
import static jcd.data.PositionState.SOUTH;
import static jcd.data.PositionState.SOUTH_EAST;
import static jcd.data.PositionState.SOUTH_WEST;
import static jcd.data.PositionState.WEST;

/**
 *
 * @author brobi
 */
public class DraggableLine extends Path implements Draggable {
    static final String LINE = "LINE";
    static final String INHERITENCE = "INHERITENCE";
    static final String USES = "USES";
    static final String AGGREGATE = "AGGREGATE";
    

    double startModX = 0;
    double startModY = 0;
    double endModX = 0;
    double endModY = 0;
    int segmentCount;
    Double triModNodeX;
    Double triModArrowX;
    Double triModNodeY;
    Double triModArrowY;
    boolean end;
    boolean start;
    boolean isAuto;
    MoveTo startPoint;
    LineTo startLine;
    MoveTo oldMidPoint;
    LineTo oldMidLine;
    MoveTo midPoint;
    LineTo midLine;            
    MoveTo endPoint;
    LineTo endLine;
    Line baseLine;
    ArrayList<Line> manualLines;
    ArrayList<DraggableCircle> manualNodes;
    ArrayList<MoveTo> midPointArray;
    ArrayList<LineTo> midLineArray;    
    ArrayList<Double> triArray;
    ArrayList<PathElement> arrayElements;
    ArrayList<DraggableCircle> midNodes;
    ObservableList<Double> triPoints;
    ObservableList<PathElement> elements;
    DraggableUMLBox childBox;
    DraggableUMLBox parentBox;
    PositionState parentPosition;
    DraggableCircle baseNode;
    DraggableCircle arrowNode;
    DraggableArrow arrowHead;
    Double[] dArray;
    MoveTo firstPoint = new MoveTo();
    LineTo firstLine = new LineTo();   
    MoveTo secondPoint = new MoveTo();
    LineTo secondLine = new LineTo();
    MoveTo thirdPoint = new MoveTo();
    LineTo thirdLine = new LineTo();  
    ArrowHeadBuilder arrowBuilder;
    String arrowType;
    LineController lineController;
    
    public DraggableLine (DraggableUMLBox parent, DraggableUMLBox child, LineController lc, String aType) {
        
        isAuto = true;
        lineController = lc;
        childBox = child;
        parentBox = parent;
        segmentCount = 1;
        startPoint = new MoveTo();
        startLine = new LineTo();
        midPoint = new MoveTo();
        midLine = new LineTo(); 
        oldMidPoint = new MoveTo();
        oldMidLine = new LineTo();
        endPoint = new MoveTo();
        endLine = new LineTo(); 
        baseLine = new Line();
        manualLines = new ArrayList();
        manualLines.add(baseLine);
        midPointArray = new ArrayList();
        midLineArray = new ArrayList();
        midNodes = new ArrayList();
        triModArrowX = 0.0;
        triModArrowY = 0.0;
        triModNodeX = 0.0;
        triModNodeY = 0.0;     
        arrowBuilder = new ArrowHeadBuilder();
        arrowType = aType;
        setNodeOrientation();
        updateComplexLine();
        setArrowHead();
        setLineBase();
        this.getElements().addAll(startPoint, startLine, midPoint, midLine, endPoint, endLine); 
        


    }
    
    
    
    public DraggableLine() {
        
    }
    
    public ArrayList<DraggableCircle> getMidNodes() {
        return midNodes;
    }
    
    public MoveTo getStartPoint() {
        return startPoint;
    }
    
    public LineTo getStartLine() {
        return startLine;
    }
    
    public MoveTo getEndPoint() {
        return endPoint;
    }
    
    public LineTo getEndLine() {
        return endLine;
    }
    
    public MoveTo getMidPoint() {
        return midPoint;
    }
    
    public LineTo getMidLine() {
        return midLine;
    } 
    
    public ArrayList<MoveTo> getMidPointArray() {
        return midPointArray;
    }
    
    public ArrayList<LineTo> getMidLineArray() {
        return midLineArray;
    }
    
    public double getStartModX() {
        return startModX;
    }
    
    public double getStartModY() {
        return startModY;
    }
    
    
    public double getEndModX() {
        return endModX;
    }
    
    public double getEndModY() {
        return endModY;
    }
        
    private void makeArrowHead() {
        
    }
    public void setArrowHead(){
        if(arrowHead != null){
            
            dArray = arrowBuilder.buildArrowHead(arrowType, parentPosition);
            triPoints.clear();
            triPoints.addAll(dArray);
            triModArrowY = arrowBuilder.getTriModArrowY();
            triModArrowX = arrowBuilder.getTriModArrowX();
            triModNodeY = arrowBuilder.getTriModNodeY();
            triModNodeX = arrowBuilder.getTriModNodeX();

            arrowHead.setLayoutX(endLine.getX()- triModArrowX);
            arrowHead.setLayoutY(endLine.getY() - triModArrowY);
            arrowNode.setLayoutX(endLine.getX()- triModNodeX);
            arrowNode.setLayoutY(endLine.getY()- triModNodeY);
            
            if(!midNodes.isEmpty())
            for(int i = 0; i < midNodes.size(); i++) {
                midNodes.get(i).setLayoutX(midPointArray.get(i).getX());
                midNodes.get(i).setLayoutY(midPointArray.get(i).getY());                
            }



        }
        else {

            arrowHead = new DraggableArrow(this, arrowType);
            triPoints = arrowHead.getPoints();
            dArray = new Double[] {
                0.0, 0.0,
                0.0, 0.0,
                0.0, 0.0};
            triPoints.addAll(dArray);
            arrowNode = new DraggableCircle(this);
            arrowNode.setPoint("end");
            arrowNode.setLayoutX(endLine.getX()-15);
            arrowNode.setLayoutY(endLine.getY());
            arrowNode.setRadius(5.0);
            
            
        }
        
    }

    
    public DraggableArrow getArrow() {
        return arrowHead;
    }
    
    public DraggableCircle getArrowNode() {
        return arrowNode;
    }
    
    public void setLineBase() {
        
        if(baseNode != null) {
            baseNode.setCenterX(startPoint.getX());
            baseNode.setCenterY(startPoint.getY());
            baseNode.setRadius(5.0);
        }
        else {
            baseNode = new DraggableCircle(this);
            baseNode.setPoint("start");
        }
        
    }
    
    public DraggableCircle getBaseNode() {
        return baseNode;
    }

    
    public void setStartPointX (double x) {
        startPoint.setX(x); 
    }
    
    public double getStartPointX() {
        return startPoint.getX();
    }
    
    public void setStartPointY (double y) {
        startPoint.setY(y);
    }
    
    public double getStartPointY() {
        return startPoint.getY();
    }
    
    public void setLineStartPointX (double x) {
        startLine.setX(x);
    }
    
    public double getStartLineEndPointX() {
        return startLine.getX();
    }
    
    public void setLineStartPointY (double y) {
        startLine.setY(y);
    }    
    
    public void setMidPointX (double x) {
        midPoint.setX(x);
    }
    
    public void setMidPointY (double y) {
        midPoint.setY(y);
    }    
    
    public void setLineMidPointX (double x) {
        midLine.setX(x);
    }
    
    public void setLineMidPointY (double y) {
        midLine.setY(y);
    }
    
    public void setEndPointX (double x) {
        endPoint.setX(x);
    }
    
    public double getEndPointX() {
        return endPoint.getX();
    }
    
    public void setEndPointY (double y) {
        endPoint.setY(y);
    } 

    public void setLineEndPointX (double x) {
        endLine.setX(x);
    }        
    
    public double getEndLinePointX() {
        return endLine.getX();
    }
    
    public void setLineEndPointY (double y) {
        endLine.setY(y);
    }
    
    public double getEndLinePointY() {
        return endLine.getY();
    }
    public double getEndPointY() {
        return endPoint.getY();
    }
    
    public void setNewValues(int x, int y) {
        
        
    }
    
    @Override
    public String getType() {
        return LINE;
    }
    
    public void dragPoint(int x,int y) {
        if(start)
            this.dragStartPoint(x, y);
        if(end)
            this.dragEndPoint(x, y);
    }
    
    public void setEnd(boolean e) {
        end = e;
    }
    
    public void setStart(boolean s) {
        start = s;
    }

    
    
    @Override
    public void drag(int x, int y) {
        setNodeOrientation();
        updateComplexLine();
    }
    public void dragEndPoint(int x, int y) {
        double test ;
        if(null != parentPosition)switch (parentPosition) {
            case WEST:
                test = parentBox.getLayoutY()+  (parentBox.getPrefHeight()/2) -(endModY +(endPoint.getY() - (y)));
                if(test > parentBox.getLayoutY() && test < parentBox.getLayoutY() + parentBox.getPrefHeight())
                    endModY = (endModY +(endPoint.getY() - (y)));
                
                break;
            case EAST:
                
                test = parentBox.getLayoutY()+  (parentBox.getPrefHeight()/2) -(endModY +(endPoint.getY() - (y)));
                if(test > parentBox.getLayoutY() && test < parentBox.getLayoutY() + parentBox.getPrefHeight())
                    endModY = (endModY +(endPoint.getY() - (y)));       
                
                break;
            case SOUTH:
                test = (parentBox.getLayoutX() +(parentBox.getPrefWidth()/2) - (endModX + (endPoint.getX() - x)));
                if(test > parentBox.getLayoutX() && test < parentBox.getLayoutX() + parentBox.getPrefWidth())
                    endModX = (endModX + (endPoint.getX() - x));
                 break;
            case SOUTH_EAST:
                
                test = (parentBox.getLayoutX() +(parentBox.getPrefWidth()/2) - (endModY + (endPoint.getY() - y)));
                if(test > parentBox.getLayoutX() && test < parentBox.getLayoutX() + parentBox.getPrefWidth())
                    endModY = (endModY + (endPoint.getY() - y));           
                 break;
                
            case SOUTH_WEST:
                test = (parentBox.getLayoutX() +(parentBox.getPrefWidth()/2) - (endModY + (endPoint.getY() - y)));
                if(test > parentBox.getLayoutX() && test < parentBox.getLayoutX() + parentBox.getPrefWidth())
                    endModY = (endModY + (endPoint.getY() - y));                
                break;
            case NORTH:
                
                test = (parentBox.getLayoutX() +(parentBox.getPrefWidth()/2) - (endModX + (endPoint.getX() - x)));
                if(test > parentBox.getLayoutX() && test < parentBox.getLayoutX() + parentBox.getPrefWidth())
                    endModX = (endModX + (endPoint.getX() - x));    
                 break;
                
            case NORTH_EAST:
                
                test = (parentBox.getLayoutX() +(parentBox.getPrefWidth()/2) - (endModY + (endPoint.getY() - y)));
                if(test > parentBox.getLayoutX() && test < parentBox.getLayoutX() + parentBox.getPrefWidth())
                    endModY = (endModY + (endPoint.getY() - y));    
                 break;                
            case NORTH_WEST:
                
                test = (parentBox.getLayoutX() +(parentBox.getPrefWidth()/2) - (endModY + (endPoint.getY() - y)));
                if(test > parentBox.getLayoutX() && test < parentBox.getLayoutX() + parentBox.getPrefWidth())
                    endModY = (endModY + (endPoint.getY() - y));                    
                break;
            default:
                break;
        }
                updateComplexLine();

    }
    
    public void dragStartPoint(int x, int y) {
        double test ;
        if(null != parentPosition)switch (parentPosition) {
            case WEST:
                test = childBox.getLayoutY()+  (childBox.getPrefHeight()/2) -(startModY +(startPoint.getY() - (y)));
                if(test > childBox.getLayoutY() && test < childBox.getLayoutY() + childBox.getPrefHeight())
                    startModY = (startModY +(startPoint.getY() - (y)));
                
                break;
            case EAST:
                
                test = childBox.getLayoutY()+  (childBox.getPrefHeight()/2) -(startModY +(startPoint.getY() - (y)));
                if(test > childBox.getLayoutY() && test < childBox.getLayoutY() + childBox.getPrefHeight())
                    startModY = (startModY +(startPoint.getY() - (y)));       
                
                break;
            case SOUTH:
                test = (childBox.getLayoutX() +(childBox.getPrefWidth()/2) - (startModX + (startPoint.getX() - x)));
                if(test > childBox.getLayoutX() && test < childBox.getLayoutX() + childBox.getPrefWidth())
                    startModX = (startModX + (startPoint.getX() - x));
                 break;
            case SOUTH_EAST:
                
                test = (childBox.getLayoutX() +(childBox.getPrefWidth()/2) - (startModX + (startPoint.getX() - x)));
                if(test > childBox.getLayoutX() && test < childBox.getLayoutX() + childBox.getPrefWidth())
                    startModX = (startModX + (startPoint.getX() - x));           
                 break;
                
            case SOUTH_WEST:
                test = (childBox.getLayoutX() +(childBox.getPrefWidth()/2) - (startModX + (startPoint.getX() - x)));
                if(test > childBox.getLayoutX() && test < childBox.getLayoutX() + childBox.getPrefWidth())
                    startModX = (startModX + (startPoint.getX() - x));                
                break;
            case NORTH:
                
                test = (childBox.getLayoutX() +(childBox.getPrefWidth()/2) - (startModX + (startPoint.getX() - x)));
                if(test > childBox.getLayoutX() && test < childBox.getLayoutX() + childBox.getPrefWidth())
                    startModX = (startModX + (startPoint.getX() - x));    
                 break;
                
            case NORTH_EAST:
                
                test = (childBox.getLayoutX() +(childBox.getPrefWidth()/2) - (startModX + (startPoint.getX() - x)));
                if(test > childBox.getLayoutX() && test < childBox.getLayoutX() + childBox.getPrefWidth())
                    startModX = (startModX + (startPoint.getX() - x));    
                 break;                
            case NORTH_WEST:
                
                test = (childBox.getLayoutX() +(childBox.getPrefWidth()/2) - (startModX + (startPoint.getX() - x)));
                if(test > childBox.getLayoutX() && test < childBox.getLayoutX() + childBox.getPrefWidth())
                    startModX = (startModX + (startPoint.getX() - x));                    
                break;
            default:
                break;
        }
            
        updateComplexLine();
    }
    

    
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        
    }
    
    @Override
    public DesignerState getStartingState() {
        return null;
    }
    
    public PositionState getPosition() {
        return parentPosition;
    }
    
    public DraggableUMLBox getChildBox() {
        return childBox;
    }
    
    public DraggableUMLBox getParentBox() {
        return parentBox;
    }
    
    public void updateComplexLine() {
        if(!isAuto)
            lineController.updateNonAutoLine(this);
        else
            lineController.updateLine(this);

    } 
    
    public void setNodeOrientation() {
        
        if((parentBox.getLayoutY() < (childBox.getLayoutY() + childBox.getPrefHeight()/2)
                    && (parentBox.getLayoutY() + parentBox.getPrefHeight() > (childBox.getLayoutY() + childBox.getPrefHeight()/2)))){
            if(childBox.getLayoutX() < parentBox.getLayoutX()) {
                parentPosition = WEST;
            }
            else if(childBox.getLayoutX() > parentBox.getLayoutX()) {
                parentPosition = EAST;
            }
        }
        else if((childBox.getLayoutY() + childBox.getPrefHeight()) > parentBox.getLayoutY() + (parentBox.getPrefHeight())) {
         
            
            // if the parent is within the y value of the center point of the child, its is in the northern region
            if(parentBox.getLayoutX() < (childBox.getLayoutX() + childBox.getPrefWidth()/2)
                    && (parentBox.getLayoutX() + parentBox.getPrefWidth() > (childBox.getLayoutX() + childBox.getPrefWidth()/2))){
                parentPosition = NORTH;
            }            
            else if(childBox.getLayoutX() < parentBox.getLayoutX()) {
                parentPosition = NORTH_EAST;
            }
            else if(childBox.getLayoutX() > parentBox.getLayoutX()){
                parentPosition = NORTH_WEST;
            }
        }
        else if(childBox.getLayoutY() + childBox.getPrefHeight() < (parentBox.getLayoutY() + (parentBox.getPrefHeight()))) {
            
            if(parentBox.getLayoutX() < (childBox.getLayoutX() + childBox.getPrefWidth()/2)
                    && (parentBox.getLayoutX() + parentBox.getPrefWidth() > (childBox.getLayoutX() + childBox.getPrefWidth()/2))){
                parentPosition = SOUTH;
            }                     
            else if(childBox.getLayoutX() < parentBox.getLayoutX()) {
                parentPosition = SOUTH_EAST;
            }
            else if(childBox.getLayoutX() > parentBox.getLayoutX()){
                parentPosition = SOUTH_WEST;
            }
   
        }
        else 
            parentPosition = EQUAL;
    }
    
    public void setArrowType(String type) {
       arrowType = type;
    }
    
    public String getArrowType() {
        return arrowType;
    }
    
    public Line getBaseLine(){
        return baseLine;
    }
    
    public ArrayList<Line> getManualLines() {
        return manualLines;
    }
    
    public ArrayList<DraggableCircle> getManualNodes() {
        return manualNodes;
    }
    
    public void setAutoLines(boolean auto){
        isAuto = auto;
    }

}