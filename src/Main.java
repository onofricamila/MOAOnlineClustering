import utils.clusterers.ClustreamClusterer;
import utils.clusterers.DenstreamClusterer;

public class Main {
    public static void main(String[] args) {
        ClustreamClusterer clustreamClusterer = new ClustreamClusterer();
        DenstreamClusterer denstreamClusterer = new DenstreamClusterer();

        System.out.println("CLUSTREAM ----------------------");
        clustreamClusterer.run();

        System.out.println("\n" + "DENSTREAM ----------------------");
        denstreamClusterer.run();
    }
}
