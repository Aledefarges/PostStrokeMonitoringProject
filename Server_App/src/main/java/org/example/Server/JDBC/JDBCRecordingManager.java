package org.example.Server.JDBC;



import org.example.Server.IFaces.RecordingManager;
import org.example.POJOS.Recording;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCRecordingManager implements RecordingManager {
    private JDBCManager manager;

    public JDBCRecordingManager(JDBCManager manager){
        this.manager = manager;
    }

    @Override
    public void addRecording(Recording recording) {
        String sql = "INSERT INTO Recordings (type, recordingDate, patient_id) VALUES (?, ?, ?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, recording.getType().name());
            ps.setDate(2, java.sql.Date.valueOf(recording.getDateRecording()));
            ps.setInt(3, recording.getPatient_id());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                recording.setId(id);  // The new id is stored
            }
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void deleteRecording (Integer recording_id){
        String sql = "DELETE FROM Recordings WHERE recording_id = ?";
        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1, recording_id);

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Recording getRecordingById(Integer recording_id) {
        Recording recording = null;

        try {
            String sql = "SELECT * FROM Recordings WHERE recording_id = ?";
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setInt(1,recording_id);

            ResultSet rs = ps.executeQuery(sql);
            if (rs.next()) {
                LocalDate dateRecording = rs.getDate("recordingDate").toLocalDate();
                Recording.Type type = Recording.Type.valueOf(rs.getString("type"));
                Integer patient_id = rs.getInt("patient_id");

                recording = new Recording( dateRecording, type, patient_id);
                recording.setId(recording_id);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recording;
    }



    public List<Recording> getListOfRecordings() {
        List<Recording> recordings= new ArrayList<Recording>();

        try {
            Statement stmt = manager.getConnection().createStatement();
            String sql = "SELECT * FROM Recordings";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                LocalDate dateRecording = rs.getDate("recordingDate").toLocalDate();
                Integer recording_id = rs.getInt("recording_id");
                Recording.Type type = Recording.Type.valueOf(rs.getString("type"));
                Integer patient_id = rs.getInt("patient_id");
                List<int[]> frameList = null;
                Recording recording = new Recording(dateRecording, type, patient_id);
                recordings.add(recording);
            }

            rs.close();
            stmt.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return recordings;
    }


}
