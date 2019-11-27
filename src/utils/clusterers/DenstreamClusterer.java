package utils.clusterers;

import moa.cluster.Cluster;
import moa.clusterers.AbstractClusterer;
import moa.clusterers.denstream.WithDBSCAN;

import java.util.Arrays;
import java.util.List;

import static config.Config.getDenStreamName;

public class DenstreamClusterer extends BasicClusterer {

    public DenstreamClusterer(){
        subfolder = getDenStreamName();
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
        /* horizon: ?. No matter the value, the result you get is the same. */
        withDBSCAN.horizonOption.setValue(1000); // = default 1000

        /* epsilon: defines the epsilon neighbourhood which is the maximal radius of micro-clusters(r<=epsilon). Range [0, 1] */
        // TODO: vary epsilonOption,
        withDBSCAN.epsilonOption.setValue(0.4); // default 0.2

        /* beta: multiplier for mu to detect outlier micro-clusters given their weight w (w<betax mu). Range [0, 1] */
        withDBSCAN.betaOption.setValue(0.2); // = default 0.2

        /* mu: minpoints as the weight w a core-micro-clusters needs to be created (w>=mu). Range [0, +inf] */
        withDBSCAN.muOption.setValue(1); // = default 1

        /* initPoints: number of points to use for initialization via DBSCAN. Default: 1000  */
        // TODO: vary initPointsOption (needed amount of points to start forming macro clusters)
        withDBSCAN.initPointsOption.setValue(initMinPoints);

        /* offline: offline multiplier for epsilon. Used for reachabilityreclustering. Range [2, 20] */
        withDBSCAN.offlineOption.setValue(2); // = default 2

        /* lambda: decay constant. Range [0.25, 1] */
        // TODO: vary lambda (forgetting component)
        withDBSCAN.lambdaOption.setValue( Double.parseDouble("".trim()) ); // = default ""

        /* processingSpeed: number of incoming points per time unit (important for decay). Role: set timestamp value (does not mean "batches to process") */
        withDBSCAN.speedOption.setValue(100); // = default 100

        // once the parameters are specified, prepare the clusterer
        withDBSCAN.resetLearningImpl();
        withDBSCAN.initialDBScan();
        return withDBSCAN;
    }
}
