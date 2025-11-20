package org.example.Server.JDBC;


import org.example.Server.IFaces.PatientManager;
import org.example.POJOS.Patient;
import org.example.POJOS.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCPatientManager implements PatientManager {
    private JDBCManager manager;
    private JDBCUserManager userManager;

    public JDBCPatientManager(JDBCManager manager){
        this.manager = manager;
        this.userManager = new JDBCUserManager(manager);
    }


    @Override
    public void addPatient(Patient patient) {

        String sqlUser = "INSERT INTO Users (email, password, role) VALUES (?, ?, ?)";

        try (
                PreparedStatement psUser = manager.getConnection().prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)
        ) {
            psUser.setString(1, patient.getEmail());
            psUser.setString(2, patient.getPassword());
            psUser.setString(3, patient.getRole().name());

            psUser.executeUpdate();

            ResultSet rs = psUser.getGeneratedKeys();
            if (rs.next()) {
                int user_id = rs.getInt(1);
                patient.setUser_id(user_id);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        String sqlPatient = "INSERT INTO Patients (user_id,name,surname,dob,sex,phone,medicalHistory,doctor) VALUES (?,?,?,?,?,?,?,?)";
        //AÑADIR LIST OF RECORDINGS
        try (PreparedStatement psPatient = manager.getConnection().prepareStatement(sqlPatient)) {
            psPatient.setInt(1, patient.getUser_id());
            psPatient.setString(2, patient.getName());
            psPatient.setString(3, patient.getSurname());
            psPatient.setString(4, String.valueOf(patient.getDob()));
            psPatient.setString(5, patient.getSurname());
            psPatient.setInt(6, patient.getSex().ordinal());
            psPatient.setInt(7, patient.getPhone());
            psPatient.setString(8, patient.getMedicalhistory());
            psPatient.setInt(9, patient.getDoctor().getUser_id());
            psPatient.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
       }
    }

    @Override
    public void deletePatient (String email){
        String sqlPatient =
                "DELETE FROM Patient WHERE user_id = (SELECT user_id FROM Users WHERE email = ? AND role = 'PATIENT')";

        String sqlUser =
                "DELETE FROM Users WHERE email = ? AND role = 'PATIENT'";

        try{
            PreparedStatement psPatient = manager.getConnection().prepareStatement(sqlPatient);
            psPatient.setString(1, email);
            psPatient.executeUpdate();
            psPatient.close();

            PreparedStatement psUser = manager.getConnection().prepareStatement(sqlUser);
            psUser.setString(1, email);
            psUser.executeUpdate();
            psUser.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePatientByEmail(String email) {
        try{
            String sql = "DELETE FROM Patients WHERE email = ?";
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);

            ps.setString(1,email);
            ps.executeUpdate();
            ps.close();

           userManager.deleteUserByEmail(email);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> getListOfPatients(){
        List<Patient> patients = new ArrayList<Patient>();

        try {
            String sql = "SELECT patient_id, name, surname, dob, email, phone, medicalHistory, sex, doctor_id FROM Patients";

            Statement stmt = manager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                int patient_id = rs.getInt("patient_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dob = rs.getDate("dob");
                String email = rs.getString("email");
                int phone = rs.getInt("phone");
                String medicalHistory = rs.getString("medicalHistory");
                Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));


                //List<Recording> recordings = jdbcRecordingManager.getRecordingOfPatient(patient_id);
                //TODO cuando este hecho getRecordingOfPatient usarlo en este metodo para que aparezcan los recordings cuando se muestra a los pacientes

                Patient p= new Patient(patient_id, name, surname, dob, email, phone, medicalHistory, sex);
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
    public Patient getPatientById(String email) {
        Patient patient = null;

        String sql  = "SELECT p.id AS patient_id, p.name, p.surname, p.dob, p.phone, p.medicalHistory, p.sex, p.doctor_id" +
                "u.password, u.email " +
                "FROM Patients p " +
                "JOIN Users u ON p.id = u.id"+
                "WHERE u.email = ?";
        try{
            PreparedStatement stmt=manager.getConnection().prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs=stmt.executeQuery();

            if(rs.next()){
                int patient_id = rs.getInt("patient_id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dob = rs.getDate("dob");
                int phone = rs.getInt("phone");
                String medicalHistory = rs.getString("medicalHistory");
                Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));
                int doctor_id = rs.getInt("doctor_id");

                patient = new Patient(patient_id,name,surname,dob,phone,medicalHistory,sex,password);
            }

            rs.close();
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();;
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
        List<Patient> patients = new ArrayList<>();
        try{
            Statement stmt = manager.getConnection().createStatement();
            String sql = "SELECT * FROM Patients WHERE doctor_id = " + doctor_id;
            ResultSet rs= stmt.executeQuery(sql);
            while(rs.next()){
                Integer patient_id = rs.getInt("patient_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));
                Date dob = rs.getDate("dob");
                String email = rs.getString("email");
                Integer phone = rs.getInt("phone");
                String medicalHistory = rs.getString("medicalHistory");

                Patient p = new Patient(patient_id, name, surname, dob, email, phone, medicalHistory, sex);
                patients.add(p);
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return patients;
   }

}



