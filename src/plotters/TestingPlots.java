package plotters;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ContourBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TestingPlots {
    public static void main(String[] args) {
        // TESTING HOW TO PLOT
        List<Double> x = NumpyUtils.linspace(-3, 3, 100);
        List<Double> y = x.stream().map(xi -> Math.sin(xi) + Math.random()).collect(Collectors.toList());

        Plot plt = Plot.create();
        plt.plot().add(x, y, "o").label("sin");
        plt.title("scatter");
        plt.legend().loc("upper right");

        try {
            plt.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                PythonExecutionException e) {
            e.printStackTrace();
        }


        // TESTING COLORS
        x = NumpyUtils.linspace(-1, 1, 100);
        y = NumpyUtils.linspace(-1, 1, 100);
        NumpyUtils.Grid<Double> grid = NumpyUtils.meshgrid(x, y);

        List<List<Double>> cCalced = grid.calcZ((xi, yj) -> Math.sqrt(xi * xi + yj * yj));

        plt = Plot.create();
        plt.pcolor().add(x, y, cCalced).cmap("plt.cm.Blues");
        plt.title("pcolor");
        plt.legend().loc("upper right");

        try {
            plt.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PythonExecutionException e) {
            e.printStackTrace();
        }


        // TESTING RADIUS
        x = NumpyUtils.linspace(-1, 1, 100);
        y = NumpyUtils.linspace(-1, 1, 100);
        grid = NumpyUtils.meshgrid(x, y);

        List<List<Double>> zCalced = grid.calcZ((xi, yj) -> Math.sqrt(xi * xi + yj * yj));

        plt = Plot.create();
        ContourBuilder contour = plt.contour().add(x, y, zCalced);
        plt.clabel(contour).inline(true).fontsize(10);
        plt.title("contour");
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
