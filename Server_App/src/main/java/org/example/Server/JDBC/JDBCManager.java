package org.example.Server.JDBC;
import org.example.Server.IFaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager {
    //changed from private to rpotected to be able to access for tests
    protected Connection c;
    private DoctorManager dMan;
    private PatientManager pMan;
    private AdministratorManager aMan;
    private RecordingManager rMan;
    private RecordingFramesManager rfrMan;

    public Connection getConnection(){
        return c;
    }

    public JDBCManager(){
        this.connect();
        this.dMan = new JDBCDoctorManager(this);
        this.pMan = new JDBCPatientManager(this);
         //¿Habría que llamar tmb al resto de JDBC Managers?
        //Cuando se crean dos constructores salen errores en los dos, igual solo puede haber uno
        this.createTables();
    }
   /* public JDBCManager(){
        this.connect();
        this.dMan = new JDBCDoctorManager(this);
        this.pMan = new JDBCPatientManager(this);
        this.aMan = new JDBCAdministratorManager(this);
        this.rMan = new JDBCRecordingManager(this);
        this.rfrMan = new JDBCRecordingFramesManager(this);
        this.createTables();
    }
*/
    private void connect() {
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection( "jdbc:sqlite:./Server_App/db/PostStrokedb.db");//
            c.createStatement().execute("PRAGMA foreign_keys = ON");

        } catch (ClassNotFoundException cnfE) {
            System.out.println("Databases not loaded");
            cnfE.printStackTrace();
        } catch (SQLException sqlE) {
            System.out.println("Error with database");
            sqlE.printStackTrace();
        }
    }

    public void close(){
        try{
            c.close();
        }catch (SQLException e) {
            System.out.println("Error closing the database");
            e.printStackTrace();
        }
    }

    public void createTables(){
        try{
            Statement createPatient = c.createStatement();
            String sql_patient = "CREATE TABLE Patients ("
                    + "patient_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT NOT NULL,"
                    + "surname TEXT NOT NULL,"
                    + "dob DATE,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL,"
                    + "phone INTEGER,"
                    + "medicalHistory TEXT,"
                    + "sex TEXT,"
                    + "doctor_id INTEGER,"
                    + "FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE SET NULL"//si un doctor se elimina, todos sus pacientes quedan con el campo doctor_id = NULL pero los pacientes no se borran
                    +")";
            createPatient.executeUpdate(sql_patient);
            createPatient.close();

            //Table doctor
            Statement createDoctor = c.createStatement();
            String sql_doctor = "CREATE TABLE Doctors ("
                    + "doctor_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "phone INTEGER,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL"
                    + ")";
            createDoctor.executeUpdate(sql_doctor);
            createDoctor.close();

            //Table Administrators
            Statement createAdmin = c.createStatement();
            String sql_administrator = "CREATE TABLE Administrator ("
                    + "admin_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "surname TEXT NOT NULL,"
                    + "phone INTEGER,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL"
                    +")";
            createAdmin.executeUpdate(sql_administrator);
            createAdmin.close();

            //Table recordings
            Statement createRecord = c.createStatement();
            String sql_recordings = "CREATE TABLE Recordings ("
                    + "recording_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "type TEXT,"
                    + "recordingDate DATE,"
                    + "patient_id INTEGER NOT NULL,"
                    + "FOREIGN KEY(patient_id) REFERENCES Patients(patient_id) ON DELETE CASCADE"
                    + ")";
            createRecord.executeUpdate(sql_recordings);
            createRecord.close();

            Statement createFrames = c.createStatement();
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
            createFrames.executeUpdate(sql_frames);
            createFrames.close();
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

    public void disconnect(){
        try {
            c.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
