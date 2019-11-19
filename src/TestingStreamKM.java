import com.yahoo.labs.samoa.instances.DenseInstance;
import moa.cluster.Clustering;
import moa.clusterers.streamkm.StreamKM;

public class TestingStreamKM {
    static DenseInstance randomInstance(int size) {
        DenseInstance instance = new DenseInstance(size);
        for (int idx = 0; idx < size; idx++) {
            instance.setValue(idx, Math.random());
        }
        return instance;
    }
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
    }
}
