import utils.clusterers.ClustreamClusterer;
import utils.clusterers.DenstreamClusterer;

public class Main {
    public static void main(String[] args) {
        ClustreamClusterer clustreamClusterer = new ClustreamClusterer();
        DenstreamClusterer denstreamClusterer = new DenstreamClusterer();

        int tGlobal = 200;

        System.out.println("CLUSTREAM ----------------------");
        clustreamClusterer.run(tGlobal);

        System.out.println("\n" + "DENSTREAM ----------------------");
        denstreamClusterer.run(tGlobal);
    }
}
