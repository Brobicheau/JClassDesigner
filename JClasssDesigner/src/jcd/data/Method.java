/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.data;

import java.util.HashMap;

/**
 *
 * @author brobi
 */
public class Method {
    
    static final String DEFAULT_NAME = "newMethod";
    static final String DEFAULT_RETURN_TYPE = "void";
    static final String DEFAULT_ACCESSOR_TYPE = "public";
    
    
    String name;
    String returnType;
    boolean isStatic;
    boolean isAbstract;
    String accessorType;
    HashMap<String, String> args;
    
    public Method(){
        
        name = DEFAULT_NAME;
        returnType = DEFAULT_RETURN_TYPE;
        accessorType = DEFAULT_ACCESSOR_TYPE;
        isStatic = false;
        isAbstract = false; 
        args = new HashMap();
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public void setReturnType(String newType) {
        returnType = newType;
    }
    
    public void setAccType(String newAccType) {
        accessorType = newAccType;
    }
    
    public void setAbstract (boolean abs) {
        isAbstract = abs;
    }
    
    public void setStatic (boolean stat){
        isStatic = stat;
    }
    
    public void addArg(String argName, String argType) {
        args.put(argName, argType);
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
    
    public boolean getAbstract(){
        return isAbstract;
    }
    
    public boolean getStatic () {
        return isStatic;
    }
    
    public HashMap<String,String> getArgs() {
        return args;
    }
    
    public void setArgs(HashMap<String, String> newArgs) {
        args = newArgs;
    }
    
}
