/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jcd.data.DataManager;
import jcd.data.DesignerState;
import static jcd.data.DesignerState.DRAGGING_NOTHING;
import jcd.data.DraggableLine;
import jcd.data.DraggableUMLBox;
import jcd.data.Method;
import jcd.data.Variable;
import jcd.gui.MethodEditPopupSingleton;
import jcd.gui.ParentEditPopupSingleton;
import jcd.gui.VariableEditPopupSingleton;
import jcd.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author brobi
 */
public class JClassEditController {
    AppTemplate app;
    DataManager dataManager;
    LineController lineController;
    HistoryController historyController;
    ParentEditPopupSingleton parentEditor;
    static final String VAR_MET_SEG_STYLE = "var_met_seg_style";
    static final String INTERFACE = "INTERFACE";
    static final String USES = "USES";
    static final String AGGREGATE = "AGGREGATE";

    
    public JClassEditController(AppTemplate initApp) {
            app = initApp;

            dataManager = (DataManager)app.getDataComponent();
            lineController = new LineController(dataManager);
            historyController = new HistoryController(app);
            parentEditor = ParentEditPopupSingleton.getSingleton();
            parentEditor.init(app.getStage());         
        }
    
    public void processAddClassButton() {
        dataManager = (DataManager)app.getDataComponent();
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        dataManager.setState(DRAGGING_NOTHING);
        
        DraggableUMLBox newBox = new DraggableUMLBox();
        historyController.setAddClassEdit(newBox);
       
        workspace.getParentList().add(newBox);
        
        dataManager.addBox(newBox);
        dataManager.setSelectedBox(newBox);
        
        workspace.reloadWorkspace();
        
    }
    
    public void processAddInterfaceButton() {
        dataManager = (DataManager)app.getDataComponent();
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        dataManager.setState(DRAGGING_NOTHING);
        
        DraggableUMLBox newBox = new DraggableUMLBox();
        newBox.setInterface(true);
        
        workspace.getParentList().add(newBox);
        historyController.setAddClassEdit(newBox);
        
        
        dataManager.addBox(newBox);
        dataManager.setSelectedBox(newBox);
        workspace.reloadWorkspace();        
        
        
    }
    
    public void processSelectButton() {
        Scene scene = app.getGUI().getPrimaryScene();
	scene.setCursor(Cursor.DEFAULT);
	
	// CHANGE THE STATE
	dataManager.setState(DesignerState.SELECTING_BOX);	
        
	
	// ENABLE/DISABLE THE PROPER BUTTONS
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
        
    }
    
    public void processEditClassName(String newName) {
        dataManager.editClassName(newName);
        
        
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    public void processEditPackageName(String newName) {
        dataManager.editPackageName(newName);    
    }
    
    public void processRemoveClassButtonRequest(){
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        
        ObservableList<Node> canvas = dataManager.getBoxes();
        DraggableUMLBox selectedBox = dataManager.getSelectedBox();
        historyController.setRemoveClassEdit(selectedBox);
        ArrayList<DraggableLine> childLines = selectedBox.getChildLines();
        ArrayList<DraggableLine> parentLines = selectedBox.getParentLines();
        ArrayList<DraggableLine> toDelete = new ArrayList();
        for(DraggableLine l : childLines){          
            toDelete.add(l);
        }
        for(DraggableLine l : parentLines){
            toDelete.add(l);
        }
        for(DraggableLine l: toDelete) {
            canvas.remove(l.getArrow());
            canvas.remove(l.getArrowNode());
            canvas.remove(l.getBaseNode());
            canvas.remove(l);  
            l.getParentBox().getParentList().remove(selectedBox);
            l.getChildBox().getParentList().remove(selectedBox);
            l.getChildBox().getChildLines().remove(l);
            l.getChildBox().getParentLines().remove(l);
            l.getParentBox().getChildLines().remove(l);
            l.getParentBox().getParentLines().remove(l);
            
        }
        workspace.getParentList().remove(selectedBox);
        
        selectedBox.getChildLines().clear();
        selectedBox.getParentLines().clear();
        selectedBox.getParentList().clear();
        canvas.remove(selectedBox);
        
        
        if(selectedBox.getIsPartOfProject())
            dataManager.getOnlyBoxes().remove(selectedBox);
        
        dataManager.setSelectedBox(null);
        
       
        
        workspace.reloadWorkspace();
        
    }
    
    public void processResizeButton() {
        dataManager.setState(DesignerState.SIZING_BOX);
     
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();        
    }
    
    public void setMethodList(DraggableUMLBox box) {
            MethodEditPopupSingleton methodEditor = MethodEditPopupSingleton.getSingleton();
            ObservableList<Method> methods = box.getMethod();
            ObservableList<Node> methodList = dataManager.getMethodList();
            methodList.clear();
            HBox newMethSeg;
            VBox nameSeg;
            VBox accessorSeg;
            VBox typeSeg;
            VBox staticSeg;
            VBox abstractSeg;
            ScrollPane argScroll;
            HBox argsSeg;
            HashMap<String, String> args;
            Collection<String> keys;
            for(Method m: methods) {
                checkForClassInMethod(m);
                checkForClassInArgs(m);
                args = m.getArgs();
                keys = args.keySet();
                argScroll = new ScrollPane();
                newMethSeg = new HBox();
                nameSeg = new VBox();
                accessorSeg = new VBox();
                typeSeg = new VBox();
                staticSeg = new VBox();
                abstractSeg = new VBox();
                argsSeg = new HBox();
                nameSeg.getChildren().add(new Label(m.getName()));
                accessorSeg.getChildren().add(new Label(m.getAccessorType()));
                if(m.getReturnType().equals(""))
                    typeSeg.getChildren().add(new Label("void"));
                else
                    typeSeg.getChildren().add(new Label(m.getReturnType()));
                if(m.getStatic())
                    staticSeg.getChildren().add(new Label("Static"));
                else
                    staticSeg.getChildren().add(new Label("Not Static"));
                if(m.getAbstract())
                    abstractSeg.getChildren().add(new Label("Abstract"));
                else
                    abstractSeg.getChildren().add(new Label("Not Abstract"));
                for(String k: keys) {
                    HBox newArg = new HBox(90);
                    newArg.getChildren().add(new Label(k +" " + args.get(k)));
                    newArg.getStyleClass().add(VAR_MET_SEG_STYLE);
                    argsSeg.getChildren().add(newArg);
                    
                }
                nameSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
                typeSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
                staticSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
                accessorSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
                abstractSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
                argsSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
                argScroll.setContent(argsSeg);
                newMethSeg.getChildren().add(nameSeg);
                newMethSeg.getChildren().add(accessorSeg);
                newMethSeg.getChildren().add(typeSeg);
                newMethSeg.getChildren().add(staticSeg);
                newMethSeg.getChildren().add(abstractSeg);
                newMethSeg.getChildren().add(argScroll);
                
                newMethSeg.setOnMouseClicked(e-> {
                    if(e.getClickCount() == 2) {
                        methodEditor.show(m);
                        Method oldMethod = new Method();
                        oldMethod.setAbstract(m.getAbstract());
                        oldMethod.setStatic(m.getStatic());
                        oldMethod.setName(m.getName());
                        oldMethod.setAccType(m.getAccessorType());
                        oldMethod.setArgs(m.getArgs());
                        oldMethod.setReturnType(m.getReturnType());
                        m.setName(methodEditor.getName());
                        m.setAccType(methodEditor.getAccessor());
                        m.setReturnType(methodEditor.getType());
                        m.setStatic(methodEditor.getStatic());
                        m.setAbstract(methodEditor.getAbstract());
                        m.setArgs(methodEditor.getArgs());
                        Method newMethod = m;
                        dataManager.getSelectedBox().checkIfAbstract();
                        historyController.setMethodDataEdit(box, oldMethod, newMethod);
                        setMethodList(box);
                    }
                    else  {
                        processMethodSingleClick(m);
                    }
                });
                
                methodList.add(newMethSeg);
            }
            updateLines();            
            box.update();      
    }
    
    public void setVariableList(DraggableUMLBox box) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        boolean needsClass;
        VariableEditPopupSingleton variableEditor = VariableEditPopupSingleton.getSingleton();
        ObservableList<Variable> variables = box.getVariables();
        ObservableList<Node> variableList = dataManager.getVariableList();
        variableList.clear();
        HBox newVarSeg;
        VBox nameSeg;
        VBox accessorSeg;
        VBox typeSeg;
        VBox staticSeg;
        for(Variable v: variables) {
            checkForClassInVariable(v);
            newVarSeg = new HBox();
            nameSeg = new VBox();
            accessorSeg = new VBox();
            typeSeg = new VBox();
            staticSeg = new VBox();
            nameSeg.getChildren().add(new Label(v.getName()));
            accessorSeg.getChildren().add(new Label(v.getAccessorType()));
            if(v.getReturnType().equals(""))
                typeSeg.getChildren().add(new Label("void"));
            else
                typeSeg.getChildren().add(new Label(v.getReturnType()));
            if(v.getStatic())
                staticSeg.getChildren().add(new Label("Static"));
            else
                staticSeg.getChildren().add(new Label("Not Static"));
            nameSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
            typeSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
            staticSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
            accessorSeg.getStyleClass().add(VAR_MET_SEG_STYLE);
            newVarSeg.getChildren().add(nameSeg);
            newVarSeg.getChildren().add(accessorSeg);
            newVarSeg.getChildren().add(typeSeg);
            newVarSeg.getChildren().add(staticSeg);
            newVarSeg.setOnMouseClicked(e-> {

                if(e.getClickCount() == 2) {
                    variableEditor.show(v);
                    Variable oldVariable = new Variable();
                    oldVariable.setVarName(v.getName());
                    oldVariable.setAccessorType(v.getAccessorType());
                    oldVariable.setReturnType(v.getReturnType());
                    oldVariable.setStatic(v.getStatic());
                    v.setVarName(variableEditor.getName());
                    v.setReturnType(variableEditor.getType());
                    v.setAccessorType(variableEditor.getAccessor());
                    v.setStatic(variableEditor.getStatic());
                    Variable newVariable = new Variable();
                    newVariable = v;
                    setVariableList(box);
                    historyController.setVariableDataEdit(box, oldVariable, newVariable);
                    
                    
                }
                else
                    processVariableSingleClick(v);
            });
            variableList.add(newVarSeg);
        }
        updateLines();
        box.update();         
    }
    
    public void makeOutsideProjectClass(String type) {
        boolean alreadyExists = false;
        ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
        for(DraggableUMLBox b: boxes) {
            if(type.equals(b.getClassName()))
                alreadyExists = true;
        }
        
        if(!alreadyExists
            && !type.equals("int")
            && !type.equals("void")
            && !type.equals("")
            && !type.equals("double")
            && !type.equals("char")
            && !type.equals("boolean")
            && !type.equals("byte")
            && !type.equals("long")
            && !type.equals("short")
            && !type.equals("float")) {
            DraggableUMLBox newBox = new DraggableUMLBox(false);
            newBox.setClassName(type);
            dataManager.addBox(newBox);
            dataManager.addOutOfProjectBox(newBox);
        }
    }
    
    public void processAddVariableButton() {      
        DraggableUMLBox box = dataManager.getSelectedBox();
        historyController.setAddVariableEdit(box, box.addVariable());        
        setVariableList(dataManager.getSelectedBox());       
    }
    
    public void processRemoveVariableButton() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        DraggableUMLBox box = dataManager.getSelectedBox();
        historyController.setRemoveVariableEdit(box, dataManager.getSelectedVariable());
        VBox variablePane = workspace.getVariablePane();
        variablePane.getChildren().remove(dataManager.getSelectedVariableBox());
        dataManager.removeVariable();
    }
    
    public void processRemoveMethodButton() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        DraggableUMLBox box =dataManager.getSelectedBox();
        historyController.setRemoveMethodEdit(box, dataManager.getSelectedMethod());
        VBox methodPane = workspace.getMethodPane();
        methodPane.getChildren().remove(dataManager.getSelectedMethodBox());
        dataManager.removeMethod();
    }
    
    public void processVariableSingleClick(Variable v) {
        dataManager.setSelectedVariable(v);
    }
    
    public void processMethodSingleClick(Method m) {
        dataManager.setSelectedMethod(m);
    }
    
    public void processAddMethodButton() {
        DraggableUMLBox box = dataManager.getSelectedBox();
        historyController.setAddMethodEdit(box, box.addMethod());
        setMethodList(dataManager.getSelectedBox());
    }        
    public void checkForClassInVariable(Variable v) {
        boolean value = false;
        boolean notFound = true;
        ArrayList<String> typeList = dataManager.getTypeList();
        if(v != null)
            if(!typeList.isEmpty())
                for(String s: typeList)  {
                    if(v.getReturnType().equals(s))
                        value = true;   
                }
                if(!value) {
                        typeList.add(v.getReturnType());
                        makeOutsideProjectClass(v.getReturnType());
                }
    }    
    
    public void checkForClassInMethod(Method m) {
        boolean value = false;
        ArrayList<String> typeList = dataManager.getTypeList();
        
            for(String s: typeList) {
                if(m.getReturnType().equals(s))
                    value = true;
            }
            if(!value) {
                typeList.add(m.getReturnType());
                makeOutsideProjectClass(m.getReturnType());
            }
    }    
  
    public void checkForClassInArgs(Method m) {
        boolean value;
        ArrayList<String> typeList = dataManager.getTypeList();
        
        
        HashMap<String, String> args = m.getArgs();
        Collection<String> keys = args.keySet();
        for(String k: keys) {
                           
            value = false;
            for(String s: typeList) {
                if(args.get(k).equals(s))
                    value = true;
                }
            
            if(!value) {
                typeList.add(args.get(k));
                makeOutsideProjectClass(args.get(k));            
            }                       
        }            
    }    
    
    public void processGridSnapToggle(boolean value) {
        dataManager.setSnap(value);
    }
    
    public void processGridLinesToggle(boolean value) {
        dataManager.setGridLines(value);
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    public void processZoomInRequest() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        ObservableList<Node> children = canvas.getChildren();
        int scaleCount = dataManager.getScaleCount();
        if(scaleCount < 5){
            canvas.setScaleX(canvas.getScaleX() + canvas.getScaleX()*.05);
            canvas.setScaleY(canvas.getScaleY() + canvas.getScaleY()*.05);
            dataManager.incrementScaleCount();
        }
    
       

   
        
    }
    
    public void processZoomOutRequest() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        ObservableList<Node> children = canvas.getChildren();
        int scaleCount = dataManager.getScaleCount();
        if(scaleCount > -8) {
            canvas.setScaleX(canvas.getScaleX() - canvas.getScaleX()*.05);
            canvas.setScaleY(canvas.getScaleY() - canvas.getScaleY()*.05);
            dataManager.decrementScaleCount();
        }

   
    }
    
    
  
    
    public void processAddOrRemoveParent(Object newParent) {
        
        //get new parent
        //if the newparent is not part of the selected boxes parent list, add it
        // else remove it.
        String newParentName = newParent.toString();
        DraggableUMLBox selectedBox = dataManager.getSelectedBox();
        ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
        DraggableUMLBox newParentBox = null;
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        for(DraggableUMLBox box: boxes) {
            if(newParentName.equals(box.getClassName()))
                newParentBox = box;
        }
        if(selectedBox.getParentList().contains(newParentBox)) {
            selectedBox.getParentList().remove(newParentBox);
        } else {
            selectedBox.getParentList().add(newParentBox);
        }
        
        updateLines();
        workspace.loadEditorProperties(dataManager.getSelectedBox());
        
    }
    
    public void processUpdateParentsRequest(ListView parentView, ObservableList<DraggableUMLBox> parentList) {
        parentEditor.show(parentList, parentView.getItems(), dataManager.getSelectedBox());
   //     dataManager.getSelectedBox().setParentList(parentEditor.getSelectedParents());
        parentView.getItems().clear();
        parentView.getItems().addAll(parentEditor.getNewParentList());
        updateLines();
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.loadEditorProperties(dataManager.getSelectedBox());
    }
    
    public void updateLines() {
        ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
        ObservableList<Node> canvas = dataManager.getBoxes();
        //get all all boxes
        //get all variable types in each box
        //get all methods in each box
        //check all variable types for a classbox of the same name
        //if there is a line with the same parentBox and childBox dont make it
        //add a line from the current box to the box with the same name as variable type
        //check all method return types for classbox with the same name
        // add lines from all classes that match
        //check all argument types for class boxes with same name
        // add lines from all classes that match
        
        for(DraggableUMLBox c : boxes){
            ObservableList<Variable> variables = c.getVariables();
            ObservableList<Method> methods = c.getMethod();
            boolean hasConnection;
            for(DraggableUMLBox p: boxes) {
                hasConnection = false;
                for(Variable v: variables){
                    if(v.getReturnType().equals(p.getClassName())){
                        hasConnection = true;                    }
                }
                for(Method m: methods){
                    if(m.getReturnType().equals(p.getClassName()))
                        hasConnection = true;
                    for(String s: m.getArgs().keySet()){
                        if(m.getArgs().get(s).equals(p.getClassName()))
                            hasConnection = true;
                    }
                }
                
                if(!hasConnection){
                    ArrayList<DraggableLine> lines = c.getChildLines();
                    for(DraggableLine l: lines) {
                        if(p == l.getChildBox()
                            && c == l.getParentBox()){
                            canvas.remove(l);
                            canvas.remove(l.getBaseNode());
                            canvas.remove(l.getArrow());
                            canvas.remove(l.getArrowNode());
                        }
                            
                    }
                }
            }
            
        }
        
        //FOR VARIABLE/METHOD/ARG LINEs
        for(DraggableUMLBox c: boxes) {
            ObservableList<Variable> variables = c.getVariables();
            ObservableList<Method> methods = c.getMethod();
            for(Variable  v: variables) {
                for(DraggableUMLBox p: boxes){
                    if(v.getReturnType().equals(p.getClassName())){
                        boolean exsists = false;
                        ArrayList<DraggableLine> cLines = c.getChildLines();
                        ArrayList<DraggableLine> pLines = p.getParentLines();
                        
                        for(DraggableLine l : cLines) {
                            String lineChildBoxName = l.getChildBox().getClassName();
                            String lineParentBoxName = l.getParentBox().getClassName();
                            String childName = c.getClassName();
                            String parentName = p.getClassName();
                            if(l.getChildBox() == p
                                && l.getParentBox() == c) {
                                exsists = true;
                            }            
                        }
                        if(!exsists){
                            if(canvas.contains(p)) {
                                DraggableLine line = new DraggableLine(c, p, lineController, getArrowType(c, p));
                                c.addChildLine(line);
                                p.addParentLine(line);                        
                                canvas.add(line);
                                canvas.add(line.getBaseNode());
                                canvas.add(line.getArrowNode());
                                canvas.add(line.getArrow());
                            }
                        }
                    }
//                    else {
//                        ArrayList<DraggableLine> lines = c.getChildLines();
//                        ArrayList<DraggableLine> toRemove = new ArrayList();
//                        for(DraggableLine l: lines){
//                            if(c.getClassName().equals(l.getParentBox().getClassName())) {
//                                toRemove.add(l);
//                            }
//                        }
//                        for(DraggableLine l : toRemove) {
//                                l.getParentBox().getParentLines().remove(l);
//                                l.getParentBox().getChildLines().remove(l);
//                                canvas.remove(l);
//                                canvas.remove(l.getBaseNode());
//                                canvas.remove(l.getArrow());
//                                canvas.remove(l.getArrowNode());                                
//                        }
//                    }
                }
            }
            for(Method m: methods){ 
                for(DraggableUMLBox p: boxes){
                    if(m.getReturnType().equals(p.getClassName())){
                          boolean exsists = false;
                        ArrayList<DraggableLine> cLines = c.getChildLines();
                        ArrayList<DraggableLine> pLines = p.getParentLines();
                        
                        for(DraggableLine l : cLines) {
                            String lineChildBoxName = l.getChildBox().getClassName();
                            String lineParentBoxName = l.getParentBox().getClassName();
                            String childName = c.getClassName();
                            String parentName = p.getClassName();
                            if(l.getChildBox() == p
                                && l.getParentBox() == c) {
                                exsists = true;
                            }            
                        }
                        if(!exsists){
                            if(canvas.contains(p)) {
                                DraggableLine line = new DraggableLine(c, p, lineController, getArrowType(c, p));
                                c.addChildLine(line);
                                p.addParentLine(line);                        
                                canvas.add(line);
                                canvas.add(line.getBaseNode());
                                canvas.add(line.getArrowNode());
                                canvas.add(line.getArrow());
                            }
                        }
                    }
                }
                for(String k: m.getArgs().keySet()) {
                    for(DraggableUMLBox p: boxes){
                        if(m.getArgs().get(k).equals(p.getClassName())){
                        boolean exsists = false;
                        ArrayList<DraggableLine> cLines = c.getChildLines();
                        ArrayList<DraggableLine> pLines = p.getParentLines();
                        
                        for(DraggableLine l : cLines) {
                            String lineChildBoxName = l.getChildBox().getClassName();
                            String lineParentBoxName = l.getParentBox().getClassName();
                            String childName = c.getClassName();
                            String parentName = p.getClassName();
                            if(l.getChildBox() == p
                                && l.getParentBox() == c) {
                                exsists = true;
                            }            
                        }
                        if(!exsists){
                            if(canvas.contains(p)) {
                                DraggableLine line = new DraggableLine(c, p, lineController, getArrowType(c, p));
                                c.addChildLine(line);
                                p.addParentLine(line);                        
                                canvas.add(line);
                                canvas.add(line.getBaseNode());
                                canvas.add(line.getArrowNode());
                                canvas.add(line.getArrow());
                            }
                        }
                        }
                    }
                }
            }
        }
        
        //FOR PARENT LINES
        for(DraggableUMLBox c: boxes){
            ArrayList<DraggableUMLBox> parentBoxes = c.getParentList();
            if(parentBoxes != null) {
                for(DraggableUMLBox p: parentBoxes) {
                        boolean exsists = false;
                        ArrayList<DraggableLine> cLines = c.getChildLines();
                        ArrayList<DraggableLine> pLines = p.getParentLines();
                        
                        for(DraggableLine l : cLines) {
                            String lineChildBoxName = l.getChildBox().getClassName();
                            String lineParentBoxName = l.getParentBox().getClassName();
                            String childName = c.getClassName();
                            String parentName = p.getClassName();
                            if(l.getChildBox() == p
                                && l.getParentBox() == p) {
                                exsists = true;
                            }            
                        }
                        if(!exsists){
                            if(canvas.contains(c)) {
                                DraggableLine line = new DraggableLine(p, c, lineController, getArrowType(p, c));
                                c.addChildLine(line);
                                p.addParentLine(line);                        
                                canvas.add(line);
                                canvas.add(line.getBaseNode());
                                canvas.add(line.getArrowNode());
                                canvas.add(line.getArrow());
                            }
                        }
                }
            }
        }
    }
    
    public String getArrowType(DraggableUMLBox child, DraggableUMLBox parent) {
        String returnValue = "";
        boolean isAggregate = false;
        boolean isUses = false;
        String parentType = parent.getClassName();
        ObservableList<Method> methods = child.getMethod();
        ObservableList<Variable> variables = child.getVariables();
        for(Variable v: variables) {
            if(parentType.equals(v.getReturnType()))
                isAggregate = true;
        }
        
        for(Method m : methods) {
            if(parentType.equals(m.getReturnType()))
                isUses = true;
            HashMap<String, String> args = m.getArgs();
            for(String k: args.keySet()){
                if(parentType.equals(args.get(k)))
                    isUses = true;
            }
        }
        
        
        
        if(child.getParentList().contains(parent)) {
            returnValue = INTERFACE;
        }
        else if (isAggregate) {
            returnValue = AGGREGATE;
        }
        else if(isUses){
            returnValue = USES;
        }
        else 
            returnValue = USES;
        
        return returnValue;
    }

    public void setAutoLines(boolean isAuto){
            for(Node l: dataManager.getLines()){
                DraggableLine line = (DraggableLine)l;
                line.setAutoLines(isAuto);
                line.updateComplexLine();
            }
    }
    
 
}
