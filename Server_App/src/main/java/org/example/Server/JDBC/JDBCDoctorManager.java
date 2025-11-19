package org.example.Server.JDBC;


import org.example.Server.IFaces.DoctorManager;
import org.example.Server.POJOS.Doctor;
import org.example.Server.POJOS.Patient;

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
     String sql = "INSERT INTO Doctors (name,surname,email,phone) VALUES (?,?,?,?)";

     try{
         PreparedStatement ps = manager.getConnection().prepareStatement(sql);

         ps.setString(1,doctor.getName());
         ps.setString(2,doctor.getSurname());
         ps.setString(3, doctor.getEmail());
         ps.setInt(4, doctor.getPhone());
         //AÑADIR LISTA PACIENTES NO Sé COMO HACERLO EN JDBC
         ps.executeUpdate();
         ps.close();

     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 @Override
 public void deleteDoctor(String email) {
     String sql = "DELETE FROM Doctors WHERE email = ?";
     try{
         PreparedStatement ps = manager.getConnection().prepareStatement(sql);

         ps.setString(1,email);

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

    String sql  = "SELECT d.id AS doctor_id, d.name, d.surname, d.phone" +
            "u.password, u.email " +
            "FROM Doctors d " +
            "JOIN Users u ON d.id = u.id"+
            "WHERE u.email = ?";
    //String sql="SELECT * FROM Doctors WHERE email =?";

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


}
