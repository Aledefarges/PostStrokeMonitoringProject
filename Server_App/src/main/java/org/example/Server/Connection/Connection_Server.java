package org.example.Server.Connection;

import org.example.POJOS.Doctor;
import org.example.POJOS.Recording;
import org.example.Server.JDBC.*;
import org.example.POJOS.Patient;
import org.example.Server.Visualization.signalsAnalyzer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//

public class Connection_Server implements Runnable{

    private final Socket socket;
    private JDBCManager db;
    private JDBCPatientManager patientManager;
    private JDBCDoctorManager doctorManager;
    private JDBCRecordingManager recordingManager;
    private JDBCRecordingFramesManager framesManager;
    private BufferedReader in;
    private PrintWriter out;
    private boolean recordingActive = false; // Indicates if the server is receiving a recording
    private int currentRecording_id = -1; // The id will change depending on the recording
    private int frameCounter = 0; // Number of frames received by each recording
    private int [] activeChannels;
    private Patient loggedPatient = null;
    private Doctor loggedDoctor = null;
    private UserType userType = null;

    public Connection_Server(Socket socket) {
        this.socket = socket;
        this.db = new JDBCManager() ;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Server connected");

            patientManager = new JDBCPatientManager(db);
            doctorManager = new JDBCDoctorManager(db);
            recordingManager = new JDBCRecordingManager(db);
            framesManager = new JDBCRecordingFramesManager(db);

            String message;
            while ((message=in.readLine()) != null) {
                System.out.println("Received message: " + message);

                String[] parts = message.split("\\|");
                String command = parts[0];

                switch (command) {
                    case "ADD_PATIENT": savePatientRegistration(parts[1]); //Funcion ya creada por nerea, se puede cambiar el nombre a handleAddPatients
                        break;
                    case "ADD_DOCTOR": saveDoctorRegistration(parts[1]);
                        break;
                    case "LOGIN": handleLogIn(parts[1]);
                        break;
                    case "CHANGE_PASSWORD": handleChangePassword(parts[1]);
                        break;
                    case "CHANGE_EMAIL": handleChangeEmail(parts[1]);
                        break;
                    case "DELETE_ACCOUNT": delete();
                        break;
                    case "START_RECORDING": handleStartRecording(parts[1]);
                        break;
                    case "FRAME": handleFrame(parts[1]);
                        break;
                    case "END_RECORDING": handleEndRecording();
                        break;
                    case "GET_RECORDING": handleGetRecording(Integer.parseInt(parts[1]));
                        break;
                    case "UPDATE_PATIENT": handleUpdatePatient(parts[1]);
                        break;
                    case "VIEW_ALL_DOCTORS": getListOfDoctors();
                        break;
                    case "VIEW_ALL_PATIENTS": getListOfPatients();
                        break;
                    case "UPDATE_PATIENT_HISTORY": {
                        String load = parts[1];
                        String[] info = load.split(";",2);
                        updatePatientHistory(info[1], info[2]);
                        break;

                    }
                    case "DELETE_RECORDING": handleDeleteRecording(Integer.parseInt(parts[1]));
                        break;
                    case "VIEW_RECORDINGS_BY_PATIENT": getListRecording(Integer.parseInt(parts[1]));
                        break;
                    default:
                        out.println("ERROR|Unknown command");
                        break;
                }
            }

            if(recordingActive){
                System.out.println("Client disconnected abruptly");
                recordingActive = false;
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            releaseResourcesServer(out,in,socket);
            org.example.Server.Connection.Server.removeConnection(this);
            releaseResourcesServer(out,in,socket);
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
           int doctorId = Integer.parseInt(parts[8]);

            Patient patient = new Patient(name, surname, dob, email, sex, medicalHistory,phone,password);

            patientManager.addPatient(patient);

            int patientId = patient.getPatient_id();
            doctorManager.assingDoctorToPatient(patientId, doctorId);

            out.println("PATIENT_SAVED");
            System.out.println(" Patient saved correctly");

        }catch(Exception e){
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR saving patient" + e.getMessage());
        }
    }

    private void saveDoctorRegistration(String d){

        try{
            String [] parts = d.split(";");
            String name = parts[0];
            String surname = parts[1];
            int phone = Integer.parseInt(parts[2]);
            String email = parts[3];
            String password = parts[4];

            Doctor doctor = new Doctor(name, surname, phone, email, password);
            doctorManager.addDoctor(doctor);
            out.println("OK|DOCTOR_SAVED");
            System.out.println(" Doctor saved correctly");

        }catch(Exception e){
            System.out.println("ERROR saving doctor" + e.getMessage());
        }
    }

    private void handleLogIn(String data){
        try {
            String[] parts = data.split(";");
            String email = parts[0];
            String password = parts[1];

            Patient patient = patientManager.getPatientByEmail(email);
            if (patient != null){
                if(patientManager.checkPassword(email, password)){
                    loggedPatient = patient;
                    loggedDoctor = null;
                    userType = UserType.PATIENT;
                    //loggedPatient.setPassword(password);
                    out.println("OK|LOGIN_SUCCESS_PATIENT");
                }else{
                    out.println("ERROR|WRONG_PASSWORD");
                }
                return;
            }

            Doctor doctor = doctorManager.getDoctorByEmail(email);
            if (doctor != null){
                if(doctorManager.checkPassword(email, password)){
                    loggedDoctor = doctor;
                    loggedPatient = null;
                    userType = UserType.DOCTOR;
                    //loggedDoctor.setPassword(password);
                    out.println("OK|LOGIN_SUCCESS_DOCTOR");
                }else{
                out.println("ERROR|WRONG_PASSWORD");
                }
                return;
            }

            out.println("ERROR|NO_SUCH_EMAIL");

        }catch(Exception e){
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("Exception in LOGIN: " + e.getMessage());
        }
    }

    private void handleChangePassword(String data){
        try {
            if (userType == null) {
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }
            String[] parts = data.split(";");
            String oldPassword = parts[0];
            String newPassword = parts[1];

            switch(userType){
                case PATIENT:
                    if (loggedPatient == null){
                        out.println("ERROR|NOT_LOGGED_IN");
                        return;
                    }
                    String storedPassword = loggedPatient.getPassword();
                    if (!storedPassword.equals(oldPassword)) {
                        out.println("ERROR|WRONG_OLD_PASSWORD");
                        return;
                    }
                    patientManager.updatePassword(loggedPatient.getPatient_id(), newPassword);
                    loggedPatient.setPassword(newPassword);
                    out.println("OK|PASSWORD_CHANGED");
                    break;

                case DOCTOR:
                    if (loggedDoctor == null){
                        out.println("ERROR|NOT_LOGGED_IN");
                        return;
                    }
                    String storePassword = loggedDoctor.getPassword();
                    if (!storePassword.equals(oldPassword)) {
                        out.println("ERROR|WRONG_OLD_PASSWORD");
                        return;
                    }
                    doctorManager.updatePassword(loggedDoctor.getDoctor_id(), newPassword);
                    loggedDoctor.setPassword(newPassword);
                    out.println("OK|PASSWORD_CHANGED");
                    break;

            }

        }catch(Exception e){
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("Exception in CHANGE_PASSWORD: " + e.getMessage());
        }
    }

    private void handleChangeEmail(String data){
        try {
            if (userType == null) {
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }
            String[] parts = data.split(";");
            String newEmail = parts[1];
            switch(userType){
                case PATIENT:
                    if (loggedPatient == null){
                        out.println("ERROR|NOT_LOGGED_IN");
                        return;
                    }
                    patientManager.updateEmail(loggedPatient.getPatient_id(), newEmail);
                    loggedPatient.setEmail(newEmail);
                    out.println("OK|EMAIL_CHANGED");
                    break;
                case DOCTOR:
                    if (loggedDoctor == null){
                        out.println("ERROR|NOT_LOGGED_IN");
                        return;
                    }
                    doctorManager.updateEmail(loggedDoctor.getDoctor_id(), newEmail);
                    loggedDoctor.setEmail(newEmail);
                    out.println("OK|EMAIL_CHANGED");
                    break;
            }

        }catch (Exception e) {
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("Exception in CHANGE_EMAIL: " + e.getMessage());
        }
    }

    private void delete(){
            try {
                if (userType == null) {
                    out.println("ERROR|NOT_LOGGED_IN");
                    return;
                }
                switch(userType) {
                    case PATIENT:
                        if(loggedPatient == null){
                            out.println("ERROR|NOT_LOGGED_IN");
                            return;
                        }
                        if(patientManager.deletePatient(loggedPatient.getEmail())){
                            out.println("OK|PATIENT_DELETED");
                            loggedPatient = null;
                            userType = null;
                        }else{
                            out.println("ERROR|PATIENT_NOT_FOUND");
                        }
                        break;
                }

            }catch (Exception e){
                out.println("ERROR|EXCEPTION");
                System.out.println("ERROR deleting patient: " + e.getMessage());
            }
    }


    private void handleStartRecording(String data){
        String[] parts = data.split(";");
        String type = parts[0].toUpperCase();
        Recording.Type typeEnum = Recording.Type.valueOf(type);
        if (loggedPatient == null) {
            out.println("ERROR|NOT_LOGGED_IN");
            return;
        }
        int patient_id = loggedPatient.getPatient_id();
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

        Recording recording = new Recording(LocalDateTime.now(), typeEnum, patient_id);
        recordingManager.addRecording(recording);
        currentRecording_id = recording.getId();
        frameCounter = 0;
        recordingActive = true;
        out.println("OK|RECORDING_STARTED|" +currentRecording_id);
        System.out.println("Recording started with ID: " + currentRecording_id +
                " | type=" + type);
    }

    private void handleFrame(String data) {

        if (!recordingActive) {
            System.err.println("ERROR|NOT_RECORDING_ACTIVE");
            return;
        }
        if(currentRecording_id <= 0){
            System.err.println("ERROR|NO_SUCH_RECORDING, recording id: "+ currentRecording_id);
            return;
        }

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
                    int ecg = frame[2];
                    sb.append(ecg);
                }else if (type == Recording.Type.EMG){
                    int emg = frame[1];
                    sb.append(emg);
                }else if (type == Recording.Type.BOTH){
                    int emg = frame[1];
                    int ecg = frame[2];
                    sb.append(emg).append(";").append(ecg);
                }  sb.append(",");
            }
            System.out.println("Sent recording Id "+ recording_id + " type:"+type);

            if(sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }

            if(userType == UserType.DOCTOR && (type == Recording.Type.ECG || type == Recording.Type.BOTH)){
                String diagnosis = signalsAnalyzer.analyzeECGFromFrames(frames, 100.0);
                out.println("RECORDING_DATA|" + recording_id + "|" + sb.toString() + "|" + diagnosis);
            }
            else{
                out.println(("RECORDING_DATA|" + recording_id + "|" + sb.toString()));
            }
        }catch(Exception e){
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR in get recording: " + e.getMessage());
        }

    }
    private void handleUpdatePatient(String p){
        try{
            if (loggedPatient == null){
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }
            String [] parts = p.split(";");
            String message = parts[0].toLowerCase();
            String value = parts[1];

            int patient_id = loggedPatient.getPatient_id();

            //Now the patient decides which field it wants to change
            switch(message){
                case "name":
                    patientManager.updateName(patient_id, value);
                    loggedPatient.setName(value);
                    break;
                case "surname":
                    patientManager.updateSurName(patient_id, value);
                    loggedPatient.setSurname(value);
                    break;
                case "phone":
                    patientManager.updatePhone(patient_id, Integer.parseInt(value));
                    loggedPatient.setPhone(Integer.parseInt(value));
                    break;
                case "medical_history":
                    patientManager.updateMedicalHistory(patient_id, value);
                    loggedPatient.setMedicalhistory(value);
                    break;
                case "dob":
                    patientManager.updateDob(patient_id, Date.valueOf(value));
                    loggedPatient.setDob(Date.valueOf(value));
                    break;
                case "sex":
                    patientManager.updateSex(patient_id, Patient.Sex.valueOf(value.toUpperCase()));
                    loggedPatient.setSex(Patient.Sex.valueOf(value.toUpperCase()));
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


    private void getListOfDoctors(){

        try{
            List<Doctor> doctors = doctorManager.getAllDoctors();
            if(doctors.isEmpty()){
                out.println("DOCTORS_LIST|EMPTY");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("DOCTORS_LIST|");

            for(Doctor d: doctors){
                sb.append(d.getDoctor_id()).append(";")
                        .append(d.getName()).append(";")
                        .append(d.getSurname()).append("|");
            }
            sb.deleteCharAt(sb.length() - 1); //eliminar el último "|"
            out.println(sb.toString());
            System.out.println("List of doctors to patient");
        }catch(Exception e){
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR in get list of doctors: " + e.getMessage());
        }
    }
    private void getListOfPatients(){
        try {
            if (loggedDoctor == null) {
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }

            List<Patient> patients = patientManager.getListOfPatientsOfDoctor(loggedDoctor.getDoctor_id());
            if(patients.isEmpty()){
                out.println("PATIENTS_LIST|EMPTY");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("PATIENTS_LIST|");
            for (Patient p : patients) {
                sb.append(p.getPatient_id()).append(";")
                        .append(p.getName()).append(";")
                        .append(p.getSurname()).append(";")
                        .append(p.getDob()).append(";")
                        .append(p.getEmail()).append(";")
                        .append(p.getPhone()).append(";")
                        .append(p.getMedicalhistory()).append(";")
                        .append(p.getSex().name()).append("|");
            }

            sb.deleteCharAt(sb.length() - 1); //eliminar el último "|"
            out.println(sb.toString());

            System.out.println("Sent list of patients to doctor: " + loggedDoctor.getEmail());

        } catch (Exception e) {
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR viewing all patients: " + e.getMessage());
        }

    }


    private void updatePatientHistory(String patient_email, String new_history){
        try{
            if (loggedDoctor == null) {
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }

            Patient patient = patientManager.getPatientByEmail(patient_email);
            if (patient == null) {
                out.println("ERROR|PATIENT_NOT_FOUND");
                return;
            }
            patientManager.updateMedicalHistory(patient.getPatient_id(), new_history);
            out.println("OK|MEDICAL_HISTORY_UPDATED");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR updating patient history: " + e.getMessage());
        }

    }


    private static void releaseResourcesServer(PrintWriter out, BufferedReader in, Socket socket){
        try {
            try{
                out.close();
            }catch(Exception ex){
                Logger.getLogger(Connection_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
                in.close();
            }catch(Exception ex){
                Logger.getLogger(Connection_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            socket.close();
        }catch(IOException ex){
            Logger.getLogger(Connection_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleDeleteRecording(int recording_id){
        try{
            if(loggedDoctor == null){
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }

            // verifica que el recording existe
            Recording rec = recordingManager.getRecordingById(recording_id);
            if(rec == null){
                out.println("ERROR|RECORDING_NOT_FOUND");
                return;
            }

            boolean recordingDeleted = recordingManager.deleteRecording(recording_id);

            if(recordingDeleted){
                out.println("OK|RECORDING_DELETED");
                System.out.println("Recording " + recording_id + " deleted by doctor.");
            }else{
                out.println("ERROR|DELETE_FAILED");
            }
        }catch(Exception e){
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR deleting recording: " + e.getMessage());
        }
    }

    private void getListRecording(int patient_id){
        try{
            List<Recording> recordings = recordingManager.getRecordingsByPatient(patient_id);
            if(recordings.isEmpty()){
                out.println("RECORDINGS_LIST|EMPTY");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("RECORDINGS_LIST|");
            for (Recording recording : recordings) {
                sb.append(recording.getId()).append(";")
                        .append(recording.getType()).append(";")
                        .append(recording.getDateRecording()).append("|");
            }
            sb.deleteCharAt(sb.length() - 1);
            out.println(sb.toString());
            System.out.println("Sent list of recordings to doctor: " + loggedDoctor.getEmail());
        } catch (Exception e) {
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR viewing recordings: " + e.getMessage());
        }

    }

    public void sendShutdownMessage(){
        if(out!= null){
            out.println("SERVER SHUTDOWN");
        }
    }

    public void close() {
        try {
            if (out != null) out.close();  // Stop sending data to server
            if (in != null) in.close();   // Stop receiving data to server
            if (socket != null) socket.close(); // close connection
        } catch (IOException e) {}
    }


    enum UserType{PATIENT, DOCTOR}
}



