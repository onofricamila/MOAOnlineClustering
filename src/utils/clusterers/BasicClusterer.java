package utils.clusterers;

import com.yahoo.labs.samoa.instances.DenseInstance;
import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.cluster.SphereCluster;
import moa.clusterers.AbstractClusterer;
import moa.core.InstanceExample;
import moa.streams.clustering.SimpleCSVStream;
import org.json.simple.JSONObject;
import utils.persitors.BasicPersistor;
import java.util.ArrayList;
import java.util.List;

import static config.Config.*;
import static utils.data_generators.StreamFromCsvGenerator.simpleCSVStream;

public abstract class BasicClusterer {
    Clustering clusteringResult;
    Clustering microClusteringResult;
    BasicPersistor basicPersistor = new BasicPersistor();
    String subfolder;
    JSONObject algoConfig = new JSONObject();

    public void run() {
        // fetch tGlobal (every 'tGlobal' processed samples we'll get the clustering result),
        // initPoints (min points needed to get started with the macro clusters) and
        // timeWindow ()
        int initPoints = getInitPoints();
        int tGlobal = getTGlobal();
        int timeWindow = getTimeWindow(); // TODO: check if the tWindow must be different for den and clu stream

        // delete old results
        resetStorage();

        int ac = 0; // ac to count till tGlobal
        int processedSamples = 0; // for debugging

        AbstractClusterer clusterer = prepareClusterer(initPoints, timeWindow);

        System.out.println(algoConfig);
        // store algoConfig
        basicPersistor.storeAlgoConfig(algoConfig, this.subfolder);

        // get CSV data
        SimpleCSVStream stream = simpleCSVStream();
        while (stream.hasMoreInstances()) {
            InstanceExample trainInst = stream.nextInstance();
            // get dense instance
            DenseInstance inst = (DenseInstance) trainInst.instance;
            // bye empty class
            inst.deleteAttributeAt(2);
            //learning code
            clusterer.trainOnInstanceImpl(inst);
            ac += 1;
            if (ac == tGlobal){
                clusteringResult = clusterer.getClusteringResult();
                microClusteringResult = clusterer.getMicroClusteringResult();
                // debug
                processedSamples += tGlobal;
                showClusteringInfo(processedSamples, clusteringResult);
                // store
                storeResult(processedSamples);
                // reset ac
                ac = 0;
            }
        }
    }


    public abstract void storeResult(int moment);

    public void storeResult(int moment, Clustering clustering, String resId) {
        List list = new ArrayList();
        for (int i = 0; i < clustering.size(); i++) {
            Cluster cluster = clustering.get(i);
            // we always get the center
            double[] center = cluster.getCenter();
            String x =Double.toString(center[0]);
            String y =Double.toString(center[1]);
            // form the specific row
            List<String> row = formRow(x,y, cluster);
            list.add(row);
        }
        String m  = Integer.toString(moment);
        String subf = getSpecificClusteringResultFolder(resId); // hook method
        // store current clustering
        basicPersistor.storeResult(m, list, subf);
    }

    protected abstract List<String> formRow(String x, String y, Cluster cluster);

    protected abstract String getSpecificClusteringResultFolder(String specificClusResId);


    public void resetStorage() {
        // delete old results and old algoConfig
        basicPersistor.resetStorage(subfolder);
    }


    // set specific parameters for each algo
    public abstract AbstractClusterer prepareClusterer(int initMinPoints, int tGlobal);


    private void showClusteringInfo(int processedSamples, Clustering clustering) {
        System.out.println(processedSamples + " procesados   |   " + clusteringResult.size() + " clusters");
        int sumW = 0;
        for (int i = 0; i < clustering.size(); i++) {
            Cluster cluster = clustering.get(i);

            double[] center = cluster.getCenter();
            double w = cluster.getWeight();
            double id = cluster.getId();

            SphereCluster sc = (SphereCluster) cluster;
            double r = sc.getRadius();

            System.out.println("\n" + "    id: " + id);
            System.out.println("    center: " + center[0] + "  , " + center[1]);
            System.out.println("    weight: " + w);
            System.out.println("    radius: " + r);
            sumW += w;
        }
        System.out.println("    * sumw: " + sumW + "\n\n");
    }
}
