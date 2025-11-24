package org.example.Server;


import org.example.Server.JDBC.JDBCManager;
import org.example.Server.Visualization.PlotRecordings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Connecting to DB...");
            JDBCManager db = new JDBCManager();
            Connection conn = db.getConnection();

            // 1. Crear recording real
            int recordingId = createTestRecording(conn);

            // 2. Insertar frames falsos
            insertFakeFrames(conn, recordingId);

            // 3. Dibujar el recording
            System.out.println("Loading series...");
            var series = PlotRecordings.loadRecordingSeries(conn, recordingId, 1); // canal 1

            System.out.println("Plotting...");
            PlotRecordings.showChart(series);

            System.out.println("Test completed OK!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int createTestRecording(Connection conn) throws Exception {
        String sql = "INSERT INTO Recordings (type, recordingDate, patient_id) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, "EMG");
        ps.setString(2, "2025-01-01");
        ps.setInt(3, 1); // ⚠️ debe existir patient_id=1

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);

        rs.close();
        ps.close();

        System.out.println("Created Recording with ID = " + id);
        return id;
    }

    private static void insertFakeFrames(Connection conn, int recordingId) throws Exception {
        String sql = "INSERT INTO RecordingFrames " +
                "(recording_id, frame_index, crc, seq, a0, a1, a2, a3, a4, a5, d0, d1, d2, d3)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        System.out.println("Inserting fake frames...");

        for (int i = 0; i < 100; i++) {

            ps.setInt(1, recordingId);
            ps.setInt(2, i);     // frame_index
            ps.setInt(3, 0);     // crc
            ps.setInt(4, i);     // seq

            // valores analógicos
            ps.setInt(5, 0);     // a0
            ps.setInt(6, (int)(200 + 100*Math.sin(i/5.0))); // a1 → bonita señal
            ps.setInt(7, 0);     // a2
            ps.setInt(8, 0);     // a3
            ps.setInt(9, 0);     // a4
            ps.setInt(10, 0);    // a5

            // digitales
            ps.setInt(11, 0);
            ps.setInt(12, 0);
            ps.setInt(13, 0);
            ps.setInt(14, 0);

            ps.addBatch();
        }

        ps.executeBatch();
        ps.close();

        System.out.println("Inserted 100 fake frames into recording " + recordingId);
    }
}
