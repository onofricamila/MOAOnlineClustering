import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ContourBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TestingPlots {
    public static void main(String[] args) {
      //  scatter();
        plotWithColors();
     //   plotSpheres();
    }


    public static void scatter(){
        // TESTING HOW TO PLOT
        List<Double> x = NumpyUtils.linspace(-3, 3, 100);
        List<Double> y = x.stream().map(xi -> Math.sin(xi) + Math.random()).collect(Collectors.toList());

        Plot plt = Plot.create();
        plt.plot().add(x, y, "s").label("sin");
        plt.title("scatter");
        plt.legend().loc("upper right");
        show(plt);
    }


    public static void plotWithColors(){
        // TESTING COLORS
        List<Double> x = NumpyUtils.linspace(-1, 1, 100);
        List<Double> y = NumpyUtils.linspace(-1, 1, 100);
        NumpyUtils.Grid<Double> grid = NumpyUtils.meshgrid(x, y);

        List<List<Double>> cCalced = grid.calcZ((xi, yj) -> Math.sqrt(xi * xi + yj * yj));

        Plot plt = Plot.create();
        plt.pcolor().add(x, y, cCalced).cmap("plt.cm.nipy_spectral"); // "plt.cm.Blues"
        plt.title("pcolor");
        plt.legend().loc("upper right");
        show(plt);
    }


    public static void plotSpheres(){
        // TESTING RADIUS
        List<Double> x = NumpyUtils.linspace(-1, 1, 5);
        List<Double> y = NumpyUtils.linspace(-1, 1, 5);
        NumpyUtils.Grid<Double> grid = NumpyUtils.meshgrid(x, y);

        List<List<Double>> zCalced = grid.calcZ((xi, yj) -> Math.sqrt(xi * xi + yj * yj));

        Plot plt = Plot.create();
        ContourBuilder contour = plt.contour().add(x, y, zCalced);
        plt.clabel(contour).inline(true).fontsize(10);
        plt.title("contour");
        plt.legend().loc("upper right");
        show(plt);
    }


    private static void show(Plot plt){
        try {
            plt.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PythonExecutionException e) {
            e.printStackTrace();
        }
    }
}
