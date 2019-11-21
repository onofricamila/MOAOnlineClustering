import com.yahoo.labs.samoa.instances.DenseInstance;
import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.cluster.SphereCluster;
import moa.clusterers.AbstractClusterer;
import moa.core.InstanceExample;
import moa.streams.clustering.SimpleCSVStream;

import static data.StreamFromCsvGenerator.simpleCSVStream;

public abstract class BasicClusterer {

    public void run(int tGlobal) {
        int i = 0; // meaning processed samples (to count till tGlobal)
        int sample = tGlobal; // for debugging

        AbstractClusterer clusterer = prepareClusterer(tGlobal);

        // get CSV data
        SimpleCSVStream stream = simpleCSVStream();
        while (stream.hasMoreInstances()) {
            InstanceExample trainInst = stream.nextInstance();
            // get dense instance
            DenseInstance inst = (DenseInstance) trainInst.instance;
            // by empty class
            inst.deleteAttributeAt(2);
            //learning code
            clusterer.trainOnInstanceImpl(inst);
            i += 1;
            if (i == tGlobal){
                // TODO: store result in csv: center + label
                Clustering clusteringResult = clusterer.getClusteringResult();
                Clustering microClusteringResult = clusterer.getMicroClusteringResult();
                // debug
                System.out.println(sample + " procesados   |   " + clusteringResult.size() + "clusters");
                // reset i
                i = 0;
                sample += tGlobal;
            }
        }

        Clustering clusteringResult = clusterer.getClusteringResult();
        Clustering microClusteringResult = clusterer.getMicroClusteringResult();

        showClusteringInfo(clusteringResult);
    }


    // set specific parameters for each algo
    public abstract AbstractClusterer prepareClusterer(int initMinPoints);


    private void showClusteringInfo(Clustering clustering) {
        int sumW = 0;
        for (int i = 0; i < clustering.size(); i++) {
            Cluster cluster = clustering.get(i);

            double[] center = cluster.getCenter();
            double w = cluster.getWeight();
            double id = cluster.getId();

            SphereCluster sc = (SphereCluster) cluster;
            double r = sc.getRadius();

            System.out.println("id: " + id);
            System.out.println("center: " + center[0] + "  , " + center[1]);
            System.out.println("weight: " + w);
            System.out.println("radius: " + r + "\n");
            sumW += w;
        }
        System.out.println("sumw: " + sumW);
    }
}
