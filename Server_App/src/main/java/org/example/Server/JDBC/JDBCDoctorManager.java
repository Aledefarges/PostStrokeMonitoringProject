package org.example.Server.JDBC;


import org.example.Server.IFaces.DoctorManager;
import org.example.POJOS.Doctor;
import org.example.POJOS.Patient;

import javax.xml.transform.Result;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JDBCDoctorManager implements DoctorManager {
 private JDBCManager manager;

 public JDBCDoctorManager(JDBCManager manager){
     this.manager=manager;
 }

 @Override
 public void addDoctor(Doctor doctor){
     String sql = "INSERT INTO Doctors (doctor_id,name,email,phone, password) VALUES (?,?,?,?,?)";

     try {

         PreparedStatement ps = manager.getConnection().prepareStatement(sql);

         ps.setInt(1, doctor.getDoctor_id());
         ps.setString(2, doctor.getName());
         ps.setString(3, doctor.getEmail());
         ps.setInt(4, doctor.getPhone());
         ps.setString(5, doctor.getPassword());

         ps.executeUpdate();
         ps.close() ;

     }catch(SQLException e){
         e.printStackTrace();
     }


 }

 @Override
 public void deleteDoctor(String email) {
     String sql = "DELETE FROM Doctors WHERE doctor_id =  ? ";

     try{
         PreparedStatement ps = manager.getConnection().prepareStatement(sql);
         ps.setString(1, email);
         ps.executeUpdate();
         ps.close();

     }catch(SQLException e){
         e.printStackTrace();
     }
 }

@Override
public void assingDoctorToPatient(Integer patient_id, Integer doctor_id){
     String sql="UPDATE Patients SET doctor_id=? WHERE id=?";
     try{
         PreparedStatement ps=manager.getConnection().prepareStatement(sql);
         ps.setInt(1,doctor_id);
         ps.setInt(2,patient_id);

         ps.executeUpdate();
         ps.close();
     }catch(SQLException e){
         e.printStackTrace();
     }
}

@Override
public Doctor searchDoctorByEmail(String email){
     Doctor doctor=null;
    JDBCPatientManager jdbcPatientManager=new JDBCPatientManager(manager);

    String sql = "SELECT * FROM Patients WHERE email = ?";

    try{
         PreparedStatement stmt=manager.getConnection().prepareStatement(sql);
         stmt.setString(1, email);

         ResultSet rs=stmt.executeQuery();

         if(rs.next()){
             int doctor_id = rs.getInt("doctor_id");
             String password = rs.getString("password");
             String name = rs.getString("name");
             String surname = rs.getString("surname");
             int phone = rs.getInt("phone");
             List<Patient> patients= jdbcPatientManager.getListOfPatientsOfDoctor(doctor_id);

             doctor = new Doctor(doctor_id, password, name, surname, email, phone, patients);
         }

         rs.close();
         stmt.close();
     }catch(SQLException e){
         e.printStackTrace();;
     }
     return doctor;
}

    @Override
    public void updatePassword(int doctor_id, String newPassword){
        String sql = "UPDATE Doctors SET password = ? WHERE doctor_id = ?";
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
            ps.setInt(2, doctor_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        }
    }


}
