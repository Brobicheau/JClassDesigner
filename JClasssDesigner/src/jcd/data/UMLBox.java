/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import saf.AppTemplate;

/**
 *
 * @author brobi
 */
public class UMLBox extends GridPane {
    
    static final int HEIGHT_RESIZE_MARGIN = 5;
    static final int WIDTH_RESIZE_MARGIN = 5;
    static final int STARTING_HEIGHT = 200;
    static final int STARTING_WIDTH = 200;
    static final int STARTING_X = 100;
    static final int STARTING_Y = 100;
    static final String DEFAULT_CLASS_NAME = "New Class";
    static final String DEFAULT_PACKAGE_NAME =  "Package Not Defined";
    static final String UML_STYLE = "uml_box";

    DataManager dataManager;
    double startX;
    double startY;
    String className;
    String packageName;
    String accessorType;
    ObservableList<Variable> variablesList;
    ObservableList<Method> methodList;
    ArrayList<Variable> variables;
    ArrayList<Method> methods;
    HashMap<String, Integer> parentList;
    boolean isAbstract;
    boolean isInterface;
    boolean isPartOfProject;
    AppTemplate app;
    int endX;
    int endY;
    

    
    public UMLBox() {
        setLayoutX(100);
        setLayoutY(100);
        setMinWidth(STARTING_WIDTH);
        setMinHeight(STARTING_HEIGHT);
        setMaxHeight(STARTING_WIDTH);
        setMaxWidth(STARTING_WIDTH);
        this.setPrefHeight(STARTING_HEIGHT);
        this.setPrefWidth(STARTING_WIDTH);
        startX = STARTING_X;
        startY = STARTING_Y;
        className = DEFAULT_CLASS_NAME;
        packageName = DEFAULT_PACKAGE_NAME;
        variables = new ArrayList();
        methods = new ArrayList();
        methodList = FXCollections.observableArrayList(methods);
        variablesList = FXCollections.observableArrayList(variables);
        accessorType = "public";
        isAbstract = false;
        isInterface = false;
        isPartOfProject = true;

        
    }
    
    public boolean getInterface() {
        return isInterface;
    }
    
    public void setInterface(boolean inter){
        isInterface = inter;
    }
    

    public void setClassName(String newName) {
        className = newName;
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
    
    
    
    public void addVariable(Variable newVar) {
        variables.add(newVar);
        variablesList.add(newVar);
    }

    public void addVariable() {
        Variable newVar = new Variable();
        variables.add(newVar);
    }
    public void addMethod() {
        Method newMeth = new Method();
        methods.add(newMeth);

    }
    
    public void addMethod(Method newMeth) {
        methods.add(newMeth);
        methodList.add(newMeth);
      
    }
    
    public ObservableList<Method> getMethod() {
        return methodList;
    }
    
    public ObservableList<Variable> getVariables() {
        return variablesList;
    }
    
    
    public void setAbstract(boolean abs) {
        isAbstract = abs;
            
    }
    
    public boolean getAbstract() {
        return isAbstract;
    }
    

    
    public String getAccessorType() {
        return accessorType;
    }
    
    public void setAccessorType(String newType) {
        accessorType = newType;
    }  
    
    public void setPartOfProject(boolean partOf) {
        isPartOfProject = partOf;
    }
    
    public boolean getIsPartOfProject() {
        return isPartOfProject;
    }
}
