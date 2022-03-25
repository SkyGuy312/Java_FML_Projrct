package backend;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Charts {
    public Charts() {
    }

    public String top_compny_char(Dataset<Row> dataset)throws IOException {
              // Create Chart
        PieChart chart = new PieChartBuilder().width (800).height (600).title (getClass ().getSimpleName ()).build ();
        // Series
       dataset.limit(10).collectAsList().stream().forEach(
               row ->chart.addSeries(row.getString(0),  row.getLong(1) ));
        // Show it
        //new SwingWrapper(chart).displayChart ();
        String path = "target/classes/top_compny_char.jpg";
        File f=new File(path);
        if(f.exists()) {
            f.delete();
        }
        BitmapEncoder.saveJPGWithQuality(chart, path, 1f);
        return path;
    }

    public String popular_title_char(Dataset<Row> dataset) throws IOException {
       List<String> title = dataset.collectAsList().stream().map(row->row.getString(0)).limit(10).collect(Collectors.toList());
       List<Long> count = dataset.collectAsList().stream().map(row->row.getLong(1)).limit(10).collect(Collectors.toList());
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title ("title Histogram").xAxisTitle ("title").yAxisTitle ("count").build ();
        // Customize Chart
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);
        // Series
        chart.addSeries ("title,count", title, count);
        // Show it
        //new SwingWrapper (chart).displayChart ();
        String path = "target/classes/popular_title_char.jpg";
        File f=new File(path);
        if(f.exists()) {
            f.delete();
        }
        BitmapEncoder.saveJPGWithQuality(chart, path, 1f);
        return "popular_title_char.jpg";

    }

    public String popular_area_char(Dataset<Row> dataset) throws IOException {
        List<String> location = dataset.collectAsList().stream().map(row->row.getString(0)).limit(10).collect(Collectors.toList());
        List<Long> count = dataset.collectAsList().stream().map(row->row.getLong(1)).limit(10).collect(Collectors.toList());
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title ("area Histogram").xAxisTitle ("areaLocation").yAxisTitle ("count").build ();
        // Customize Chart
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);
        // Series
        chart.addSeries ("title,count", location, count);
        // Show it
        //new SwingWrapper (chart).displayChart ();
        String path = "target/classes/popular_area_char.jpg";
        File f=new File(path);
        if(f.exists()) {
            f.delete();
        }
        BitmapEncoder.saveJPGWithQuality(chart, path, 1f);
        return "popular_area_char.jpg";
    }

    public String KMean(List<Double> result) throws IOException {
        List<Integer> count = new ArrayList<>();
        for (int i = 2; i <= 8; i++) {count.add(i);}
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title("squared euclidean distance").
                xAxisTitle("number of clusters").
                yAxisTitle("Value").build();
        // Customize Chart
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setYAxisLogarithmic(true);
        chart.getStyler().setXAxisLabelRotation(45);
        // Series
        chart.addSeries ("clusters", count, result);
        // Show it
        //new SwingWrapper (chart).displayChart ();
        String path = "target/classes/popular_area_char.jpg";
        File f=new File(path);
        if(f.exists())
            f.delete();
        BitmapEncoder.saveJPGWithQuality(chart, path, 1f);
        return "popular_area_char.jpg";
    }
}
