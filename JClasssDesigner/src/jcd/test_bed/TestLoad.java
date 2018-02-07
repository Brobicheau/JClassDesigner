/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.test_bed;

import java.util.HashMap;
import java.util.Set;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jcd.data.DataManager;
import jcd.data.DraggableUMLBox;
import jcd.data.Method;
import jcd.data.Variable;
import jcd.file.FileManager;

/**
 *
 * @author brobi
 */
public class TestLoad  {


        

    
    public static void main(String [] args) throws Exception {
        FileManager file = new FileManager();
        DataManager data = new DataManager();
        Pane p = new Pane();
        data.setBoxes(p.getChildren());
        String filePath = "work\\DesignSaveTest.Json";        
        file.loadData(data, filePath);
        ObservableList<Node> boxList;
        boxList = data.getBoxes();
        showLoadComplete(boxList);
        
        
    }
        
        private static void showLoadComplete(ObservableList boxList){
            for(Object node: boxList) {
                DraggableUMLBox box = (DraggableUMLBox)node;
                System.out.println(box.getClassName());
                System.out.println(box.getPackageName());
                System.out.println(box.getLayoutX());
                System.out.println(box.getLayoutY());
                System.out.println(box.getAbstract());
                System.out.println(box.getWidth());
                System.out.println(box.getHeight());
                ObservableList<Variable> varList = box.getVariables();
                for(Variable v: varList){
                    System.out.println(v.getAccessorType());
                    System.out.println(v.getName());
                    System.out.println(v.getReturnType());
                    System.out.println(v.getStatic());
                }
                ObservableList<Method> methList = box.getMethod();
                for(Method m: methList) {
                    System.out.println(m.getAbstract());
                    System.out.println(m.getAccessorType());
                    System.out.println(m.getName());
                    System.out.println(m.getReturnType());
                    System.out.println(m.getStatic());
                    HashMap<String, String> args = m.getArgs();
                    Set<String> keys = args.keySet();
                    for(String k: keys) {
                        System.out.println(k);
                        System.out.println(args.get(k));
                    }
                }
            }
        }
    
}
