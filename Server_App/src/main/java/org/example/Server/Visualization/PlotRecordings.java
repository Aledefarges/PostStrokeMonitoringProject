package org.example.Server.Visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Arrays;

public class PlotRecordings {
public static void showChartFromArray(Double[] data, String title) {
    XYSeries series = new XYSeries("Data");
    for(int i = 0; i < data.length; i++) {
        series.add(i, data[i]);
    }

    XYSeriesCollection dataset = new XYSeriesCollection(series);

    JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "Time (s)",
            "Value",
            dataset
    );

    XYPlot plot = chart.getXYPlot();

    double min = Arrays.stream(data).mapToDouble(v -> v).min().orElse(0);
    double max = Arrays.stream(data).mapToDouble(v -> v).max().orElse(1);

    if (min == max) {
        max = min + 1;
    }
    // Fix margins
    double margin = (max - min) * 0.1;
    plot.getRangeAxis().setRange(min-margin, max+margin);

    ChartPanel chartPanel = new ChartPanel(chart);

    JFrame frame = new JFrame("Grafic "+title);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setContentPane(chartPanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    }
}