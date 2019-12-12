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

import static config.Config.getInitPoints;
import static utils.data_generators.StreamFromCsvGenerator.simpleCSVStream;

public abstract class BasicClusterer {
    Clustering clusteringResult;
    Clustering microClusteringResult;
    BasicPersistor basicPersistor = new BasicPersistor();
    String subfolder;
    JSONObject algoConfig = new JSONObject();

    public void run(int tGlobal) {
        // fetch initPoints: min points needed to get started with the macro clusters
        Integer initPoints = getInitPoints();

        // delete old results
        resetStorage();

        int i = 0; // meaning processed samples (to count till tGlobal)
        int sample = tGlobal; // for debugging

        AbstractClusterer clusterer = prepareClusterer(initPoints, tGlobal);

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
            i += 1;
            if (i == tGlobal){
                // TODO: store result in csv: center + label
                clusteringResult = clusterer.getClusteringResult();
                microClusteringResult = clusterer.getMicroClusteringResult();
                // debug
                showClusteringInfo(sample, clusteringResult);
                // store
                storeResult(sample);
                // reset i
                i = 0;
                sample += tGlobal;
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


    private void showClusteringInfo(int sample, Clustering clustering) {
        System.out.println(sample + " procesados   |   " + clusteringResult.size() + " clusters");
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
