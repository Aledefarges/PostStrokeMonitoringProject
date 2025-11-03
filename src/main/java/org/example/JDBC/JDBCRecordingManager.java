package org.example.JDBC;

import org.example.IFaces.RecordingManager;
import org.example.POJOS.Patient;
import org.example.POJOS.Recording;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCRecordingManager implements RecordingManager {
    private JDBCManager manager;

    public JDBCRecordingManager(JDBCManager manager){
        this.manager = manager;
    }

    public void addRecording(Recording recording) {
        String sql = "INSERT INTO Recordings (type, recordingDate) VALUES (?, ?, ?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            //ahora me da error pq tiene que estar creado getConnection en JDBCManager

            ps.setString(1, recording.getType());
            ps.setDate(2, recording.getDateRecording());
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

  /*  public Recording getRecordingById(Integer id){
        Recording recording = null;
        try{
            Statement stmt = manager.getConnection().createStatement();
            String sql = "SELECT * FROM recording WHERE id = " + id;
            ResultSet rs = stmt.executeQuery(sql);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return recording;
    }

    */

}
