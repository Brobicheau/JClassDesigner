/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TreeItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import jcd.data.DataManager;
import jcd.data.Draggable;
import jcd.data.DraggableUMLBox;
import jcd.data.Method;
import jcd.data.Variable;
import jcd.gui.Workspace;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class FileManager implements AppFileComponent {

    /**
     * This method is for saving user work, which in the case of this
     * application means the data that constitutes the page DOM.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        DataManager dataManager = (DataManager)data;
        
	
	// FIRST THE BACKGROUND COLOR

	// NOW BUILD THE JSON OBJCTS TO SAVE
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	ObservableList<Node> boxes = dataManager.getBoxes();
    ArrayList<DraggableUMLBox> outOfProjectBoxes = dataManager.getOutOfProjectBoxes();
    
    //ArrayList<Node> lines = dataManager.getLines();
        
	for (Node node : boxes) {
        Draggable temp = (Draggable)node;
        if(temp.getType().equals("BOX")) {
            DraggableUMLBox box = (DraggableUMLBox)node;
            JsonObject boxJson = makeJsonBoxObject(box);
            arrayBuilder.add(boxJson);
        }
    }
	JsonArray boxArray = arrayBuilder.build();
    
    JsonArrayBuilder jsonOutBoxBuilder = Json.createArrayBuilder();
    for(DraggableUMLBox outBox: outOfProjectBoxes){   
        JsonObject outBoxJson = makeJsonBoxObject(outBox);
        jsonOutBoxBuilder.add(outBoxJson);
    }
    JsonArray outBoxArray = jsonOutBoxBuilder.build();
//	
//        JsonArrayBuilder lineArrayBuilder = Json.createArrayBuilder();
//        for(Node lineNode: lines) {
//            if()
//            DraggableLine line = (DraggableLine)lineNode;
//
//            JsonObject lineJson = Json.createObjectBuilder()
//                    .add("startPoint", 1)
//                    .add("leftEndPoint" , 1)
//                    .add("rightEndPoint", 1).build();
//            lineArrayBuilder.add(lineJson);
//        }
//        JsonArray lineArray = lineArrayBuilder.build();
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add("boxes", boxArray)
        .add("outOfProjectBoxes", outBoxArray)
         //.add("lines", lineArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();

    }
    
    public JsonArray makeVariablesArray(ObservableList<Variable> var) {
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for(Variable v: var) {                    
            JsonObject varJso = Json.createObjectBuilder()
                .add("name", v.getName())
                .add("accessorType", v.getAccessorType())
                .add("returnType", v.getReturnType())
                .add("isStatic", v.getStatic()).build();
                arrayBuilder.add(varJso);
        }
        
        JsonArray varArray = arrayBuilder.build();
     
        return varArray;
    }
    
    public JsonArray makeMethodsArray(ObservableList<Method> meth) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Method m: meth) {
            JsonObject methJso = Json.createObjectBuilder()
                    .add("name", m.getName())
                    .add("returnType", m.getReturnType())
                    .add("accessorType", m.getAccessorType())
                    .add("isAbstract", m.getAbstract())
                    .add("isStatic", m.getStatic())
                    .add("args", makeArgsArray(m.getArgs()))
                    .build();
            arrayBuilder.add(methJso);
        }
        
        JsonArray methArray = arrayBuilder.build();
        
        return methArray;
            
    }
    
    public JsonArray makeArgsArray(HashMap<String, String> args) {
        JsonObject argsJso = null;
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        Set<String> keys = args.keySet();
        for(String a: keys) {
            JsonObject argJso = Json.createObjectBuilder()
            .add("argName", a)
            .add("arg", args.get(a)).build();
            arrayBuilder.add(argJso);
        }
        JsonArray argArray = arrayBuilder.build();
        return argArray;
    }
    
    public JsonObject makeJsonBoxObject (DraggableUMLBox box) {
        JsonObject boxJson;
        
        if(box.getIsPartOfProject()) {
            boolean isAbstract = box.getAbstract();
            boolean isPartOfProject = box.getIsPartOfProject();
            boolean isInterface = box.getInterface();
            double x = box.getLayoutX();
            double y = box.getLayoutY();
            double width = box.getPrefHeight();
            double height = box.getPrefHeight();
            String className = box.getClassName();
            String packageName = box.getPackageName();
            String accessorType = box.getAccessorType();
            ObservableList<Variable> variables = box.getVariables();
            ObservableList<Method> methods = box.getMethod();

            boxJson = Json.createObjectBuilder()
                .add("parentList", makeParentListArray(box.getParentList()))
                .add("accessorType", accessorType)
                .add("isAbstract", isAbstract)
                .add("isInterface", isInterface)
                .add("isPartOfProject", isPartOfProject)
                .add("className", className)
                .add("packageName", packageName)
                .add("variables", makeVariablesArray(variables))
                .add("methods", makeMethodsArray(methods))
                .add("x", x)
                .add("y", y)
                .add("width", width)
                .add("height", height).build();
        }
        else {
            boolean isPartOfProject = box.getIsPartOfProject();
            double x = box.getLayoutX();
            double y = box.getLayoutY();
            double width = box.getPrefWidth();
            double height = box.getPrefHeight();
            String className = box.getClassName();
            String packageName = box.getPackageName();

            boxJson = Json.createObjectBuilder()    
                .add("className", className)
                .add("packageName", packageName)
                .add("isPartOfProject", isPartOfProject)
                .add("x", x)
                .add("y", y)
                .add("width", width)
                .add("height", height).build();  
        }
            
        return boxJson;
    }
    
    public JsonArray makeParentListArray(ArrayList<DraggableUMLBox> parentList){
        
        JsonArrayBuilder parentArrayBuilder = Json.createArrayBuilder();
        
        for(DraggableUMLBox p: parentList) {
                JsonObject boxJson = makeJsonBoxObject(p);
            parentArrayBuilder.add(boxJson);
        }
        JsonArray parentArray = parentArrayBuilder.build();
        return parentArray;
    }
    
      
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        
        DataManager dataManager = (DataManager)data;
        dataManager.reset();
        JsonObject json = loadJSONFile(filePath);
            
        JsonArray jsonBoxArray = json.getJsonArray("boxes");
        JsonArray jsonOutBoxArray = json.getJsonArray("outOfProjectBoxes");
	for (int i = 0; i < jsonBoxArray.size(); i++) {
	    JsonObject jsonBox = jsonBoxArray.getJsonObject(i);
	    DraggableUMLBox box = loadBox(jsonBox);
	    dataManager.addBox(box);
	}
    for(DraggableUMLBox b: dataManager.getOnlyBoxes()){
        dataManager.setSelectedBox(b);
        dataManager.unselectBox();
        dataManager.updateLines();
    }
    dataManager.getWorkspace().reloadWorkspace();
    dataManager.updateLines();
          
    }
    



    
    public DraggableUMLBox loadBox(JsonObject jsonBox) {
        DraggableUMLBox box;

        boolean isPartOfProject = jsonBox.getBoolean("isPartOfProject");
        
        if(!isPartOfProject){
            
            box = new DraggableUMLBox(isPartOfProject);
            
            int x = jsonBox.getInt("x");
            int y = jsonBox.getInt("y");
            int width = jsonBox.getInt("width");
            int height = jsonBox.getInt("height");
            String className = jsonBox.getString("className");
            String packageName = jsonBox.getString("packageName");
            
            
            
            box.setLayoutX(x);
            box.setLayoutY(y);
            box.setMinWidth(width);
            box.setMaxWidth(width);
            box.setPrefWidth(width);
            box.setMinHeight(height);
            box.setMaxHeight(height);
            box.setPrefHeight(height);
            box.setClassName(className);
            box.setPackageName(packageName);
            box.setPartOfProject(isPartOfProject);      
            box.setClassName(className);
            
        }
        else {
            box = new DraggableUMLBox();
            int x = jsonBox.getInt("x");
            int y = jsonBox.getInt("y");
            int width = jsonBox.getInt("width");
            int height = jsonBox.getInt("height");
            String className = jsonBox.getString("className");
            String packageName = jsonBox.getString("packageName");
            String accessorType = jsonBox.getString("accessorType");
            boolean isAbstract = jsonBox.getBoolean("isAbstract");
            boolean isInterface = jsonBox.getBoolean("isInterface");
            ArrayList<DraggableUMLBox> parentList = loadParentList(jsonBox.getJsonArray("parentList"));

          
            box.setLayoutX(x);
            box.setLayoutY(y);
            box.setMinWidth(width);
            box.setMaxWidth(width);
            box.setPrefWidth(width);
            box.setMinHeight(height);
            box.setMaxHeight(height);
            box.setPrefHeight(height);
            box.setAccessorType(accessorType);
            box.setAbstract(isAbstract);
            box.setPartOfProject(isPartOfProject);
            box.setInterface(isInterface);
            box.setParentList(parentList);
            box.setPackageName(packageName);
            box.setClassName(className);
            
            

            JsonArray jsonVariableArray = jsonBox.getJsonArray("variables");
            for(int i = 0; i < jsonVariableArray.size(); i++) {
                JsonObject jsonVar = jsonVariableArray.getJsonObject(i);
                Variable var = loadVariable(jsonVar);
                box.addVariable(var);
            }      

            JsonArray jsonMethodArray = jsonBox.getJsonArray("methods");
            for(int i = 0; i < jsonMethodArray.size(); i++) {
                JsonObject jsonMeth = jsonMethodArray.getJsonObject(i);
                Method meth = loadMethod(jsonMeth);
                box.addMethod(meth);
            }
        }
        return box;
    }
    
    public ArrayList<DraggableUMLBox> loadParentList(JsonArray jsonParentArray) {
        
        ArrayList<DraggableUMLBox> parentList = new ArrayList();
        
        for( int i = 0; i < jsonParentArray.size() ; i++) {
            JsonObject jsonParent = jsonParentArray.getJsonObject(i);
            DraggableUMLBox parentBox = loadBox(jsonParent);
            parentList.add(parentBox);
        }
        return parentList;
    }
    
    
    public Variable loadVariable(JsonObject jsonVar) {
        Variable var = new Variable();
        String name = jsonVar.getString("name");
        String returnType = jsonVar.getString("returnType");
        String accessorType = jsonVar.getString("accessorType");
        boolean isStatic = jsonVar.getBoolean("isStatic");
        
        var.setVarName(name);
        var.setAccessorType(accessorType);
        var.setReturnType(returnType);
        var.setStatic(isStatic);
        
        return var;
    }
    
    public Method loadMethod(JsonObject jsonMeth) {
        Method meth = new Method();
        String name = jsonMeth.getString("name");
        String returnType = jsonMeth.getString("returnType");
        String accessorType = jsonMeth.getString("accessorType");
        boolean isStatic = jsonMeth.getBoolean("isStatic");
        boolean isAbstract = jsonMeth.getBoolean("isAbstract");
        
        meth.setName(name);
        meth.setReturnType(returnType);
        meth.setAccType(accessorType);
        meth.setStatic(isStatic);
        meth.setAbstract(isAbstract);
        
        JsonArray argsArray = jsonMeth.getJsonArray("args");
        for(int i = 0; i < argsArray.size(); i++) {
            JsonObject jsonArgs = argsArray.getJsonObject(i);
            String argName = jsonArgs.getString("argName");
            String argType = jsonArgs.getString("arg");
            meth.addArg(argName, argType);
        }
        
        return meth;
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    @Override
    public void saveDataAs(AppDataComponent data, String filePath) throws IOException {
        
    }
    @Override
    public void exportToCode(AppDataComponent data, String filePath) throws IOException {
        TreeItem<File> root = new TreeItem();
        
        //@TODO FIX TAB THINGY. PUT ACCESSOR TYPE IN CLASSES AND ABSTRACT AND INTERFACES
        DataManager dataManager = ((DataManager)data);
        ArrayList<DraggableUMLBox> boxes =  dataManager.getOnlyBoxes();
        makePackageDirectories(boxes, filePath);
        
    }
    
    @Override
    public void exportToPhoto(AppDataComponent data, String filePath) throws IOException {
        DataManager dataManager = (DataManager)data;
        Workspace workspace = dataManager.getWorkspace();
        Pane canvas = workspace.getCanvas();
            WritableImage image = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
            canvas.snapshot(new SnapshotParameters(), image);
            
        File file = new File(filePath+ ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }        
    }
    
    public void exportClass(DraggableUMLBox box, PrintWriter out, ArrayList<String> packNames, HashMap<String, String> classNamesAndPacks) throws IOException {
        boolean importUsed;
        ArrayList<String> usedImports = new ArrayList();
        ObservableList<Method> methods = box.getMethod();
        ObservableList<Variable> variables = box.getVariables();
        for(String p: packNames){
            
            for(Variable v: variables) {
                Collection<String> keys = classNamesAndPacks.keySet();
                for(String k: keys) {
                    if(v.getReturnType().equals(k)){
                        if(!usedImports.isEmpty()) {
                            importUsed = false;
                            for(String s: usedImports){
                                if(s.equals(classNamesAndPacks.get(k) + "." + k)) {
                                    importUsed = true;
                                }
                            }
                            if(!importUsed) {
                                out.print("import " + classNamesAndPacks.get(k) + "." + k + ";\n");
                                usedImports.add(classNamesAndPacks.get(k) + "." + k);      
                            }
                        }
                        else{
                            out.print("import " + classNamesAndPacks.get(k) + "." + k + ";\n");
                            usedImports.add(classNamesAndPacks.get(k) + "." + k);    
                        }
                    }
                }
                try {
                    Class c = Class.forName(p + "." + v.getReturnType());
                    if(!usedImports.isEmpty()) {
                        importUsed = false;
                        for(String s: usedImports){
                            if(s.equals(c.getName())){
                                importUsed = true;
                            }
                        }
                        if(!importUsed) {
                            out.print("import " + c.getName() + ";\n");
                            usedImports.add(c.getName()); 
                        }
                    }
                    else {
                        out.print("import " + c.getName() + ";\n");
                        usedImports.add(c.getName()); 
                    }
                }   
            catch (ClassNotFoundException e) {}
            }
            
            for(Method m: methods) {
                Collection<String> packKeys = classNamesAndPacks.keySet();
                for(String ke: packKeys) {
                    if(m.getReturnType().equals(ke)){
                        if(!usedImports.isEmpty()) {
                            importUsed = false;
                            for(String s: usedImports){
                                if(s.equals(classNamesAndPacks.get(ke) + "." + ke)) {
                                    importUsed = true;
                                }
                            }
                            if(!importUsed) {
                                out.print("import " + classNamesAndPacks.get(ke) + "." + ke + ";\n");
                                usedImports.add(classNamesAndPacks.get(ke) + "." + ke);      
                            }
                        }
                        else{
                            out.print("import " + classNamesAndPacks.get(ke) + "." + ke + ";\n");
                            usedImports.add(classNamesAndPacks.get(ke) + "." + ke);    
                        }  
                    }
                }
                try {
                    Class c = Class.forName(p + "." + m.getReturnType());
                    if(!usedImports.isEmpty()) {
                        importUsed = false;
                        for(String s: usedImports){
                            if(s.equals(c.getName())){
                                importUsed = true;
                            }
                        }
                        if(!importUsed) {
                            out.print("import " + c.getName() + ";\n");
                            usedImports.add(c.getName()); 
                        }
                    }
                    else {
                        out.print("import " + c.getName() + ";\n");
                        usedImports.add(c.getName()); 
                    }
                }   
                catch (ClassNotFoundException e) {}
                    
            
                if(!m.getArgs().isEmpty()) {
                    HashMap<String, String> args = m.getArgs();
                    Collection<String> keys = args.keySet();
                    for(String ke: packKeys) {
                        if(m.getReturnType().equals(ke)){
                            if(!usedImports.isEmpty()) {
                                importUsed = false;
                                for(String s: usedImports){
                                    if(s.equals(classNamesAndPacks.get(ke) + "." + ke)) {
                                        importUsed = true;
                                    }
                                }
                                if(!importUsed) {
                                    out.print("import " + classNamesAndPacks.get(ke) + "." + ke + ";\n");
                                    usedImports.add(classNamesAndPacks.get(ke) + "." + ke);      
                                }
                            }
                            else{
                                out.print("import " + classNamesAndPacks.get(ke) + "." + ke + ";\n");
                                usedImports.add(classNamesAndPacks.get(ke) + "." + ke);    
                            }  
                        }
                    }                    
                    for(String k: keys){
                        try {
                            Class c = Class.forName(p + "." + args.get(k));
                            if(!usedImports.isEmpty()) {
                                importUsed = false;
                                for(String s: usedImports){
                                    if(s.equals(c.getName())){
                                        importUsed = true;
                                    }
                                }
                                if(!importUsed) {
                                    out.print("import " + c.getName() + ";\n");
                                    usedImports.add(c.getName()); 
                                }
                            }
                            else {
                                out.print("import " + c.getName() + ";\n");
                                usedImports.add(c.getName()); 
                            }
                        }   
                        catch (ClassNotFoundException e) {}
                    }
                }
            }                
        }        
        

        
        out.print("\n\t");
        out.print(box.getAccessorType());
        if(box.getAbstract()){
            out.print(" abstract ");
        out.print("class " );
        out.print(box.getClassName() + " ");             
        }
        else if(box.getInterface()){
            out.print(" interface ");
            out.print(box.getClassName() + " "); 
        }
        else{
            out.print(" class " );
            out.print(box.getClassName() + " ");
        }
        out.print("{");
        out.print("\n\n");
        if(variables != null)
          exportVariables(variables, out, 1);
        if(methods != null)
            exportMethods(methods,out, 1, box.getInterface());
        out.print("\n}");
        
    }
    
    public void exportVariables(ObservableList<Variable> variables, PrintWriter out, int tabCount)throws IOException {
        
        for(Variable v: variables) {
            out.print("\t");
            if(v.getAccessorType() != null)
                out.print(v.getAccessorType()+ " ");
            if(v.getStatic())
                out.print("static ");
            out.print(v.getReturnType() + " ");
            out.print(v.getName());
            out.print("; \n");
        }
        
    }
    
    public void exportMethods(ObservableList<Method> methods, PrintWriter out, int tabCount, boolean isInterface)throws IOException {
        

        for(Method m: methods) {
            out.print("\n\n\n");
            out.print("\t");
            out.print(m.getAccessorType() + " ");
            if(m.getReturnType() != null){
                out.print(m.getReturnType() + " ");
            }
            else
                out.print(" ");
            out.print(m.getName());
            out.print("(");
            if(m.getArgs()!= null){
                int count = 1;
                Set<String> keys = m.getArgs().keySet();
                for(String k: keys){
                    out.print(m.getArgs().get(k)+ " ");
                    out.print(k);
                    if(count < keys.size())
                        out.print(", ");
                    count++;
                }
            }
            out.print(")");
            if(isInterface) {
                out.print(";\n");
            }
            else {
                out.print(" { \n\n");
                if(m.getReturnType() != null && !"void".equals(m.getReturnType())) {
                    out.print("\t");
                    if("int".equals(m.getReturnType())
                            || "float".equals(m.getReturnType())
                            || "double".equals(m.getReturnType())
                            || "long".equals(m.getReturnType()))
                        out.print("\treturn 0;\n");
                    else if("boolean".equals(m.getReturnType()))
                        out.print("\treturn false; \n");
                    else
                        out.print("\treturn null; \n");                
                }
                out.print("\t");
                out.print("}");
            }    
        }
    }
    
    public void makePackageDirectories(ArrayList<DraggableUMLBox> boxes, String filePath)throws IOException {
        //get all the package names of files to be exported
        // make files directories based on the package name
        //put the class files in there
    HashMap<String, String> classNamesAndPacks = new HashMap();
        ArrayList<DraggableUMLBox> directoryBoxes = new ArrayList();
        ArrayList<String> packageNames = new ArrayList();
        ArrayList<String> importPackages = new ArrayList();
        for(DraggableUMLBox b:boxes){
            if(b.getIsPartOfProject()){                
                directoryBoxes.add(b);
                classNamesAndPacks.put(b.getClassName(), b.getPackageName());                
                packageNames.add(b.getPackageName());
            }
        }
        for(DraggableUMLBox b:boxes){
            importPackages.add(b.getPackageName());
        }
        
        String rootPath = (filePath);
        File rootDir = new File(filePath);
        rootDir.mkdir(); 
        File src = new File(rootPath);
        src.mkdir();
        createSubDirs(packageNames, filePath);
        createClassFiles(boxes, directoryBoxes, filePath, classNamesAndPacks, importPackages);
    

    }
    
    public void createSubDirs(ArrayList<String> packageNames, String filePath){
        
        for(String p: packageNames){
            
            String directoryPath = makeDirectoryPath(p);
            File newDir = new File(filePath + "\\" + directoryPath);
            if(newDir.mkdirs()){
            }
            else if(!newDir.mkdirs()){
                System.out.print(directoryPath + "\n");

            }
        }
        
    }
    
    public String makeDirectoryPath(String packageName) {
        String directoryPath = "";
        if(packageName.contains("."));{
            directoryPath = packageName;
            while(directoryPath.contains("."))
                directoryPath = packageName.replace(".","\\");
           
    }
        return directoryPath;
        
    }
    
    public void createClassFiles(ArrayList<DraggableUMLBox> boxes,
        ArrayList<DraggableUMLBox> directoryBoxes, String filePath,
        HashMap<String, String> classNamesAndPacks, ArrayList<String> packageNames) throws IOException {

                   
        for(DraggableUMLBox b: directoryBoxes) {
            String packagePath = b.getPackageName();
            while(packagePath.contains("."))
                    packagePath = packagePath.replace(".","\\");            
            String directoryPath = filePath + "\\" + packagePath + "\\" +  b.getClassName() + ".java";
            File newClassFile = new File(directoryPath);
            if(!newClassFile.createNewFile())
                System.out.print("DIDNTWORK");
            PrintWriter out = new PrintWriter(newClassFile);
            out.print("package " + b.getPackageName() + ";\n\n");                    
            exportClass(b, out, packageNames, classNamesAndPacks);
            out.close();
        }
        
    }
    
    

}


