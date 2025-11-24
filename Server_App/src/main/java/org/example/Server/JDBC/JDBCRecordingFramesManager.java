package org.example.Server.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCRecordingFramesManager {

    private JDBCManager manager;
    public JDBCRecordingFramesManager(JDBCManager manager) {
        this.manager = manager;
    }
    public JDBCManager getManager() {
        return manager;
    }

    public void addFrame(int recording_id, int frameIndex, int crc, int seq, int[]analog, int[]digital){
        String sql = "INSERT INTO RecordingFrames (" +
                "recording_id, frame_index, crc, seq, a0,a1,a2,a3,a4,a5,d0,d1,d2,d3)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1,recording_id);
            ps.setInt(2,frameIndex);
            ps.setInt(3,crc);
            ps.setInt(4,seq);
            for (int i = 0; i < 6; i++)
                ps.setInt(5 + i, analog[i]);
            for (int i = 0; i < 4; i++)
                ps.setInt(11 + i, digital[i]);
            ps.executeUpdate();
            ps.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteFramesByRecording(int recording_id) {
        try {
            String sql = "DELETE FROM RecordingFrames WHERE recording_id = ?";
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1, recording_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<int[]> getFramesByRecording(int recording_id) {

        List<int[]> frames = new ArrayList<>();
        String sql = "SELECT * FROM RecordingFrames WHERE recording_id = ? ORDER BY frame_index ASC";

        try {
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1, recording_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int seq = rs.getInt("seq");
                int crc = rs.getInt("crc");

                int[] analog = new int[]{
                        rs.getInt("a0"),
                        rs.getInt("a1"),
                        rs.getInt("a2"),
                        rs.getInt("a3"),
                        rs.getInt("a4"),
                        rs.getInt("a5")
                };

                int[] digital = new int[]{
                        rs.getInt("d0"),
                        rs.getInt("d1"),
                        rs.getInt("d2"),
                        rs.getInt("d3")
                };

                // Formato combinado: [seq, a0..a5, d0..d3, crc]
                int[] frame = new int[12];
                frame[0] = seq;

                for (int i = 0; i < 6; i++)
                    frame[1 + i] = analog[i];
                for (int i = 0; i < 4; i++)
                    frame[7 + i] = digital[i];
                frame[11] = crc;
                frames.add(frame);
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frames;
    }


}
