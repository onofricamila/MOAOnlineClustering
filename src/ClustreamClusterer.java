import moa.clusterers.AbstractClusterer;
import moa.clusterers.clustream.WithKmeans;

public class ClustreamClusterer extends BasicClusterer {

    @Override
    public AbstractClusterer prepareClusterer() {
        WithKmeans withKmeans = new WithKmeans();
        withKmeans.kOption.setValue(2);
        withKmeans.timeWindowOption.setValue(100);
        withKmeans.maxNumKernelsOption.setValue(1000);
        //    wkm.kernelRadiFactorOption.setValue(5);
        withKmeans.resetLearningImpl();
        return withKmeans;
    }
}
