package org.example.Server.JDBC;

import org.example.Server.IFaces.UserManager;
import org.example.Server.POJOS.Doctor;
import org.example.Server.POJOS.Patient;
import org.example.Server.POJOS.User;

import javax.management.Query;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUserManager implements UserManager {

    private JDBCManager manager;

    public JDBCUserManager(JDBCManager manager){
        this.manager=manager;
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO Users (email, password, role) VALUES (?, ?, ?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().name());

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void deleteUser(String email){
        String sql = "DELETE FROM Users WHERE email = ?";
        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            ps.setString(1, email);

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    //método temporal (luego lo pondremos encrypted password
    public void changePassword(User u, String new_password) {
        String sql = "UPDATE Users SET password = ? WHERE user_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(3, new_password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Para cuando creemos los menús, ponemos este método, no va aquí
   /* private void login() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Email: \n");
        String email = reader.readLine();

        System.out.println("Password: \n");
        String password = reader.readLine();

        User u = new User() {};
        checkPassword(email, password);

        if (u != null & u.getRole().equals("ADMINISTRATOR")) {
            System.out.println("Login of administrator successful!");
            //le pasamos el admin menu cuando lo creemos
            //adminMenu
        } else if (u != null & u.getRole().equals("DOCTOR")) {
            System.out.println("Login of doctor successful!");
            //le pasamos el doctor menu cuando lo creemos
            //doctorMenu

        } else if (u != null & u.getRole().equals("PATIENT")) {
            System.out.println("Login of patient successful!");
            //le pasamos el patient menu cuando lo creemos
            //patientMenu
        }

    }*/
    @Override
    public boolean checkPassword(String username, String password) {
        String sql = "SELECT password FROM users WHERE email = ?";
        try{
            PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String pass = rs.getString("password");
                if(pass.equals(password)){
                    return true;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void changeEmail(int user_id, String email) {
        String sql = "UPDATE Patients SET email = ? WHERE user_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, user_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void changeEmail(User u, String new_email) {
        String sql = "UPDATE Users SET password = ? WHERE user_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(2, new_email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM Users WHERE email = ?";

        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Integer user_id = rs.getInt("user_id");
                String password = rs.getString("password");
                User.Role role = User.Role.valueOf(rs.getString("role"));

                switch (role){
                    case PATIENT: {
                        user = new Patient(user_id, email, password);
                        break;
                    }
                    case DOCTOR:{
                        user = new Doctor(user_id, email, password);
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Role not recognized");
                    }
                }

            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
