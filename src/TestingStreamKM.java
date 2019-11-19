import com.yahoo.labs.samoa.instances.DenseInstance;
import moa.cluster.Clustering;
import moa.clusterers.streamkm.StreamKM;
import static data.InstanceGenerator.randomInstance;

public class TestingStreamKM {
    public static void main(String[] args) {
        StreamKM streamKM = new StreamKM();
        streamKM.numClustersOption.setValue(5); // default setting
        streamKM. resetLearningImpl();
        for (int i = 0; i < 50000; i++) {
            DenseInstance d = randomInstance(2);
            streamKM.trainOnInstanceImpl(d);
        }
        Clustering result = streamKM.getClusteringResult();
        System.out.println("size = " + result.size());
        System.out.println("dimension = " + result.dimension());
        System.out.println("result = " + result);
    }
}
