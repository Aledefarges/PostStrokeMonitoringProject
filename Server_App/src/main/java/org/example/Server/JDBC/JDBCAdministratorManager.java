package org.example.Server.JDBC;

import org.example.Server.IFaces.AdministratorManager;
import org.example.POJOS.Administrator;
import org.example.POJOS.Doctor;
import org.example.POJOS.Patient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
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
        String sql = "INSERT INTO Administrators (name, surname,email,phone, password) VALUES (?,?,?,?,?)";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, administrator.getName());
            ps.setString(2, administrator.getSurname());
            ps.setString(3, administrator.getEmail());
            ps.setInt(4, administrator.getPhone());

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(administrator.getPassword().getBytes());
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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteAdministrator(String email) {
        String sql = "DELETE FROM Administrators WHERE email =  ? ";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Administrator searchAdministratorByEmail(String email){
        Administrator admin = null;

        String sql = "SELECT * FROM Administrators WHERE email = ?";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);

            try(ResultSet rs= ps.executeQuery()){
                if(rs.next()){
                    String dbEmail = rs.getString("email");
                    String dbPassword = rs.getString("password");
                    System.out.println("FOUND IN DB → email=[" + dbEmail + "], password=[" + dbPassword + "]");

                    admin = new Administrator(
                            rs.getInt("admin_id"),
                            dbEmail,
                            dbPassword,
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getInt("phone")
                    );
                }else{
                    System.out.println("NOT FOUND in DB");
                }

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return admin;
    }

    @Override
    public void updatePassword(int admin_id, String newPassword){
        String sql = "UPDATE Administrators SET password = ? WHERE admin_id = ?";
        try (Connection c = manager.getConnection();
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
        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, email);

            try(ResultSet rs = ps.executeQuery()){
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
        }
        catch(SQLException e){
            e.printStackTrace();
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return false;
    }
}
