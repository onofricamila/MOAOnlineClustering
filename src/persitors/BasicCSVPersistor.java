package persitors;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class BasicCSVPersistor {
    public String rootFolderName = "resources";
    public String rootFolderPath = "/home/camila/Desktop/TESIS/Github_Repo_TestingMOAOnlineClustering/src/" + rootFolderName;

    // for different runs of the algorithms
    // we do not wanna keep old files with old clusterings
    public void resetStorage(String subfolder){
        String currentFolder =  rootFolderPath + "/" + subfolder;
        File directory = new File(currentFolder);
        try {
            if (directory.exists()) {
                // First, remove files from into the folder
                FileUtils.cleanDirectory(directory);
                // Then, remove the folder
                FileUtils.deleteDirectory(directory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void storeResult(String fileName, List<List<String>> rows, String subfolder){
        try {
            String currentFolder =  rootFolderPath + "/" + subfolder;
            File directory = new File(currentFolder);

            // check if the directory needs to be created
            if (! directory.exists()){
                directory.mkdirs();
            }

            String fileWExtention = fileName + ".csv";
            String completePath = currentFolder + "/" + fileWExtention;
            FileWriter csvWriter = new FileWriter(completePath);

            for (List<String> rowData : rows) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
