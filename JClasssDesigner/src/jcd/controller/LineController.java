/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.controller;

import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import jcd.data.DataManager;
import jcd.data.DraggableCircle;
import jcd.data.DraggableLine;
import jcd.data.DraggableUMLBox;
import static jcd.data.PositionState.EAST;
import static jcd.data.PositionState.EQUAL;
import static jcd.data.PositionState.NORTH;
import static jcd.data.PositionState.NORTH_EAST;
import static jcd.data.PositionState.NORTH_WEST;
import static jcd.data.PositionState.SOUTH;
import static jcd.data.PositionState.SOUTH_EAST;
import static jcd.data.PositionState.SOUTH_WEST;
import static jcd.data.PositionState.WEST;
import saf.AppTemplate;

/**
 *
 * @author brobi
 */
public class LineController {
    AppTemplate app;
    DataManager dataManager;
    
    public LineController(DataManager data) {
        dataManager = data;
    }
    
    public void addNode(DraggableCircle c) {
                dataManager.getBoxes().add(c);
    }
    
    
    public void updateLine(DraggableLine line) {
        MoveTo startPoint = line.getStartPoint();
        LineTo startLine = line.getStartLine();
        MoveTo endPoint = line.getEndPoint();
        LineTo endLine = line.getEndLine();
        MoveTo midPoint = line.getMidPoint();
        LineTo midLine = line.getMidLine();     
        double startModX = line.getStartModX();
        double startModY = line.getStartModY(); 
        double endModX = line.getEndModX();
        double endModY = line.getEndModY();    
        ArrayList<DraggableCircle> midNodes = line.getMidNodes();
        ArrayList<LineTo> midLineArray = line.getMidLineArray();
        ArrayList<MoveTo> midPointArray = line.getMidPointArray();
        
        DraggableUMLBox childBox = line.getChildBox();
        DraggableUMLBox parentBox = line.getParentBox();

        if(null != line.getPosition()) switch (line.getPosition()) {
           
            case NORTH_WEST:
       
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) -  startModX);
                startPoint.setY(childBox.getLayoutY());
                startLine.setX(startPoint.getX());
                startLine.setY(parentBox.getLayoutY()+ (parentBox.getPrefHeight()/2) - endModY) ;
                
                endPoint.setX(startLine.getX());
                endPoint.setY(startLine.getY());
                endLine.setX(parentBox.getLayoutX()+ parentBox.getPrefWidth());
                endLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY );

                midPoint.setX(startLine.getX());
                midPoint.setY(startLine.getY()); 
                midLine.setX(startLine.getX());
                midLine.setY(startLine.getY());   
                
                line.setLineBase();
                line.setArrowHead();
                
                break;               
            case NORTH_EAST:
                
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) -  startModX);
                startPoint.setY(childBox.getLayoutY());
                startLine.setX(startPoint.getX());
                startLine.setY(parentBox.getLayoutY()+ (parentBox.getPrefHeight()/2) - endModY) ;
                
                endPoint.setX(startLine.getX());
                endPoint.setY(startLine.getY());
                endLine.setX(parentBox.getLayoutX());
                endLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY );
                
          
                
                //make second segment
                midPoint.setX(startLine.getX());
                midPoint.setY(startLine.getY()); 
                midLine.setX(startLine.getX());
                midLine.setY(startLine.getY());    
                
                            line.setLineBase();
                            line.setArrowHead();
                break;
            case NORTH:
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) - startModX);
                startPoint.setY(childBox.getLayoutY());
                startLine.setX(startPoint.getX());
                startLine.setY(childBox.getLayoutY() -((childBox.getLayoutY())-(parentBox.getLayoutY() + parentBox.getHeight() ))/2) ;
          
                endPoint.setX(parentBox.getLayoutX() + (parentBox.getPrefWidth()/2) -endModX);
                endPoint.setY(startLine.getY());
                endLine.setX(endPoint.getX());
                endLine.setY(parentBox.getLayoutY() + parentBox.getPrefHeight());
                
                //make second segment
                midPoint.setX(startLine.getX());
                midPoint.setY(startLine.getY()); 
                midLine.setX(endPoint.getX());
                midLine.setY(endPoint.getY());    

                            line.setLineBase();
                            line.setArrowHead();              
                
                break;
            case SOUTH_WEST:
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) - startModX);
                startPoint.setY(childBox.getLayoutY()+ childBox.getPrefHeight() );
                startLine.setX(startPoint.getX());
                startLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY) ;
                
                endPoint.setX(startLine.getX());
                endPoint.setY(startLine.getY());
                endLine.setX(parentBox.getLayoutX() + parentBox.getPrefWidth());                
                endLine.setY(startLine.getY());
                
                //make second segment
                midPoint.setX(startLine.getX());
                midPoint.setY(startLine.getY()); 
                midLine.setX(startLine.getX());
                midLine.setY(startLine.getY());      
                
                            line.setLineBase();
                            line.setArrowHead();
                
                break;
            case SOUTH_EAST:
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) - startModX);
                startPoint.setY(childBox.getLayoutY()+ (childBox.getPrefHeight()));
                startLine.setX(startPoint.getX());
                startLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY) ;
                
                endPoint.setX(startLine.getX());
                endPoint.setY(startLine.getY());
                endLine.setX(parentBox.getLayoutX());
                endLine.setY(startLine.getY());
                
                //make second segment
                midPoint.setX(startLine.getX());
                midPoint.setY(startLine.getY()); 
                midLine.setX(startLine.getX());
                midLine.setY(startLine.getY());     
                
                            line.setLineBase();
                            line.setArrowHead();
                break;
            case SOUTH:
                
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) - startModX);
                startPoint.setY(childBox.getLayoutY()+ childBox.getPrefHeight() );
                startLine.setX(startPoint.getX());
                startLine.setY(parentBox.getLayoutY() - ((parentBox.getLayoutY())
                    -(childBox.getLayoutY() + childBox.getHeight() ))/2);
                
                endPoint.setX(parentBox.getLayoutX() + (parentBox.getPrefWidth()/2) - endModX);
                endPoint.setY(startLine.getY());
                endLine.setX(endPoint.getX());
                endLine.setY(parentBox.getLayoutY());
                
                //make second segment
                midPoint.setX(startLine.getX());
                midPoint.setY(startLine.getY()); 
                midLine.setX(endPoint.getX());
                midLine.setY(endPoint.getY());   
                
                            line.setLineBase();
                            line.setArrowHead();
                break;
            case WEST:


               startPoint.setX(childBox.getLayoutX() + childBox.getPrefWidth());
               startPoint.setY(childBox.getLayoutY()+  (childBox.getPrefHeight()/2) - startModY);               
               startLine.setX(childBox.getLayoutX() - ((childBox.getLayoutX())
                   -(parentBox.getLayoutX() + parentBox.getPrefWidth()))/2);
               startLine.setY(startPoint.getY());

               endPoint.setX(startLine.getX());
               endPoint.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY);
               endLine.setX(parentBox.getLayoutX());
               endLine.setY(endPoint.getY());                   


               //make second segment
                midPoint.setX(startLine.getX());
                midPoint.setY(startLine.getY()); 
                midLine.setX(endPoint.getX());
                midLine.setY(endPoint.getY());    
                
                            line.setLineBase();
                            line.setArrowHead();
                break;
            case EAST:

                startPoint.setX(childBox.getLayoutX());
                startPoint.setY(childBox.getLayoutY() + (childBox.getPrefHeight()/2) - startModY);
                startLine.setX(parentBox.getLayoutX() - ((parentBox.getLayoutX())
                    -(childBox.getLayoutX() + childBox.getPrefWidth()))/2);
                startLine.setY(startPoint.getY());

               endPoint.setX(startLine.getX());
               endPoint.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY);
               endLine.setX(parentBox.getLayoutX() + parentBox.getPrefWidth());
               endLine.setY(endPoint.getY());                      


                //make second segment
                midPoint.setX(startLine.getX());
                midPoint.setY(startLine.getY()); 
                midLine.setX(endPoint.getX());
                midLine.setY(endPoint.getY());    
                
                            line.setLineBase();
                            line.setArrowHead();
                break;
            case EQUAL:
                
                
                break;
            default:
                break;
                
        }   
    }
    
    public void updateNonAutoLine(DraggableLine line) {
        DraggableUMLBox childBox = line.getChildBox();
        DraggableUMLBox parentBox = line.getParentBox();
        ArrayList<DraggableCircle> manualNodes = line.getManualNodes();
        MoveTo startPoint = line.getStartPoint();
        LineTo startLine = line.getStartLine();
        MoveTo endPoint = line.getEndPoint();
        LineTo endLine = line.getEndLine();
        MoveTo midPoint = line.getMidPoint();
        LineTo midLine = line.getMidLine();     
        double startModX = line.getStartModX();
        double startModY = line.getStartModY(); 
        double endModX = line.getEndModX();
        double endModY = line.getEndModY();    
        ArrayList<DraggableCircle> midNodes = line.getMidNodes();
        ArrayList<LineTo> midLineArray = line.getMidLineArray();
        ArrayList<MoveTo> midPointArray = line.getMidPointArray();
                    
        
         if(null != line.getPosition()) switch (line.getPosition()) {
           
            case NORTH_WEST:
       
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) -  startModX);
                startPoint.setY(childBox.getLayoutY());
                startLine.setX(parentBox.getLayoutX()+ parentBox.getPrefWidth());
                startLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY ) ;
                
        
                
                line.setLineBase();
                line.setArrowHead();
                
                break;               
            case NORTH_EAST:
                
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) -  startModX);
                startPoint.setY(childBox.getLayoutY());
                startLine.setX(parentBox.getLayoutX());
                startLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY ) ;
 
                
                line.setLineBase();
                line.setArrowHead();  
                
                          
                break;
            case NORTH:
      
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) - startModX);
                startPoint.setY(childBox.getLayoutY());
                startLine.setX(parentBox.getLayoutX() + (parentBox.getPrefWidth()/2) -endModX);
                startLine.setY(parentBox.getLayoutY() + parentBox.getPrefHeight()) ;
          

                
                line.setLineBase();
                line.setArrowHead();
                                  
                
                break;
            case SOUTH_WEST:
                
                
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) - startModX);
                startPoint.setY(childBox.getLayoutY()+ childBox.getPrefHeight() );
                startLine.setX(parentBox.getLayoutX() + parentBox.getPrefWidth());
                startLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY) ;
                

                            line.setLineBase();
                            line.setArrowHead();
                
                break;
            case SOUTH_EAST:
                
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) - startModX);
                startPoint.setY(childBox.getLayoutY()+ (childBox.getPrefHeight()));
                startLine.setX(parentBox.getLayoutX());
                startLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY) ;
                
      
                            line.setLineBase();
                            line.setArrowHead();
                break;
            case SOUTH:
                
                startPoint.setX(childBox.getLayoutX() + (childBox.getPrefWidth()/2) - startModX);
                startPoint.setY(childBox.getLayoutY()+ childBox.getPrefHeight() );
                startLine.setX(parentBox.getLayoutX() + (parentBox.getPrefWidth()/2) - endModX);
                startLine.setY(parentBox.getLayoutY());
                
                endPoint.setX(parentBox.getLayoutX() + (parentBox.getPrefWidth()/2) - endModX);
                endPoint.setY(startLine.getY());
                endLine.setX(endPoint.getX());
                endLine.setY(parentBox.getLayoutY());
                              
     
                
                            line.setLineBase();
                            line.setArrowHead();
                break;
            case WEST:

               startPoint.setX(childBox.getLayoutX() + childBox.getPrefWidth());
               startPoint.setY(childBox.getLayoutY()+  (childBox.getPrefHeight()/2) - startModY);               
               startLine.setX(parentBox.getLayoutX());
               startLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY);

               endPoint.setX(startLine.getX());
               endPoint.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY);
               endLine.setX(parentBox.getLayoutX());
               endLine.setY(endPoint.getY());      
                
                
                line.setLineBase();
                line.setArrowHead();  
                
                            line.setLineBase();
                            line.setArrowHead();
                break;
            case EAST:

                startPoint.setX(childBox.getLayoutX());
                startPoint.setY(childBox.getLayoutY() + (childBox.getPrefHeight()/2) - startModY);
                startLine.setX(parentBox.getLayoutX() + parentBox.getPrefWidth());
                startLine.setY(parentBox.getLayoutY() + (parentBox.getPrefHeight()/2) - endModY);

                            line.setLineBase();
                            line.setArrowHead();
                break;
            case EQUAL:
                
                
                break;
            default:
                break;
                
        }          
        
        
        
    }

                    
//    public void makeBaseDraggableNode(DraggableLine line) {
//        ArrayList<DraggableCircle> midNodes = line.getMidNodes();
//        ArrayList<MoveTo> midPointArray = line.getMidPointArray();
//        ArrayList<LineTo> midLineArray = line.getMidLineArray();
//       
//            MoveTo endPoint = line.getEndPoint();
//            LineTo endLine = line.getEndLine();
//            midPointArray.add(endPoint);
//            midLineArray.add(endLine);
//            DraggableCircle circ = new DraggableCircle(line);
//            line.setBaseMidNode(circ);
//            circ.setRadius(5.0);
//            circ.setLayoutX(endPoint.getX());
//            circ.setLayoutY(endPoint.getY());
//            circ.setPoint("midBase");
//            circ.setNodePoint(endPoint);
//            circ.setNodeLine(endLine);
//            circ.setOnMouseClicked(e->{
//            if(e.getClickCount() == 2)
//                if(midNodes.size() >= 1)
//                    addDraggableNode(circ); 
//            });
//            midNodes.add(circ);
//            addNode(circ); 
//                    
//        
//    }
//    
//        public void setNodes(DraggableCircle node) {
//            if(node.getPreviousNode() != null) {
//            node.getNodeLine().setX(node.getPreviousNode().getNodePoint().getX());
//            node.getNodeLine().setY(node.getPreviousNode().getNodePoint().getY());
//            setNodes(node.getPreviousNode());
//            }
//        }
//    
//        public void makeSecondDraggableNode() {
//            
//        }
//        
//        public void addDraggableNode(DraggableCircle c) {
//        ArrayList<DraggableCircle> midNodes = c.getParentLine().getMidNodes();
//        ArrayList<MoveTo> midPointArray = c.getParentLine().getMidPointArray();
//        ArrayList<LineTo> midLineArray = c.getParentLine().getMidLineArray();
//                MoveTo oldPoint = c.getNodePoint();
//                LineTo oldLine = c.getNodeLine();
//                MoveTo newPoint = new MoveTo();
//                LineTo newLine = new LineTo();
//                
//                newPoint.setX(oldPoint.getX());
//                newPoint.setY(oldPoint.getY());
//                
//                oldPoint.setX(oldPoint.getX() -(oldPoint.getX() - oldLine.getX())/2);
//                oldPoint.setY(oldPoint.getY()-(oldPoint.getY() - oldLine.getY())/2);
//                c.setLayoutX(oldPoint.getX());
//                c.setLayoutY(oldPoint.getY());
//                
//                c.getParentLine().getElements().add(newLine);
//                c.getParentLine().getElements().add(newPoint);
//
//                
//                newLine.setX(oldPoint.getX());
//                newLine.setY(oldPoint.getY());
//                midPointArray.add(newPoint);
//                midLineArray.add(newLine);
//                
//                DraggableCircle newNode = new DraggableCircle(c.getParentLine());
//                newNode.setOnMouseClicked(e->{
//                    addDraggableNode(newNode);
//                });
//                newNode.setRadius(5.0);
//                newNode.setLayoutX(newPoint.getX());
//                newNode.setLayoutY(newPoint.getY());
//                newNode.setNodeLine(newLine);
//                newNode.setNodePoint(newPoint);
//                addNode(newNode);
//                midNodes.add(newNode);
//                newNode.setPreviousNode(newNode);
//                c.getParentLine().setBaseMidNode(newNode);
//                
//                        
//        }
//        
 
}
