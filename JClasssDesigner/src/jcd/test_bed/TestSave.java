/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.test_bed;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Stage;
import jcd.data.DataManager;
import jcd.file.FileManager;

/**
 *
 * @author brobi
 */
public class TestSave  {
    


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                try {
            ObservableList<Node> boxList;
            ArrayList<Node> lineList;
            FileManager fileManager = new FileManager();
            ThreadExample threadExample;
            String filePath = "work\\DesignSaveTest.Json";        
            DataManager dataManager = new DataManager();
            threadExample = new ThreadExample();
            lineList = threadExample.getLines();
            boxList = threadExample.getBoxes();    
            dataManager.setBoxes(boxList);
            dataManager.setLines(lineList);
            fileManager.saveData(dataManager, filePath);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}


