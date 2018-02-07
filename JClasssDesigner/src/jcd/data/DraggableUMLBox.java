/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import saf.AppTemplate;

/**
 *
 * @author brobi
 */
public class DraggableUMLBox extends GridPane implements Draggable  {

    static final int HEIGHT_RESIZE_MARGIN = 5;
    static final int WIDTH_RESIZE_MARGIN = 5;
    static final int STARTING_HEIGHT = 200;
    static final int STARTING_WIDTH = 200;
    static final int EXTERNAL_CLASS_WIDTH = 100;
    static final int EXTERNAL_CLASS_HEIGHT = 75;
    static final int STARTING_Y_POSITION = 500;
    static final int STARTING_X_POSITION = 750;
    static final String DEFAULT_CLASS_NAME = "NewClass";
    static final String DEFAULT_PACKAGE_NAME =  "Package Not Defined";
    static final String UML_STYLE_NE = "uml_box_ne";
    static final String UML_STYLE = "uml_box";
    static final String BOX = "BOX";
    ArrayList<DraggableLine> childLines;
    ArrayList<DraggableLine> parentLines;
    double startX;
    double startY;
    VBox titleBox;
    VBox variablesBox;
    VBox methodsBox;
    int variableCount = 0;
    int methodCount = 0;
    DataManager dataManager;
    String className;
    String packageName;
    String accessorType;
    Label classNameLabel;
    ObservableList<Variable> variablesList;
    ObservableList<Method> methodList;
    ArrayList<Variable> variables;
    ArrayList<Method> methods;
    ArrayList<DraggableUMLBox> parentList;
    boolean isAbstract;
    boolean isInterface;
    boolean isPartOfProject;
    AppTemplate app;
    int endX;
    int endY;
    
    public DraggableUMLBox() {
        
        setupBoxStyle();
        setupBoxDefaults();
        
    }
    
    public DraggableUMLBox(boolean isPart) {
        isPartOfProject = isPart;
        if(!isPartOfProject) {
            setupNonExportStyle();
            setupNonExportDefaults();
        }
    }
    
    @Override
    public String getType() {
        return BOX;
    }
    
    
    public ArrayList<DraggableLine> getParentLines() {
        return parentLines;
    }
    
    public ArrayList<DraggableLine> getChildLines() {
        return childLines;
    }
    
   private void setupBoxDefaults() {
        this.setPrefHeight(STARTING_HEIGHT);
        this.setPrefWidth(STARTING_WIDTH);
        setLayoutX(STARTING_X_POSITION);
        setLayoutY(STARTING_Y_POSITION);
        setMinWidth(STARTING_WIDTH);
        setMinHeight(STARTING_HEIGHT);
        setMaxHeight(STARTING_HEIGHT);
        setMaxWidth(STARTING_WIDTH);
        startX = 0.0;
        startY = 0.0;
        className = DEFAULT_CLASS_NAME;
        packageName = DEFAULT_PACKAGE_NAME;   
        variables = new ArrayList();
        methods = new ArrayList();
        methodList = FXCollections.observableArrayList(methods);
        variablesList = FXCollections.observableArrayList(variables);
        accessorType = "public";
        childLines = new ArrayList();
        parentLines = new ArrayList();
        parentList = new ArrayList();        
       
        isAbstract = false;
        isInterface = false;
        isPartOfProject = true;
    }

    private void setupNonExportDefaults() {

        this.setMinWidth(EXTERNAL_CLASS_WIDTH);
        this.setMinHeight(EXTERNAL_CLASS_HEIGHT);
        this.setMaxHeight(EXTERNAL_CLASS_HEIGHT);
        this.setMaxWidth(EXTERNAL_CLASS_WIDTH);
        this.setPrefHeight(EXTERNAL_CLASS_HEIGHT);
        this.setPrefWidth(EXTERNAL_CLASS_WIDTH);
        setLayoutX(STARTING_X_POSITION);
        setLayoutY(STARTING_Y_POSITION);
        className = DEFAULT_CLASS_NAME;
        packageName = DEFAULT_PACKAGE_NAME;   
        variables = new ArrayList();
        methods = new ArrayList();
        methodList = FXCollections.observableArrayList(methods);
        variablesList = FXCollections.observableArrayList(variables);
        accessorType = " ";
        isAbstract = false;
        isInterface = false;
        childLines = new ArrayList();
        parentLines = new ArrayList();
        parentList = new ArrayList();        
        
        
    }
   
    public void initLabel() {
        if(isPartOfProject) {
            classNameLabel = new Label();

            classNameLabel.setText(className);
            titleBox.getChildren().add(classNameLabel); 
            this.checkIfAbstract();
            this.checkInterface();
        }
        else if(!isPartOfProject) {
            classNameLabel = new Label();
            classNameLabel.setText(className);
            titleBox.getChildren().add(classNameLabel);   
        }
        
    }
    
    public void addChildLine (DraggableLine line){
        childLines.add(line);
    }
    
    public void addParentLine(DraggableLine line) {
        parentLines.add(line);
    }
   
    public void setPartOfProject(boolean isPart) {
        isPartOfProject = isPart;
    }
    

    public boolean getIsPartOfProject() {
        return isPartOfProject;
    }
    
    private void checkInterface() {
        if (classNameLabel != null)
        {
            if(isInterface) {
                classNameLabel.setText("<<Interface>>\n"+className);
            }      
        }
    }
    
    private void setupBoxStyle() {
       
        
        titleBox = new VBox();
        variablesBox = new VBox();
        methodsBox = new VBox();
        titleBox.getStyleClass().add(UML_STYLE);
        variablesBox.getStyleClass().add(UML_STYLE);     
        methodsBox.getStyleClass().add(UML_STYLE);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(100);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(33);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(33);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(34);  
        
        this.getColumnConstraints().add(col1);
        this.getRowConstraints().add(row1);
        this.getRowConstraints().add(row2);
        this.getRowConstraints().add(row3);
        
        setRowIndex(titleBox, 0);
        setColumnIndex(titleBox, 0);
        
        setRowIndex(variablesBox, 1);
        setColumnIndex(variablesBox, 0);
        
        setRowIndex(methodsBox, 2);
        setColumnIndex(methodsBox,0);   
        
        this.getChildren().add(titleBox);
        this.getChildren().add(variablesBox);
        this.getChildren().add(methodsBox);
        
        
    }
    
    private void setupNonExportStyle() {
            titleBox = new VBox();
            parentList = new ArrayList();
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(100);
            RowConstraints row1 = new RowConstraints();
            row1.setPercentHeight(100);
            this.getColumnConstraints().add(col1);
            this.getRowConstraints().add(row1);
            titleBox.getStyleClass().add(UML_STYLE_NE);
            this.getChildren().add(titleBox);
    }
    
    @Override
    public void drag(int x, int y){
        relocate(x, y);
        if(childLines != null) {
            for(DraggableLine line: childLines) {
                line.drag(x, y);
            }
        }
            
        if(parentLines != null) {
            for(DraggableLine line: parentLines) {
                line.drag(x, y);
            }            
        }
    }
    
    public void snap(int x, int y) {
        int moddedX = x % 20;
        int newX = x - moddedX;
        int moddedY = y % 20;
        int newY = y - moddedY;
        relocate(newX, newY);
        for(DraggableLine childLine: childLines) {
            childLine.drag(x, y);
        }
            
        for(DraggableLine parentLine: parentLines) {
            parentLine.drag(x, y);
        }
    }
    
    
    public void sizeAll(int x, int y){
        
        if(startX == 0 || startY == 0) {
            startX = x;
            startY = y;
        }
        else {
            double width = this.getMinWidth() +(x - startX);
            double height = this.getMinHeight() +(y - startY);            
            setMinWidth(width);
            setMaxWidth(width);
            setPrefWidth(width);
            setMinHeight(height);
            setMaxHeight(height);
            setPrefHeight(height);
            startY = y;
            startX = x;
        }
    }
    
    public void sizeWidth(int x){
        if(startX == 0)
            startX = x;
        else {
        double width = this.getMinWidth() +(x - startX);
        setMinWidth(width);
        setMaxWidth(width);
        setPrefWidth(width);
        startX = x;
        
        }
    }
        
    public void sizeHeight(int y){   
        if(startY == 0)
            startY = y;
        else {
        double height = this.getMinHeight() +(y - startY);
        setMinHeight(height);
        setMaxHeight(height);
        setPrefHeight(height);
        startY = y;
        }
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight){
        
    } 
   
    @Override
    public DesignerState getStartingState() {
        return DesignerState.STARTING_BOX;
    }
    
    public void setClassName(String newName) {
        className = newName;
        updateName();
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setPackageName (String newName) {
        packageName = newName;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    
    public void updateName() {
        if(classNameLabel!= null) {
            if(isAbstract)
                classNameLabel.setText("{abstract}" + "\n" + className);
            else if(!isAbstract && !isInterface)
                classNameLabel.setText(className);
            if(isInterface)
                classNameLabel.setText("<<Interface>>" + "\n" + className);
        }
    }
    
    
    public void addVariable(Variable newVar) {
        variables.add(newVar);
        variablesList.add(newVar);
    }

    public Variable addVariable() {
        variableCount ++;
        Variable newVar = new Variable();
        variables.add(newVar);
        variablesList.add(newVar);
        newVar.setVarName(newVar.getName() + variableCount);
        updateVariableNames();
        return newVar;
    }
    
    public void removeVariable(Variable v) {
            variables.remove(v);
            variablesList.remove(v);
            variableCount --;
    }
    
    public void removeMethod(Method m) {
        methods.remove(m);
        methodList.remove(m);
        methodCount--;
        checkIfAbstract();
    }
    
    public void updateVariableNames() {
        variablesBox.getChildren().clear();
        for(Variable v: variables) {
            if(v != null) {
            String display = "";
            if(v.getAccessorType().equals("public"))
                display = display + "+";
            else if(v.getAccessorType().equals("private"))
                display = display + "-";
            else
                display = display + "#";
            if(v.getStatic())
                display = display + "$";           
            display = display + v.getName() + ":";
            display = display + v.getReturnType();
            Label newVarLabel = new Label(display);
            variablesBox.getChildren().add(newVarLabel);
            }  
        }
    }
    
    public void updateMethodNames() {
        methodsBox.getChildren().clear();
        int count;
        for(Method m: methods) {
            if(m != null) {
                HashMap<String, String> args = m.getArgs();
                Collection<String> keys = args.keySet();
                String display = "";
                if(m.getAccessorType().equals("public"))
                    display = display + "+";
                else if(m.getAccessorType().equals("private"))
                    display = display + "-";
                else
                    display = display + "#";
                if(m.getStatic())
                    display = display + "$";  
                display = display + m.getName();
                display = display + "(";
                if(args != null){
                    count = 1;
                    for(String k: keys){
                        
                        display = display + k + ": " + args.get(k);
                        if(count != args.size())
                            display = display + ", ";
                        count++;
                            
                    }
                }
                display = display + "):";
                display = display + m.getReturnType();
                if(m.getAbstract())
                    display = display + "{abstract}";
                Label newMethLabel = new Label(display);
                methodsBox.getChildren().add(newMethLabel);
            }
        }
    }
    public Method addMethod() {
        methodCount++;
        Method newMeth = new Method();
        methods.add(newMeth);
        methodList.add(newMeth);
        newMeth.setName(newMeth.getName() + methodCount);
        checkIfAbstract();
        update();
        return newMeth;

    }
    
    public void addMethod(Method newMeth) {
        methods.add(newMeth);
        methodList.add(newMeth);
        checkIfAbstract();
      
    }
    
    public ObservableList<Method> getMethod() {
        return methodList;
    }
    
    public ObservableList<Variable> getVariables() {
        return variablesList;
    }
    
    public void setVariableList( ObservableList<Variable> newVariables) {
        variablesList = newVariables;
    }
    
    
    public void setAbstract(boolean abs) {
        isAbstract = abs;
        update();            
    }
    
    public void setInterface(boolean inter) {
        isInterface = inter;
        if(classNameLabel != null) {
            if(isInterface)
            {
                classNameLabel.setText("<<Interface>> \n" + className);
            }
            else
                classNameLabel.setText(className);
        }
    }
    
    public boolean getAbstract() {
        return isAbstract;
    }
    
    public boolean getInterface() {
        return isInterface;
    }
    
    public void checkIfAbstract() {
        boolean value = false;
        if(classNameLabel != null) {
            for(Method m: methods) {
                if(m.getAbstract())
                    value = true;
            }
             if(value) {
                 isAbstract = value;
                 classNameLabel.setText("{abstract}" + "\n" +  className);
             }
             else if(!value) {
                 isAbstract = value;
                 classNameLabel.setText(className);
             }
        }
    }  
    public String getAccessorType() {
        return accessorType;
    }
    
    public void setAccessorType(String newType) {
        accessorType = newType;
    }
    
    public ArrayList<DraggableUMLBox> getParentList() {
        return parentList;
    }
    
    public void setAllHeight(double newHeight) {
        this.setMinHeight(newHeight);
        this.setMaxHeight(newHeight);
        this.setPrefHeight(newHeight);
        startY = newHeight;
    }
    
    public void setAllWidth(double newWidth) {
        this.setMinWidth(newWidth);
        this.setMaxWidth(newWidth);        
        this.setPrefWidth(newWidth);
        startX = newWidth;
       
    }
    
    public void update() {
        updateName();
        updateVariableNames();
        updateMethodNames();
    }
    
    public void setParentList (ArrayList<DraggableUMLBox> parents) {
        parentList = parents;
    }
}
