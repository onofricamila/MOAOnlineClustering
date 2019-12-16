package utils.clusterers;

import moa.cluster.Cluster;
import moa.clusterers.AbstractClusterer;
import moa.clusterers.clustream.ClustreamKernel;
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
        double radius = ((ClustreamKernel) cluster).getRadius();
        String rad =Double.toString(radius);
        return Arrays.asList(x, y, rad);
    }

    @Override
    protected String getSpecificClusteringResultFolder(String specificClusResId) {
        // returns the clu stream root folder + a specific one, depending on what results are being stored: micro / macro
        return subfolder + '/' + specificClusResId;
    }


    @Override
    public AbstractClusterer prepareClusterer(int initPoints) {
        WithKmeans withKmeans = new WithKmeans();
        /* horizon: Defines the time window in seconds to be used in CluStream. Used to free some space to insert a
        new kernel, controling which microclusters become expired */
        int timeWindow = 200; // default 1000
        withKmeans.timeWindowOption.setValue(timeWindow);

        /* m: Defines the maximum number of micro-clusters used in CluStream. A buffer of the same size will be used
        for initialization: once the buffer is full, the macro clusters start being formed. */
        // TODO: vary maxNumKernels. A buffer of the same size will be used for initialization
        int maxNumKernels = initPoints; // default 100
        withKmeans.maxNumKernelsOption.setValue(maxNumKernels);

        /* t: Maximal boundary factor (= kernel radius factor). When deciding to add a new data point to a micro-cluster,
        the maximum boundary is defined as a factor of the RMS deviation of the data points in the micro-cluster from the centroid. */
        int kernelRadiFactor = 2; // default 2
        withKmeans.kernelRadiFactorOption.setValue(kernelRadiFactor);

        /* k: Number of macro-clusters to produce using weighted k-means. */
        int k = 2; // default 5
        withKmeans.kOption.setValue(k);

        // fill json object algoConfig
        algoConfig.put("timeWindow", timeWindow);
        algoConfig.put("maxNumKernels", maxNumKernels);
        algoConfig.put("kernelRadiFactor", kernelRadiFactor);
        algoConfig.put("k", k);

        return withKmeans;
    }
}
