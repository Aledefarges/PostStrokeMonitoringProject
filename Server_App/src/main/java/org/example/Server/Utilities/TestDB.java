package org.example.Server.Utilities;

import org.example.Server.JDBC.JDBCManager;

import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {

        JDBCManager manager = new JDBCManager();

        if (manager.getConnection() == null) {
            System.err.println("❌ Connection is NULL — Database NOT connected!");
            return;
        }

        System.out.println("✅ Database connected!");

        try {
            Statement stmt = manager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");

            System.out.println("📌 Tables in database:");
            while (rs.next()) {
                System.out.println(" - " + rs.getString("name"));
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.err.println("❌ Error querying database!");
            e.printStackTrace();
        }

        manager.close();
        System.out.println("🔚 Connection closed successfully.");
    }
}
