package org.example.Server.Visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

/*public class PlotRecordings {
    public static XYSeries loadRecordingSeries(Connection c, int recordingId, int channel) throws Exception {
        XYSeries series = new XYSeries("Recording" + recordingId + "(a" + channel + ")");

        String sql = "SELECT frame_index, a0, a1, a2, a3, a4, a5 " +
                      "FROM RecordingFrames WHERE recording_id = ? ORDER BY frame_index ASC";

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, recordingId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int idx = rs.getInt("frame_index");
            int val = rs.getInt("a" + channel); //escoge canal
            series.add(idx, val);
        }
        rs.close();
        ps.close();
        return series;
    }


    public static void showChart(XYSeries series) {
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(series.getKey().toString(), "Índice", "Valor", dataset);

        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame(series.getKey().toString());
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}

 */


public class PlotRecordings {
public static void showChartFromArray(Double[] data, String title) {
    XYSeries series = new XYSeries("Data");
    for(int i = 0; i < data.length; i++) {
        series.add(i, data[i]);
    }

    XYSeriesCollection dataset = new XYSeriesCollection(series);

    JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "Tiempo (s)",
            "Valor",
            dataset
    );

    XYPlot plot = chart.getXYPlot();

    double min = Arrays.stream(data).mapToDouble(v -> v).min().orElse(0);
    double max = Arrays.stream(data).mapToDouble(v -> v).max().orElse(1);
//
//    if (min == max) {
//        max = min + 1;
//    }
//    // Expandir el rango para ver la señal
//    double margin = (max - min) * 0.1;
//    plot.getRangeAxis().setRange(min-margin, max+margin);

    ChartPanel chartPanel = new ChartPanel(chart);

    JFrame frame = new JFrame("Gráfica "+title);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Esto hace que si cierras una ventana, no se cierre el programa, sigue funcionando
    frame.setContentPane(chartPanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    }
}