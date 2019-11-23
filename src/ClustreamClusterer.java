import moa.cluster.Cluster;
import moa.cluster.SphereCluster;
import moa.clusterers.AbstractClusterer;
import moa.clusterers.clustream.WithKmeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClustreamClusterer extends BasicClusterer {

    public ClustreamClusterer(){
        subfolder = "clustream";
    }


    @Override
    public void storeResult(int moment) {
        // TODO: implement this method
        List list = new ArrayList();
        for (int i = 0; i < microClusteringResult.size(); i++) {
            Cluster cluster = microClusteringResult.get(i);
            double[] center = cluster.getCenter();
            double radius = ((SphereCluster) cluster).getRadius();
            String x =Double.toString(center[0]);
            String y =Double.toString(center[1]);
            String rad =Double.toString(radius);

            List<String> row = Arrays.asList(x, y, rad);
            list.add(row);
        }
        String m  = Integer.toString(moment);
        // store current clustering
        String completePath = subfolder + '/' + "micro" ;
        basicCSVPersistor.storeResult(m, list, completePath);

        list = new ArrayList();
        for (int i = 0; i < clusteringResult.size(); i++) {
            Cluster cluster = clusteringResult.get(i);
            double[] center = cluster.getCenter();
            double radius = ((SphereCluster) cluster).getRadius();
            String x =Double.toString(center[0]);
            String y =Double.toString(center[1]);
            String rad =Double.toString(radius);

            List<String> row = Arrays.asList(x, y, rad);
            list.add(row);
        }
        m  = Integer.toString(moment);
        // store current clustering
        completePath = subfolder + '/' + "macro" ;
        basicCSVPersistor.storeResult(m, list, completePath);
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
