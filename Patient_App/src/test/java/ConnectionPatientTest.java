import Bitalino.Frame;
import Connection.*;
import org.example.POJOS.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConnectionPatientTest {
    //Mocks
    @Mock
    private Socket socketMock;
    @Mock
    private BufferedReader in;
    @Mock
    private PrintWriter out;

    //Class to be tested
    private Connection_Patient conn;

    //Data
    private static int test_id = 99999;
    private static String test_email = "testemail@gmail.com";
    private static Patient patient;
    private static String password = "password123";
    private static int doctor_id = 1;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        conn = Mockito.spy(new Connection_Patient());

        conn.setSocket(socketMock);
        conn.setPrintWriter(out);
        conn.setBufferedReader(in);

        //Add the patient we are going to test
        Date dob = Date.valueOf(LocalDate.of(2020, 1, 1));
        patient = new Patient(test_id, password, "Juan", "García", dob, test_email, 678934567, "Diabetes", Patient.Sex.M);

    }
    @Test
    @DisplayName("Add patient: Sends ADD_PATIENT and verifies answer PATIENT_SAVED")
    public void testAddPatient() throws IOException {
        when(in.readLine()).thenReturn("PATIENT_SAVED");
        boolean result = conn.sendPatientToServer(patient, doctor_id);
        assertTrue(result, "Returns true if patient has been correctly added");

        String expected = "ADD_PATIENT|" +
                patient.getName() + ";" +
                patient.getSurname() + ";" +
                patient.getDob() + ";" +
                patient.getEmail() + ";" +
                patient.getPhone() + ";" +
                patient.getMedicalhistory() + ";" +
                patient.getSex().name() + ";" +
                patient.getPassword() + ";" +
                doctor_id;


        verify(out).println(expected);


    }
    @Test
    @DisplayName("Update name: Sends UPDATE_PATIENT|name and verifies answer OK")
    public void testUpdateName() throws IOException {
        String new_name = "Pablo";
        when(in.readLine()).thenReturn("OK|PATIENT_UPDATED");
        boolean result = conn.sendUpdateToServer("name", new_name);
        assertTrue(result, "Returns true if patient's name has been correctly updated");
        verify(out).println("UPDATE_PATIENT|name;" + new_name);
    }
    @Test
    @DisplayName("Update surname: Sends UPDATE_PATIENT|surname and verifies answer OK")
    public void testUpdateSurname() throws IOException {
        String new_surname = "Martínez";
        when(in.readLine()).thenReturn("OK|PATIENT_UPDATED");
        boolean result = conn.sendUpdateToServer("surname", new_surname);
        assertTrue(result, "Returns true if patient's surname has been correctly updated");
        verify(out).println("UPDATE_PATIENT|surname;" + new_surname);
    }

    @Test
    @DisplayName("Update phone number: Sends UPDATE_PATIENT|phone and verifies answer OK")
    public void testUpdatePhone() throws IOException {
        int new_phone = 698168937;
        when(in.readLine()).thenReturn("OK|PATIENT_UPDATED");
        boolean result = conn.sendUpdateToServer("phone", String.valueOf(new_phone));
        assertTrue(result, "Returns true if patient's phone number has been correctly updated");
        verify(out).println("UPDATE_PATIENT|phone;" + new_phone);
    }
    @Test
    @DisplayName("Update sex: Sends UPDATE_PATIENT|sex and verifies answer OK")
    public void testUpdateSex() throws IOException {
        Patient.Sex new_sex = Patient.Sex.F;
        when(in.readLine()).thenReturn("OK|PATIENT_UPDATED");
        boolean result = conn.sendUpdateToServer("sex", String.valueOf(new_sex));
        assertTrue(result, "Returns true if patient's sex has been correctly updated");
        verify(out).println("UPDATE_PATIENT|sex;" + new_sex);
    }
    @Test
    @DisplayName("Update medical history: Sends UPDATE_PATIENT|medical_history and verifies answer OK")
    public void testMedicalHistory() throws IOException {
        String new_medicalHistory = "High fever";
        when(in.readLine()).thenReturn("OK|PATIENT_UPDATED");
        boolean result = conn.sendUpdateToServer("medical_history", new_medicalHistory);
        assertTrue(result, "Returns true if patient's medical history has been correctly updated");
        verify(out).println("UPDATE_PATIENT|medical_history;" + new_medicalHistory);
    }
    @Test
    @DisplayName("Update date of birth: Sends UPDATE_PATIENT|dob and verifies answer OK")
    public void testUpdateDob() throws IOException {
        Date new_date = Date.valueOf(LocalDate.of(2002, 10, 3));
        when(in.readLine()).thenReturn("OK|PATIENT_UPDATED");
        boolean result = conn.sendUpdateToServer("dob", String.valueOf(new_date));
        assertTrue(result, "Returns true if patient's dob has been correctly updated");
        verify(out).println("UPDATE_PATIENT|dob;" + new_date);
    }
    @Test
    @DisplayName("Change email: Sends CHANGE_EMAIL and verifies answer OK")
    public void testChangeEmail() throws IOException {
        String new_email = "testemail2@gmail.com";
        when(in.readLine()).thenReturn("OK|EMAIL_CHANGED");
        boolean result = conn.sendChangeEmail(test_email, new_email);
        assertTrue(result, "Returns true if patient's email has been correctly updated");
        verify(out).println("CHANGE_EMAIL|" + test_email + ";" + new_email);
    }
    @Test
    @DisplayName("Change password: Sends CHANGE_PASSWORD and verifies answer OK")
    public void testChangePassword() throws IOException {
        String new_password = "New_password";
        when(in.readLine()).thenReturn("OK|PASSWORD_CHANGED");
        boolean result = conn.sendChangePassword(password, new_password);
        assertTrue(result, "Returns true if patient's password has been correctly updated");
        verify(out).println("CHANGE_PASSWORD|" + password + ";" + new_password);
    }
    @Test
    @DisplayName("Delete patient: sends DELETE_ACOUNT and it verifies if it is OK")
    public void testDeletePatient() throws IOException {
        when(in.readLine()).thenReturn("OK|PATIENT_DELETED");
        boolean result = conn.deletePatientFromServer();

        assertTrue(result, "Returns true if patient is eliminated");
        verify(out).println("DELETE_ACCOUNT|");
        verify(out).println("LOGOUT|");

    }
    @Test
    @DisplayName("Check password (of log in): sends LOGIN and verifies it the username and password are correct")
    public void testCheckPassword() throws IOException {
        when(in.readLine()).thenReturn("OK|LOGIN_SUCCESS");
        String result = conn.sendLogIn(test_email, password);

        assertEquals("OK|LOGIN_SUCCESS", result, "Must return response OK|LOGIN_SUCCESS");
        verify(out).println("LOGIN|" + test_email + ";" + password);
    }
    @Test
    @DisplayName("Start recording")
    public void testStartRecording() throws IOException {
        when(in.readLine()).thenReturn("OK|RECORDING_STARTED|25");
        int [][] result = conn.startRecording("EMG");
        assertEquals(0, result[0][0], "Must return 0"); //channel for EMG is 0
        assertEquals(25, result[1][0]); //id is 25
        verify(out).println("START_RECORDING|EMG");

    }
    @Test
    @DisplayName("Send frames")
    public void sendFrames() throws IOException {
        Frame frame = new Frame();
        frame.seq = 10;
        frame.analog = new int [] {500, 0, 0, 0, 0, 0};
        frame.digital = new int [] {1, 0, 1, 0};
        Frame [] frames = new Frame [] {frame};
        int [] channels = {0};
        conn.sendFrames(frames, channels);
        verify(out).println("FRAME|10;500;1;0;1;0");
    }
    @Test
    @DisplayName("End recording")
    public void endRecording() throws IOException {
        when(in.readLine()).thenReturn("OK|RECORDING_SAVED");
        conn.endRecordingAndGetResponse();
        verify(out).println("END_RECORDING");
    }
    @Test
    @DisplayName("Request recording data")
    public void RequestRecording() throws IOException {
        when(in.readLine()).thenReturn("RECORDING_DATA|25|10,20,30");
        Double [][] result = conn.requestRecordingData(25, "ECG");
        assertArrayEquals(new Double [] {10.0, 20.0, 30.0}, result[0]);
        verify(out).println("GET_RECORDING|25");
    }
    @Test
    @DisplayName("Request medical history: Sends GET_MEDICAL_HISTORY and waits for server response")
    public void testGetMedicalHistory() throws IOException {
        String medicalHistory = "Diabetes";
        when(in.readLine()).thenReturn("MEDICAL_HISTORY|"+medicalHistory);
        String result = conn.requestMedicalHistory(test_id);
        assertEquals(medicalHistory, result, "Must return the medical history");
        verify(out).println("GET_MEDICAL_HISTORY|" + test_id);
    }
    @AfterEach
    public void cleanup(){
        conn.close();
    }


}
