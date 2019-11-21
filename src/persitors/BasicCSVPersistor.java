package persitors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BasicCSVPersistor {
    public void storeResult(String fileName, List<List<String>> rows){
        try {
            String directoryName = "resources";
            String folder = "/home/camila/Desktop/TESIS/Github_Repo_TestingMOAOnlineClustering/src/" + directoryName;
            File directory = new File(folder);
            if (! directory.exists()){
                directory.mkdir();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.
            }

            String fileWExtention = fileName + ".csv";
            String completePath = folder + "/" + fileWExtention;
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
