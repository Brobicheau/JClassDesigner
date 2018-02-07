/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jcd.controller.BoxController;
import jcd.controller.HistoryController;
import jcd.gui.Workspace;
import saf.AppTemplate;
import saf.components.AppDataComponent;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class DataManager implements AppDataComponent {
    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    ObservableList<Node> boxList;
    ArrayList<DraggableUMLBox> boxes;
    ArrayList<DraggableUMLBox> outOfProjectBoxes;
    ObservableList<Node> variableList;
    ObservableList<Node> methodList;
    ArrayList<String> typeList;
    DraggableUMLBox selectedBox;
    DraggableLine selectedLine;
    DraggableUMLBox newBox;
    DesignerState state;
    
    
    Effect highlightedEffect;
    Stack<EditItem> undoList;
    Stack<EditItem> redoList;
    ArrayList<Node> lineList;
    BoxController boxController;
    double sceneScaleX;
    double sceneScaleY;
    double currentScaleFactor;
    double baseScaleFactor;
    boolean gridSnap;
    boolean gridLines;
    int scaleCount;
    String className;
    String packageName;
    Variable selectedVariable;
    HBox selectedVariableBox;
    HBox selectedMethodBox;
    Method selectedMethod;
    HistoryController historyController;

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public DataManager(AppTemplate initApp) throws Exception {
	// KEEP THE APP FOR LATER
        app = initApp;
        historyController = new HistoryController(app);
        undoList = new Stack();
        redoList = new Stack();
        lineList = new ArrayList();
        typeList = new ArrayList();
        boxes = new ArrayList();
        outOfProjectBoxes = new ArrayList();
        currentScaleFactor = .05;
        baseScaleFactor = 1.0;
        scaleCount = 0;
        
    }
    
    public DataManager () throws Exception {
        lineList = new ArrayList();
    }
    
    
    public void setBoxController (BoxController bc) {
        
        boxController = bc;
    }
    public void setBoxes (ObservableList<Node> initBox) {
        boxList = initBox;
    }
    
    public void setVariableList(ObservableList<Node> initVariableList) {
        variableList = initVariableList;
    }
    
    public void setMethodList(ObservableList<Node> initMethodList) {
        methodList = initMethodList;
    }
    
    public void addOutOfProjectBox(DraggableUMLBox box) {
        outOfProjectBoxes.add(box);
    }
    
    public ArrayList<DraggableUMLBox> getOutOfProjectBoxes() {
        return outOfProjectBoxes;
    }

    public void newClass() {
        DraggableUMLBox box = new DraggableUMLBox();
        boxes.add(box);
        newBox = box;
        initNewBox();
    }
    
    public void setBaseScale(double base) {
        baseScaleFactor = base;
    }
    
    public double getBaseScale() {
        return baseScaleFactor;
    }
    
    public void setSnap(boolean snap) {
        gridSnap = snap;
    }
    
    public void setGridLines(boolean lines) {
        gridLines = lines;
    }
    
    public boolean getSnap() {
        return gridSnap;
    }
    
    public boolean getGridLines() {
        return gridLines;
    }
    
    public ArrayList<DraggableUMLBox> getOnlyBoxes() {
        return boxes;
    }
    
    public void initLabel(DraggableUMLBox box){
        
        for(DraggableUMLBox b: boxes){
            if(b.getClassName().equals(box.getClassName())
                && b!=box){
                box.setClassName(box.getClassName() + "1");
                initLabel(box);
            }
        }
    }

    
    public void initNewBox() {
        
//        if (selectedBox != null) {
//	    unhighlightBox(selectedBox);
//	    selectedBox = null;
//	}  
        setSelectedBox(newBox);

       // setHandlers(newBox);
        boxList.add(newBox);
        initLabel(newBox);
        newBox.initLabel();

        
    }
    
    public void addBox(DraggableUMLBox box) {

        boxes.add(box);
        boxList.add(box);
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
        if(app != null){
            initLabel(box);
            box.initLabel();
        }
        

    }
    
    public void setScaleFactor(double newScaleFactor) {
        currentScaleFactor = newScaleFactor;
    }
    
    public double getScaleFactor() {
        return currentScaleFactor;
    }
    
    public void setSelectedLine(DraggableLine line) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        
        if(selectedLine  == null) {
            selectedLine = line;
            highlightLine();
        }
        else if (selectedLine != null){
            unhighlightLine();
            selectedLine = line;
            highlightLine();
        }
    }
    
    public void setSelectedBox(DraggableUMLBox box) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        
       if(selectedBox == null)
       {
           selectedBox = box;
           highlightBox();   
       }
       else if(selectedBox != null)
       {
         unhighlightBox();
         selectedBox = box;
         highlightBox();
       }
       
       if(selectedBox != null)
            workspace.loadEditorProperties(box);
       
    }
    public void unselectBox() {
        if(selectedBox != null)
        {
            unhighlightBox();
             selectedBox = null;
        }
    }
    public void highlightBox () {
        if(selectedBox != null) {
            DropShadow dp = new DropShadow();
            dp.setColor(Color.YELLOW);
            selectedBox.setEffect(dp);
        }
        
    }
    
    public void highlightLine() {
        DropShadow dp = new DropShadow();
        dp.setColor(Color.YELLOW);
        selectedLine.setEffect(dp);       
        selectedLine.getBaseNode().setEffect(dp);
        selectedLine.getArrow().setEffect(dp);
    }
    public void unhighlightLine() {
        if(selectedLine != null) {
         selectedLine.setEffect(null);
         selectedLine.getBaseNode().setEffect(null);
         selectedLine.getArrow().setEffect(null);
        }
    }
    
    public void unselectLine() {
        if(selectedLine != null)
            selectedLine = null;
    }
    
    public void unhighlightBox() {
        if(selectedBox != null) 
            selectedBox.setEffect(null);
    }
    
    public ObservableList<Node> getBoxes() {
        return boxList;
    }
    
    public DraggableUMLBox getNewBox() {
        return newBox;
    }
    
    public DraggableUMLBox getSelectedBox() {
        return selectedBox;
    }
    
    public void setState(DesignerState initState) {
        state = initState;
    }
    
    public DesignerState getState () {
        return state;
    }
    
    public void editClassName (String newName) {
        String oldName = selectedBox.getClassName();
        historyController.setClassNameEdit(oldName, newName);
        selectedBox.setClassName(newName);
    }
    
    public void editPackageName(String newName) {
        historyController.setPackageNameEdit(selectedBox);
        selectedBox.setPackageName(newName);
    }
    public boolean isInState(DesignerState test) {
        return state == test;
        
    }
    

    

    
  public Draggable selectTopItem(int x, int y) {
	Draggable item = getTopItem(x, y);
    if(item != null){
        if(item.getType().equals("BOX")) {
            DraggableUMLBox box = (DraggableUMLBox)item;
            if (box == selectedBox)
                return box;
            if (selectedBox != null) {
                unhighlightBox();
            }
            if (box != null) {
                setSelectedBox(box);
                Workspace workspace = (Workspace)app.getWorkspaceComponent();
                workspace.loadSelectedBoxSettings(box);
            }
        }
        else if(item.getType().equals("CIRCLE")) {
            DraggableCircle circle = (DraggableCircle)item;
           
            if (circle.getParentLine() == selectedLine)
                return circle;
            if (selectedLine != null) {
                unhighlightLine();
            }
            if (circle != null) {
                setSelectedLine(circle.getParentLine());
                Workspace workspace = (Workspace)app.getWorkspaceComponent();
            }            
        }
        else if(item.getType().equals("ARROW")) {
            DraggableArrow arrow = (DraggableArrow)item;
            
            if(arrow.getParentLine() == selectedLine)
                return arrow;
            
            if(selectedLine != null) {
                unhighlightLine();
            }
            if(arrow != null) {
                setSelectedLine(arrow.getParentLine());
                
            }
        }
    }
	
    return item;
	
    }


   public DraggableLine getSelectedLine() {
       return selectedLine;
       
   }
    public Draggable getTopItem(int x, int y) {
        for (int i = boxList.size() - 1; i >= 0; i--) {
            Draggable item = (Draggable)boxList.get(i);
            if(item.getType().equals("BOX")) {
                DraggableUMLBox box = (DraggableUMLBox)boxList.get(i);
                if(box.getBoundsInParent().contains(x, y)){
                    return item;
                }
            }
            else if(item.getType().equals("CIRCLE")){
                DraggableCircle circle = (DraggableCircle)boxList.get(i);             
                if(circle.getBoundsInParent().contains(x, y)){
                    return item;
                }       
            }
            else if (item.getType().equals("ARROW")) {
                DraggableArrow arrow = (DraggableArrow)boxList.get(i);
               // if(arrow.contains(x, y));
                //    return item;
            }
        }
        return null;
    }    
    
     public ArrayList<Node> getLines() {
        return lineList;
    }
     
     public void setLines(ArrayList<Node> lines) {
         lineList = lines;
     }
    
    public void addLine(DraggableLine line) {
        lineList.add(line);
    }
    
    public void addUndo(EditItem itemToAdd) {
        undoList.push(itemToAdd);
    }
    

    
    public void setSizedEdit(DraggableUMLBox box) {
        historyController.setSizedEdit(box);
    }
    
    public void setSelectedVariable(Variable v) {
            selectedVariable = v;
            highlightVariable();
        
    }
    
    public void setSelectedMethod(Method m) {
        selectedMethod = m;
        highlightMethod();
    }
    
    public void highlightMethod() {
        if(selectedMethodBox != null)
            selectedMethodBox.setEffect(null);
        if(methodList != null) {
            for(Node n: methodList) {
                HBox method = (HBox)n;
                Node segNode = method.getChildren().get(0);
                VBox seg = (VBox)segNode;
                Node vNode = seg.getChildren().get(0);
                Label m = (Label)vNode;
                String methodName = m.getText();
                if(methodName.equals(selectedMethod.getName()))
                    selectedMethodBox = method;
            } 
        }
        DropShadow dp = new DropShadow();
        dp.setColor(Color.YELLOW);
        selectedMethodBox.setEffect(dp);
    }
    public void highlightVariable() {
        if(selectedVariableBox != null)
            selectedVariableBox.setEffect(null);
        if(variableList != null)
            for(Node n: variableList) {
                HBox variable = (HBox)n;
                Node segNode = variable.getChildren().get(0);
                VBox seg = (VBox)segNode;
                Node vNode = seg.getChildren().get(0);
                Label v = (Label)vNode;
                String variableName = v.getText();
                if(variableName.equals(selectedVariable.getName()))
                    selectedVariableBox = variable;   
            }
        DropShadow dp = new DropShadow();
        dp.setColor(Color.YELLOW);
        selectedVariableBox.setEffect(dp);
        
    }
    
    public void removeVariable() {
        selectedBox.removeVariable(selectedVariable);
        variableList.remove(selectedVariable);
        selectedVariableBox = null;
        selectedBox.update();
    }
    
    public void removeMethod() {
        selectedBox.removeMethod(selectedMethod);
        methodList.remove(selectedMethod);
        selectedMethodBox = null;
        selectedBox.update();
    }
    
    public HBox getSelectedVariableBox() {
        return selectedVariableBox;
    }
    
    public HBox getSelectedMethodBox() {
        return selectedMethodBox;
    }
    
    public ObservableList<Node> getVariableList() {
        return variableList;
    }
    
    public ObservableList<Node> getMethodList() {
        return methodList;
    }
    
    public void setAddLineEdit(DraggableLine line) {
         boolean added = true;
        //historyController.setAddOrRemoveLineEdit(line, added);
    }
    
    public Variable getSelectedVariable() {
        return selectedVariable;
    }
    public void addRedo(EditItem newItem ){
        redoList.push(newItem);
    }
    
    public EditItem getRedo() {
        return redoList.pop();
    }
    
    public EditItem getUndo() {
        return undoList.pop();
    }
    public ArrayList<String> getTypeList() {
        return typeList;
    }
    
    public void updateLines() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.updateLines();
    }
    
    public Method getSelectedMethod() {
        return selectedMethod;
    }
    /**
     * This function clears out the HTML tree and reloads it with the minimal
     * tags, like html, head, and body such that the user can begin editing a
     * page.
     */
    @Override
    public void reset() {
    boxList.clear();
    boxes.clear();
    outOfProjectBoxes.clear();
    variableList.clear();
    methodList.clear();
    typeList.clear();
    selectedBox = null;
    selectedLine = null;
    newBox = null;
    state = null;
    
    
    highlightedEffect = null;
    lineList.clear();
    sceneScaleX = 0;
    sceneScaleY= 0;
    currentScaleFactor = 0;
    baseScaleFactor = 0;
    gridSnap = false;
    gridLines = false;
    scaleCount = 0;
    className = null;
    packageName = null;
    selectedVariable= null;
    selectedVariableBox = null;
    selectedMethodBox = null;
    selectedMethod = null;
    
        historyController = new HistoryController(app);

        currentScaleFactor = .05;
        baseScaleFactor = 1.0;
        scaleCount = 0;    
    
    }
    
    public void incrementScaleCount() {
        scaleCount++;
    }
    
    public void decrementScaleCount() {
        scaleCount--;
    }
    
    public int getScaleCount(){ 
        return scaleCount;
    }
    
    public Workspace getWorkspace() {
        return (Workspace)app.getWorkspaceComponent();
    }
}
