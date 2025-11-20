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
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            //ahora me da error pq tiene que estar creado getConnection en JDBCManager

            ps.setString(1, recording.getType().name());
            ps.setDate(2, java.sql.Date.valueOf(recording.getDateRecording()));
            ps.setInt(3, recording.getPatient_id());

            ps.executeUpdate();
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
            Statement stmt = manager.getConnection().createStatement();
            String sql = "SELECT * FROM Recordings WHERE recording_id = " + recording_id;

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                LocalDate dateRecording = rs.getDate("recordingDate").toLocalDate();
                Recording.Type type = Recording.Type.valueOf(rs.getString("type"));
                Integer patient_id = rs.getInt("patient_id");
                List<int[]> frameList = null;
                recording = new Recording(recording_id, dateRecording, type, patient_id, frameList);

            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recording;
    }



    public List<Recording> getListOfRecordings() {
        List<Recording> recordings= new ArrayList<Recording>();

        try {
            Statement stmt = manager.getConnection().createStatement();
            String sql = "SELECT * FROM recording";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                LocalDate dateRecording = rs.getDate("recordingDate").toLocalDate();
                Integer recording_id = rs.getInt("id");
                Recording.Type type = Recording.Type.valueOf(rs.getString("type"));
                Integer patient_id = rs.getInt("patient_id");
                List<int[]> frameList = null;
                Recording recording = new Recording(recording_id, dateRecording, type, patient_id, frameList);
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
