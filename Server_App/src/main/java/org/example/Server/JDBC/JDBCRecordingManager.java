package org.example.Server.JDBC;



import org.example.Server.IFaces.RecordingManager;
import org.example.POJOS.Recording;

import java.sql.*;
import java.time.LocalDate;
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
            ps.setDate(2, java.sql.Date.valueOf(recording.getDateRecording()));
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
    @Override
    public void deleteRecording (Integer recording_id){
        String sql = "DELETE FROM Recordings WHERE recording_id = ?";
        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, recording_id);

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public Recording getRecordingById(Integer recording_id) {
        String sql = "SELECT recording_id, type, recordingDate, patient_id FROM Recordings WHERE recording_id = ?";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1,recording_id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    LocalDate dateRecording = rs.getDate("recordingDate").toLocalDate();
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


    public List<Recording> getListOfRecordings() {
        List<Recording> recordings= new ArrayList<Recording>();

        String sql = "SELECT * FROM Recordings";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                LocalDate dateRecording = rs.getDate("recordingDate").toLocalDate();
                int recording_id = rs.getInt("recording_id");
                Recording.Type type = Recording.Type.valueOf(rs.getString("type"));
                int patient_id = rs.getInt("patient_id");

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
