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
        withKmeans.kOption.setValue(2);
        withKmeans.timeWindowOption.setValue(1000); // default
        // TODO: vary maxNumKernelsOption. A buffer of the same size will be used for initialization
        withKmeans.maxNumKernelsOption.setValue(initMinPoints-1);
        //    wkm.kernelRadiFactorOption.setValue(5);
        withKmeans.resetLearningImpl();
        return withKmeans;
    }
}
