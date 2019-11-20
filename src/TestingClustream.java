import com.yahoo.labs.samoa.instances.DenseInstance;
import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.cluster.SphereCluster;
import moa.clusterers.clustream.WithKmeans;
import moa.core.InstanceExample;
import moa.streams.clustering.SimpleCSVStream;

import static data.StreamFromCsvGenerator.simpleCSVStream;

public class TestingClustream {
    public static void main(String[] args) {
        WithKmeans wkm = new WithKmeans();
        wkm.kOption.setValue(2);
        wkm.timeWindowOption.setValue(100);
        wkm.maxNumKernelsOption.setValue(1000);
    //    wkm.kernelRadiFactorOption.setValue(5);
        wkm.resetLearningImpl();

        /*for (int i = 0; i < 10000; i++) {
            wkm.trainOnInstanceImpl(randomInstance(2));
        }
*/
        SimpleCSVStream stream = simpleCSVStream();
        while (stream.hasMoreInstances()) {
            InstanceExample trainInst = stream.nextInstance();
            // get dense instance
            DenseInstance inst = (DenseInstance) trainInst.instance;
            // by empty class
            inst.deleteAttributeAt(2);
            //learning code
            wkm.trainOnInstanceImpl(inst);
        }

        Clustering clusteringResult = wkm.getClusteringResult();
        Clustering microClusteringResult = wkm.getMicroClusteringResult();

        int sumW = 0;
        Clustering list = microClusteringResult;
        for (int i = 0; i < list.size(); i++) {
            Cluster cluster = list.get(i);
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
