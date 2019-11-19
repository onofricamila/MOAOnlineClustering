import com.yahoo.labs.samoa.instances.DenseInstance;
import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.cluster.SphereCluster;
import moa.clusterers.denstream.WithDBSCAN;
import static data.InstanceWithHeaderGenerator.randomInstance;

public class TestingDenstream {
    public static void main(String[] args) {
        WithDBSCAN withDBSCAN = new WithDBSCAN();
        withDBSCAN.resetLearningImpl();
        withDBSCAN.initialDBScan();
        for (int i = 0; i < 1500; i++) {
            DenseInstance d = randomInstance(5);
            withDBSCAN.trainOnInstanceImpl(d);
        }
        Clustering clusteringResult = withDBSCAN.getClusteringResult();
        Clustering microClusteringResult = withDBSCAN.getMicroClusteringResult();

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
            System.out.println("radius: " + r);
            System.out.println("weight: " + w + "\n");
            sumW += w;
        }
        System.out.println("sumw: " + sumW);
    }
}
