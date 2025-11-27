package org.example.Server.JDBC;

import org.example.POJOS.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestJDBCPatientManager {
    private static JDBCPatientManager patientManager;
    private static JDBCManager manager;
    private static Connection conn;

    //Data
    private static int test_id = 99999;
    private static String test_email = "testemail@gmail.com";
    private static Patient patient;
    private static String password = "password123";

    @BeforeAll
    public static void setUp() throws Exception {
        //Initialice all managers
        manager = new JDBCManager();
        patientManager = new JDBCPatientManager(manager);
        LocalDate localDate = LocalDate.of(2002, 7, 6);
        Date dob = Date.valueOf(localDate);
        patient = new Patient(test_id, password,"Juan", "Garcia", dob, test_email, 678934567, "Diabetes", Patient.Sex.M);

    }
    @BeforeEach
    public void startTransaction() throws SQLException {
        conn = manager.getConnection();
        conn.setAutoCommit(false);
    }
    @AfterEach
    public void eliminateTransaction() throws SQLException {
        //this method eliminates the patient from the database after the test,
        //this way we don't get data that we don't want in the database
        if(conn != null) {
            conn.rollback();
            conn.setAutoCommit(true);
            conn.close();
        }
    }
    @Test
    public void testAddPatient() throws SQLException {
        patientManager.addPatient(patient);
        Patient p = patientManager.getPatientByEmail(test_email);

        assertNotNull(p, "Patient found in the database");
        assertEquals("Juan", p.getName(), "Patient name must match");
    }
    @Test
    public void testUpdateName() throws Exception {
        String new_name = "Pablo";
        patientManager.addPatient(patient);
        int id = patient.getPatient_id();
        //Change name
        patientManager.updateName(id, new_name);
        //Verify that the name is changed correctly
        Patient p = patientManager.getPatientByEmail(test_email);
        assertEquals(new_name, p.getName(), "Patient name must match");
    }
    @Test
    public void testUpdateSurname() throws Exception {
        String new_surname = "Martínez";
        patientManager.addPatient(patient);
        int id = patient.getPatient_id();
        //Change surname
        patientManager.updateSurName(id, new_surname);
        //Verify that the surname is changed correctly
        Patient p = patientManager.getPatientByEmail(test_email);
        assertEquals(new_surname, p.getSurname(), "Patient surname must match");
    }
    @Test
    public void testUpdatePhone() throws Exception {
        int new_phone = 698168937;
        patientManager.addPatient(patient);
        int id = patient.getPatient_id();
        //Change phone number
        patientManager.updatePhone(id, new_phone);
        //Verify that the phone number is changed correctly
        Patient p = patientManager.getPatientByEmail(test_email);
        assertEquals(new_phone, p.getPhone(), "Patient phone number must match");
    }
    @Test
    public void testUpdateSex() throws Exception {
        Patient.Sex new_sex = Patient.Sex.F;
        patientManager.addPatient(patient);
        int id = patient.getPatient_id();
        //Change sex
        patientManager.updateSex(id, new_sex);
        //Verify that the name is changed correctly
        Patient p = patientManager.getPatientByEmail(test_email);
        assertEquals(new_sex, p.getSex(), "Patient sex must match");
    }
    @Test
    public void testMedicalHistory() throws Exception {
        String new_medicalHistory = "High fever";
        patientManager.addPatient(patient);
        int id = patient.getPatient_id();
        //Change medical history
        patientManager.updateMedicalHistory(id, new_medicalHistory);
        //Verify that the name is changed correctly
        Patient p = patientManager.getPatientByEmail(test_email);
        assertEquals(new_medicalHistory, p.getMedicalhistory(), "Medical history must match");
    }
    @Test
    public void testUpdateDob() throws Exception {
        Date new_date = Date.valueOf(LocalDate.of(2002, 10, 3));
        patientManager.addPatient(patient);
        int id = patient.getPatient_id();
        //Change date of birth
        patientManager.updateDob(id, new_date);
        //Verify that the dob is changed correctly
        Patient p = patientManager.getPatientByEmail(test_email);
        assertEquals(new_date, p.getDob(), "Patient's date of birth must match");
    }
    @Test
    public void testUpdateEmail() throws Exception {
        String new_email = "testemail2@gmail.com";
        patientManager.addPatient(patient);
        int id = patient.getPatient_id();
        //Change email
        patientManager.updateEmail(id, new_email);
        //Verify that the email is changed correctly
        //Important to see if it finds the patient with its new email
        Patient p = patientManager.getPatientByEmail(new_email);
        assertEquals(new_email, p.getEmail(), "Patient's email must match");
    }
    @Test
    public void testUpdatePassword() throws Exception {
        String new_password = "New_password";
        patientManager.addPatient(patient);
        int id = patient.getPatient_id();
        //Change password
        patientManager.updatePassword(id, new_password);
        //Verify that the password is changed correctly
        Patient p = patientManager.getPatientByEmail(test_email);
        assertEquals(new_password, p.getPassword(), "Patient's password must match");
    }
    @Test
    public void testDeletePatient() throws Exception {
        patientManager.addPatient(patient);
        //verify that it returns true, if it does, it will return true
        assertTrue(patientManager.deletePatient(test_email), "Must return true");
        //verify that the patient no longer exists in the database
        assertNull(patientManager.getPatientByEmail(test_email), "Patient not found");
        //try to eliminate a patient which emails is not correct
        assertFalse(patientManager.deletePatient("doesnotexist@gmail.com"), "Must return false");
    }
    @Test
    public void testCheckPassword() throws Exception {
        patientManager.addPatient(patient);
        assertTrue(patientManager.checkPassword(test_email, password), "Must return true");
        assertFalse(patientManager.checkPassword(test_email, "wrongpassword"), "Must return false");
    }

}
