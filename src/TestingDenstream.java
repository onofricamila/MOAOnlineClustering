import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.DenseInstance;
import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.cluster.SphereCluster;
import moa.clusterers.denstream.WithDBSCAN;

import java.util.ArrayList;
import java.util.Random;

public class TestingDenstream {
    static DenseInstance randomInstance(int size) {
        // generates the name of the features which is called as InstanceHeader
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        for (int i = 0; i < size; i++) {
            attributes.add(new Attribute("feature_" + i));
        }
        // create instance header with generated feature name
        InstancesHeader streamHeader = new InstancesHeader(
                new Instances("Mustafa Çelik Instance",attributes, size));

        // generates random data
        double[] data = new double[2];
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            data[i] = random.nextDouble();
        }

        // creates an instance and assigns the data
        DenseInstance inst = new DenseInstance(1.0, data);

        // assigns the instanceHeader(feature name)
        inst.setDataset(streamHeader);

        return inst;
    }


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

        System.out.println(clusteringResult);

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
