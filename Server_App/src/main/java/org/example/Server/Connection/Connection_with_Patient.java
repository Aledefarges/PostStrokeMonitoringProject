package org.example.Server.Connection;

import org.example.POJOS.Recording;
import org.example.Server.JDBC.JDBCManager;
import org.example.Server.JDBC.JDBCPatientManager;
import org.example.POJOS.Patient;
import org.example.Server.JDBC.JDBCRecordingFramesManager;
import org.example.Server.JDBC.JDBCRecordingManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
//

public class Connection_with_Patient{

    //private final Socket socket;
    private JDBCManager db;
    private JDBCPatientManager patientManager;
    private JDBCRecordingManager recordingManager;
    private JDBCRecordingFramesManager framesManager;
    private BufferedReader in;
    private PrintWriter out;
    private boolean recordingActive = false; // Indicates if the server is receiving a recording
    private int currentRecording_id = -1; // The id will change depending on the recording
    private int frameCounter = 0; // Number of frames received by each recording
    private int [] activeChannels;

    public Connection_with_Patient(JDBCManager db) {
        this.db = db;
    }

    public static void main(String[] args) {
        JDBCManager db = new JDBCManager();
        Connection_with_Patient server = new Connection_with_Patient(db);
        server.start();
    } //Este main ahora va en una clase aparte debido a que implementamos Runnable

    public void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(9000);
            Socket socket = serverSocket.accept();
            System.out.println("Patient connected");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("SERVER: Connected"); // EL PACIENTE YA PUEDE LEERLO
            patientManager = new JDBCPatientManager(db);
            recordingManager = new JDBCRecordingManager(db);
            framesManager = new JDBCRecordingFramesManager(db);
            String message; // esto para tenerlo ordenado y añadir lo de Utilities:
            while((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                String[] parts = message.split("\\|");
                String command = parts[0];
                switch (command) {
                    case "ADD_PATIENT":
                        savePatientRegistration(parts[1]); //Funcion ya creada por nerea, se puede cambiar el nombre a handleAddPatients
                        break;
                    case "LOGIN":
                        handleLogIn(parts[1]);
                        break;
                    case "CHANGE_PASSWORD":
                        handleChangePassword(parts[1]);
                        break;
                    case "CHANGE_EMAIL":
                        handleChangeEmail(parts[1]);
                        break;
                    case "DELETE_PATIENT":
                        deletePatient(parts[1]);
                        break;
                    case "START_RECORDING":
                            handleStartRecording(parts[1]);
                            break;
                    case "FRAME":
                        handleFrame(parts[1]);
                        break;
                    case "END_RECORDING":
                        handleEndRecording();
                        break;
                    case "GET_RECORDING":
                        handleGetRecording(Integer.parseInt(parts[1]));
                        break;
                    case "UPDATE_PATIENT":
                        handleUpdatePatient(parts[1]);
                        break;
                    default:
                        out.println("ERROR|Unknown command");
                        break;
                }
            }
        } catch(IOException e){
            System.out.println("ERROR Connecting" + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        }
    }

private void savePatientRegistration(String p){

        try{
            String [] parts = p.split(";");
           String name = parts[0];
           String surname = parts[1];
           Date dob = Date.valueOf(parts[2]);
           String email = parts[3];
           int phone = Integer.parseInt(parts[4]);
           String medicalHistory = parts[5];
           Patient.Sex sex = Patient.Sex.valueOf(parts[6].trim().toUpperCase());
           String password = parts[7];

            Patient patient = new Patient(name, surname, dob, email, sex, medicalHistory,phone,password);

            patientManager.addPatient(patient);

            out.println("PATIENT_SAVED");
            System.out.println(" Patient saved correctly");

        }catch(Exception e){
            System.out.println("ERROR saving patient" + e.getMessage());
        }
    }

    private void handleLogIn(String data){
        try {
            String[] parts = data.split(";");
            String email = parts[0];
            String password = parts[1];
            Patient patient = patientManager.getPatientByEmail(email);

            /*if (patient == null) {
                out.println("ERROR|NO_SUCH_EMAIL");
                return;
            }*/
            if (patientManager.checkPassword(email, password)) {
                out.println("OK|LOGIN_SUCCESS");
            } else {
                out.println("ERROR|WRONG_PASSWORD");
            }
        }catch(Exception e){
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("Exception in LOGIN: " + e.getMessage());
        }
    }

    private void handleChangePassword(String data){
        try {
            String[] parts = data.split(";");
            String email = parts[0];
            String oldPassword = parts[1];
            String newPassword = parts[2];

            Patient patient = patientManager.getPatientByEmail(email);

            //Comprobar contraseña antigua:
            if (!patient.getPassword().equals(oldPassword)) {
                out.println("ERROR|WRONG_OLD_PASSWORD");
                return;
            }

            patientManager.updatePassword(patient.getPatient_id(), newPassword);
            out.println("OK|PASSWORD_CHANGED");

        }catch(Exception e){
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("Exception in CHANGE_PASSWORD: " + e.getMessage());
        }
    }

    private void handleChangeEmail(String data){
        try {
            String[] parts = data.split(";");
            String email = parts[0];
            String newEmail = parts[1];

            Patient patient = patientManager.getPatientByEmail(email);

            if (patient == null) {
                out.println("ERROR|NO_SUCH_EMAIL");
                return;
            }
            patientManager.updateEmail(patient.getPatient_id(), newEmail);
            out.println("OK|EMAIL_CHANGED");
        }catch (Exception e) {
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("Exception in CHANGE_EMAIL: " + e.getMessage());
        }
    }

    private void deletePatient(String email){
            try {
                boolean deleted = patientManager.deletePatient(email);
                if (deleted) {
                    out.println("OK|PATIENT_DELETED");
                    System.out.println("Patient deleted correctly");
                } else {
                    out.println("ERROR|PATIENT_NOT_FOUND");
                }
            }catch (Exception e){
                out.println("ERROR|EXCEPTION");
                System.out.println("ERROR deleting patient: " + e.getMessage());
            }
    }


    private void handleStartRecording(String data){
        String[] parts = data.split(";");
        int patient_id = Integer.parseInt(parts[0]);
        String type = parts[1].toUpperCase();
        Recording.Type typeEnum = Recording.Type.valueOf(type);

        switch (type){
            case "EMG":
                activeChannels = new int[]{0};
                break;
            case "ECG":
                activeChannels = new int[]{1};
                break;
            case "BOTH":
                activeChannels = new int[]{0,1};
                break;
            default:
                out.println("ERROR|NO_SUCH_TYPE");
                return;
        }

        Recording recording = new Recording(LocalDate.now(), typeEnum, patient_id);
        recordingManager.addRecording(recording);
        currentRecording_id = recording.getId();
        frameCounter = 0;
        recordingActive = true;
        out.println("OK|RECORDING_STARTED|" +currentRecording_id);
        System.out.println("Recording started with ID: " + currentRecording_id +
                " | type=" + type);
    }

    private void handleFrame(String data) {

        if (!recordingActive) return;

        String[] p = data.split(";");
        int seq = Integer.parseInt(p[0]);
        // Leer analógicos según el número de canales activos (1 o 2)
        int[] analogFull = new int[6];
        for (int i = 0; i < activeChannels.length; i++) {
            int bitalinoChannel = activeChannels[i];
            int value = Integer.parseInt(p[1+i]);

            analogFull[bitalinoChannel] = value;
        }
        int[] digital = new int[4];
        // digitalStart indica el primer valor digital
        int digitalStart = 1 + activeChannels.length; // Indica donde empiezan los digitales
        for (int i = 0; i < 4; i++) {
            digital[i] = Integer.parseInt(p[digitalStart + i]);
        }

        framesManager.addFrame(currentRecording_id, frameCounter++, 0, seq, analogFull, digital);
    }


    private void handleEndRecording() {
        recordingActive = false;
        out.println("OK|RECORDING_SAVED");
        System.out.println("Recording finished. Total frames: " + frameCounter);
    }

    private void handleGetRecording(int recording_id){
        try{
            Recording recording = recordingManager.getRecordingById(recording_id);
            if (recording == null){
                out.println("ERROR|NO_DATA");
                return;
            }
            Recording.Type type = recording.getType();
            List<int[]> frames = framesManager.getFramesByRecording(recording_id);
            if (frames == null || frames.isEmpty()){
                out.println("ERROR|NO_DATA");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int[] frame : frames) {
                if(type == Recording.Type.ECG){
                    int ecg = frame[3];
                    sb.append(ecg);
                }else if (type == Recording.Type.EMG){
                    int emg = frame[2];
                    sb.append(emg);
                }else if (type == Recording.Type.BOTH){
                    int emg = frame[2];
                    int ecg = frame[3];
                    sb.append(emg).append(";").append(ecg);
                }  sb.append(",");
            }
            System.out.println("Sent recording Id "+ recording_id + " type:"+type);

            if(sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }
            out.println(("RECORDING_DATA|" + recording_id + "|" + sb.toString()));
        }catch(Exception e){
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR in get recording: " + e.getMessage());
        }

    }
    private void handleUpdatePatient(String p){
        try{
            String [] parts = p.split(";");
            String email = parts[0];
            String message = parts[1].toLowerCase();
            String value = parts[2];

            //Search the patient in the database given its email
            Patient patient = patientManager.getPatientByEmail(email);

            //Check if the patient exists in the database
            if(patient == null){
                out.println("ERROR|PATIENT_NOT_FOUND");
                return;
            }
            //If the patient exists, we search for its id
            int patient_id = patient.getPatient_id();

            //Now the patient decides which field it wants to change
            switch(message){
                case "name":
                    patientManager.updateName(patient_id, value);
                    break;
                case "surname":
                    patientManager.updateSurName(patient_id, value);
                    break;
                case "phone":
                    patientManager.updatePhone(patient_id, Integer.parseInt(value));
                    break;
                case "medical_history":
                    patientManager.updateMedicalHistory(patient_id, value);
                    break;
                case "dob":
                    patientManager.updateDob(patient_id, Date.valueOf(value));
                    break;
                case "sex":
                    patientManager.updateSex(patient_id, Patient.Sex.valueOf(value.toUpperCase()));
                    break;

                default:
                    out.println("ERROR|UNKNOWN_FIELD");
                    return;
            }
            out.println("OK|PATIENT_UPDATED");

        }
        catch(Exception e){
            System.out.println("ERROR|EXCEPTION " + e.getMessage());

        }
    }



}



