package org.example.Server.JDBC;
import org.example.Server.IFaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {

    private static final String DB_URL = "jdbc:sqlite:./Server_App/db/PostStrokedb4.db";

    static {
        try{
            Class.forName("org.sqlite.JDBC");
            createTables();
        }catch (ClassNotFoundException e){
            System.err.println("SQLite JDBC driver not found");
            e.printStackTrace();
        }catch (SQLException e){
            System.err.println("Error initializing db");
            e.printStackTrace();
        }
    }

    public JDBCManager(){

    }

    public Connection getConnection(){
        try{
            Connection c = DriverManager.getConnection(DB_URL);
            try(Statement st = c.createStatement()){
                st.execute("PRAGMA foreign_keys = ON");
            }
            return c;
        }catch (SQLException e){
            throw new RuntimeException("Cannot connect to database");
        }
    }


    private static void createTables() throws SQLException{
        try(Connection c = DriverManager.getConnection(DB_URL);
        Statement st = c.createStatement()){
            st.execute("PRAGMA foreign_keys = ON");

            //Table doctors
            String sql_doctor = "CREATE TABLE IF NOT EXISTS Doctors ("
                    + "doctor_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "phone INTEGER,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL"
                    + ")";
            st.executeUpdate(sql_doctor);

            //Table Patients
            String sql_patient = "CREATE TABLE IF NOT EXISTS Patients ("
                    + "patient_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT NOT NULL,"
                    + "surname TEXT NOT NULL,"
                    + "dob TEXT,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL,"
                    + "phone INTEGER,"
                    + "medicalHistory TEXT,"
                    + "sex TEXT,"
                    + "doctor_id INTEGER,"
                    + "feedback TEXT,"
                    + "FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE SET NULL"//si un doctor se elimina, todos sus pacientes quedan con el campo doctor_id = NULL pero los pacientes no se borran
                    +")";
            st.executeUpdate(sql_patient);

            //Table recordings
            String sql_recordings = "CREATE TABLE IF NOT EXISTS Recordings ("
                    + "recording_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "type TEXT,"
                    + "recordingDate TIMESTAMP,"
                    + "patient_id INTEGER NOT NULL,"
                    + "FOREIGN KEY(patient_id) REFERENCES Patients(patient_id) ON DELETE CASCADE"
                    + ")";
            st.executeUpdate(sql_recordings);

            //Table frames
            String sql_frames = "CREATE TABLE IF NOT EXISTS RecordingFrames ("
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
                    + "FOREIGN KEY(recording_id) REFERENCES Recordings(recording_id) ON DELETE CASCADE"
                    + ")";
            st.executeUpdate(sql_frames);
            System.out.println("Database initialized, tables ensured");
        }
        catch (SQLException sqlE) {
            if (sqlE.getMessage().contains("Already exists")){
                System.out.println("No need to create the tables; already there");
            }
            else {
                System.out.println("Error in query");
                sqlE.printStackTrace();
            }
        }
    }

}
