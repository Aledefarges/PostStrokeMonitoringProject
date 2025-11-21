package org.example.Server.JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {
    //changed from private to rpotected to be able to access for tests
    protected Connection c = null;

    public JDBCManager() {
        try{

            //Revisar esto kkjk
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection( "jdbc:sqlite:.PostStrokeMonitoring/Server_App/Database/PostStrokeDatabase.db");//
            c.createStatement().execute("PRAGMA foreign_keys = ON");
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void createTables(){
        try{
            Statement stmt = c.createStatement();

            //Table doctor
            String sql_doctor = "CREATE TABLE Doctors ("
                    + "doctor_id INTEGER PRIMARY KEY, "
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "phone INTEGER,"
                    + "email TEXT NOT NULL,"
                    + "password TEXT NOT NULL"
                    + ")";
            stmt.executeUpdate(sql_doctor);

            //Table patient
            String sql_patient = "CREATE TABLE Patients ("
                    + "patient_id INTEGER PRIMARY KEY NOT NULL, "
                    + "name TEXT NOT NULL,"
                    + "surname TEXT NOT NULL,"
                    + "dob TEXT,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL,"
                    + "phone INTEGER,"
                    + "medicalHistory TEXT,"
                    + "sex TEXT,"
                    + "doctor_id INTEGER,"
                    + "FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE SET NULL" //si un doctor se elimina, todos sus pacientes quedan con el campo doctor_id = NULL pero los pacientes no se borran
                    +")";
            stmt.executeUpdate(sql_patient);

            //Table recordings
            String sql_recordings = "CREATE TABLE Recordings ("
                    + "recording_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "type TEXT,"
                    + "recordingDate TEXT,"
                    + "patient_id INTEGER NOT NULL,"
                    + "FOREIGN KEY(patient_id) REFERENCES Patients(patient_id) ON DELETE CASCADE,)"
                    + ")";
            stmt.executeUpdate(sql_recordings);

            String sql_frames = "CREATE TABLE RecordingFrames ("
                    + "frame_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "recording_id INTEGER NOT NULL,"
                    + "frame_index INTEGER NOT NULL,"
                    + "crc INTEGER,"
                    + "seq INTEGER,"
                    + "a0 INTEGER,"
                    + "a1 INTEGER,"
                    + "a2 INTEGER,"
                    + "a3 INTEGER,"
                    + "a4 INTEGER,"
                    + "a5 INTEGER,"
                    + "d0 INTEGER,"
                    + "d1 INTEGER,"
                    + "d2 INTEGER,"
                    + "d3 INTEGER,"
                    + "FOREIGN KEY(recording_id) REFERENCES Recordings(recording_id)"
                    + ")";
            stmt.executeUpdate(sql_frames);


            //Table Administrators
            String sql_administrator = "CREATE TABLE Administrators ("
                    + "admin_id INTEGER PRIMARY KEY,"
                    + "name TEXT NOT NULL,"
                    + "surname TEXT NOT NULL,"
                    + "phone INTEGER,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL,"
                    + ")";
            stmt.executeUpdate(sql_administrator);
        }
        catch(SQLException e){
            if(!e.getMessage().contains("already exists"))
            {
                e.printStackTrace();
            }
        }
    }
    public Connection getConnection(){
        return c;
    }
    public void disconnect(){
        try {
            c.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
