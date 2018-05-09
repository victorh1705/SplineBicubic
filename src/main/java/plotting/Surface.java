package plotting;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.la4j.Vector;
import structure.Matriz;

public class Surface extends AbstractAnalysis {

    private Matriz matriz;
    private Vector vector;


    public Surface(Matriz matriz, Vector vector) {
        this.matriz = matriz;
        this.vector = vector;
    }

    @Override
    public void init() {
        // Define a function to plot
        Mapper mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                return Matriz.valorZ2(matriz,vector,x,y);
            }
        };

        // Define range and precision for the function to plot
        Range range = new Range(0, 2);
        int steps = 20;

        // Create the object to represent the function over the given range.
        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        // Create a chart
        chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
        chart.getScene().getGraph().add(surface);
    }
}