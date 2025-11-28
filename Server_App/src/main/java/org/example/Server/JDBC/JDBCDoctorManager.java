package org.example.Server.JDBC;



import org.example.POJOS.Doctor;
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

         MessageDigest md = MessageDigest.getInstance("MD5");
         md.update(doctor.getPassword().getBytes());
         byte[] encryptedPassword = md.digest();

         StringBuilder sb = new StringBuilder();
         for (byte b: encryptedPassword){
             sb.append(String.format("%02x",b)); //2 digit hexadecimal
         }
         String encryptedStringPassword = sb.toString();

         ps.setString(5, encryptedStringPassword);
         ps.executeUpdate();

     }catch(SQLException e){
         e.printStackTrace();
     }
     catch(Exception e2){
         e2.printStackTrace();
     }


 }

 @Override
 public boolean deleteDoctor(String email) {
     String sql = "DELETE FROM Doctors WHERE email =  ? ";

     try(Connection c=manager.getConnection();
     PreparedStatement ps=c.prepareStatement(sql)){
         ps.setString(1, email);
         int rowsAffected = ps.executeUpdate(); //executeUpdate devuelve cuanats filas fueron eliminadas por el sql, el resultado puede ser 0 (no exsiet este mail), 1 se elimino paciente (ya que el mail es UNIQUE y no puede haber más de uno)
         //ps.executeUpdate();
         return  rowsAffected == 1; //False si no se elimino ningun paciente (no existia ese email), true si se elimino un paciente

     }catch(SQLException e){
         e.printStackTrace();
         return false;
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

                 // List<Patient> patients= jdbcPatientManager.getListOfPatientsOfDoctor(doctor_id);

                 doctor = new Doctor(name, surname, phone, email, password);
             }
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

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return doctor;
    }

    @Override
    public void updatePassword(int doctor_id, String newPassword){
        String sql = "UPDATE Doctors SET password = ? WHERE doctor_id = ?";
        try (Connection c=manager.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)){

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

        try(Connection c=manager.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1, email);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    String pass = rs.getString("password");

                   /* MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getBytes());
                    byte[] digest = md.digest();

                    //Converting byte[] to hexadecimal String so it can be compared with the stored password
                    StringBuilder sb = new StringBuilder();
                    for (byte b: digest){
                        sb.append(String.format("%02x",b));
                    }
                    String encryptedPass = sb.toString();
                    */

                    return pass.equalsIgnoreCase(password);

                }
            } catch(SQLException e){
            e.printStackTrace();
            }
        }catch (Exception e2){
            e2.printStackTrace();
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

