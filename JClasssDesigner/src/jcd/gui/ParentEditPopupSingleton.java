package jcd.gui;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import jcd.data.DraggableUMLBox;

/**
 * This class serves to present custom text messages to the user when
 * events occur. Note that it always provides the same controls, a label
 * with a message, and a single ok button. 
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class ParentEditPopupSingleton extends Stage {
    // HERE'S THE SINGLETON OBJECT
    static ParentEditPopupSingleton singleton = null;
    
    // HERE ARE THE DIALOG COMPONENTS
    Scene messageScene;
    Label messageLabel;
    VBox mainPane;
    VBox checkPane;
    ArrayList<CheckBox> checkArray;
    Button doneButton;
    ObservableList<DraggableUMLBox> newParents;
    ObservableList<String> newParentView;
    ArrayList<DraggableUMLBox> parentList;   
    ArrayList<String> newParentViewList;
    /**
     * Initializes this dialog so that it can be used repeatedly
     * for all kinds of messages. Note this is a singleton design
     * pattern so the constructor is private.
     * 
     * @param owner The owner stage of this modal dialoge.
     * 
     * @param closeButtonText Text to appear on the close button.
     */
    private ParentEditPopupSingleton() {}
    
    /**
     * A static accessor method for getting the singleton object.
     * 
     * @return The one singleton dialog of this object type.
     */
    public static ParentEditPopupSingleton getSingleton() {
	if (singleton == null)
	    singleton = new ParentEditPopupSingleton();
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
        doneButton= new Button("Done");
        doneButton.setOnAction(e->{
               this.hide();
            });
        
        mainPane = new VBox();
        checkPane = new VBox();
        
        
        mainPane.getChildren().add(checkPane);
        mainPane.getChildren().add(doneButton);
        mainPane.setPadding(new Insets(80, 60, 80, 60));
        mainPane.setSpacing(20);
        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(mainPane);
        this.setScene(messageScene);
    }
    
    public ObservableList<DraggableUMLBox> getSelectedParents() {
         newParents = FXCollections.observableArrayList(parentList);
         return newParents;
    }
    
    public ObservableList<String> getNewParentList() {
        newParentView = FXCollections.observableArrayList(newParentViewList);
        return newParentView;
    }

    
 
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param title The title to appear in the dialog window.
     * 
     * @param message Message to appear inside the dialog.
     */
    public void show(ObservableList<DraggableUMLBox> parents, ObservableList<String> currentParents, DraggableUMLBox currentBox) {
        
    setTitle("Parent Editor");
    
    parentList = new ArrayList();
    newParentViewList = new ArrayList();
    for(String s: currentParents)
        newParentViewList.add(s);
    checkArray = new ArrayList();
    checkPane.getChildren().clear();
	// SET THE DIALOG TITLE BAR TITLE
    for(DraggableUMLBox b: parents) {
        if(b != currentBox) {
            CheckBox check = new CheckBox(b.getClassName() + ":");
            check.setSelected(false);
            check.setIndeterminate(false);
            for(String s: currentParents) {
                if(s.equals(b.getClassName())) {
                    check.setSelected(true);
                    check.selectedProperty().set(true);
                }
            }
            checkPane.getChildren().add(check);
            check.setOnAction(e->{
                if(check.selectedProperty().get()) {
                    parentList.add(b);
                    newParentViewList.add(b.getClassName());                    
                }
                else if(!check.selectedProperty().get()) {
                    parentList.remove(b);    
                    newParentViewList.remove(b.getClassName());
                }
            
            });
            checkArray.add(check);
        }
   
    }
    
	// SET THE MESSAGE TO DISPLAY TO THE USER

	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
        
    }
}
