import moa.clusterers.AbstractClusterer;
import moa.clusterers.denstream.WithDBSCAN;

public class DenstreamClusterer extends BasicClusterer {

    @Override
    public AbstractClusterer prepareClusterer() {
        WithDBSCAN withDBSCAN = new WithDBSCAN();
        withDBSCAN.horizonOption.setValue(100);
        withDBSCAN.epsilonOption.setValue(0.4);
        withDBSCAN.resetLearningImpl();
        withDBSCAN.initialDBScan();
        return withDBSCAN;
    }
}
