import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.yahoo.labs.samoa.instances.DenseInstance;
import moa.cluster.Clustering;
import moa.clusterers.streamkm.StreamKM;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

        // TESTING HOW TO PLOT
        List<Double> x = NumpyUtils.linspace(-3, 3, 100);
        List<Double> y = x.stream().map(xi -> Math.sin(xi) + Math.random()).collect(Collectors.toList());

        Plot plt = Plot.create();
        plt.plot().add(x, y, "o").label("sin");
        plt.title("scatter");
        plt.legend().loc("upper right");
        try {
            plt.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PythonExecutionException e) {
            e.printStackTrace();
        }
    }
}
