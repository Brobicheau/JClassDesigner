/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.test_bed;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jcd.data.DataManager;
import jcd.data.DraggableLine;
import jcd.data.DraggableUMLBox;
import jcd.data.Method;
import jcd.data.Variable;
import jcd.file.FileManager;

/**
 *
 * @author brobi
 */
public class ThreadExample extends Application {
    ObservableList<Node> boxList;
    ArrayList<Node> newBoxList;
    ArrayList<Node> lineList;
    DataManager data;
    ObservableList<Node> boxes;
    FileManager fileManager;
    
        @Override
    public void start(Stage primaryStage) {
      
    }
    
    public ThreadExample()throws Exception{
        
        Pane p = new Pane();
        boxList = p.getChildren();
        newBoxList = new ArrayList();
        lineList = new ArrayList();
        data = new DataManager();
        makeThreadExample();
        makeCounterTask();
        makeDateTask();
        makePauseHandler();
        makeStartHandler();
        makeEventHandler();
        makeApplication();
        makeClassBoxes();
        
        
    }
    
    public ArrayList<Node> getLines() {
        return lineList;
    }
    
    public ObservableList<Node> getBoxes() {
        return boxList;
    }
    public ArrayList<Node> getNewBoxes() {
        return newBoxList;
    }
    public ObservableList displayData() {
       return boxes;
    }
    
    private void makeClassBoxes() {
        DraggableUMLBox stringBox = new DraggableUMLBox(false);
        stringBox.setClassName("String");
        stringBox.setPackageName("java.lang");
        stringBox.setPartOfProject(false);
        
        stringBox.setLayoutX(180);
        stringBox.setLayoutY(100);
        boxList.add(stringBox);
        
        DraggableUMLBox stageBox = new DraggableUMLBox(false);
        stageBox.setClassName("Stage");
        stageBox.setPackageName("javafx.stage");
        stageBox.setPartOfProject(false);
        stageBox.setLayoutX(260);
        stageBox.setLayoutY(100);
        boxList.add(stageBox);
        
        DraggableUMLBox borderPaneBox = new DraggableUMLBox(false);
        borderPaneBox.setClassName("BorderPane");
        borderPaneBox.setPackageName("javafx.scene.layout");
        borderPaneBox.setPartOfProject(false);
        borderPaneBox.setLayoutX(340);
        borderPaneBox.setLayoutY(100);
        boxList.add(borderPaneBox);
        
        DraggableUMLBox flowPaneBox = new DraggableUMLBox(false);
        flowPaneBox.setClassName("FlowPane");
        flowPaneBox.setPackageName("javafx.scene.layout");
        flowPaneBox.setPartOfProject(false);
        flowPaneBox.setLayoutX(420);
        flowPaneBox.setLayoutY(100);
        boxList.add(flowPaneBox);
        
        DraggableUMLBox buttonBox = new DraggableUMLBox(false);
        buttonBox.setClassName("Button");
        buttonBox.setPackageName("javafx.scene.control");
        buttonBox.setPartOfProject(false);
        buttonBox.setLayoutX(500);
        buttonBox.setLayoutY(100);
        boxList.add(buttonBox);
        
        DraggableUMLBox scrollPaneBox = new DraggableUMLBox(false);
        scrollPaneBox.setClassName("ScrollPane");
        scrollPaneBox.setPackageName("javafx.scene.layout");
        scrollPaneBox.setPartOfProject(false);
        scrollPaneBox.setLayoutX(580);
        scrollPaneBox.setLayoutY(100);
        boxList.add(scrollPaneBox);
        
        DraggableUMLBox textAreaBox = new DraggableUMLBox(false);
        textAreaBox.setClassName("TextArea");
        textAreaBox.setPackageName("javafx.scene.conroll");
        textAreaBox.setPartOfProject(false);
        textAreaBox.setLayoutX(660);
        textAreaBox.setLayoutY(100);
        boxList.add(textAreaBox);
        
        DraggableUMLBox threadBox = new DraggableUMLBox(false);
        threadBox.setClassName("Thread");
        threadBox.setPackageName("java.lang");
        threadBox.setPartOfProject(false);
        threadBox.setLayoutX(740);
        threadBox.setLayoutY(100);
        boxList.add(threadBox);
        
        DraggableUMLBox taskBox = new DraggableUMLBox(false);
        taskBox.setClassName("Task");
        taskBox.setPackageName("javafx.concurrent");
        taskBox.setPartOfProject(false);
        taskBox.setLayoutX(820);
        taskBox.setLayoutY(100);
        boxList.add(taskBox);
        
        DraggableUMLBox stateBox = new DraggableUMLBox(false);
        stateBox.setClassName("State");
        stateBox.setPackageName("java.lang.Thread");   
        stateBox.setPartOfProject(false);
        stateBox.setLayoutX(900);
        stateBox.setLayoutY(100);
        boxList.add(stateBox);
        
        DraggableUMLBox dateBox = new DraggableUMLBox(false);
        dateBox.setClassName("Date");
        dateBox.setPackageName("java.util");
        dateBox.setPartOfProject(false);
        dateBox.setLayoutX(980);
        dateBox.setLayoutY(100);
        boxList.add(dateBox);
        
        DraggableUMLBox platBox = new DraggableUMLBox(false);
        platBox.setClassName("Platform");
        platBox.setPackageName("javafx.application");
        platBox.setPartOfProject(false);
        platBox.setLayoutX(1060);
        platBox.setLayoutY(100);
        boxList.add(platBox);
        
        DraggableUMLBox eventBox = new DraggableUMLBox(false);
        eventBox.setClassName("Event");
        eventBox.setPackageName("javafx.event");
        eventBox.setPartOfProject(false);
        eventBox.setLayoutX(1140);
        eventBox.setLayoutY(100);
        boxList.add(eventBox);
    }
    
    private void makeThreadExample() {
        
        DraggableLine line = new DraggableLine();
        lineList.add(line);
        
        DraggableUMLBox box = new DraggableUMLBox();
        box.setLayoutX(0);
        box.setLayoutY(200.0);
        box.setPackageName("te");
        box.setClassName("ThreadExample");
        
        Variable startText = new Variable();
        startText.setReturnType("String");
        startText.setStatic(true);
        startText.setVarName("START_TEXT");
        box.addVariable(startText);
        
        
        Variable pauseText = new Variable();
        pauseText.setReturnType("String");
        pauseText.setStatic(true);
        pauseText.setVarName("PAUSE_TEXT");
        box.addVariable(pauseText);
        
        Variable window = new Variable();
        window.setAccessorType("private");
        window.setReturnType("Stage");
        window.setVarName("window");
        
        Variable appPane = new Variable();
        appPane.setAccessorType("private");
        appPane.setReturnType("BorderPane");
        appPane.setVarName("appPane");
        box.addVariable(appPane);
        
        Variable topPane = new Variable();
        topPane.setVarName("topPane");
        topPane.setAccessorType("private");
        topPane.setReturnType("FlowPane");
        box.addVariable(topPane);
        
        Variable startButton = new Variable();
        startButton.setAccessorType("private");
        startButton.setReturnType("Button");
        startButton.setVarName("startButton");
        box.addVariable(startButton);
        
        Variable pauseButton = new Variable();
        pauseButton.setVarName("pauseButton");
        pauseButton.setAccessorType("private");
        pauseButton.setReturnType("Button");
        box.addVariable(pauseButton);
        
        Variable scrollPane = new Variable();
        scrollPane.setVarName("scrollPane");
        scrollPane.setReturnType("ScrollPane");
        scrollPane.setAccessorType("private");
        box.addVariable(scrollPane);
        
        Variable textArea = new Variable();
        textArea.setVarName("textArea");
        textArea.setReturnType("TextArea");
        textArea.setAccessorType("private");
        box.addVariable(textArea);
        
        Variable dateThread = new Variable();
        dateThread.setAccessorType("private");
        dateThread.setVarName("dateThread");
        dateThread.setReturnType("Thread");
        box.addVariable(dateThread);
        
        Variable dateTask = new Variable();
        dateTask.setAccessorType("private");
        dateTask.setReturnType("Task");
        dateTask.setVarName("dateTask");
        box.addVariable(dateTask);
        
        Variable counterThread = new Variable();
        counterThread.setAccessorType("private");
        counterThread.setReturnType("Thread");
        counterThread.setVarName("counterThread");
        box.addVariable(counterThread);
        
        Variable counterTask = new Variable();
        counterTask.setAccessorType("private");
        counterTask.setReturnType("Task");
        counterTask.setVarName("counterTask");
        box.addVariable(counterTask);
        
        Variable work = new Variable();
        work.setAccessorType("private");
        work.setReturnType("boolean");
        work.setVarName("work");
        box.addVariable(work);

        
        Method start = new Method();
        start.setAccType("private");
        start.setName("start");
        start.addArg("primaryStage", "Stage");
        start.addArg("test", "String");
        box.addMethod(start);
        
        Method startWork = new Method();
        startWork.setName("startWork");
        box.addMethod(startWork);
        
        Method pauseWork = new Method();
        pauseWork.setName("pauseWork");
        box.addMethod(pauseWork);
        
        Method doWork = new Method();
        doWork.setName("doWork");
        doWork.setReturnType("boolean");
        box.addMethod(doWork);
        
        Method appendText = new Method();
        appendText.setName("appendText");
        appendText.addArg("textToAppend", "String");
        box.addMethod(appendText);
        
        Method sleep = new Method();
        sleep.setName("sleep");
        sleep.addArg("timeToSleep", "int");
        box.addMethod(sleep);
        
        Method initLayout = new Method();
        initLayout.setAccType("private");
        initLayout.setName("initLayout");
        box.addMethod(initLayout);
        
        Method initHandlers = new Method();
        initHandlers.setAccType("private");
        initHandlers.setName("initHandlers");
        box.addMethod(initHandlers);
        
        Method initWindow = new Method();
        initWindow.setName("initWindow");
        initWindow.setAccType("private");
        initWindow.addArg("initPrimaryStage", "Stage");
        box.addMethod(initWindow);
        
        Method initThreads = new Method();
        initThreads.setName("initThreads");
        initThreads.setAccType("private");
        box.addMethod(initThreads);
        
        Method main = new Method();
        main.setName("main");
        main.setStatic(true);
        main.addArg("args", "String[]");
        box.addMethod(main);
        
        boxList.add(box);
        
    }
    
    private void makeCounterTask() {
        DraggableUMLBox box = new DraggableUMLBox();
        box.setLayoutX(200.0);
        box.setLayoutY(200.);        
        box.setPackageName("te.task");
        box.setClassName("CounterTask");
        
        Variable app = new Variable();
        app.setVarName("app");
        app.setReturnType("ThreadExample");
        app.setAccessorType("private");
        box.addVariable(app);
        
        Variable counter = new Variable();
        counter.setAccessorType("private");
        counter.setVarName("counter");
        counter.setReturnType("int");
        box.addVariable(counter);
        
        Method counterTask = new Method();
        counterTask.setName("CounterTask");
        counterTask.addArg("initApp", "ThreadExample");
        box.addMethod(counterTask);
        
        Method call = new Method();
        call.setAccType("protected");
        call.setName("call");
        box.addMethod(call);
        
        boxList.add(box);
    }
    
    private void makeDateTask() {
        
        DraggableUMLBox box = new DraggableUMLBox();
        box.setLayoutX(400.0);
        box.setLayoutY(200.0);        
        box.setClassName("DateTask");
        box.setPackageName("te.task");
        
        Variable app = new Variable();
        app.setVarName("app");
        app.setAccessorType("private");
        app.setReturnType("ThreadExample");
        box.addVariable(app);
        
        Variable now = new Variable();
        now.setVarName("now");
        now.setReturnType("Date");
        now.setAccessorType("private");
        box.addVariable(now);
        
        Method dateTask = new Method();
        dateTask.setName("DateTask");
        dateTask.addArg("initApp", "ThreadExample");
        box.addMethod(dateTask);
        
        Method call = new Method();
        call.setAccType("protected");
        call.setName("call");
        box.addMethod(call);
        
        boxList.add(box);
    }
    
    private void makePauseHandler() {
        DraggableUMLBox box = new DraggableUMLBox();
        
        box.setLayoutX(600.0);
        box.setLayoutY(200.0);
        box.setClassName("PauseHandler");
        box.setPackageName("te.handlers");
        
        Variable app = new Variable();
        app.setVarName("app");
        app.setAccessorType("private");
        app.setReturnType("ThreadExample");
        box.addVariable(app);
        
        Method pauseHandler = new Method();
        pauseHandler.setName("PauseHandler");
        pauseHandler.addArg("initApp", "ThreadExample");
        box.addMethod(pauseHandler);
        
        Method handle = new Method();
        handle.setName("handle");
        handle.addArg("event", "Event");
        box.addMethod(handle);
        
        boxList.add(box);
        
    }
    
    private void makeStartHandler() {
        DraggableUMLBox box = new DraggableUMLBox();
        
        box.setLayoutX(800.0);
        box.setLayoutY(200.0);
        box.setClassName("StartHandler");
        box.setPackageName("te.handlers");
        
        Variable app = new Variable();
        app.setVarName("app");
        app.setAccessorType("private");
        app.setReturnType("ThreadExample");
        box.addVariable(app);
        
        Method startHandler = new Method();
        startHandler.setName("StartHandler");
        startHandler.addArg("initApp", "ThreadExample");
        box.addMethod(startHandler);
        
        Method handle = new Method();
        handle.setName("handle");
        handle.addArg("event", "Event");
        box.addMethod(handle);
        
        boxList.add(box);
    }
    
    private void makeEventHandler() {
        DraggableUMLBox box = new DraggableUMLBox();
    
        box.setLayoutX(1000.0);
        box.setLayoutY(200.0);        
        box.setClassName("EventHandler");
        box.setPackageName("te.handlers.moreHandlers");
        box.setInterface(true);
        
        Method handle = new Method();
        handle.setName("handle");
        handle.addArg("event", "Event");
        
        box.addMethod(handle);
        
        boxList.add(box);
        
        
    }
    
    private void makeApplication() {
        
        DraggableUMLBox box = new DraggableUMLBox();
        
        box.setLayoutX(0);
        box.setLayoutY(400.0);
        box.setClassName("Application");
        box.setPackageName("te.app");
        
        Method start = new Method();
        start.setName("start");
        start.setAbstract(true);
        start.addArg("primaryStage", "Stage");
        box.addMethod(start);
        
        
        
        boxList.add(box);
        
        
    }   
}


