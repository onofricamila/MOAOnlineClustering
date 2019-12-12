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

    private static JSONObject getAlgoNames() {
        return algoNames;
    }

    private static JSONObject getTimeSeriesParams() {
        return timeSeriesParams;
    }

    private static JSONObject getPaths() {
        return paths;
    }

    private static String retTimeSeriesToyDatasetName() {
        return timeSeriesToyDatasetName;
    }

    public static Object fetchElementIfNull(Callable getter){
        try {
            Object object =  getter.call();
            if (object != null){
                return object;
            }
            // else
            fetchConfig();
            return  getter.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getElementFromJSONObject(String key, Callable getter){
        return  ( (JSONObject) fetchElementIfNull(getter) ).get(key);
    }

    public static String getClusteringResultsPath(){
        String key = "clusteringResultsPath";
        return (String) getElementFromJSONObject(key, Config::getPaths);
    }

   public static String getTimeSeriesDatasetsPath(){
       String key = "timeSeriesDatasetsPath";
       return (String) getElementFromJSONObject(key, Config::getPaths);
    }

    public static String getTimeSeriesToyDatasetName(){
        return (String) fetchElementIfNull(Config::retTimeSeriesToyDatasetName);
    }

    public static String getCluStreamName(){
        String key = "clustream";
        return (String) getElementFromJSONObject(key, Config::getAlgoNames);
    }

    public static String getDenStreamName(){
        String key = "denstream";
        return (String) getElementFromJSONObject(key, Config::getAlgoNames);
    }

    public static Integer getTGlobal(){
        String key = "tGlobal";
        return Integer.parseInt( (String) getElementFromJSONObject(key, Config::getTimeSeriesParams) );
    }

    public static Integer getTimeWindow(){
        String key = "timeWindow";
        return Integer.parseInt( (String) getElementFromJSONObject(key, Config::getTimeSeriesParams) );
    }

    public static Integer getInitPoints(){
        String key = "initPoints";
        return Integer.parseInt( (String) getElementFromJSONObject(key, Config::getTimeSeriesParams) );
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
