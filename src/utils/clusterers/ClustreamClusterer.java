package utils.clusterers;

import moa.cluster.Cluster;
import moa.cluster.SphereCluster;
import moa.clusterers.AbstractClusterer;
import moa.clusterers.clustream.WithKmeans;

import java.util.Arrays;
import java.util.List;

import static config.Config.getCluStreamName;

public class ClustreamClusterer extends BasicClusterer {

    public ClustreamClusterer(){
        subfolder = getCluStreamName();
    }

    @Override
    public void storeResult(int moment) {
        storeResult(moment, microClusteringResult, "micro");
        storeResult(moment, clusteringResult,  "macro");
    }

    @Override
    protected List<String> formRow(String x, String y, Cluster cluster) {
        double radius = ((SphereCluster) cluster).getRadius();
        String rad =Double.toString(radius);
        return Arrays.asList(x, y, rad);
    }

    @Override
    protected String getSubFolder(String resId) {
        return subfolder + '/' + resId;
    }


    @Override
    public AbstractClusterer prepareClusterer(int initMinPoints) {
        WithKmeans withKmeans = new WithKmeans();
        /* horizon: Defines the time window to be used in CluStream (snapshots horizon). */
        withKmeans.timeWindowOption.setValue(1000); // = default 1000

        /* m: Defines the maximum number of micro-clusters used in CluStream. A buffer of the same size will be used
        for initialization: once the buffer is full, the macro clusters start being formed. Default: 100. */
        // TODO: vary maxNumKernelsOption. A buffer of the same size will be used for initialization
        withKmeans.maxNumKernelsOption.setValue(initMinPoints-1);

        /* t: Maximal boundary factor (=Kernel radius factor). When deciding to add a newdata point to a micro-cluster,
        the maximum boundary is defined as a factor oftof the RMS deviation of the data points in the micro-cluster from the centroid. */
        withKmeans.kernelRadiFactorOption.setValue(2); // = default 2

        /* k: Number of macro-clusters to produce using weighted k-means. Default: 5. */
        withKmeans.kOption.setValue(2);

        // once the parameters are specified, prepare the clusterer
        withKmeans.resetLearningImpl();
        return withKmeans;
    }
}
