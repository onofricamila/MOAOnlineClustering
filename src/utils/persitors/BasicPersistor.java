package utils.persitors;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static config.Config.getClusteringResultsPath;

public class BasicPersistor {
    public String clusteringResultsPath = getClusteringResultsPath();

    // for different runs of the algorithms
    // we do not wanna keep old files with old clusterings
    public void resetStorage(String subfolder){
        String currentFolder =  clusteringResultsPath + "/" + subfolder;
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
            String currentFolder =  clusteringResultsPath + "/" + subfolder;
            this.createDirectoryIfNotExists(currentFolder);

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


    // checks if the directory needs to be created
    public void createDirectoryIfNotExists(String folder){
        File directory = new File(folder);
        if (! directory.exists()){
            directory.mkdirs();
        }

    }


    // writes JSON file
    public void storeAlgoConfig(JSONObject algoConfig, String subfolder) {
        String currentFolder =  clusteringResultsPath + subfolder;
        this.createDirectoryIfNotExists(currentFolder);

        String fileName = "algoConfig";
        String fileWExtention = fileName + ".json";
        String completePath = currentFolder + "/" + fileWExtention;

        try (FileWriter jsonWriter = new FileWriter(completePath)) {
            jsonWriter.write(algoConfig.toJSONString());
            jsonWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
