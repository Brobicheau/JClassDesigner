/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.gui;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import static jcd.PropertyType.ADD_CLASS_TOOLTIP;
import static jcd.PropertyType.*;
import jcd.controller.BoxController;
import jcd.controller.HistoryController;
import jcd.controller.JClassEditController;
import jcd.data.DataManager;
import jcd.data.Draggable;
import jcd.data.DraggableUMLBox;
import saf.ui.AppGUI;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class Workspace extends AppWorkspaceComponent {

    static final String TOOLBAR_BUTTON = "toolbar_button";
    static final String EDIT_PANE_STYLE = "edit_pane";
    static final String BORDERED_PANE_STYLE = "bordered_pane";
    static final String CANVAS_STYLE = "canvas_style";
    static final String GRID_LABEL_TEXT = "Grid";
    static final String SNAP_LABEL_TEXT = "Snap";
    static final String CLASS_NAME_LABEL = "Class Name: ";
    static final String PACKAGE_NAME_LABEL = "Package: ";
    static final String PARENTS_LABEL = "Parent: ";
    static final String VARIABLES_LABEL = "Variables ";
    static final String METHODS_LABEL = "Methods ";
    static final String VARIABLES_METHODS_STYLE = "variables_methods_style";
    static final String GRID_NODES_STYLE = "grid_node_style";
    static final String VAR_MET_SEG_STYLE = "var_met_seg_style";
    static final String CANVAS_GRID_STYLE = "canvas_grid_style";
    static final String LIST_PANE = "list_pane";
    static final int VMBOX_HEIGHT = 200;
    static final int VMBOX_WIDTH = 300;
    
    
    
    ObservableList<DraggableUMLBox> parents = FXCollections.observableArrayList();
    
    
    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    JClassEditController classEditController;
    BoxController boxController;
    HistoryController historyController;
            
    SplitPane workspaceSplitPane;
    
    Pane canvas;
    ScrollPane canvasContainer;
    VBox editPane;
    
    
    HBox editToolbar;
    HBox sceneToolbar;
    
    VBox gridLabelNode;
    VBox gridNode;
    
    ScrollPane variableBase;
    ScrollPane methodBase;
    VBox variablePane;
    HBox variableAddOrRemoveNode;
    VBox methodPane;
    HBox methodAddOrRemoveNode;
    
    Button selectButton;
    Button resizeButton;
    Button addClassButton;
    Button addInterfaceButton;
    Button removeButton;
    Button undoButton;
    Button redoButton;
    Button zoomInButton;
    Button zoomOutButton;
    Button addVariableButton;
    Button removeVariableButton;
    Button addMethodButton;
    Button removeMethodButton;
    Button editParentButton;
    Button addParentButton;
    
    CheckBox gridLinesBox;
    Label gridLabel;
    CheckBox gridSnapBox;
    Label snapLabel;         
    
    Label classNameLabel;
    Label packageNameLabel;
    Label parentNameLabel;
    Label variableLabel;
    Label methodLabel;
    
    TextField classNameField;
    TextField packageNameField;
    
    
    TableView variableTable;
    TableColumn variableNameColumn;
    TableColumn variableAccessorColumn;
    TableColumn variableStaticColumn;
    TableColumn variableTypeColumn;
    
    TableView methodTable;
    TableColumn methodNameColumn;
    TableColumn methodReturnTypeColumn;
    TableColumn methodAccessorColumn;
    TableColumn methodStaticColumn;
    TableColumn methodAbstractColumn;
    TableColumn methodArgsColumn;
    
    ListView parentView;
    ComboBox parentPicker;
    
    VariableEditPopupSingleton variableEditor;
    MethodEditPopupSingleton methodEditor;
    ParentEditPopupSingleton parentEditor;
    
    VBox parentPickerButton;
    HBox parentNode;
     ProgressBar p = new ProgressBar(0);

    boolean updating;
    
    //TableView variableView;
   // TableView methodView;
    

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public Workspace(AppTemplate initApp) throws IOException {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();
    variableEditor = VariableEditPopupSingleton.getSingleton();
    variableEditor.init(app.getStage());        
    methodEditor = MethodEditPopupSingleton.getSingleton();
    methodEditor.init(app.getStage());   
        layoutGUI();
        setupHandlers();
        
    }

    private void layoutGUI(){

        HBox fileToolbar = gui.getFileToolbar();
        HBox toolbar = gui.getToolbar();
        workspaceSplitPane = new SplitPane();
        
        parentPickerButton = new VBox();
        parentNode = new HBox();
        
        //toolbar stuff
        editToolbar = new HBox();
        sceneToolbar = new HBox();
        canvasContainer = new ScrollPane();
        
        //grid editing node stuff
        gridNode = new VBox();       
        gridLinesBox = new CheckBox();
        gridSnapBox = new CheckBox();
        gridLabelNode = new VBox();
        gridLabel = new Label(GRID_LABEL_TEXT);
        snapLabel = new Label(SNAP_LABEL_TEXT);
        
        variableTable = new TableView();
        methodTable = new TableView();

        //editing pane stuff
        variableBase = new ScrollPane();
        methodBase = new ScrollPane();
        editPane = new VBox();
        variablePane = new VBox();
        variableAddOrRemoveNode = new HBox();
        methodPane = new VBox();
        methodAddOrRemoveNode = new HBox();
        parentView = new ListView();
        parentPicker = new ComboBox();
        editParentButton = new Button("Edit Parents");
//        variableView = new TableView();
//        methodView = new TableView();
        
        //canvas pane
        canvas = new Pane();
        
        
        //TOOLBARS
        selectButton = gui.initChildButton(editToolbar, SELECT_TOOL_ICON.toString(), SELECT_TOOL_TOOLTIP.toString(), false);
        resizeButton = gui.initChildButton(editToolbar, RESIZE_ICON.toString(), RESIZE_TOOLTIP.toString(), false);
        addClassButton = gui.initChildButton(editToolbar, ADD_CLASS_ICON.toString(), ADD_CLASS_TOOLTIP.toString(), false);
        addInterfaceButton = gui.initChildButton(editToolbar, ADD_INTERFACE_ICON.toString(), ADD_INTERFACE_TOOLTIP.toString(),false);
        removeButton = gui.initChildButton(editToolbar, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), false);
        undoButton = gui.initChildButton(editToolbar, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), false);
        redoButton = gui.initChildButton(editToolbar, REDO_ICON.toString(), REDO_TOOLTIP.toString(), false);
        
        zoomOutButton = gui.initChildButton(sceneToolbar, ZOOMIN_ICON.toString(), ZOOMIN_TOOLTIP.toString(), false);
        zoomInButton = gui.initChildButton(sceneToolbar, ZOOMOUT_ICON.toString(), ZOOMOUT_TOOLTIP.toString(), false);
        
        gridNode.getChildren().add(gridLinesBox);
        gridNode.getChildren().add(gridSnapBox);
        gridLabelNode.getChildren().add(gridLabel);
        gridLabelNode.getChildren().add(snapLabel);
        sceneToolbar.getChildren().add(gridNode);
        sceneToolbar.getChildren().add(gridLabelNode);
        
        double t = toolbar.getWidth();
        fileToolbar.prefWidthProperty().set(t/3);
        editToolbar.prefWidthProperty().set(t/3);
        sceneToolbar.prefWidthProperty().set(t/3);
        toolbar.getChildren().add(editToolbar);
        toolbar.getChildren().add(sceneToolbar);
        
        // EDIT PANE
        
        //Class name field
        classNameLabel = new Label(CLASS_NAME_LABEL);
        classNameField = new TextField();
        
        //package name field
        packageNameLabel = new Label(PACKAGE_NAME_LABEL);
        packageNameField = new TextField();
        
        //parent name field
        parentNameLabel = new Label(PARENTS_LABEL);

        
        //Variable field
        variableLabel = new Label(VARIABLES_LABEL);
        variableAddOrRemoveNode.getChildren().add(variableLabel);
        addVariableButton = gui.initChildButton(variableAddOrRemoveNode, ADD_VARIABLE_ICON.toString(), ADD_VARIABLE_TOOLTIP.toString(), false);
        removeVariableButton = gui.initChildButton(variableAddOrRemoveNode, REMOVE_VARIABLE_ICON.toString(), REMOVE_VARIABLE_TOOLTIP.toString(), false);
        variableBase.setContent(variablePane);
        variableBase.setMaxWidth(VMBOX_WIDTH);
        variableBase.setPrefWidth(VMBOX_WIDTH);
        variableBase.setMinWidth(VMBOX_WIDTH);        
        variableBase.setMaxHeight(VMBOX_HEIGHT);
        variableBase.setPrefHeight(VMBOX_HEIGHT);
        variableBase.setMinHeight(VMBOX_HEIGHT);
        
        //Method field
        methodLabel = new Label(METHODS_LABEL);
        methodAddOrRemoveNode.getChildren().add(methodLabel);
        addMethodButton = gui.initChildButton(methodAddOrRemoveNode, ADD_METHOD_ICON.toString(), ADD_METHOD_TOOLTIP.toString(), false);
        removeMethodButton = gui.initChildButton(methodAddOrRemoveNode, REMOVE_METHOD_ICON.toString(), REMOVE_METHOD_TOOLTIP.toString(), false);
        methodBase.setContent(methodPane);
        methodBase.setMaxWidth(VMBOX_WIDTH);
        methodBase.setPrefWidth(VMBOX_WIDTH);
        methodBase.setMinWidth(VMBOX_WIDTH);        
        methodBase.setMaxHeight(VMBOX_HEIGHT);
        methodBase.setPrefHeight(VMBOX_HEIGHT);
        methodBase.setMinHeight(VMBOX_HEIGHT);    
        
        
        variableNameColumn = new TableColumn("Name");
        variableAccessorColumn = new TableColumn("Accessor");
        variableStaticColumn = new TableColumn("Static");
        variableTypeColumn = new TableColumn("Type");

        methodNameColumn = new TableColumn("Name");
        methodReturnTypeColumn = new TableColumn("Return Type");
        methodAccessorColumn = new TableColumn("Accessor");
        methodStaticColumn = new TableColumn("Static");
        methodAbstractColumn = new TableColumn("Abstract");
        methodArgsColumn = new TableColumn("Args");
        
        variableTable.getColumns().addAll(variableNameColumn, variableAccessorColumn, variableStaticColumn, variableTypeColumn);
        methodTable.getColumns().addAll(methodNameColumn, methodAccessorColumn, methodReturnTypeColumn, methodStaticColumn,methodAbstractColumn,methodArgsColumn   );

        
        
        
        
        
        addParentButton = new Button("Add/Remove Parent");
        Button b = new Button();
        b.setOnAction(e-> {
            for(int i = 0; i < 100; i++){
                System.out.print(i);
                p.setProgress((double)(i/100));
            }
         
 
        });
        editPane.getChildren().add(b);
        editPane.getChildren().add(p);        
        editPane.getChildren().add(classNameLabel);

        
        editPane.getChildren().add(classNameField);
        editPane.getChildren().add(packageNameLabel);
        editPane.getChildren().add(packageNameField);
        editPane.getChildren().add(parentNameLabel);
        
        addParentButton.setPrefHeight(20);
        addParentButton.setPrefWidth(150);
        parentPicker.setPrefHeight(20);
        parentPicker.setPrefWidth(150);
        parentView.setPrefWidth(150);
        parentPickerButton.getChildren().add(addParentButton);
        parentPickerButton.getChildren().add(parentPicker);
        parentNode.getChildren().add(parentPickerButton);
        parentNode.getChildren().add(parentView);
        editPane.getChildren().add(parentNode);
      //  editPane.getChildren().add(addParentButton);
       // editPane.getChildren().add(parentPicker);
       // editPane.getChildren().add(editParentButton);
       // editPane.getChildren().add(parentView);
        editPane.getChildren().add(variableAddOrRemoveNode);
        editPane.getChildren().add(variableBase);
        editPane.getChildren().add(methodAddOrRemoveNode);
        editPane.getChildren().add(methodBase);

        
        workspace = new BorderPane();
        
       // workspaceSplitPane.getItems().add(canvas);
       // workspaceSplitPane.getItems().add(editPane);
        //((BorderPane)workspace).setCenter(workspaceSplitPane);
               
       canvasContainer.setContent(canvas);
       canvasContainer.setHvalue(canvasContainer.getHmax()/2);
       canvasContainer.setVvalue(canvasContainer.getVmax()/2);
       canvas.setPrefHeight(1500);
       canvas.setMinHeight(1500);
       canvas.setMaxHeight(1500);
       canvas.setPrefWidth(2000);
       canvas.setMinWidth(2000);
       canvas.setMaxWidth(2000);

       
        ((BorderPane)workspace).setCenter(canvasContainer);
        ((BorderPane)workspace).setRight(editPane);
        canvas.prefWidthProperty().bind(editPane.widthProperty());
        canvas.prefHeightProperty().bind(editPane.heightProperty());
        DataManager data = (DataManager)app.getDataComponent();
        data.setBoxes(canvas.getChildren());
        data.setVariableList(variablePane.getChildren());
        data.setMethodList(methodPane.getChildren());
        
        initStyle();   
    }
    
    public VBox getEditPane() {
        return editPane;
    }
    
    public ObservableList<DraggableUMLBox> getParentList() {
        return parents;
    }
    
    private void setupHandlers() {
        
        classEditController = new JClassEditController(app);
        boxController = new BoxController(app);
        historyController = new HistoryController(app);
        
    
        
        
        selectButton.setOnAction(e->{
            classEditController.processSelectButton();
        });
        
        addInterfaceButton.setOnAction(e->{
            classEditController.processAddInterfaceButton();
        });
        
        addClassButton.setOnAction(e->{
           classEditController.processAddClassButton();
        });
        
        removeButton.setOnAction(e->{
            classEditController.processRemoveClassButtonRequest();
        });
        
        resizeButton.setOnAction(e->{
            classEditController.processResizeButton();
        });        
        
        addVariableButton.setOnAction(e->{
            classEditController.processAddVariableButton();
        });
        
        removeVariableButton.setOnAction(e->{
            classEditController.processRemoveVariableButton();
        });
        
        addMethodButton.setOnAction(e-> { 
            classEditController.processAddMethodButton();
        });
        
        removeMethodButton.setOnAction(e-> {
            classEditController.processRemoveMethodButton();
        });
        
        zoomInButton.setOnAction(e->{
            classEditController.processZoomInRequest();
        });
        
        zoomOutButton.setOnAction(e->{
            classEditController.processZoomOutRequest();
        
        });        
        
        gridLinesBox.setOnAction(e->{
            classEditController.processGridLinesToggle(gridLinesBox.selectedProperty().get());
        });
        
        gridSnapBox.setOnAction(e->{
            classEditController.processGridSnapToggle(gridSnapBox.selectedProperty().get());
        });
        
        undoButton.setOnAction(e->{
            historyController.processUndoButtonRequest();
        });
        
        redoButton.setOnAction(e->{
            historyController.processRedoButtonRequest();
        });
        
        editParentButton.setOnAction(e->{
            classEditController.processUpdateParentsRequest(parentView, parents);
        });
        
        addParentButton.setOnAction(e->{
            classEditController.processAddOrRemoveParent(parentPicker.getSelectionModel().getSelectedItem());
        });
        
        
        
 
       
         
         
       
        
        //TEXT EDIT HANDLERS
        classNameField.textProperty().addListener(e->{
            if(!updating)
                classEditController.processEditClassName(classNameField.getText());
        });
        
        packageNameField.textProperty().addListener(e->{
            if(!updating)
                classEditController.processEditPackageName(packageNameField.getText());
        });
        
        
        canvas.setOnMouseMoved(e-> {
            boxController.processCanvasMouseMoved((int)e.getX(), (int) e.getY());
        });   
        canvas.setOnMousePressed(e->{
            boxController.processCanvasMousePress((int)e.getX(), (int)e.getY());
        });
        canvas.setOnMouseReleased(e->{
            boxController.processCanvasMouseRelease((int)e.getX(), (int)e.getY());
        });
        canvas.setOnMouseDragged(e->{
            boxController.processCanvasMouseDragged((int)e.getX(), (int)e.getY());
        });
        canvas.setOnMouseExited(e->{
            boxController.processCanvasMouseExited((int)e.getX(), (int)e.getY());
        });        
    }
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    @Override
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
        
        zoomInButton.getStyleClass().add(TOOLBAR_BUTTON);
        zoomOutButton.getStyleClass().add(TOOLBAR_BUTTON);
        redoButton.getStyleClass().add(TOOLBAR_BUTTON);
        undoButton.getStyleClass().add(TOOLBAR_BUTTON);
        removeButton.getStyleClass().add(TOOLBAR_BUTTON);
        addInterfaceButton.getStyleClass().add(TOOLBAR_BUTTON);
        addClassButton.getStyleClass().add(TOOLBAR_BUTTON);
        selectButton.getStyleClass().add(TOOLBAR_BUTTON);
        resizeButton.getStyleClass().add(TOOLBAR_BUTTON);
        editPane.getStyleClass().add(EDIT_PANE_STYLE);
        editToolbar.getStyleClass().add("bordered_pane");
        sceneToolbar.getStyleClass().add("bordered_pane");
        canvas.getStyleClass().add(CANVAS_STYLE);
        variableLabel.getStyleClass().add(VARIABLES_METHODS_STYLE);
        methodLabel.getStyleClass().add(VARIABLES_METHODS_STYLE);
        gridNode.getStyleClass().add(GRID_NODES_STYLE);
        gridLabelNode.getStyleClass().add(GRID_NODES_STYLE);
        variableBase.getStyleClass().add(CANVAS_STYLE);
        methodBase.getStyleClass().add(CANVAS_STYLE);
        variablePane.getStyleClass().add(CANVAS_STYLE);
        methodBase.getStyleClass().add(CANVAS_STYLE);
    }
     
        public void loadSelectedBoxSettings(DraggableUMLBox box) {
            
        
        }
        
        public void loadEditorProperties(DraggableUMLBox box) {
            updating = true;
            
            if(updating) {
                if(box != null) {
                    classNameField.setText(box.getClassName());
                    packageNameField.setText(box.getPackageName()); 
                    ArrayList<String> items = new ArrayList();
                    if(parents != null)
                        for(DraggableUMLBox b: parents)
                            if(!(b == box))
                                items.add(b.getClassName());
                    ObservableList<String> itemsList = FXCollections.observableArrayList(items);
                    if(itemsList != null)
                        parentPicker.setItems(itemsList);
                    else
                        parentPicker.getItems().clear();
                    parentPicker.getSelectionModel().clearSelection();
                    ArrayList<String> parentItems = new ArrayList();
                    for(DraggableUMLBox b: box.getParentList()){
                        parentItems.add(b.getClassName());
                        classEditController.updateLines();
                    }
                    ObservableList<String> parentViewItems = FXCollections.observableArrayList(parentItems);
                    if(parentViewItems != null){
                        parentView.setItems(parentViewItems);
                    }
                    if(box.getIsPartOfProject())
                    {
                        classNameField.setDisable(false);
                        variableBase.setVisible(true);
                        methodBase.setVisible(true);
                        variableLabel.setVisible(true);
                        methodLabel.setVisible(true);
                        addVariableButton.setVisible(true);
                        removeVariableButton.setVisible(true);
                        addMethodButton.setVisible(true);
                        removeMethodButton.setVisible(true);
                        box.updateVariableNames();                
                        classEditController.setMethodList(box);
                        classEditController.setVariableList(box);
                    }
                    else {
                        classNameField.setDisable(true);
                        variableBase.setVisible(false);
                        methodBase.setVisible(false);
                        variableLabel.setVisible(false);
                        methodLabel.setVisible(false);
                        addVariableButton.setVisible(false);
                        removeVariableButton.setVisible(false);
                        addMethodButton.setVisible(false);
                        removeMethodButton.setVisible(false);
                    }
                }
                else{
                        classNameField.setVisible(true);
                        variableBase.setVisible(false);
                        methodBase.setVisible(false);
                        variableLabel.setVisible(false);
                        methodLabel.setVisible(false);
                        addVariableButton.setVisible(false);
                        removeVariableButton.setVisible(false);
                        addMethodButton.setVisible(false);
                        removeMethodButton.setVisible(false);                    
                }
            }
            updating = false;
        }

        public Pane getCanvas() {
            return canvas;
        }
        
        public VBox getVariablePane() {
            return variablePane;
        }
        
        public VBox getMethodPane() {
            return methodPane;
        }
        
        public void fillParentPicker(ArrayList<String> parents) {
            
        }
        
        public void updateLines() {
            classEditController.updateLines();
        }
        
        public void upateVariables(DraggableUMLBox box) {
            classEditController.setVariableList(box);
        }
        
        public void updateMethods(DraggableUMLBox box) {
            classEditController.setMethodList(box);
        }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
        DataManager dataManager = (DataManager)app.getDataComponent();

       // classEditController.updateLines();
        canvas.getStyleClass().clear();
        if(dataManager.getGridLines())
            canvas.getStyleClass().add(CANVAS_GRID_STYLE);
        else
            canvas.getStyleClass().add(CANVAS_STYLE);
        
        parents.clear();
        for(Node node: canvas.getChildren()) {
            Draggable n = (Draggable)node;
            if("BOX".equals(n.getType())){
                DraggableUMLBox box = (DraggableUMLBox)n;
                parents.add(box);
            }
        }
       
          
    }
}

