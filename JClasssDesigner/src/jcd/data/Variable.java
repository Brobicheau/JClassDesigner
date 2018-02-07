/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import javafx.scene.Node;

/**
 *
 * @author brobi
 */
public class Variable  {
    
    static final String DEFAULT_NAME = "newVar";
    static final String DEFAULT_RETURN_TYPE = "int";
    static final String DEFAULT_ACCESSOR_TYPE = "public";
    String name;
    String returnType;
    boolean isStatic;
    String accessorType;
    
    public Variable() {
        name = DEFAULT_NAME;
        returnType = DEFAULT_RETURN_TYPE;
        accessorType = DEFAULT_ACCESSOR_TYPE;
        isStatic = false;
    }
    
    public void setVarName(String newName) {
        name = newName;
    }
    
    public void setReturnType(String newRetType) {
        returnType = newRetType;
    }
    
    public void setAccessorType(String newAccType) {
        accessorType = newAccType;
    }
    
    public void setStatic(boolean staticSetting) {
        isStatic = staticSetting;
    }
    
    public String getName() {
        return name;
    }
    
    public String getReturnType() {
        return returnType;
    }
    
    public String getAccessorType() {
        return accessorType;
    }
    
    public boolean getStatic() {
        return isStatic;
    }
    
}
