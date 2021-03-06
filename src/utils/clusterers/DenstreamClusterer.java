package utils.clusterers;

import moa.cluster.Cluster;
import moa.cluster.SphereCluster;
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
        double radius = ((SphereCluster) cluster).getRadius();
        String rad = Double.toString(radius);// return row
        return Arrays.asList(x, y, rad, label);
    }


    @Override
    protected String getSpecificClusteringResultFolder(String specificClusResId) {
        // here we only care about micro clusters, so they will be stored in the den stream root folder, not in a specific one.
        return subfolder;
    }

    @Override
    public AbstractClusterer prepareClusterer(int initMinPoints) {
        WithDBSCAN withDBSCAN = new WithDBSCAN();

        /* epsilon: defines the epsilon neighbourhood which is the maximal radius of micro-clusters(r<=epsilon). Range [0, 1] */
        double epsilon = 0.1; // default 0.2
        withDBSCAN.epsilonOption.setValue(epsilon);

        /* mu: minpoints as the weight w a core-micro-clusters needs to be created (w>=mu). Range [0, +inf] */
        int mu = 1; // default 1
        withDBSCAN.muOption.setValue(mu);

        /* beta: multiplier for mu to detect outlier micro-clusters given their weight w (w< beta*mu). Range [0, 1] */
        double beta = 0.2; // default 0.2
        withDBSCAN.betaOption.setValue(beta);

         /* initPoints: number of points to use for initialization via DBSCAN. */
        int initPoints = initMinPoints; // default: 1000
        withDBSCAN.initPointsOption.setValue(initPoints);

        /* offline: offline multiplier for epsilon. Used for reachability reclustering: DenStream applies reachability
        (from DBSCAN) between micro-clusters for reclustering using epsilon x offline (defaults to 2) as the
        reachability threshold. Range [2, 20] */
        int offline = 5; // default 2
        withDBSCAN.offlineOption.setValue(offline);

        /* lambda: decay constant. Used to determine 'the portion' you are going to forget when returning certain att,
        and also when determining if a cluster must be deleted or not. Range [0.25, 1] */
        double lambda = 0.7; // default ""
        withDBSCAN.lambdaOption.setValue(lambda);

        /* processingSpeed: number of incoming points per time unit (important for decay). Role: set timestamp value */
        int speed = 50; // default 100
        withDBSCAN.speedOption.setValue(speed);

        // fill json object algoConfig
        algoConfig.put("epsilon", epsilon);
        algoConfig.put("beta", beta);
        algoConfig.put("mu", mu);
        algoConfig.put("initPoints", initPoints);
        algoConfig.put("offline", offline);
        algoConfig.put("lambda", lambda);
        algoConfig.put("speed", speed);

        return withDBSCAN;
    }
}
