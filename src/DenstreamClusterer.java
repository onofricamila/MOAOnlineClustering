import moa.clusterers.AbstractClusterer;
import moa.clusterers.denstream.WithDBSCAN;

public class DenstreamClusterer extends BasicClusterer {

    @Override
    public AbstractClusterer prepareClusterer() {
        WithDBSCAN withDBSCAN = new WithDBSCAN();
        // no matter the horizonOption, the result you get is the same
        withDBSCAN.horizonOption.setValue(1000); // default
        withDBSCAN.epsilonOption.setValue(0.4); // TODO: variate
        // TODO: variate initPointsOption (needed amount of points to start forming macro clusters)
        withDBSCAN.initPointsOption.setValue(100);
        // role: set timestamp value (does not mean batches to process)
        withDBSCAN.speedOption.setValue(100); // default
        withDBSCAN.resetLearningImpl();
        withDBSCAN.initialDBScan();
        return withDBSCAN;
    }
}
