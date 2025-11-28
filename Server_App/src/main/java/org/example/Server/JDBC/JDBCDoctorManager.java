package org.example.Server.JDBC;



import org.example.POJOS.Doctor;
import org.example.POJOS.Exceptions;
import org.example.POJOS.Patient;
import org.example.Server.IFaces.DoctorManager;

import javax.xml.transform.Result;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDBCDoctorManager implements DoctorManager {
 private JDBCManager manager;

 public JDBCDoctorManager(JDBCManager manager){
     this.manager=manager;
 }

 @Override
 public void addDoctor(Doctor doctor){
     String sql = "INSERT INTO Doctors (name, surname, phone, email, password) VALUES (?,?,?,?,?)";

     try(Connection c=manager.getConnection();
     PreparedStatement ps=c.prepareStatement(sql)){
         ps.setString(1, doctor.getName());
         ps.setString(2, doctor.getSurname());
         ps.setInt(3, doctor.getPhone());
         ps.setString(4, doctor.getEmail());
         ps.setString(5,doctor.getPassword());
         ps.executeUpdate();

     }catch(SQLException e){
         e.printStackTrace();
     }
     catch(Exception e2){
         e2.printStackTrace();
     }


 }



@Override
public void assingDoctorToPatient(Integer patient_id, Integer doctor_id){
     String sql="UPDATE Patients SET doctor_id=? WHERE patient_id=?";
     try(Connection c=manager.getConnection();
     PreparedStatement ps=c.prepareStatement(sql)){
         ps.setInt(1,doctor_id);
         ps.setInt(2,patient_id);

         ps.executeUpdate();
     }catch(SQLException e){
         e.printStackTrace();
     }
}

@Override
public Doctor getDoctorByEmail(String email){
    Doctor doctor=null;
    String sql = "SELECT * FROM Doctors WHERE email = ?";

    try(Connection c=manager.getConnection();
    PreparedStatement ps=c.prepareStatement(sql)){
         ps.setString(1, email);

         try(ResultSet rs=ps.executeQuery()){
             if(rs.next()){
                 String password = rs.getString("password");
                 String name = rs.getString("name");
                 String surname = rs.getString("surname");
                 int phone = rs.getInt("phone");
                 int doctor_id = rs.getInt("doctor_id");

                 // List<Patient> patients= jdbcPatientManager.getListOfPatientsOfDoctor(doctor_id);

                 doctor = new Doctor(name, surname, phone, email, password);
                 doctor.setDoctor_id(doctor_id);
             }
         } catch (Exceptions e) {
             throw new RuntimeException(e);
         }
    }catch(SQLException e){
         e.printStackTrace();
     }
     return doctor;
}
@Override
    public Doctor searchDoctorById(int doctor_id){
        Doctor doctor=null;

        String sql = "SELECT * FROM Doctors WHERE doctor_id = ?";

        try(Connection c=manager.getConnection();
        PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1, doctor_id);

            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    int phone = rs.getInt("phone");
                    String email = rs.getString("email");

                    doctor = new Doctor(name, surname, phone, email, password);
                    doctor.setDoctor_id(rs.getInt("doctor_id"));
                }

            } catch (Exceptions e) {
                throw new RuntimeException(e);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return doctor;
    }


    @Override
    public void updatePassword(int doctor_id, String newPassword){
        String sql = "UPDATE Doctors SET password = ? WHERE doctor_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)){

            ps.setString(1, newPassword);
            ps.setInt(2, doctor_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        }
    }
    @Override
    public void updateEmail(int doctor_id, String newEmail) {
        String sql = "UPDATE Doctors SET email = ? WHERE doctor_id = ?";
        try (
            Connection c=manager.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newEmail);
            ps.setInt(2, doctor_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkPassword(String email, String password) {
        String sql = "SELECT password FROM Doctors WHERE email = ?";

        try(Connection c=manager.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1, email);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    String pass = rs.getString("password");

                    return pass.equalsIgnoreCase(password);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Doctor> getAllDoctors(){
            List<Doctor> doctorList = new ArrayList<>();
        try{
                String sql = "SELECT doctor_id, name, surname FROM Doctors";
                Connection c = manager.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Doctor doctor = new Doctor(
                            rs.getString("name"),
                            rs.getString("surname"),
                            0,
                            null, null
                    );
                    doctor.setDoctor_id(rs.getInt("doctor_id"));
                    doctorList.add(doctor);
                }
                rs.close();
                ps.close();
            }catch(Exception e){
                System.out.println("ERROR get list of doctors: "+e.getMessage());
            }
            return doctorList;

    }

}

