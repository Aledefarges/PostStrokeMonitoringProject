package org.example.Server.JDBC;

import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJDBCMAnager extends JDBCManager{

    public TestJDBCMAnager(){
        try{
            this.c = DriverManager.getConnection("jdbc:sqlite::memory:"); //only used by tests and brand new DB each time
            this.c.createStatement().execute("PRAGMA foreign_keys = ON");

            createTables();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
