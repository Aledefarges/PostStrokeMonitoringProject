package org.example.Server.JDBC;

import org.example.POJOS.Doctor;
import org.example.POJOS.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class JDBCDoctorManagerTest {

    private JDBCManager manager;
    private JDBCDoctorManager jdbcDoctorManager;

    @BeforeEach
    void setUp() {
        manager =  new TestJDBCMAnager();
        jdbcDoctorManager = new JDBCDoctorManager(manager);
    }
    @Test
    void testAddDoctor() throws SQLException{

        Doctor doctor = new Doctor();
        doctor.setEmail("test@doctor.com");
        doctor.setPassword("password123");
        doctor.setRole(User.Role.DOCTOR);
        doctor.setName("John");
        doctor.setSurname("Doe");
        doctor.setPhone(123456789);
        jdbcDoctorManager.addDoctor(doctor);

        //check inserted user
        PreparedStatement psUser = manager.getConnection().prepareStatement("SELECT * FROM Users WHERE email = ?");
        psUser.setString(1,"test@doctor.com");
        ResultSet rsUser = psUser.executeQuery();
        assertTrue(rsUser.next(), "User should be inserted");
        int userId = rsUser.getInt("user_id");
        assertEquals("DOCTOR", rsUser.getString("role"));

        PreparedStatement psDoctor = manager.getConnection().prepareStatement("SELECT * FROM Doctors WHERE doctor_id = ?");
        psDoctor.setInt(1,userId);
        ResultSet rsDoctor = psDoctor.executeQuery();
        assertTrue(rsDoctor.next(), "Doctor should be inserted");
        assertEquals("John", rsDoctor.getString("name"));
        assertEquals("Doe", rsDoctor.getString("surname"));
        assertEquals(123456789, rsDoctor.getInt("phone"));

        rsUser.close();
        rsDoctor.close();
        psUser.close();
        psDoctor.close();

    }
  
}