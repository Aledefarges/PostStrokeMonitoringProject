package org.example.Server.JDBC;


import org.example.Server.IFaces.DoctorManager;
import org.example.Server.POJOS.Doctor;
import org.example.Server.POJOS.Patient;

import javax.xml.transform.Result;
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
     String insertUserSQL = "INSERT INTO Users (email, password, role) VALUES (?,?,?)";

     try{
         PreparedStatement psUser = manager.getConnection().prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
         psUser.setString(1, doctor.getEmail());
         psUser.setString(2, doctor.getPassword());
         psUser.setString(3, doctor.getRole().name());

         psUser.executeUpdate();

         //Obter el user_id autogenerado
         ResultSet rs = psUser.getGeneratedKeys();
         if (rs.next()) {
             int user_id = rs.getInt(1);
             doctor.setUser_id(user_id); //Asignar el user_id al doctor
         }
         rs.close();
         psUser.close();


     } catch (SQLException e) {
         e.printStackTrace();
     }

     String insertDoctorSQL = "INSERT INTO Doctors (doctor_id, name,surname,phone) VALUES (?,?,?,?)";
     try{
         PreparedStatement psDoctor = manager.getConnection().prepareStatement(insertDoctorSQL);

         psDoctor.setInt(1, doctor.getUser_id());
         psDoctor.setString(2, doctor.getName());
         psDoctor.setString(3, doctor.getSurname());
         psDoctor.setInt(4, doctor.getPhone());

         psDoctor.executeUpdate();
         psDoctor.close();
     } catch (SQLException e) {
         e.printStackTrace();
     }

 }

 @Override
 public void deleteDoctor(String email) {

     //borro de administrators usando el user_id
    String sqlDoctor = "DELETE FROM Doctors WHERE doctor_id = (SELECT user_id FROM Users WHERE email = ? AND role = 'DOCTOR')";
    //luego borro de users
    String sqlUser = "DELETE FROM Users WHERE email = ? AND role = 'DOCTOR'";

     try{
         PreparedStatement psDoctor = manager.getConnection().prepareStatement(sqlDoctor);
         psDoctor.setString(1,email);
         psDoctor.executeUpdate();
         psDoctor.close();

         PreparedStatement psUser = manager.getConnection().prepareStatement(sqlUser);
         psUser.setString(1, email);
         psUser.executeUpdate();
         psUser.close();
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
