package org.example.Server.JDBC;

import org.example.Server.IFaces.AdministratorManager;
import org.example.Server.POJOS.Administrator;
import org.example.Server.POJOS.Doctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        String sql = "INSERT INTO Administrators (name,surname,email,phone) VALUES (?,?,?,?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);

            ps.setString(1,administrator.getName());
            ps.setString(2,administrator.getSurname());
            ps.setString(3, administrator.getEmail());
            ps.setInt(4, administrator.getPhone());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAdministrator(String email){
        String sql = "DELETE FROM Administrators WHERE email=?";
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
    public Administrator searchAdminById(Integer id){
        Administrator administrator=null;
        String sql="SELECT * FROM Administrators WHERE id=?";
        try{
            Statement stmt=manager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Integer admin_id=rs.getInt("id");
            String name=rs.getString("name");
            String surname=rs.getString("surname");
            String email=rs.getString("email");
            Integer phone=rs.getInt("phone");

            administrator = new Administrator(admin_id,name,surname,email,phone);

            rs.close();
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();;
        }
        return administrator;
    }
}
