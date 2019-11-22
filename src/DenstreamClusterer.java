import moa.cluster.Cluster;
import moa.clusterers.AbstractClusterer;
import moa.clusterers.denstream.WithDBSCAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DenstreamClusterer extends BasicClusterer {

    public DenstreamClusterer(){
        subfolder = "denstream";
    }


    @Override
    public void storeResult(int moment) {
        List list = new ArrayList();
        for (int i = 0; i < microClusteringResult.size(); i++) {
            Cluster cluster = microClusteringResult.get(i);
            double[] center = cluster.getCenter();
            double id = cluster.getId();
            String x =Double.toString(center[0]);
            String y =Double.toString(center[1]);
            String label =Double.toString(id);

            List<String> row = Arrays.asList(x, y, label);
            list.add(row);
        }
        String m  = Integer.toString(moment);
        // store current clustering
        basicCSVPersistor.storeResult(m, list, subfolder);
    }


    @Override
    public AbstractClusterer prepareClusterer(int initMinPoints) {
        WithDBSCAN withDBSCAN = new WithDBSCAN();
        // no matter the horizonOption, the result you get is the same
        withDBSCAN.horizonOption.setValue(1000); // default
        withDBSCAN.epsilonOption.setValue(0.4); // TODO: vary
        // TODO: vary initPointsOption (needed amount of points to start forming macro clusters)
        withDBSCAN.initPointsOption.setValue(initMinPoints);
        // role: set timestamp value (does not mean batches to process)
        withDBSCAN.speedOption.setValue(100); // default
        withDBSCAN.resetLearningImpl();
        withDBSCAN.initialDBScan();
        return withDBSCAN;
    }
}
