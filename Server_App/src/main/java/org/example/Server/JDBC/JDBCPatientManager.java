package org.example.Server.JDBC;


import org.example.Server.IFaces.PatientManager;
import org.example.POJOS.Patient;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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

        String sql = "INSERT INTO Patients (patient_id,name,surname,dob,email, sex,phone,medicalHistory,password) VALUES (?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement ps = manager.getConnection().prepareStatement(sql);

            ps.setInt(1, patient.getPatient_id());
            ps.setString(2, patient.getName());
            ps.setString(3, patient.getSurname());
            ps.setString(4, String.valueOf(patient.getDob()));
            ps.setString(5, patient.getEmail());
            ps.setString(6, patient.getSex().toString().trim());
            ps.setInt(7, patient.getPhone());
            ps.setString(8, patient.getMedicalhistory());
            //ps.setInt(9, patient.getDoctor().getDoctor_id());
            ps.setString(9, patient.getPassword());

            ps.executeUpdate();
            ps.close() ;
            //FALTA AÑADIR RECORDINGS
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    public boolean deletePatient (String email){
        String sql = "DELETE FROM Patients WHERE email =  ? ";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            int rowsAffected = ps.executeUpdate(); //executeUpdate devuelve cuanats filas fueron eliminadas por el sql, el resultado puede ser 0 (no exsiet este mail), 1 se elimino paciente (ya que el mail es UNIQUE y no puede haber más de uno)
            //ps.executeUpdate();
            ps.close();
            return  rowsAffected == 1; //False si no se elimino ningun paciente (no existia ese email), true si se elimino un paciente


        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Esto es exactamente lo mismo que lo de arriba
//    @Override
//    public void deletePatientByEmail(String email) {
//        try{
//            String sql = "DELETE FROM Patients WHERE email = ?";
//            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
//
//            ps.setString(1,email);
//            ps.executeUpdate();
//            ps.close();
//
//           userManager.deleteUserByEmail(email);
//
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//    }

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
    public Patient getPatientById(int patient_id) {
        Patient patient = null;

        try{
            Statement stmt=manager.getConnection().createStatement();
            String sql = "SELECT * FROM Patients WHERE patient_id = ?";

            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()){
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dob = rs.getDate("dob");
                int phone = rs.getInt("phone");
                String medicalHistory = rs.getString("medicalHistory");
                Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));
                int doctor_id = rs.getInt("doctor_id");
                String email = rs.getString("email");

                patient = new Patient(name,surname,dob,email, phone,medicalHistory,sex,password);
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

   @Override
    public void updatePassword(int patient_id, String newPassword){
        String sql = "UPDATE Patients SET password = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)){

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(newPassword.getBytes());
            byte[] encryptedPassword = md.digest();

            //Converting byte[] to hexadecimal String so it can be stored in TEXT
            StringBuilder sb = new StringBuilder();
            for (byte b: encryptedPassword){
                sb.append(String.format("%02x",b)); //2 digit hexadecimal
            }
            String encryptedStringPassword = sb.toString();

            ps.setString(1, encryptedStringPassword);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        }
    }

    @Override
    public boolean checkPassword(String email, String password) {
        String sql = "SELECT password FROM Patients WHERE email = ?";
        try{
            PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String pass = rs.getString("password");

                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] digest = md.digest();

                //Converting byte[] to hexadecimal String so it can be compared with the stored password
                StringBuilder sb = new StringBuilder();
                for (byte b: digest){
                    sb.append(String.format("%02x",b));
                }
                String encryptedPass = sb.toString();

                return pass.equalsIgnoreCase(encryptedPass);

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return false;
    }

}



