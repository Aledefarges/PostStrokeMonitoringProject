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
            c = DriverManager.getConnection("jdbc:sqlite:.Database/PostStrokeDatabase.db");//
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
            //Table Users //IF NOT EXIST queremos?
            String sql_user = "CREATE TABLE Users ("
                    + "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "email TEXT UNIQUE NOT NULL,"
                    + "password TEXT NOT NULL,"
                    + "role TEXT CHECK (role IN ('DOCTOR', 'PATIENT', 'ADMINISTRATOR')) NOT NULL"
                    + ")";
            stmt.executeUpdate(sql_user);
            //Table doctor
            String sql_doctor = "CREATE TABLE Doctors ("
                    + "doctor_id INTEGER PRIMARY KEY, "
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "phone INTEGER,"
                    + "FOREIGN KEY (doctor_id) REFERENCES Users(user_id) ON DELETE CASCADE"+
                    ")";
            stmt.executeUpdate(sql_doctor);

            //Table patient
            String sql_patient = "CREATE TABLE Patients ("
                    + "patient_id INTEGER PRIMARY KEY, "
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "dob TEXT,"
                    + "phone INTEGER,"
                    + "medicalHistory TEXT,"
                    + "sex TEXT,"
                    + "doctor_id INTEGER,"
                    + "FOREIGN KEY (patient_id) REFERENCES Users(user_id) ON DELETE CASCADE,"
                    + "FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id) ON DELETE SET NULL" //si un doctor se elimina, todos sus pacientes quedan con el campo doctor_id = NULL pero los pacientes no se borran
                    +")";
            stmt.executeUpdate(sql_patient);

            //Table recordings //CAMBIARLO LUEGO
            String sql_recordings = "CREATE TABLE Recordings ("
                    + "recording_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "type TEXT,"
                    + "recordingDate TEXT,"
                    + "patient_id INTEGER REFERENCES Patients(patient_id) ON DELETE CASCADE,)"
                    + ")";
            stmt.executeUpdate(sql_recordings);



            //Table Administrators
            String sql_administrator = "CREATE TABLE Administrators ("
                    + "admin_id INTEGER PRIMARY KEY,"
                    + "name TEXT,"
                    + "surname TEXT,"
                    + "phone INTEGER,"
                    + "FOREIGN KEY (admin_id) REFERENCES Users(user_id) ON DELETE CASCADE"
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
