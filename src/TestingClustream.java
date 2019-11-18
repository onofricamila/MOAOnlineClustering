import com.yahoo.labs.samoa.instances.DenseInstance;
import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.cluster.SphereCluster;
import moa.clusterers.clustream.WithKmeans;

public class TestingClustream {
    static DenseInstance randomInstance(int size) {
        DenseInstance instance = new DenseInstance(size);
        for (int idx = 0; idx < size; idx++) {
            instance.setValue(idx, Math.random());
        }
        return instance;
    }

    public static void main(String[] args) {
        WithKmeans wkm = new WithKmeans();
        wkm.kOption.setValue(5);
        wkm.timeWindowOption.setValue(100);
        wkm.maxNumKernelsOption.setValue(1000);
        wkm.kernelRadiFactorOption.setValue(5);
        wkm.resetLearningImpl();

        for (int i = 0; i < 10000; i++) {
            wkm.trainOnInstanceImpl(randomInstance(2));
        }

        Clustering clusteringResult = wkm.getClusteringResult();
        Clustering microClusteringResult = wkm.getMicroClusteringResult();

        int sumW = 0;
        Clustering list = clusteringResult;
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
