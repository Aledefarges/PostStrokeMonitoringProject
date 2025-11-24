package org.example.Server.JDBC;

import org.example.Server.IFaces.AdministratorManager;
import org.example.POJOS.Administrator;
import org.example.POJOS.Doctor;
import org.example.POJOS.Patient;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JDBCAdministratorManager implements AdministratorManager {
    private JDBCManager manager;

    public JDBCAdministratorManager(JDBCManager manager){
        this.manager=manager;
    }

    //AddAdministrator
    //DeleteAdministrator
    //SearchById
    @Override
    public void addAdministrator(Administrator administrator){
       //H
        String sql = "INSERT INTO Administrators (admin_id,name,email,phone, password) VALUES (?,?,?,?,?)";

        try {

            PreparedStatement ps = manager.getConnection().prepareStatement(sql);

            ps.setInt(1, administrator.getAdmin_id());
            ps.setString(2, administrator.getName());
            ps.setString(3, administrator.getEmail());
            ps.setInt(4, administrator.getPhone());
            ps.setString(5, administrator.getPassword());

            ps.executeUpdate();
            ps.close() ;

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAdministrator(String email) {

        String sql = "DELETE FROM Administrators WHERE admin_id =  ? ";

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
    public Administrator searchAdministratorByEmail(String email){
        Administrator admin = null;

        String sql = "SELECT * FROM Administrators WHERE email = ?";

        try{
            PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs=stmt.executeQuery();

            if(rs.next()){
                int admin_id = rs.getInt("admin_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int phone = rs.getInt("phone");
                String password = rs.getString("password");


                admin = new Administrator(admin_id, email, password, name, surname, phone);
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();;
        }
        return admin;
    }

    @Override
    public void updatePassword(int admin_id, String newPassword){
        String sql = "UPDATE Administrators SET password = ? WHERE admin_id = ?";
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
            ps.setInt(2, admin_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        }
    }

    @Override
    public boolean checkPassword(String email, String password) {
        String sql = "SELECT password FROM Administrators WHERE email = ?";
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
