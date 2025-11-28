package org.example.Server.JDBC;
import org.example.Server.IFaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {
    //changed from private to rpotected to be able to access for tests
//    protected Connection c;
//    private DoctorManager dMan;
//    private PatientManager pMan;
//    private AdministratorManager aMan;
//    private RecordingManager rMan;
//    private RecordingFramesManager rfrMan;



    private static final String DB_URL = "jdbc:sqlite:./Server_App/db/PostStrokeDataBase.db";

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

            //Table Patients
            String sql_patient = "CREATE TABLE Patients ("
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
                    + "FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE SET NULL"//si un doctor se elimina, todos sus pacientes quedan con el campo doctor_id = NULL pero los pacientes no se borran
                    +")";
            st.executeUpdate(sql_patient);

            //Table doctors
            String sql_doctor = "CREATE TABLE Doctors ("
                    + "doctor_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "phone INTEGER,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL"
                    + ")";
            st.executeUpdate(sql_doctor);

            //Table Administrators
            String sql_administrator = "CREATE TABLE Administrators ("
                    + "admin_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "surname TEXT NOT NULL,"
                    + "phone INTEGER,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL"
                    +")";
            st.executeUpdate(sql_administrator);

            //Table recordings
            String sql_recordings = "CREATE TABLE Recordings ("
                    + "recording_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "type TEXT,"
                    + "recordingDate TEXT,"
                    + "patient_id INTEGER NOT NULL,"
                    + "FOREIGN KEY(patient_id) REFERENCES Patients(patient_id) ON DELETE CASCADE"
                    + ")";
            st.executeUpdate(sql_recordings);

            //Table frames
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
                    + "FOREIGN KEY(recording_id) REFERENCES Recordings(recording_id) ON DELETE CASCADE"
                    + ")";
            st.executeUpdate(sql_frames);
            System.out.println("Database initialized, tables ensured");
        }
        catch (SQLException sqlE) {
            if (sqlE.getMessage().contains("already exists")){
                System.out.println("No need to create the tables; already there");
            }
            else {
                System.out.println("Error in query");
                sqlE.printStackTrace();
            }
        }
    }

}
