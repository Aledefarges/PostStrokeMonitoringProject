package org.example.Server.JDBC;

import org.example.Server.IFaces.RecordingManager;
import org.example.POJOS.Recording;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCRecordingManager implements RecordingManager {
    private final JDBCManager manager;

    public JDBCRecordingManager(JDBCManager manager){
        this.manager = manager;
    }

    @Override
    public void addRecording(Recording recording) {
        String sql = "INSERT INTO Recordings (type, recordingDate, patient_id) VALUES (?, ?, ?)";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, recording.getType().name());
            Timestamp ts = Timestamp.valueOf(recording.getDateRecording());
            ps.setTimestamp(2, ts);
            ps.setInt(3, recording.getPatient_id());

            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                    int id = rs.getInt(1);
                    recording.setId(id);  // The new id is stored
                    System.out.println("DEBUG: New recording_id = "+id);
                }
            }
        }catch (SQLException e){
            System.err.println("Error inserting recording");
            e.printStackTrace();
        }
    }
    public boolean deleteRecording (int recording_id){
        String sql = "DELETE FROM Recordings WHERE recording_id = ?";
        try(Connection c = manager.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, recording_id);
            int rows = ps.executeUpdate();
            return rows == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public Recording getRecordingById(Integer recording_id) {
        String sql = "SELECT recording_id, type, recordingDate, patient_id FROM Recordings WHERE recording_id = ?";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,recording_id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Timestamp ts = rs.getTimestamp("recordingDate");
                    LocalDateTime dateRecording = ts.toLocalDateTime();
                    Recording.Type type = Recording.Type.valueOf(rs.getString("type"));
                    int patient_id = rs.getInt("patient_id");

                    Recording recording = new Recording(dateRecording, type, patient_id);
                    recording.setId(rs.getInt("recording_id"));
                    return recording;
                }
            }
        }catch (SQLException e){
            System.err.println("Error getting recording= "+recording_id);
            e.printStackTrace();
        }
        return null;
    }


    public List<Recording> getRecordingsByPatient(int patient_id) {
        List<Recording> recordings= new ArrayList<>();

        String sql = "SELECT * FROM Recordings WHERE patient_id = ? ORDER BY recordingDate DESC";

        try{
                Connection c = manager.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ps.setInt(1,patient_id);
                ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Timestamp ts = rs.getTimestamp("recordingDate");
                LocalDateTime dateRecording = ts.toLocalDateTime();
                int recording_id = rs.getInt("recording_id");
                Recording.Type type = Recording.Type.valueOf(rs.getString("type"));

                Recording recording = new Recording(dateRecording, type, patient_id);
                recording.setId(recording_id);

                recordings.add(recording);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return recordings;
    }
}
