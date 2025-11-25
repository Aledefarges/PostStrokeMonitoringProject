package org.example.Server.Visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    ChartPanel chartPanel = new ChartPanel(chart);

    JFrame frame = new JFrame("Gráfica "+title);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Esto hace que si cierras una ventana, no se cierre el programa, sigue funcionando
    frame.setContentPane(chartPanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    }
}