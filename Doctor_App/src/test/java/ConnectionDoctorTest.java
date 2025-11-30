

import Connection.Connection_Doctor;
import org.example.POJOS.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


public class ConnectionDoctorTest {
    //Mocks
    @Mock
    private Socket socketMock;
    @Mock
    private BufferedReader in;
    @Mock
    private PrintWriter out;

    //Class to be tested
    private Connection_Doctor conn;
    //Data
    private static int test_id = 99999;
    private static String test_email = "doctortest@gmail.com";
    private static Doctor doctor;
    private static String password = "doctor123";


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        conn = Mockito.spy(new Connection_Doctor());

        conn.setSocket(socketMock);
        conn.setPrintWriter(out);
        conn.setBufferedReader(in);


        //Add the doctor we are going to test
        doctor = new Doctor("Ana", "Fernández", 642697987, test_email, password);

    }
    @Test
    @DisplayName("Add doctor: Sends ADD_DOCTOR and verifies answer DOCTOR_SAVED")
    public void testAddDoctor() throws IOException {
        when(in.readLine()).thenReturn("OK|DOCTOR_SAVED");
        boolean result = conn.sendRegisterDoctor(doctor);
        assertTrue(result, "Returns true if doctor has been correctly added");

        String expected = "ADD_DOCTOR|" + doctor.getName() + ";" + doctor.getSurname() + ";" + doctor.getPhone() + ";"
                + doctor.getEmail() + ";" + doctor.getPassword();
        verify(out).println(expected);
    }

    @Test
    @DisplayName("Update email: Sends CHANGE_EMAIL and verifies answer OK")
    public void testUpdateEmail() throws IOException {
        String new_email = "doctoremail21@gmail.com";
        when(in.readLine()).thenReturn("OK|EMAIL_CHANGED");
        boolean result = conn.sendChangeEmail(test_email, new_email);
        assertTrue(result, "Returns true if doctor's email has been correctly updated");
        verify(out).println("CHANGE_EMAIL|" + test_email + ";" + new_email);
    }

    @Test
    @DisplayName("Update password: Sends CHANGE_PASSWORD and verifies answer OK")
    public void testUpdatePassword() throws IOException {
        String new_password = "New_password1";
        when(in.readLine()).thenReturn("OK|PASSWORD_CHANGED");
        boolean result = conn.sendChangePassword(password, new_password);
        assertTrue(result, "Returns true if doctor's password has been correctly updated");
        verify(out).println("CHANGE_PASSWORD|" + password + ";" + new_password);
    }
    @Test
    //REVISAR
    @DisplayName("Request specific recording: Sends GET_RECORDING")
    public void testGetRecording() throws IOException {
        int recording_id = 5;
        String data = "RECORDING_DATA|EMG|100,200,300";
        when(in.readLine()).thenReturn(data);
        String result = conn.requestSpecificRecording(recording_id);
        assertEquals(data, result, "Must return the same");
        verify(out).println("GET_RECORDING|" + recording_id);

    }
    @Test
    @DisplayName("Update patient's medical history: Sends UPDATE_PATIENT HISTORY and verifies answer OK")
    public void testSendPatientHistory() throws IOException {
        when(in.readLine()).thenReturn("OK|MEDICAL_HISTORY_UPDATED");
        String new_medicalHistory = "Bronchitis";
        String patientEmail = "patientemail123@gmail.com";

        boolean result = conn.sendPatientHistory(patientEmail, new_medicalHistory);
        assertTrue(result, "Returns true if patient history has been correctly updated");
        verify(out).println("UPDATE_PATIENT_HISTORY|" + patientEmail + ";" + new_medicalHistory);
    }
    @Test
    @DisplayName("Request all patients: Sends VIEW_ALL_PATIENTS")
    public void testViewAllPatients() throws IOException {
        //Create a few strings of patient's data
        String patient1 = "19;Jorge;Diaz;1987-06-09;jorge@gmail.com;672309346;Asthma;M";
        String patient2 = "7;Laura;López;2001-08-02;laura@yahoo.es;634987234;None;F";
        String data = "PATIENTS_LIST|" + patient1 + "|" + patient2;

        when(in.readLine()).thenReturn(data);
        List<Patient> result = conn.requestAllPatients();

        //Tests
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(out).println("VIEW_ALL_PATIENTS|");
        verify(out).flush(); //to send the data through the socket
        //verify data from a patient
        assertEquals("Jorge", result.get(0).getName());
    }
    @Test
    @DisplayName("Login: Sends LOGIN and verifies answer from server")
    public void testLogin() throws IOException {
        String data = "OK|LOGIN_SUCCESS|9999"; //9999 is the id
        when(in.readLine()).thenReturn(data);
        String result = conn.sendDoctorLogin(test_email, password);
        assertEquals(data, result, "Must return the same");
        verify(out).println("LOGIN|" + test_email + ";" + password);
    }
    @Test
    @DisplayName("Request recording by patient: Sends VIEW_RECORDING_BY_PATIENT and verifies answer")
    public void testRecordingByPatient() throws IOException {
        int patient_id = 15;
        //Create strings of recordings
        String recording1 = "101;EMG;2025-11-29T10:00:00";
        String recording2 = "102;ECG;2025-11-20T12:00:00";
        String data = "RECORDINGS_LIST|" + recording1 + "|" + recording2;

        when(in.readLine()).thenReturn(data);
        List<Recording> result = conn.requestRecordingsByPatient(patient_id);
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(out).println("VIEW_RECORDINGS_BY_PATIENT|" + patient_id);
        verify(out).flush();
    }

    @AfterEach
    public void cleanup(){
        conn.close();
    }
}
