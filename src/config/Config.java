package config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Config {
    public static String clusteringResultsPath;
    public static String csvDatasetsPath;
    public static String timeSeriesToyDatasetName;
    public static JSONObject algoNames;

    public static String getClusteringResultsPath(){
        if (clusteringResultsPath != null){
            return clusteringResultsPath;
        }
        // else
        fetchConfig();
        return clusteringResultsPath;
    }

    public static String getCsvDatasetsPath(){
        if (csvDatasetsPath != null){
            return csvDatasetsPath;
        }
        // else
        fetchConfig();
        return csvDatasetsPath;
    }

    public static String getTimeSeriesToyDatasetName(){
        if (timeSeriesToyDatasetName != null){
            return timeSeriesToyDatasetName;
        }
        // else
        fetchConfig();
        return timeSeriesToyDatasetName;
    }

    public static String getCluStreamName(){
        String key = "clustream";
        String cluStreamName = (String) algoNames.get(key);
        if (cluStreamName != null){
            return cluStreamName;
        }
        // else
        fetchConfig();
        return (String) algoNames.get(key);
    }

    public static String getDenStreamName(){
        String key = "denstream";
        String denStreamName = (String) algoNames.get(key);
        if (denStreamName != null){
            return denStreamName;
        }
        // else
        fetchConfig();
        return (String) algoNames.get(key);
    }

    // this method will fetch the data and fill the variables
    private static void fetchConfig() {
        JSONParser parser = new JSONParser();
        try {
            String configFilePath = "/home/camila/Desktop/TESIS/DATA/config.json";
            FileReader reader = new FileReader(configFilePath);
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            clusteringResultsPath = (String) jsonObject.get("clusteringResultsPath");
            csvDatasetsPath = (String) jsonObject.get("csvDatasetsPath");
            timeSeriesToyDatasetName = (String) jsonObject.get("timeSeriesToyDatasetName");
            algoNames = (JSONObject) jsonObject.get("algoNames");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
