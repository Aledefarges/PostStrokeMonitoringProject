package org.example.Server.JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {
    private Connection c = null;

    public JDBCManager() {
        try{
            //Revisar esto
            c = DriverManager.getConnection("jdbc:sqlite:C:\\PostStrokeMonitoringProject\\src\\main\\java\\org\\example\\DataBase\\PostStrokeDatabase.db");
            c.createStatement().execute("PRAGMA foreign_keys = ON");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void createTables(){
        try{
            Statement stmt = c.createStatement();

            //Table doctor
            String sql_doctor = "CREATE TABLE Doctors ("
                    + "doctor_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "phone INTEGER)";
            stmt.executeUpdate(sql_doctor);

            //Table patient
            String sql_patient = "CREATE TABLE Patients ("
                    + "patient_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "dob TEXT,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "phone INTEGER,"
                    + "medicalHistory TEXT,"
                    + "sex TEXT,"
                    + "doctor_id INTEGER NOT NULL)";
            stmt.executeUpdate(sql_patient);

            //Table recordings
            String sql_recordings = "CREATE TABLE Recordings ("
                    + "recording_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "type TEXT,"
                    + "recordingDate TEXT,"
                    + "patient_id INTEGER NOT NULL)";
            stmt.executeUpdate(sql_recordings);

            //Table Users
            String sql_user = "CREATE TABLE Users ("
                    + "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username TEXT,"
                    + "password TEXT,"
                    + "role TEXT)";
            stmt.executeUpdate(sql_user);
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
