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

        String sql = "INSERT INTO Administrator (admin_id,name,email,phone, password) VALUES (?,?,?,?,?)";

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

        String sql = "SELECT * FROM Administrator WHERE email = ?";

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
}
