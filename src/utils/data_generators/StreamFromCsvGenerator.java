package utils.data_generators;

import moa.streams.clustering.SimpleCSVStream;
import static config.Config.getCsvDatasetsPath;
import static config.Config.getTimeSeriesToyDatasetName;

public class StreamFromCsvGenerator {
    public static SimpleCSVStream simpleCSVStream() {
        SimpleCSVStream stream = new SimpleCSVStream();
        String pathToCSV = getCsvDatasetsPath() + getTimeSeriesToyDatasetName();
        stream.csvFileOption.setValue(pathToCSV);
        stream.classIndexOption.setValue(false); //last column is class lable
        stream.prepareForUse();
        return stream;
    }
}
