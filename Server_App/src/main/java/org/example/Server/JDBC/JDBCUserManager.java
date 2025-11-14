package org.example.Server.JDBC;

import org.example.Server.IFaces.UserManager;
import org.example.Server.POJOS.User;

import javax.management.Query;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUserManager implements UserManager {

    private JDBCManager manager;

    public JDBCUserManager(JDBCManager manager){
        this.manager=manager;
    }
    @Override

    //método temporal (luego lo pondremos encrypted password
    public void changePassword(User u, String new_passwd) {
        String sql = "UPDATE Users SET password = ? WHERE user_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(3, new_passwd);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
