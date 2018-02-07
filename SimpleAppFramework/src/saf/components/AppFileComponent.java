package saf.components;

import java.io.IOException;

/**
 * This interface provides the structure for file components in
 * our applications. Note that by doing so we make it possible
 * for customly provided descendent classes to have their methods
 * called from this framework.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public interface AppFileComponent {
    public void saveData(AppDataComponent data, String filePath) throws IOException;
    public void saveDataAs(AppDataComponent data, String filePath) throws IOException;
    public void loadData(AppDataComponent data, String filePath) throws IOException;
    public void exportToCode(AppDataComponent data, String filePath) throws IOException;
    public void exportToPhoto(AppDataComponent data, String filePath) throws IOException;    
}
