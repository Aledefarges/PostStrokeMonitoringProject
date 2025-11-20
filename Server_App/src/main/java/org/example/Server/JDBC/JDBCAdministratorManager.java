package org.example.Server.JDBC;

import org.example.Server.IFaces.AdministratorManager;
import org.example.POJOS.Administrator;
import org.example.POJOS.Doctor;
import org.example.POJOS.Patient;

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
        String user = "INSERT INTO Users (email, password, role) VALUES (?,?,?)";

        try{
            PreparedStatement ps1 = manager.getConnection().prepareStatement(user, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, administrator.getEmail());
            ps1.setString(2, administrator.getPassword());
            ps1.setString(3, administrator.getRole().name());
            ps1.executeUpdate();
            ps1.close();

            ResultSet rs = ps1.getGeneratedKeys();
            if(rs.next()){
                int user_id = rs.getInt(1);
                administrator.setUser_id(user_id);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "INSERT INTO Administrators (user_id, name, surname, phone) VALUES (?,?,?,?)";
        try{
            PreparedStatement ps2 = manager.getConnection().prepareStatement(sql);
            ps2.setInt(1, administrator.getUser_id());
            ps2.setString(2, administrator.getName());
            ps2.setString(3, administrator.getSurname());
            ps2.setInt(4, administrator.getPhone());
            ps2.executeUpdate();
            ps2.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteAdministrator(String email) {

        //borro de administrators usando el user_id
        String sqlAdmin = "DELETE FROM Administrators WHERE admin_id = (SELECT user_id FROM Users WHERE email = ? AND role = 'ADMINISTRATOR')";
        //luego borro de users
        String sqlUser = "DELETE FROM Users WHERE email = ? AND role = 'ADMINISTRATOR'";

        try{
            PreparedStatement psAdmin = manager.getConnection().prepareStatement(sqlAdmin);
            psAdmin.setString(1,email);
            psAdmin.executeUpdate();
            psAdmin.close();

            PreparedStatement psUser = manager.getConnection().prepareStatement(sqlUser);
            psUser.setString(1, email);
            psUser.executeUpdate();
            psUser.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Administrator searchAdministratorByEmail(String email){
        Administrator admin = null;

        String sql  = "SELECT u.user_id, u.email, u.password, a.name, a.surname, a.phone " +
                "FROM Users u JOIN Administrators a ON u.user_id = a.user_id " +
                "WHERE u.email = ? AND u.role = 'ADMINISTRATOR'";
        try{
            PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs=stmt.executeQuery();

            if(rs.next()){
                int admin_id = rs.getInt("user_id");
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
}
