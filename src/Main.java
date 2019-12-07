import utils.clusterers.ClustreamClusterer;
import utils.clusterers.DenstreamClusterer;

import static config.Config.getTGlobal;

public class Main {
    public static void main(String[] args) {
        ClustreamClusterer clustreamClusterer = new ClustreamClusterer();
        DenstreamClusterer denstreamClusterer = new DenstreamClusterer();

        int tGlobal = getTGlobal();

        System.out.println("CLUSTREAM ----------------------");
        clustreamClusterer.run(tGlobal);

        System.out.println("\n" + "DENSTREAM ----------------------");
        denstreamClusterer.run(tGlobal);
    }
}
