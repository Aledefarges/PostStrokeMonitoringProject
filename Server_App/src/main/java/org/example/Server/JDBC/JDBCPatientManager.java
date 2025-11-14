package org.example.Server.JDBC;

import org.example.IFaces.PatientManager;
import org.example.POJOS.Patient;
import org.example.POJOS.Recording;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCPatientManager implements PatientManager {
    private JDBCManager manager;

    public JDBCPatientManager(JDBCManager manager){
        this.manager = manager;
    }

    @Override
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO Patients (name, surname, dob, email, phone, medicalhistory, sex) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            //ahora me da error pq tiene que estar creado getConnection en JDBCManager

            ps.setString(1, patient.getName());
            ps.setString(2, patient.getSurname());
            ps.setDate(3, patient.getDob()); //Da error pq lo tenemos puesto como LocalDate y no Data en POJO, pero no hay en JDBC local date creo
            ps.setString(4, patient.getEmail());
            ps.setInt(5, patient.getPhone());
            ps.setString(6, patient.getMedicalhistory());
            ps.setString(7, patient.getSex()); //Da error pq ns como ponerlo con un enumerado y no string pq no existe la funcion de enumerado en SQL

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePatient (Integer patient_id){
        String sql = "DELETE FROM Patients WHERE patient_id = ?";
        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            //da error pq tiene que estar creada en clase JDBCManager la funcion getConnection
            ps.setInt(1, patient_id);

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePatientByEmail(String email) {
        String sql = "DELETE FROM Patients WHERE email = ?";

        try {

            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
            ps.close();
            System.out.println("Patient record deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete patient.");
        }
    }

    @Override
    public List<Patient> getListOfPatients(){
        List<Patient> patients = new ArrayList<Patient>();
        JDBCRecordingManager jdbcRecordingManager = new JDBCRecordingManager(manager);

        try {
            Statement stmt = manager.getConnection().createStatement();
            String sql = "Select * FROM Patients";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                Integer patient_id = rs.getInt("patient_id");
                String name= rs.getString("name");
                String surname = rs.getString("surname");
                Date dob = rs.getDate("dob");
                String email = rs.getString("email");
                Integer phone = rs.getInt("phone");
                String medicalhistory = rs.getString("medicalhistory");
                String sex = rs.getString("sex");

                //List<Recording> recordings = jdbcRecordingManager.getRecordingOfPatient(patient_id);
                //TODO cuando este hecho getRecordingOfPatient usarlo en este metodo para que aparezcan los recordings cuando se muestra a los pacientes

                Patient p= new Patient(patient_id, name, surname, dob, email, phone, medicalhistory, sex);
                //cuando este el getRecordingOfPatient añadir aqui tambien el atributo recording
                patients.add(p);

            }

            rs.close();
            stmt.close();

        }catch(Exception e) {
            e.printStackTrace();}

        return patients;
    }

    @Override
    public Patient getPatientById(Integer patient_id) {
        Patient patient = null;

        try {
            Statement stmt = manager.getConnection().createStatement();
            String sql = "SELECT * FROM Patients WHERE patient_id = " + patient_id;

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dob = rs.getDate("dob");
                String email = rs.getString("email");
                Integer phone = rs.getInt("phone");
                String medicalhistory = rs.getString("medicalhistory");
                String sex = rs.getString("sex");


                patient = new Patient(patient_id, name, surname, dob, email, phone, medicalhistory, sex);

            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public Patient getPatientByEmail(String email) {
        Patient patient = null;

        String sql = "SELECT * FROM Patients WHERE email = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Integer patient_id = rs.getInt("patient_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dob = rs.getDate("dob");
                Integer phone = rs.getInt("phone");
                String medicalhistory = rs.getString("medicalhistory");
                String sex = rs.getString("sex");

                patient = new Patient(patient_id, name, surname, dob, email, phone, medicalhistory, sex);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    //TODO updates de los atributos

}
