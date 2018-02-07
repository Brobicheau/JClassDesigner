/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.controller;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import jcd.data.DataManager;
import jcd.data.DraggableLine;
import jcd.data.DraggableUMLBox;
import jcd.data.EditItem;
import jcd.data.Method;
import jcd.data.Variable;
import jcd.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author brobi
 */
public class HistoryController {
    
    DataManager dataManager;
    AppTemplate app;
    
    public HistoryController(AppTemplate initApp) {
        app = initApp;
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        dataManager = (DataManager)app.getDataComponent();
        
    }
    
    public void setRemoveClassEdit(DraggableUMLBox box) {
        
        EditItem newEdit = new EditItem();
        newEdit.setRemoveClassEdit(box);
        dataManager.addUndo(newEdit);
        
    }
    
    public void setAddClassEdit(DraggableUMLBox box) {
        EditItem newEdit = new EditItem();
        newEdit.setAddClassEdit(box);
        dataManager.addUndo(newEdit);
    }
    
    public void setMovedEdit (double x, double y) {
        
        EditItem newItem = new EditItem();
        newItem.setMoveEdit(dataManager.getSelectedBox().getClassName(), x, y);
        dataManager.addUndo(newItem);
    }
    
    public void setSizedEdit(DraggableUMLBox box) {
        
        EditItem newItem = new EditItem();
        newItem.setIsSizeEdit(box.getClassName(), box.getHeight(), box.getWidth());
        dataManager.addUndo(newItem);
    }
    
    public void setClassNameEdit(String oldName, String newName) {
        
        EditItem newItem = new EditItem();
        newItem.setClassNameEdit(oldName, newName);
        dataManager.addUndo(newItem);
    }
    
    public void setPackageNameEdit(DraggableUMLBox box) {
        
        EditItem newItem = new EditItem();
        newItem.setPackageNameEdit(box.getClassName(), box.getPackageName());
        dataManager.addUndo(newItem);
    }
    
    public void setAddVariableEdit(DraggableUMLBox box ,Variable v) {
        
        EditItem newItem = new EditItem();
        newItem.setAddVariableEdit(box,v);
        dataManager.addUndo(newItem);
    }
    
    public void setRemoveVariableEdit(DraggableUMLBox box, Variable v) {
        EditItem newItem = new EditItem();
        newItem.setRemoveVariableEdit(box, v);
        dataManager.addUndo(newItem);
    }
    
    public void setVariableDataEdit(DraggableUMLBox box, Variable oldVariable, Variable newVariable){
        EditItem newItem = new EditItem();
        newItem.setVariableDataEdit(box.getClassName(), oldVariable, newVariable);
        dataManager.addUndo(newItem);
    }
    
    public void setAddMethodEdit(DraggableUMLBox box, Method m) {
        
        EditItem newItem = new EditItem();
        newItem.setAddMethodEdit(box, m);
        dataManager.addUndo(newItem);
    }
    
    public void setRemoveMethodEdit(DraggableUMLBox box, Method m){
        
        EditItem newItem = new EditItem();
        newItem.setRemoveMethodEdit(box, m);
        dataManager.addUndo(newItem);
    }
    
    public void setMethodDataEdit(DraggableUMLBox box, Method oldMethod, Method newMethod){
        
        EditItem newItem = new EditItem();
        newItem.setMethodDataEdit(box, oldMethod, newMethod);
        dataManager.addUndo(newItem);
    }
    
    public void setAddLineEdit() {
        
        EditItem newItem = new EditItem();
        //newItem.setAddLineEdit(line);
        dataManager.addUndo(newItem);
    }
    
    public void setLineMoveEdit(DraggableLine oldLine, DraggableLine newLine){
        
        EditItem newItem = new EditItem();
        newItem.setLineMoveEdit(oldLine, newLine);
        dataManager.addUndo(newItem);
    }
    
    public void processUndoButtonRequest(){
        EditItem undo = dataManager.getUndo();
        
        if(undo.getItemEdit()){
            if(undo.getStringEdit()) {
                if(undo.getClassNameEdit()) {
                    //get old and new box names
                    //find box with new name
                    //replace new name with old name
                    String newName = undo.getNewName();
                    String oldName = undo.getOldName();
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    for(DraggableUMLBox b: boxes) {
                        if(newName.equals(b.getClassName()))
                            b.setClassName(oldName);
                    }
                }   
                else if(undo.getPackageNameEdit()) {
                    //get class name and old package name
                    //find box with name
                    //replace package name
                    String oldPackageName = undo.getEditedPackageName();
                    String className = undo.getEditedClassName();
                    String newName;
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    for(DraggableUMLBox b: boxes){
                        if(className.equals(b.getClassName())){
                            newName = b.getPackageName();
                            b.setPackageName(oldPackageName);
                            undo.setEditedPackageName(newName);
                        }
                            
                    }
                    
                }
                else if (undo.getParentEdit()) {
                    String className = undo.getEditedClassName();
                    String parentName = undo.getEditedParentName();
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    DraggableUMLBox parentBox = null;
                    for(DraggableUMLBox b : boxes) {
                        if(parentName.equals(b.getClassName()))
                            parentBox = b;
                    }
                    if(parentBox != null) {
                        for(DraggableUMLBox b: boxes) {
                            if(className.equals(b.getClassName())){
                                b.getParentList().add(parentBox);
                            }
                        }
                    }
                }
            }
            else if (undo.getMethodEdit()) {
                
                if(undo.getAddMethodEdit()) {
                    Method method = undo.getEditedMethod();
                    Workspace workspace = (Workspace)app.getWorkspaceComponent();
                    dataManager.setSelectedMethod(method);
                   VBox methodPane = workspace.getMethodPane();
                   methodPane.getChildren().remove(dataManager.getSelectedMethodBox());
                   dataManager.removeMethod();   
                }
                else if(undo.getRemoveMethodEdit()) {
                    String className = undo.getEditedClassName();
                    Method method = undo.getEditedMethod();
                    for(DraggableUMLBox b: dataManager.getOnlyBoxes()){
                        if(className.equals(b.getClassName())){
                            b.addMethod(method);
                            Workspace workspace = (Workspace)app.getWorkspaceComponent();
                            workspace.updateMethods(b);
                            workspace.updateLines();
                        }
                    }
                }
                else if(undo.getMethodDataEdit()){
                    String className = undo.getEditedClassName();
                    Method oldMethod = undo.getOldMethod();
                    Method newMethod = undo.getNewMethod();
                    Method replaceMethod = new Method();
                    replaceMethod.setName(newMethod.getName());
                    replaceMethod.setReturnType(newMethod.getReturnType());
                    replaceMethod.setAccType(newMethod.getAccessorType());
                    replaceMethod.setStatic(newMethod.getStatic());
                    replaceMethod.setAbstract(newMethod.getAbstract());
                    replaceMethod.setArgs(newMethod.getArgs());
                    String oldMethName = oldMethod.getName();
                    String newMethName = newMethod.getName();
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    for(DraggableUMLBox b: boxes) {
                        if(className.equals(b.getClassName())) {
                            for(Method m: b.getMethod()){
                                if(m == newMethod){
                                    undo.setMethodDataEdit(b, replaceMethod, newMethod);
                                    m.setName(oldMethod.getName());
                                    m.setAccType(oldMethod.getAccessorType());
                                    m.setAbstract(oldMethod.getAbstract());
                                    m.setArgs(oldMethod.getArgs());
                                    m.setReturnType(oldMethod.getReturnType());
                                    m.setStatic(oldMethod.getStatic());
                                    String replacEName = replaceMethod.getName();
                                    b.update();
                                }
                            }
                        }
                    }
                }
            }   
            else if(undo.getVariableEdit()) {
                
                if(undo.getAddVariableEdit()){
                    Variable variable = undo.getEditedVariable();
                            Workspace workspace = (Workspace)app.getWorkspaceComponent();
                            dataManager.setSelectedVariable(variable);
                           VBox variablePane = workspace.getVariablePane();
                           variablePane.getChildren().remove(dataManager.getSelectedVariableBox());
                           dataManager.removeVariable();
                                   
                    
                }
                else if (undo.getRemoveVariableEdit()) {
                    Variable variable = undo.getEditedVariable();
                    String className = undo.getEditedClassName();
                    for(DraggableUMLBox b: dataManager.getOnlyBoxes()) {
                            if(className.equals(b.getClassName())){
                                b.addVariable(variable);
                            Workspace workspace = (Workspace)app.getWorkspaceComponent();
                            workspace.upateVariables(b);
                            workspace.updateLines();
                        }
                    }
                }
                else if (undo.getVariableDataEdit()) {
                    String className = undo.getEditedClassName();
                    Variable oldVariable = undo.getOldVariable();
                    Variable newVariable = undo.getNewVariable();
                    Variable replaceVariable = new Variable();
                    replaceVariable.setVarName(newVariable.getName());
                    replaceVariable.setAccessorType(newVariable.getAccessorType());
                    replaceVariable.setStatic(newVariable.getStatic());
                    replaceVariable.setReturnType(newVariable.getReturnType());
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    for(DraggableUMLBox b: boxes) {
                        if(className.equals(b.getClassName())){
                            for(Variable v: b.getVariables()){
                                if(v == newVariable){
                                    undo.setVariableDataEdit(className, replaceVariable, newVariable);
                                    v.setAccessorType(oldVariable.getAccessorType());
                                    v.setReturnType(oldVariable.getReturnType());
                                    v.setVarName(oldVariable.getName());
                                    v.setStatic(oldVariable.getStatic());
                                    b.update();
                                }
                            }
                        }
                    }
                }
            }
        }
        else if( undo.getCanvasEdit()) {
            
            if(undo.getMovedEdit()){
                //get x amd y for moved box
                //get name of class edited
                //change back to old position
                String className = undo.getEditedClassName();
                double x = undo.getMovedX();
                double y = undo.getMovedY();
                ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                for(DraggableUMLBox b : boxes){
                    if(className.equals(b.getClassName())){
                        undo.setMoveEdit(className, b.getLayoutX(), b.getLayoutY());
                        b.drag((int)x, (int)y);
                    }
                }
            }
            else if(undo.getSizedEdit()) {
                String className = undo.getEditedClassName();
                double width = undo.getBoxSizedWidth();
                double height = undo.getBoxSizedHeight();
                ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                for(DraggableUMLBox b : boxes) {
                    if(className.equals(b.getClassName())){
                        undo.setIsSizeEdit(className, b.getHeight(), b.getWidth());
                        b.setAllHeight(height);
                        b.setAllWidth(width);
                    }
                }
                
            }
            else if(undo.getRemoveClassEdit()) {
                DraggableUMLBox box = undo.getEditedBox();
                ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                ObservableList<Node> canvas = dataManager.getBoxes();
                canvas.add(box);
                boxes.add(box);
                dataManager.setSelectedBox(box);
                undo.setRemoveClassEdit(box);
            }
            else if (undo.getAddClassEdit()) {
                DraggableUMLBox box = undo.getEditedBox();
                ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                ObservableList<Node> canvas = dataManager.getBoxes();
                canvas.remove(box);
                boxes.remove(box);
                undo.setAddClassEdit(box);
                    
            }
            else if(undo.getLineMoveEdit()) {
                ObservableList<Node> canvas = dataManager.getBoxes();
                DraggableLine oldLine = undo.getOldLine();
                DraggableLine newLine = undo.getNewLine();
                ArrayList<DraggableLine> lines = oldLine.getChildBox().getParentLines();
                for(DraggableLine l: lines){
                    if(oldLine.getParentBox() == l.getParentBox()
                        && oldLine.getChildBox() == l.getChildBox()){
                        l = oldLine;
                    }
                }
                
            }
            else if(undo.getParentPointerMoveEdit()) {
                
            }
            else if (undo.getChildBaseMoveEdit()) {
                
            }
        }
        dataManager.addRedo(undo);
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.loadEditorProperties(dataManager.getSelectedBox());
        workspace.updateLines();
            
    }

    public void processRedoButtonRequest(){
        EditItem redo = dataManager.getRedo();
        
        if(redo.getItemEdit()){
            if(redo.getStringEdit()) {
                if(redo.getClassNameEdit()) {
                    //get old and new box names
                    //find box with new name
                    //replace new name with old name
                    String oldName = redo.getNewName();
                    String newName = redo.getOldName();
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    for(DraggableUMLBox b: boxes) {
                        if(newName.equals(b.getClassName()))
                            b.setClassName(oldName);
                    }
                }   
                else if(redo.getPackageNameEdit()) {
                    //get class name and old package name
                    //find box with name
                    //replace package name
                    String oldPackageName = redo.getEditedPackageName();
                    String className = redo.getEditedClassName();
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    for(DraggableUMLBox b: boxes){
                        if(className.equals(b.getClassName()))
                            b.setPackageName(oldPackageName);
                    }
                    
                }
                else if (redo.getParentEdit()) {
                    String className = redo.getEditedClassName();
                    String parentName = redo.getEditedParentName();
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    DraggableUMLBox parentBox = null;
                    for(DraggableUMLBox b : boxes) {
                        if(parentName.equals(b.getClassName()))
                            parentBox = b;
                    }
                    if(parentBox != null) {
                        for(DraggableUMLBox b: boxes) {
                            if(className.equals(b.getClassName())){
                                b.getParentList().add(parentBox);
                            }
                        }
                    }
                }
            }
            else if (redo.getMethodEdit()) {
                
                if(redo.getAddMethodEdit()) {
                    String className = redo.getEditedClassName();
                    Method method = redo.getEditedMethod();
                    for(DraggableUMLBox b: dataManager.getOnlyBoxes()){
                        if(className.equals(b.getClassName())){
                            b.addMethod(method);
                            Workspace workspace = (Workspace)app.getWorkspaceComponent();
                            workspace.updateMethods(b);
                            workspace.updateLines();
                        }
                    }
                }
                else if(redo.getRemoveMethodEdit()) {
                      Method method = redo.getEditedMethod();
                    Workspace workspace = (Workspace)app.getWorkspaceComponent();
                    dataManager.setSelectedMethod(method);
                   VBox methodPane = workspace.getMethodPane();
                   methodPane.getChildren().remove(dataManager.getSelectedMethodBox());
                   dataManager.removeMethod();   
                }
                else if(redo.getMethodDataEdit()){
                    String className = redo.getEditedClassName();
                    Method oldMethod = redo.getOldMethod();
                    Method newMethod = redo.getNewMethod();
                    Method replaceMethod = new Method();
                    replaceMethod.setName(newMethod.getName());
                    replaceMethod.setReturnType(newMethod.getReturnType());
                    replaceMethod.setAccType(newMethod.getAccessorType());
                    replaceMethod.setStatic(newMethod.getStatic());
                    replaceMethod.setAbstract(newMethod.getAbstract());
                    replaceMethod.setArgs(newMethod.getArgs());
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    for(DraggableUMLBox b: boxes) {
                        if(className.equals(b.getClassName())) {
                            for(Method m: b.getMethod()){
                                if(m == newMethod){
                                    redo.setMethodDataEdit(b, replaceMethod, newMethod);
                                    m.setName(oldMethod.getName());
                                    m.setAccType(oldMethod.getAccessorType());
                                    m.setAbstract(oldMethod.getAbstract());
                                    m.setArgs(oldMethod.getArgs());
                                    m.setReturnType(oldMethod.getReturnType());
                                    m.setStatic(oldMethod.getStatic());
                                    b.update();
                                }
                            }
                        }
                    }
                }
            }   
            else if(redo.getVariableEdit()) {
                
                if(redo.getAddVariableEdit()){
                    Variable variable = redo.getEditedVariable();
                    String className = redo.getEditedClassName();
                    for(DraggableUMLBox b: dataManager.getOnlyBoxes()) {
                            if(className.equals(b.getClassName())){
                                b.addVariable(variable);
                            Workspace workspace = (Workspace)app.getWorkspaceComponent();
                            workspace.upateVariables(b);
                            workspace.updateLines();
                        }
                    }
                }
                else if (redo.getRemoveVariableEdit()) {
                    Variable variable = redo.getEditedVariable();
                    
                    Workspace workspace = (Workspace)app.getWorkspaceComponent();
                    dataManager.setSelectedVariable(variable);
                   VBox variablePane = workspace.getVariablePane();
                   variablePane.getChildren().remove(dataManager.getSelectedVariableBox());
                   dataManager.removeVariable();
                }
                else if (redo.getVariableDataEdit()) {
                    String className = redo.getEditedClassName();
                    Variable oldVariable = redo.getOldVariable();
                    Variable newVariable = redo.getNewVariable();
                    Variable replaceVariable = new Variable();
                    replaceVariable.setVarName(newVariable.getName());
                    replaceVariable.setAccessorType(newVariable.getAccessorType());
                    replaceVariable.setStatic(newVariable.getStatic());
                    replaceVariable.setReturnType(newVariable.getReturnType());                    
                    ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                    for(DraggableUMLBox b: boxes) {
                        if(className.equals(b.getClassName())){
                            for(Variable v: b.getVariables()){
                                if(v == newVariable){
                                    redo.setVariableDataEdit(className, replaceVariable, newVariable);
                                    v.setAccessorType(oldVariable.getAccessorType());
                                    v.setReturnType(oldVariable.getReturnType());
                                    v.setVarName(oldVariable.getName());
                                    v.setStatic(oldVariable.getStatic());
                                    b.update();
                                }
                            }
                        }
                    }
                }
            }
        }
        else if( redo.getCanvasEdit()) {
            
            if(redo.getMovedEdit()){
                //get x amd y for moved box
                //get name of class edited
                //change back to old position
                String className = redo.getEditedClassName();
                double x = redo.getMovedX();
                double y = redo.getMovedY();
                ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                for(DraggableUMLBox b : boxes){
                    if(className.equals(b.getClassName())){
                        redo.setMoveEdit(className, b.getLayoutX(), b.getLayoutY());
                        b.drag((int)x, (int)y);
                    }
                }
            }
            else if(redo.getSizedEdit()) {
                String className = redo.getEditedClassName();
                double width = redo.getBoxSizedWidth();
                double height = redo.getBoxSizedHeight();
                ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                for(DraggableUMLBox b : boxes) {
                    if(className.equals(b.getClassName())){
                        redo.setIsSizeEdit(className, b.getHeight(), b.getWidth());
                        b.setAllHeight(height);
                        b.setAllWidth(width);
                    }
                }
                
            }
            else if(redo.getRemoveClassEdit()) {
                DraggableUMLBox box = redo.getEditedBox();
                ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                ObservableList<Node> canvas = dataManager.getBoxes();
                canvas.remove(box);
                boxes.remove(box);
                redo.setRemoveClassEdit(box);

            }
            else if (redo.getAddClassEdit()) {
                 DraggableUMLBox box = redo.getEditedBox();
                ArrayList<DraggableUMLBox> boxes = dataManager.getOnlyBoxes();
                ObservableList<Node> canvas = dataManager.getBoxes();
                canvas.add(box);
                boxes.add(box);
                dataManager.setSelectedBox(box);
                redo.setAddClassEdit(box);
                    
            }
            else if(redo.getLineMoveEdit()) {
                ObservableList<Node> canvas = dataManager.getBoxes();
                DraggableLine oldLine = redo.getOldLine();
                DraggableLine newLine = redo.getNewLine();
                ArrayList<DraggableLine> lines = oldLine.getChildBox().getParentLines();
                for(DraggableLine l: lines){
                    if(oldLine.getParentBox() == l.getParentBox()
                        && oldLine.getChildBox() == l.getChildBox()){
                        l = oldLine;
                    }
                }
                
            }
            else if(redo.getParentPointerMoveEdit()) {
                
            }
            else if (redo.getChildBaseMoveEdit()) {
                
            }
        }
        dataManager.addUndo(redo);
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.loadEditorProperties(dataManager.getSelectedBox());
        workspace.updateLines();
            
    }
        
}