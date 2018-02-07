/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author brobi
 */
public class EditItem {
    
    //broad level edits
    boolean isItemEdit;
    boolean isCanvasEdit;
    
    // item level edits
    boolean isStringEdit;
    boolean isMethodEdit;
    boolean isVariableEdit;
    
    // canvas level edits
    boolean isMoveEdit;
    boolean isSizeEdit;
    boolean isAddClassEdit;
    boolean isRemoveClassEdit;
    boolean isLineEdit;
    
    //String level edit
    boolean isPackageNameEdit;
    boolean isClassNameEdit;
    boolean isParentEdit;
    
    //Method level edit
    boolean isAddMethodEdit;
    boolean isRemoveMethodEdit;
    boolean isMethodDataEdit;
    
    //Variable Level Edit
    boolean isAddVariableEdit;
    boolean isRemoveVariableEdit;
    boolean isVariableDataEdit;
    
    //Line level edit
    boolean isLineMoveEdit;
    boolean isLineSplitEdit;
    boolean isParentPointerMoveEdit;
    boolean isChildBaseMoveEdit;
    
    String boxEditedClassName;
    String editedPackageName;
    
    DraggableUMLBox editedBox;
    
    ObservableList<Method> editedMethodList;
    ObservableList<Variable> editedVariableList;
    
    double boxMovedX;
    double boxMovedY;
    
    double boxSizedHeight;
    double boxSizedWidth;    
    ArrayList<DraggableUMLBox> boxEditedParentList;
    
    double startModX;
    double startModY;
    double endModX;
    double endModY;
    
    String oldClassName;
    String newClassName;
    String oldVariableName;
    String newVariableName;
    String oldMethodName;
    String newMethodName;
    String parentEditedName;
    
    DraggableLine oldLine;
    DraggableLine newLine;
    
    
    Method newMethod;
    Method oldMethod;
    Method editedMethod;
    Variable newVariable;
    Variable oldVariable;
    Variable editedVariable;
    
    public EditItem () {
        //base level
         isItemEdit = false;
         isCanvasEdit = false;

        // item level edits
         isStringEdit = false;
         isMethodEdit = false;
         isVariableEdit = false;

        // canvas level edits
         isMoveEdit = false;
         isSizeEdit = false;
         isAddClassEdit = false;
         isRemoveClassEdit = false;
         isLineEdit = false;

        //String level edit
         isPackageNameEdit = false;
         isClassNameEdit = false;
         isParentEdit = false;

        //Method level edit
         isAddMethodEdit = false;
         isMethodDataEdit = false;

        //Variable Level Edit
         isAddVariableEdit = false;
         isRemoveVariableEdit = false;

        //Line level edit
         isLineMoveEdit = false;
         isLineSplitEdit = false;

         editedBox = null;
         boxEditedParentList = null;
        
        oldClassName = "";
        newClassName = "";
        oldVariableName = "";
        newVariableName = "";
        oldMethodName = "";
        newMethodName = "";
        
        editedPackageName = "";
        parentEditedName = "";
        
         
         
    }
    
  
    
    public void setMoveEdit(String className, double x, double y) {
        isMoveEdit = true;
        isCanvasEdit = true;
        
        boxEditedClassName = className;
        
        boxMovedX = x;
        boxMovedY = y;
        
    }
    
    public void setIsSizeEdit(String className, double height, double width) {
        isSizeEdit = true;
        isCanvasEdit = true;
        
        boxEditedClassName = className;
        
        boxSizedHeight = height;
        boxSizedWidth = width;
    }
    
    public void setRemoveClassEdit(DraggableUMLBox box) {
        
        // add = true, remove = false;
        
        isRemoveClassEdit = true;
        isCanvasEdit = true;
        
        editedBox = box;
    }
    
    public void setAddClassEdit(DraggableUMLBox box) {
         isAddClassEdit = true;
         isCanvasEdit = true;
         
         editedBox = box;
    }
    
    public void setPackageNameEdit (String nameOfEdited, String oldPackageName) {
        isPackageNameEdit = true;
        isStringEdit = true;
        isItemEdit = true;
        
        editedPackageName = oldPackageName;
        boxEditedClassName = nameOfEdited;
        
    }
    
    public void setParentEdit(String parentName, String childName) {
        isItemEdit = true;
        isParentEdit = true;
        
        boxEditedClassName = childName;
        parentEditedName = parentName;
    }
    
    public void setClassNameEdit(String previousName, String newName) {
        isClassNameEdit = true;
        isStringEdit = true;
        isItemEdit = true;
        
        oldClassName = previousName;
        newClassName = newName;
    }
    
    public void setParentNameEdit (DraggableUMLBox box) {
        isParentEdit = true;
        isStringEdit = true;
        isItemEdit = true;
        
        boxEditedParentList = box.getParentList();
        
        boxEditedClassName = box.getClassName();
        
    }
    
    public void setAddMethodEdit(DraggableUMLBox box, Method m) {
        
        isAddMethodEdit = true;
        isMethodEdit = true;
        isItemEdit = true;
        
        boxEditedClassName = box.getClassName();
        editedMethod = m;
    }
    
    public void setRemoveMethodEdit(DraggableUMLBox box, Method m) {
        
        isRemoveMethodEdit = true;
        isMethodEdit = true;
        isItemEdit = true;
        
        boxEditedClassName = box.getClassName();
        editedMethod = m;
    }
    
    public void setMethodDataEdit(DraggableUMLBox box,Method oldMeth, Method newMeth) {
        isMethodEdit = true;
        isMethodDataEdit = true;
        isItemEdit = true;
        
        
        boxEditedClassName = box.getClassName();
        oldMethod = oldMeth;
        newMethod = newMeth;
    }
    
    public void setAddVariableEdit(DraggableUMLBox box, Variable v) {
        
        isAddVariableEdit = true;
        isVariableEdit = true;
        isItemEdit = true;
        
        boxEditedClassName = box.getClassName();
        editedVariable = v;
    }
    
    public void setRemoveVariableEdit(DraggableUMLBox box, Variable v) {
        
        isVariableEdit = true;
        isRemoveVariableEdit = true;
        isItemEdit = true;
        
        boxEditedClassName = box.getClassName();
        editedVariable = v;
    }
    
    public void setVariableDataEdit(String boxName, Variable oldVariableData, Variable newVariableData) {
         isVariableDataEdit = true;
         isVariableEdit = true;
         isItemEdit = true;
         
         oldVariable = oldVariableData;
         newVariable = newVariableData;
         boxEditedClassName = boxName;
         
    }
    
    public void setParentPointerMoveEdit(DraggableLine line) {
        isCanvasEdit = true;
        isParentPointerMoveEdit = true;
        
        startModX = line.getStartModX();
        startModY = line.getStartModY();
        
        
    }
    
    public void setChildBaseMoveEdit(DraggableLine line) {
        
        isCanvasEdit = true;
        isChildBaseMoveEdit = true;
        
        endModX = line.getEndModX();
        endModY = line.getEndModY();
    }

    public void setLineMoveEdit(DraggableLine old, DraggableLine newL) {
        
        isCanvasEdit = true;
        isLineMoveEdit = true;
        
        oldLine = old;
        newLine = newL;
    }
    
    public void setLineSplitEdit(DraggableUMLBox box) {
    }
    

    
    public void setAddOrRemoveLineEdit(DraggableLine line, boolean addOrRemove) {
        
        
    }
    
    public boolean getItemEdit() {
        return isItemEdit;
    }
    
    public boolean getCanvasEdit() {
        return isCanvasEdit;
    }

    public boolean getStringEdit() {
        return isStringEdit;
    }
    
    public boolean getMethodEdit() {
        return isMethodEdit;
    }
    
    public boolean getVariableEdit() {
        return isVariableEdit;
    }
    
    public boolean getMovedEdit() {
        return isMoveEdit;
    }
    
    public boolean getSizedEdit() {
        return isSizeEdit;
    }
    
    public boolean getAddClassEdit() {
        return isAddClassEdit;
    }
    
    public boolean getRemoveClassEdit() {
        return isRemoveClassEdit;
    }
    
    public boolean getLineEdit() {
        return isLineEdit;
    }
    
    public boolean getClassNameEdit() {
        return isClassNameEdit;
    }
    
    public boolean getPackageNameEdit() {
        return isPackageNameEdit;
    }
    
    public boolean getParentEdit() {
        return isParentEdit;
    }
    
    public boolean getRemoveMethodEdit() {
        return isRemoveMethodEdit;
    }
    
    public boolean getAddMethodEdit() {
         return isAddMethodEdit;
    }

    public boolean getAddVariableEdit() {
        return isAddVariableEdit;
    }
    
    public boolean getRemoveVariableEdit() {
        return isRemoveVariableEdit;
    }

    public boolean getParentPointerMoveEdit() {
        return isParentPointerMoveEdit;
    }
    
    public boolean getChildBaseMoveEdit() {
        return isChildBaseMoveEdit;
    }

    public boolean getLineSplitEdit() {
        return isLineSplitEdit;
    }  
    
    public boolean getVariableDataEdit() {
        return isVariableDataEdit;
    }
    
    public String getOldName() {
        return oldClassName;
    }
    
    public String getNewName() {
        return newClassName;
    }
    
    public String getEditedClassName() {
        return boxEditedClassName;
    }
   
    public String getEditedPackageName() {
        return editedPackageName;
    }
    
    public String getNewMethodName() {
        return newMethodName;
    }
    
    public String getOldMethodName() {
        return oldMethodName;
    }
    
    public String getNewVariableName() {
        return newVariableName;
    }
    
    public String getOldVariableName() {
        return oldVariableName;
    }
    
    public double getMovedX() {
        return boxMovedX;
    }
    
    public double getMovedY() {
        return boxMovedY;
    }
    
    public double getBoxSizedHeight() {
        return boxSizedHeight;
    }
    
    public double getBoxSizedWidth() {
        return boxSizedWidth;
    }
    
    public DraggableUMLBox getEditedBox() {
        return editedBox;
    }
    
    public String getEditedParentName() {
        return parentEditedName;
    }
    
    public Variable getOldVariable() {
        return oldVariable;
    }
    
    public Variable getNewVariable() {
        return newVariable;
    }
    
    public boolean getMethodDataEdit() {
        return isMethodDataEdit;
    }
    
    public Method getOldMethod() {
        return oldMethod;
    }
    
    public Method getNewMethod() {
        return newMethod;
    }
    
    public Variable getEditedVariable() {
        return editedVariable;
    }
    
    public Method getEditedMethod() {
        return editedMethod;
    }
    
    public DraggableLine getOldLine() {
        return oldLine;
    }
    
    public DraggableLine getNewLine() {
        return newLine;
    }
    
    public boolean getLineMoveEdit() {
        return isLineMoveEdit;
    }
    
    public void setEditedPackageName (String newName) {
        editedPackageName = newName;
    }
}
         