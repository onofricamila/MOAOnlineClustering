import moa.cluster.Cluster;
import moa.clusterers.AbstractClusterer;
import moa.clusterers.denstream.WithDBSCAN;

import java.util.Arrays;
import java.util.List;

public class DenstreamClusterer extends BasicClusterer {

    public DenstreamClusterer(){
        subfolder = "denstream";
    }


    @Override
    public void storeResult(int moment) {
        storeResult(moment, microClusteringResult, "micro");
    }

    @Override
    protected List<String> formRow(String x, String y, Cluster cluster) {
        // get specific atts
        Double id = cluster.getId();
        String label = Double.toString(id);
        // return row
        return Arrays.asList(x, y, label);
    }


    @Override
    protected String getSubFolder(String resId) {
        return subfolder;
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
