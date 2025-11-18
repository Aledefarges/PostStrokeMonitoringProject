package org.example.Server.JDBC;

import org.example.Server.IFaces.AdministratorManager;
import org.example.Server.POJOS.Administrator;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public Administrator searchAdminById(){

    }
}
