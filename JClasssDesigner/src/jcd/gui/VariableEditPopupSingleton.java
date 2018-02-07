package jcd.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import jcd.data.Variable;

/**
 * This class serves to present custom text messages to the user when
 * events occur. Note that it always provides the same controls, a label
 * with a message, and a single ok button. 
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class VariableEditPopupSingleton extends Stage {
    // HERE'S THE SINGLETON OBJECT
    static VariableEditPopupSingleton singleton = null;
    
    // HERE ARE THE DIALOG COMPONENTS
    Scene messageScene;
    String newName;
    String newAccessorType;
    String newReturnType;
    boolean newStatic;
    Label messageLabel;
    Label nameLabel;
    Label typeLabel;
    Label accessorLabel;
    Label staticLabel;
    TextArea nameField;
    TextArea typeField;
    TextArea accessorField;
    CheckBox staticCheck;
    ComboBox<String> accessorBox;
    VBox mainPane;
    HBox nameSeg;
    HBox accessorSeg;
    HBox typeSeg;
    HBox staticSeg;
    Button closeButton;
    Button doneButton;
    
    /**
     * Initializes this dialog so that it can be used repeatedly
     * for all kinds of messages. Note this is a singleton design
     * pattern so the constructor is private.
     * 
     * @param owner The owner stage of this modal dialoge.
     * 
     * @param closeButtonText Text to appear on the close button.
     */
    private VariableEditPopupSingleton() {}
    
    /**
     * A static accessor method for getting the singleton object.
     * 
     * @return The one singleton dialog of this object type.
     */
    public static VariableEditPopupSingleton getSingleton() {
	if (singleton == null)
	    singleton = new VariableEditPopupSingleton();
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
        
        
        mainPane = new VBox();
        nameSeg = new HBox();
        typeSeg = new HBox();
        accessorSeg = new HBox();
        staticSeg = new HBox();
        
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
        staticCheck = new CheckBox();
        doneButton = new Button("Done");
        doneButton.setOnAction(e-> {
            newName = nameField.getText();
            newReturnType = typeField.getText();
            newAccessorType = accessorBox.getValue();
            newStatic = staticCheck.selectedProperty().get();
            this.hide();
        });
        
        nameLabel = new Label("Variable Name");
        typeLabel = new Label("Variable Type");
        accessorLabel= new Label("Accessor Type");
        staticLabel = new Label("Static modifier");
        
        nameSeg.getChildren().add(nameLabel);
        nameSeg.getChildren().add(nameField);
        typeSeg.getChildren().add(typeLabel);
        typeSeg.getChildren().add(typeField);
        accessorSeg.getChildren().add(accessorLabel);
        accessorSeg.getChildren().add(accessorBox);
        staticSeg.getChildren().add(staticLabel);
        staticSeg.getChildren().add(staticCheck);
        
        mainPane.getChildren().add(nameSeg);
        mainPane.getChildren().add(typeSeg);
        mainPane.getChildren().add(accessorSeg);
        mainPane.getChildren().add(staticSeg);
        mainPane.getChildren().add(doneButton);
  
        mainPane.setPadding(new Insets(80, 60, 80, 60));
        mainPane.setSpacing(20);
        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(mainPane);
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
    
 
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param title The title to appear in the dialog window.
     * 
     * @param message Message to appear inside the dialog.
     */
    public void show(Variable v) {
        
    setTitle("Variable Editor");
	// SET THE DIALOG TITLE BAR TITLE
    nameField.setText(v.getName());
    typeField.setText(v.getReturnType());
    accessorBox.setValue(v.getAccessorType());
    staticCheck.setSelected(v.getStatic());
	// SET THE MESSAGE TO DISPLAY TO THE USER
 
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
        
    }
}
