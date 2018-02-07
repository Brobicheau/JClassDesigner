package jcd.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import jcd.data.Method;

/**
 * This class serves to present custom text messages to the user when
 * events occur. Note that it always provides the same controls, a label
 * with a message, and a single ok button. 
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class MethodEditPopupSingleton extends Stage {
    // HERE'S THE SINGLETON OBJECT
    static MethodEditPopupSingleton singleton = null;
    
    // HERE ARE THE DIALOG COMPONENTS
    Scene messageScene;
    String newName;
    String newAccessorType;
    String newReturnType;
    boolean newStatic;
    boolean newAbstract;
    Label messageLabel;
    Label nameLabel;
    Label typeLabel;
    Label accessorLabel;
    Label staticLabel;
    Label abstractLabel;
    Label argNameLabel;
    Label argTypeLabel;
    TextArea nameField;
    TextArea typeField;
    TextArea accessorField;
    TextArea argNameField;
    TextArea argTypeField;
    CheckBox staticCheck;
    CheckBox abstractCheck;
    ComboBox<String> accessorBox;
    ScrollPane mainScroll;
    VBox mainPane;
    HBox nameSeg;
    HBox accessorSeg;
    HBox typeSeg;
    HBox staticSeg;
    VBox abstractSeg;
    VBox argumentSeg;
    HBox baseArgSeg;
    Button closeButton;
    Button doneButton;
    Button addArgButton;
    Button removeArgButton;
    ArrayList<HBox> argList;
    HashMap<String, String> args;
    HashMap<String, String> newArgs;
    HBox selectedSeg;
   //     int count = 0;

    
    
    /**
     * Initializes this dialog so that it can be used repeatedly
     * for all kinds of messages. Note this is a singleton design
     * pattern so the constructor is private.
     * 
     * @param owner The owner stage of this modal dialoge.
     * 
     * @param closeButtonText Text to appear on the close button.
     */
    private MethodEditPopupSingleton() {}
    
    /**
     * A static accessor method for getting the singleton object.
     * 
     * @return The one singleton dialog of this object type.
     */
    public static MethodEditPopupSingleton getSingleton() {
	if (singleton == null)
	    singleton = new MethodEditPopupSingleton();
	return singleton;
    }
    
    /**
     * This function fully initializes the singleton dialog for use.
     * 
     * @param owner The window above which this dialog will be centered.
     */
    public void init(Stage owner) {
        // MAKE IT MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        args = new HashMap();
        setupWindow();
    }
    
    private void setupWindow() {

        
        mainScroll = new ScrollPane();
        argList = new ArrayList();
        newArgs = new HashMap();
        argList = new ArrayList();
       
        
        nameLabel = new Label("Method Name");
        typeLabel = new Label("Method Type");
        accessorLabel= new Label("Accessor Type");
        staticLabel = new Label("Static modifier");
        abstractLabel = new Label("Abstract Modifier");
        argNameLabel = new Label("Argument Name");
        argTypeLabel = new Label("Argument Type");
                
        
        mainPane = new VBox();
        nameSeg = new HBox();
        typeSeg = new HBox();
        accessorSeg = new HBox();
        staticSeg = new HBox();
        abstractSeg = new VBox();
        argumentSeg = new VBox();
        Collection<String> keys = args.keySet();
        for(String s: keys) {
            HBox newSeg = new HBox();
            argNameLabel = new Label("Argument Name");
            argTypeLabel = new Label("Argument Type");            
            TextArea newArgNameField = new TextArea(s);
            newArgNameField.setMaxSize(150, 10);
            TextArea newArgTypeField = new TextArea(args.get(s));
            newArgTypeField.setMaxSize(150, 10);
            newSeg.getChildren().add(argNameLabel);
            newSeg.getChildren().add(newArgNameField);
            newSeg.getChildren().add(argTypeLabel);
            newSeg.getChildren().add(newArgTypeField);
            argumentSeg.getChildren().add(newSeg);
            argList.add(newSeg);
            newSeg.setOnMouseClicked(q->{
                setSelectedArg(newSeg);
            });            
        }
        addArgButton = new Button("Add Argument");
        addArgButton.setOnAction(e-> {
            HBox newSeg = new HBox();
            argNameLabel = new Label("Argument Name");
            argTypeLabel = new Label("Argument Type");            
            TextArea newArgNameField = new TextArea("NewArgName");
            newArgNameField.setMaxSize(150, 10);
            TextArea newArgTypeField = new TextArea("NewArgType");
            newArgTypeField.setMaxSize(150, 10);
            newSeg.getChildren().add(argNameLabel);
            newSeg.getChildren().add(newArgNameField);
            newSeg.getChildren().add(argTypeLabel);
            newSeg.getChildren().add(newArgTypeField);
            argumentSeg.getChildren().add(newSeg);
            argList.add(newSeg);
            newSeg.setOnMouseClicked(q->{
                setSelectedArg(newSeg);
            });
        });
        removeArgButton = new Button("Remvoe Argument");
        removeArgButton.setOnAction(e->{
            argumentSeg.getChildren().remove(selectedSeg);
            argList.remove(selectedSeg);
            selectedSeg = null;
        });
        
        nameField = new TextArea();
        nameField.setMaxSize(150, 10);
        typeField = new TextArea();
        typeField.setMaxSize(150, 10);
        accessorField = new TextArea();
        accessorField.setMaxSize(150, 10);
        accessorBox = new ComboBox();
        accessorBox.getItems().addAll(
            "public",
            "private",
            "protected");
        argNameField = new TextArea();
        argNameField.setMaxSize(150, 10);
        argTypeField = new TextArea();
        argTypeField.setMaxSize(150,10);
        staticCheck = new CheckBox();
        abstractCheck = new CheckBox();
        doneButton = new Button("Done");
        doneButton.setOnAction(e-> {
            newName = nameField.getText();
            newReturnType = typeField.getText();
            newAccessorType = accessorBox.getValue();
            newStatic = staticCheck.selectedProperty().get();
            newAbstract = abstractCheck.selectedProperty().get();
            
            for(HBox h: argList) {
                TextArea nameArea = (TextArea)h.getChildren().get(1);
                TextArea typeArea = (TextArea)h.getChildren().get(3);
                String name = nameArea.getText();
                String type = typeArea.getText();
                newArgs.put(name, type);
            }           
            
            this.hide();
        });
        
        
        nameSeg.getChildren().add(nameLabel);
        nameSeg.getChildren().add(nameField);
        typeSeg.getChildren().add(typeLabel);
        typeSeg.getChildren().add(typeField);
        accessorSeg.getChildren().add(accessorLabel);
        accessorSeg.getChildren().add(accessorBox);
        staticSeg.getChildren().add(staticLabel);
        staticSeg.getChildren().add(staticCheck);
        abstractSeg.getChildren().add(abstractLabel);
        abstractSeg.getChildren().add(abstractCheck);
  
        
        
        mainPane.getChildren().add(nameSeg);
        mainPane.getChildren().add(typeSeg);
        mainPane.getChildren().add(accessorSeg);
        mainPane.getChildren().add(staticSeg);
        mainPane.getChildren().add(abstractSeg);
        mainPane.getChildren().add(addArgButton);
        mainPane.getChildren().add(removeArgButton);
        mainPane.getChildren().add(argumentSeg);
        mainPane.getChildren().add(doneButton);
  
        mainPane.setPadding(new Insets(80, 60, 80, 60));
        mainPane.setSpacing(20);
        // AND PUT IT IN THE WINDOW
        mainScroll.setContent(mainPane);
        messageScene = new Scene(mainScroll);
        
        this.setScene(messageScene);        
    }
    
    public String getName() {
        return newName;
    }
    
    public String getAccessor() {
        return newAccessorType;
    }
    
    public String getType () {
        return newReturnType;
    }
    
    public boolean getStatic() {
        return newStatic;
    }
    
    public boolean getAbstract() {
        return newAbstract;
    }
     public HashMap<String, String> getArgs() {
         return newArgs;
     }
 
     public void setSelectedArg(HBox seg) {
         if(selectedSeg != null)
             unhighlightSeg();
         
         selectedSeg = seg;
         highlightSeg();         
     }
     
     public void highlightSeg() {
         DropShadow dp = new DropShadow();
        dp.setColor(Color.YELLOW);
        selectedSeg.setEffect(dp);
     }
     
     public void unhighlightSeg() {
         selectedSeg.setEffect(null);
     }
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param title The title to appear in the dialog window.
     * 
     * @param message Message to appear inside the dialog.
     */
    public void show(Method m) {
        
        
    setTitle("Method Editor");
    
    mainPane.getChildren().clear();
    args = m.getArgs();
    setupWindow();
	// SET THE DIALOG TITLE BAR TITLE
    nameField.setText(m.getName());
    typeField.setText(m.getReturnType());
    staticCheck.setSelected(m.getStatic());
    abstractCheck.setSelected(m.getAbstract());
    accessorBox.setValue(m.getAccessorType());

    
    
    
	// SET THE MESSAGE TO DISPLAY TO THE USER
 
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
        
    }
}
