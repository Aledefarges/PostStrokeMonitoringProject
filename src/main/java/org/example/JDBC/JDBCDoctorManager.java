package org.example.JDBC;

import org.example.IFaces.DoctorManager;
import org.example.POJOS.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDoctorManager implements DoctorManager {
 private JDBCManager manager;

 public JDBCDoctorManager(JDBCManager manager){
     this.manager=manager;
 }

 //hola porfavor que funcione

 @Override
 public void addDoctor(Doctor doctor){
     String sql = "INSERT INTO Doctors (name,surname,email,phone) VALUES (?,?,?,?)";

     try{
         PreparedStatement ps = manager.getConnection().prepareStatement(sql);

         ps.setString(1,doctor.getName());
         ps.setString(2,doctor.getSurname());
         ps.setString(3, doctor.getEmail());
         ps.setInt(4, doctor.getPhone());

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
public Doctor searchDoctorById(Integer id){
     Doctor doctor=null;
     String sql="SELECT * FROM Doctors WHERE id=?";
     try{
         Statement stmt=manager.getConnection().createStatement();
         ResultSet rs = stmt.executeQuery(sql);

         Integer doctor_id=rs.getInt("id");
         String name=rs.getString("name");
         String surname=rs.getString("surname");
         String email=rs.getString("email");
         Integer phone=rs.getInt("phone");

         doctor = new Doctor(doctor_id,name,surname,email,phone);

         rs.close();
         stmt.close();
     }catch(SQLException e){
         e.printStackTrace();;
     }
     return doctor;
}

}
