/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.test_bed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Stage;
import jcd.data.DataManager;
import jcd.data.DraggableLine;
import jcd.file.FileManager;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import jcd.data.DraggableUMLBox;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author brobi
 */
public class ThreadExampleTest extends Application{
    
        @Override
        public void start(Stage primaryStage) throws Exception{
        }    
    
    public ThreadExampleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }



    /**
     * Test of displayData method, of class ThreadExample.
     * @throws java.lang.Exception
     */
    

    
        @Test
        
    public void testThreadExample() throws Exception {

        Stage primaryStage = null;
        ThreadExample instance = new ThreadExample();
        instance.start(primaryStage);        
        String filePath = "work\\DesignSaveTest.Json";
        System.out.println("ThreadExample");
        DataManager data = new DataManager();
        FileManager fileManager = new FileManager();
        instance.start(primaryStage);
        ObservableList<Node> originalBoxes = instance.getBoxes();
        ArrayList<Node> originalLines = instance.getLines();
        data.setBoxes((ObservableList)originalBoxes);
        data.setLines(originalLines);
        fileManager.saveData(data, filePath);
        data.reset();
        fileManager.loadData(data, filePath);
        for(int i = 0; i < 1; i++){
            ObservableList<Node> newBoxes = data.getBoxes();
            ArrayList<Node> newLines = data.getLines();
            DraggableUMLBox newBox = (DraggableUMLBox)newBoxes.get(i);
            HashMap<String, String> args = newBox.getMethod().get(i).getArgs();
            Collection<String> keys = args.keySet();
            ArrayList<String> argNames = new ArrayList();
            ArrayList<String> argTypes = new ArrayList();             
            for(String k : keys) {
                argNames.add(k);
                argTypes.add(args.get(k));
            }
            DraggableLine newLine = (DraggableLine)newLines.get(i);
            assertEquals("Class Name", "ThreadExample", newBox.getClassName());
            assertEquals("X Position", 0, newBox.getLayoutX(), 0);
            assertEquals("Y Position", 200.0, newBox.getLayoutY(), 0);     
            assertFalse("Abstract", newBox.getAbstract());
            assertFalse("Interface", newBox.getInterface());
            assertEquals("Variable Name", "START_TEXT", newBox.getVariables().get(i).getName());
            assertEquals("Variable Type", "String", newBox.getVariables().get(i).getReturnType());
            assertEquals("Method Name", "start", newBox.getMethod().get(i).getName());
            assertEquals("Method Type", "private", newBox.getMethod().get(i).getAccessorType());
            assertEquals("Argument Name 1", "primaryStage", argNames.get(0));
            assertEquals("Argument Type 1", "Stage", argTypes.get(0));
            assertEquals("Argument Name 2", "test", argNames.get(1));
            assertEquals("Argument Type 2", "String", argTypes.get(1));   
            assertFalse("AbstractMethod", newBox.getMethod().get(i).getAbstract());
            assertEquals("Line Start Point", 10, newLine.getStartPoint(), 0);
            assertEquals("Line Left End Point", 20, newLine.getLeftEndPoint(), 0);
            assertEquals("Line Right End Point", 30, newLine.getRightEndPoint(), 0);              
        
        }

    }
    
        @Test
        
    public void testThreadExampleAbstract()throws Exception {
        Stage primaryStage = null;
        String filePath = "work\\DesignSaveTest.Json";
        
        System.out.println("ThreadExampleAbstract");
        DataManager data = new DataManager();
        FileManager fileManager = new FileManager();
        ThreadExampleAbstract instance = new ThreadExampleAbstract();
        instance.start(primaryStage);
        ObservableList<Node> originalBoxes = instance.getBoxes();
        ArrayList<Node> originalLines = instance.getLines();
        data.setBoxes((ObservableList)originalBoxes);
        data.setLines(originalLines);
        fileManager.saveData(data, filePath);
        data.reset();
        fileManager.loadData(data, filePath);
        for(int i = 0; i < 1; i++){
            ObservableList<Node> newBoxes = data.getBoxes();
            ArrayList<Node> newLines = data.getLines();
            DraggableUMLBox newBox = (DraggableUMLBox)newBoxes.get(i);
            HashMap<String, String> args = newBox.getMethod().get(i).getArgs();
            Collection<String> keys = args.keySet();
            ArrayList<String> argNames = new ArrayList();
            ArrayList<String> argTypes = new ArrayList();             
            for(String k : keys) {
                argNames.add(k);
                argTypes.add(args.get(k));
            }
            DraggableLine newLine = (DraggableLine)newLines.get(i);
            assertEquals("Class Name", "ThreadExampleAbstract", newBox.getClassName());
            assertEquals("X Position", 100, newBox.getLayoutX(), 0);
            assertEquals("Y Position", 100, newBox.getLayoutY(), 0);     
            assertTrue("Abstract", newBox.getAbstract());
            assertFalse("Interface", newBox.getInterface());
            assertEquals("Variable Name", "START_TEXT", newBox.getVariables().get(i).getName());
            assertEquals("Variable Type", "String", newBox.getVariables().get(i).getReturnType());
            assertEquals("Method Name", "start", newBox.getMethod().get(i).getName());
            assertEquals("Method Type", "private", newBox.getMethod().get(i).getAccessorType());
            assertEquals("Argument Name 1", "primaryStage", argNames.get(0));
            assertEquals("Argument Type 1", "Stage", argTypes.get(0));
            assertEquals("Argument Name 2", "absTest", argNames.get(1));
            assertEquals("Argument Type 2", "String", argTypes.get(1));   
            assertTrue("AbstractMethod", newBox.getMethod().get(i).getAbstract());
            assertEquals("Line Start Point", 10, newLine.getStartPoint(), 0);
            assertEquals("Line Left End Point", 20, newLine.getLeftEndPoint(), 0);
            assertEquals("Line Right End Point", 30, newLine.getRightEndPoint(), 0);              
        }
    }
        
    @Test
    
    public void testThreadExampleInterface()throws Exception {
        Stage primaryStage = null;
        String filePath = "work\\DesignSaveTest.Json";
        
        System.out.println("ThreadExampleInterface");
        DataManager data = new DataManager();
        FileManager fileManager = new FileManager();
        ThreadExampleInterface instance = new ThreadExampleInterface();
        instance.start(primaryStage);
        ObservableList<Node> originalBoxes = instance.getBoxes();
        ArrayList<Node> originalLines = instance.getLines();
        data.setBoxes((ObservableList)originalBoxes);
        data.setLines(originalLines);
        fileManager.saveData(data, filePath);
        data.reset();
        fileManager.loadData(data, filePath);
        for(int i = 0; i < 1; i++){
            ObservableList<Node> newBoxes = data.getBoxes();
            ArrayList<Node> newLines = data.getLines();
            DraggableUMLBox newBox = (DraggableUMLBox)newBoxes.get(i);
            HashMap<String, String> args = newBox.getMethod().get(i).getArgs();
            Collection<String> keys = args.keySet();
            ArrayList<String> argNames = new ArrayList();
            ArrayList<String> argTypes = new ArrayList();             
            for(String k : keys) {
                argNames.add(k);
                argTypes.add(args.get(k));
            }
            DraggableLine newLine = (DraggableLine)newLines.get(i);
            assertEquals("Class Name", "ThreadExampleInterface", newBox.getClassName());
            assertEquals("X Position", 100, newBox.getLayoutX(), 0);
            assertEquals("Y Position", 100, newBox.getLayoutY(), 0);     
            assertTrue("Interface", newBox.getInterface());
            assertFalse("Abstract", newBox.getAbstract());
            assertEquals("Variable Name", "START_TEXT", newBox.getVariables().get(i).getName());
            assertEquals("Variable Type", "String", newBox.getVariables().get(i).getReturnType());
            assertEquals("Method Name", "start", newBox.getMethod().get(i).getName());
            assertEquals("Method Type", "private", newBox.getMethod().get(i).getAccessorType());
            assertEquals("Argument Name 1", "primaryStage", argNames.get(0));
            assertEquals("Argument Type 1", "Stage", argTypes.get(0));
            assertEquals("Argument Name 2", "interTest", argNames.get(1));
            assertEquals("Argument Type 2", "String", argTypes.get(1));   
            assertFalse("AbstractMethod", newBox.getMethod().get(i).getAbstract());            
            assertEquals("Line Start Point", 10, newLine.getStartPoint(), 0);
            assertEquals("Line Left End Point", 20, newLine.getLeftEndPoint(), 0);
            assertEquals("Line Right End Point", 30, newLine.getRightEndPoint(), 0);            
    }
   }
}
