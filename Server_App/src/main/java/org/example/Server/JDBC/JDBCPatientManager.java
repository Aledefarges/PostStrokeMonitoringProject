package org.example.Server.JDBC;


import org.example.Server.IFaces.PatientManager;
import org.example.Server.POJOS.Patient;

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
        String sql = "INSERT INTO Patients (name, surname, dob, phone, medicalhistory, sex) VALUES (?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            //ahora me da error pq tiene que estar creado getConnection en JDBCManager

            ps.setString(1, patient.getName());
            ps.setString(2, patient.getSurname());
            ps.setDate(3, patient.getDob()); //Da error pq lo tenemos puesto como LocalDate y no Data en POJO, pero no hay en JDBC local date creo
            ps.setInt(4, patient.getPhone());
            ps.setString(5, patient.getMedicalhistory());
            ps.setString(6, patient.getSex().name()); //Da error pq ns como ponerlo con un enumerado y no string pq no existe la funcion de enumerado en SQL

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
                Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));


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
                Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));



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
                Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));


                patient = new Patient(patient_id, name, surname, dob, email, phone, medicalhistory, sex);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public void updateName(int patient_id, String name) {
        String sql = "UPDATE Patients SET name = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSurName(int patient_id, String surname) {
        String sql = "UPDATE Patients SET surname = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, surname);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmail(int patient_id, String email) {
        String sql = "UPDATE Patients SET email = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSex(int patient_id, Patient.Sex sex) {
        String sql = "UPDATE Patients SET sex = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, sex.name());
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDob(int patient_id, Date dob) {
        String sql = "UPDATE Patients SET dob = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dob);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePhone(int patient_id, int phone) {
        String sql = "UPDATE Patients SET phone = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, phone);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMedicalHistory(int patient_id, String medicalHistory) {
        String sql = "UPDATE Patients SET medicalHistory = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, medicalHistory);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   @Override
   public List<Patient> getListOfPatientsOfDoctor(Integer doctor_id){
        List<Patient> patients = new ArrayList<Patient>();
        try{
            Statement stmt = manager.getConnection().createStatement();
            String sql = "SELECT * FROM Patient WHERE doctor_id = " + doctor_id;
            ResultSet rs= stmt.executeQuery(sql);
            while(rs.next()){
               Integer patient_id = rs.getInt("patient_id");
               String name = rs.getString("name");
               String surname = rs.getString("surname");
               Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));
               Date dob = rs.getDate("dob");
               String email = rs.getString("email");
               Integer phone = rs.getInt("phone");
               Patient patient = new Patient(patient_id,name,surname,sex,dob,email,phone);
               patients.add(patient);
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return patients;
   }

}
