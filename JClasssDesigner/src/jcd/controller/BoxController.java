/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.controller;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import jcd.data.DataManager;
import jcd.data.DesignerState;
import static jcd.data.DesignerState.DRAGGING_BOX;
import static jcd.data.DesignerState.DRAGGING_LINE;
import static jcd.data.DesignerState.DRAGGING_NOTHING;
import static jcd.data.DesignerState.SELECTING_BOX;
import static jcd.data.DesignerState.SIZING;
import static jcd.data.DesignerState.SIZING_BOX;
import static jcd.data.DesignerState.SIZING_CORNER;
import static jcd.data.DesignerState.SIZING_HEIGHT;
import static jcd.data.DesignerState.SIZING_WIDTH;
import jcd.data.Draggable;
import jcd.data.DraggableArrow;
import jcd.data.DraggableCircle;
import jcd.data.DraggableLine;
import jcd.data.DraggableUMLBox;
import jcd.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author brobi
 */
public class BoxController {
    
    

    static final int HEIGHT_RESIZE_MARGIN = 5;
    static final int WIDTH_RESIZE_MARGIN = 5;    
    
    AppTemplate app;
    DataManager dataManager;
    HistoryController historyController;
    double historyX;
    double historyY;
 
        
    public BoxController (AppTemplate initApp) {
        app = initApp;
        dataManager = (DataManager)app.getDataComponent();
        historyController = new HistoryController(app);
    }
    
  
    
 
    
    public void setCursor(int x, int y) {
        Scene scene = app.getGUI().getPrimaryScene();
        dataManager = (DataManager)app.getDataComponent();
        DraggableUMLBox box = dataManager.getSelectedBox();
        
        if(box != null) {
            if(x > (box.getLayoutX() + box.getWidth()) - WIDTH_RESIZE_MARGIN && x <=box.getLayoutX() + box.getWidth()
                    && y > ((box.getHeight()+ box.getLayoutY()) - HEIGHT_RESIZE_MARGIN) && y <= box.getHeight()+ box.getLayoutY()) {
                scene.setCursor(Cursor.SE_RESIZE);
            }                  
            else if(x > (box.getLayoutX() + box.getWidth()) - WIDTH_RESIZE_MARGIN && x <=box.getLayoutX() + box.getWidth()) {
                scene.setCursor(Cursor.E_RESIZE);
            }
            else if(y > ((box.getHeight()+ box.getLayoutY()) - HEIGHT_RESIZE_MARGIN) && y <= box.getHeight()+ box.getLayoutY()) {
                scene.setCursor(Cursor.S_RESIZE);
            }
            else {
                scene.setCursor(Cursor.DEFAULT);
            }
        }
    }
    
    public void processCanvasMouseMoved(int x, int y) {
        dataManager = (DataManager)app.getDataComponent();
        Scene scene = app.getGUI().getPrimaryScene();

        if(dataManager.isInState(DesignerState.DRAGGING_BOX))
        {

        }
        else if(dataManager.isInState(DesignerState.SIZING_BOX) || dataManager.isInState(SIZING))
        {     
            setCursor(x ,y);
        }

    }
    
    public void processCanvasMouseExited(int x, int y) {
            dataManager = (DataManager)app.getDataComponent();
            if (dataManager.isInState(DesignerState.DRAGGING_BOX)) {

            }
            else if (dataManager.isInState(DesignerState.SIZING_BOX)) {

            }
    }
    
    public void processCanvasMousePress(int x, int y) {
        dataManager = (DataManager)app.getDataComponent();
        if(dataManager.isInState(DesignerState.SELECTING_BOX))
        {
            Workspace workspace = (Workspace)app.getWorkspaceComponent();            
            Draggable item = dataManager.selectTopItem(x,y);
            Scene scene = app.getGUI().getPrimaryScene();
            if(item == null) {
                dataManager.unselectBox();
                dataManager.unhighlightBox();
                dataManager.unhighlightLine();
                dataManager.unselectLine();
            }
            if(item != null) {
                if(item.getType().equals("BOX")) {
                    DraggableUMLBox box = (DraggableUMLBox)item;
                    if (box != null)
                    {
                        historyX = dataManager.getSelectedBox().getLayoutX();
                        historyY = dataManager.getSelectedBox().getLayoutY();
                        scene.setCursor(Cursor.MOVE);
                        dataManager.setState(DesignerState.DRAGGING_BOX);
                        workspace.loadEditorProperties(box);
                        app.getGUI().updateToolbarControls(false);
                    }
                    else {
                        scene.setCursor(Cursor.DEFAULT);
                        dataManager.setState(DRAGGING_NOTHING);
                        app.getWorkspaceComponent().reloadWorkspace();
                    }
                }
                else if(item.getType().equals("CIRCLE")) {
                    DraggableCircle circle = (DraggableCircle)item;
                    if (circle != null)
                    {
                        historyController.setLineMoveEdit(circle.getParentLine(), circle.getParentLine());
                        scene.setCursor(Cursor.MOVE);
                        dataManager.setState(DesignerState.DRAGGING_LINE);
                        app.getGUI().updateToolbarControls(false);
                        if(circle.getPoint().equals("start")) {
                            circle.getParentLine().setEnd(false);
                            circle.getParentLine().setStart(true);
                        }
                        else if (circle.getPoint().equals("end")) {
                            circle.getParentLine().setEnd(true);
                            circle.getParentLine().setStart(false);
                        }
                     //   dataManager.setMoveEdit(line);
                    }
                    else {
                        scene.setCursor(Cursor.DEFAULT);
                        dataManager.setState(DRAGGING_NOTHING);
                        app.getWorkspaceComponent().reloadWorkspace();
                    }
                }
                else if(item.getType().equals("ARROW")) {
                    DraggableArrow arrow = (DraggableArrow)item;
                    
                    if (arrow != null)
                    {
                        scene.setCursor(Cursor.MOVE);
                        dataManager.setState(DesignerState.DRAGGING_LINE);
                        app.getGUI().updateToolbarControls(false);
                        arrow.getParentLine().setEnd(true);
                        arrow.getParentLine().setStart(false);
                     //   dataManager.setMoveEdit(line);
                    }
                    else {
                        scene.setCursor(Cursor.DEFAULT);
                        dataManager.setState(DRAGGING_NOTHING);
                        app.getWorkspaceComponent().reloadWorkspace();
                    }                    
                }
            }
        }
        else if(dataManager.isInState(SIZING_BOX))
        {
           DraggableUMLBox box = dataManager.getSelectedBox();
 
              if(x > (box.getLayoutX() + box.getWidth()) - WIDTH_RESIZE_MARGIN && x <=box.getLayoutX() + box.getWidth()
                      && y > ((box.getHeight()+ box.getLayoutY()) - HEIGHT_RESIZE_MARGIN) && y <= box.getHeight()+ box.getLayoutY())
              {
                  dataManager.setState(SIZING_CORNER);
                  historyController.setSizedEdit(box);
              }            
              else if(x > (box.getLayoutX() + box.getWidth()) - WIDTH_RESIZE_MARGIN && x <=box.getLayoutX() + box.getWidth())
              {
                  dataManager.setState(SIZING_WIDTH);
                  historyController.setSizedEdit(box);
                  
              }
              else if(y > ((box.getHeight()+ box.getLayoutY()) - HEIGHT_RESIZE_MARGIN) && y <= box.getHeight()+ box.getLayoutY())
              {
                  dataManager.setState(SIZING_HEIGHT);
                  historyController.setSizedEdit(box);
                  
              }
              else{
                  dataManager.setState(SIZING_BOX);
                  historyController.setSizedEdit(box);
                  
              }
        }
        
    }
    
    
    public void processCanvasMouseDragged(int x, int y) {
        dataManager = (DataManager)app.getDataComponent();
        if (dataManager.isInState(SIZING_HEIGHT)
            || dataManager.isInState(SIZING_WIDTH)
            || dataManager.isInState(SIZING_CORNER)) {
            DraggableUMLBox box = dataManager.getSelectedBox();
            if(box != null) {
                if(dataManager.isInState(SIZING_CORNER))
                {
                    box.sizeAll(x, y);
                    for(DraggableLine l: box.getParentLines()) {
                        l.updateComplexLine();
                    }
                    for(DraggableLine l: box.getChildLines()){
                        l.updateComplexLine();
                    }
                }            
                else if(dataManager.isInState(SIZING_WIDTH))
                {
                    box.sizeWidth(x);   
                    for(DraggableLine l: box.getParentLines()) {
                        l.updateComplexLine();
                    }
                    for(DraggableLine l: box.getChildLines()){
                        l.updateComplexLine();
                    }                  
                }
                else if(dataManager.isInState(SIZING_HEIGHT))
                {
                    box.sizeHeight(y);
                    for(DraggableLine l: box.getParentLines()) {
                        l.updateComplexLine();
                    }
                    for(DraggableLine l: box.getChildLines()){
                        l.updateComplexLine();
                    }                    
                }
            }
        }
        else if (dataManager.isInState(DRAGGING_BOX)) {
            Workspace workspace = (Workspace)app.getWorkspaceComponent();

                    DraggableUMLBox box = dataManager.getSelectedBox();
                    box.drag(x, y);
                    workspace.reloadWorkspace();
                    app.getGUI().updateToolbarControls(false); 

        }
        else if(dataManager.isInState(DRAGGING_LINE)){
            Workspace workspace = (Workspace)app.getWorkspaceComponent();
            DraggableLine line = dataManager.getSelectedLine();
                    line.dragPoint(x, y);
        }
    }  
    
    public void processCanvasMouseRelease(int x, int y) {
	dataManager = (DataManager)app.getDataComponent();
	if (dataManager.isInState(SIZING_HEIGHT)
        || dataManager.isInState(SIZING_WIDTH)
        || dataManager.isInState(SIZING_CORNER)) {
	   // dataManager.selectSizedShape();
	    app.getGUI().updateToolbarControls(false);
        dataManager.setState(SIZING_BOX);
	}
	else if (dataManager.isInState(DesignerState.DRAGGING_BOX)) {
        DraggableUMLBox box = dataManager.getSelectedBox();
        if(dataManager.getSnap())
            box.snap(x,y);
        dataManager.setState(SELECTING_BOX);
        historyController.setMovedEdit(historyX, historyY);
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.DEFAULT);
        app.getGUI().updateToolbarControls(false);
    }
    else if(dataManager.isInState(DesignerState.DRAGGING_LINE)){
       // DraggableLine line = dataManager.getSelectedLine();
        dataManager.setState(SELECTING_BOX);    
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.DEFAULT);
        app.getGUI().updateToolbarControls(false);        
    }
	else if (dataManager.isInState(DesignerState.DRAGGING_NOTHING)) {
	    dataManager.setState(SELECTING_BOX);
	}
    }
}
