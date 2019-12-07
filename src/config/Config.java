package config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class Config {
    public static String timeSeriesToyDatasetName;
    public static JSONObject paths;
    public static JSONObject algoNames;
    public static JSONObject timeSeriesParams;

    public static JSONObject getAlgoNames() {
        return algoNames;
    }

    public static JSONObject getTimeSeriesParams() {
        return timeSeriesParams;
    }

    public static JSONObject getPaths() {
        return paths;
    }

    public static Object getFromJSONObject(String key, Callable function){
        try {
            JSONObject jsonObject = (JSONObject) function.call();
            if (jsonObject != null){
                return jsonObject.get(key);
            }
            // else
            fetchConfig();
            return  ( (JSONObject) function.call() ).get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getClusteringResultsPath(){
        String key = "clusteringResultsPath";
        return (String) getFromJSONObject(key, Config::getPaths);
    }

   public static String getTimeSeriesDatasetsPath(){
       String key = "timeSeriesDatasetsPath";
       return (String) getFromJSONObject(key, Config::getPaths);
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
        return (String) getFromJSONObject(key, Config::getAlgoNames);
    }

    public static String getDenStreamName(){
        String key = "denstream";
        return (String) getFromJSONObject(key, Config::getAlgoNames);
    }

    public static Integer getTGlobal(){
        String key = "tGlobal";
        return Integer.parseInt( (String) getFromJSONObject(key, Config::getTimeSeriesParams) );
    }

    public static Integer getInitPoints(){
        String key = "initPoints";
        return Integer.parseInt( (String) getFromJSONObject(key, Config::getTimeSeriesParams) );
    }

    // this method will fetch the data and fill the variables
    private static void fetchConfig() {
        JSONParser parser = new JSONParser();
        try {
            String configFilePath = "/home/camila/Desktop/TESIS/DATA/config.json";
            FileReader reader = new FileReader(configFilePath);
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            timeSeriesToyDatasetName = (String) jsonObject.get("timeSeriesToyDatasetName");
            paths = (JSONObject) jsonObject.get("paths");
            algoNames = (JSONObject) jsonObject.get("algoNames");
            timeSeriesParams = (JSONObject) jsonObject.get("timeSeriesParams");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
