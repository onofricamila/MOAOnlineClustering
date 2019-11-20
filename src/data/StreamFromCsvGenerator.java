package data;

import moa.streams.clustering.SimpleCSVStream;

public class StreamFromCsvGenerator {
    public static SimpleCSVStream simpleCSVStream() {
        SimpleCSVStream stream = new SimpleCSVStream();
        String pathToCSV = "/home/camila/Desktop/TESIS/Github_Repo_ToyDatasetsPersistorIntoCsvs/resources/custom_circumferences_without_k.csv";
        stream.csvFileOption.setValue(pathToCSV);
        stream.classIndexOption.setValue(false); //last column is class lable
        stream.prepareForUse();
        return stream;
    }
}
