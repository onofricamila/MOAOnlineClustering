import moa.clusterers.AbstractClusterer;
import moa.clusterers.clustream.WithKmeans;

public class ClustreamClusterer extends BasicClusterer {

    @Override
    public AbstractClusterer prepareClusterer() {
        WithKmeans withKmeans = new WithKmeans();
        withKmeans.kOption.setValue(2);
        withKmeans.timeWindowOption.setValue(1000); // default
        // TODO: variate maxNumKernelsOption. A buffer of the same size will be used for initialization
        withKmeans.maxNumKernelsOption.setValue(100);
        //    wkm.kernelRadiFactorOption.setValue(5);
        withKmeans.resetLearningImpl();
        return withKmeans;
    }
}
