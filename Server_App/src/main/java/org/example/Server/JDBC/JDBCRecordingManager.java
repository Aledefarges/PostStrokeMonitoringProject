package org.example.Server.JDBC;

import org.example.IFaces.RecordingManager;
import org.example.POJOS.Patient;
import org.example.POJOS.Recording;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDate;

public class JDBCRecordingManager implements RecordingManager {
    private JDBCManager manager;

    public JDBCRecordingManager(JDBCManager manager){
        this.manager = manager;
    }

    public void addRecording(Recording recording) {
        String sql = "INSERT INTO Recordings (type, recordingDate, patient_id) VALUES (?, ?, ?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            //ahora me da error pq tiene que estar creado getConnection en JDBCManager

            ps.setString(1, recording.getType());
            ps.setDate(2, recording.getDateRecording());
            //ps.setInt(3, recording.getId())
            //Ns si tmb hay que meter el patient_id

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
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
/*
    public Recording getRecordingById(Integer recording_id) {
        Recording recording = null;

        try {
            Statement stmt = manager.getConnection().createStatement();
            String sql = "SELECT * FROM Recordings WHERE recording_id = " + recording_id;

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                LocalDate dateRecording = rs.getDate("recordingDate").toLocalDate();
                Type type = rs.getString("type");
                //Integer patient_id = rs.getInt("patient_id");
                recording = new Recording(recording_id, dateRecording, type);

            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recording;
    }

*/

}
