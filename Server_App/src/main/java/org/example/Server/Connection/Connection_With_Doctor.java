package org.example.Server.Connection;

import org.example.POJOS.Doctor;
import org.example.POJOS.Patient;
import org.example.Server.JDBC.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection_With_Doctor implements Runnable{

    private final Socket socket;
    private JDBCManager db;
    private BufferedReader in;
    private PrintWriter out;
    private JDBCDoctorManager doctorManager;
    private JDBCRecordingManager recordingManager;
    private JDBCRecordingFramesManager framesManager;
    private JDBCPatientManager patientManager;
    private Doctor loggedDoctor = null;

    public Connection_With_Doctor(Socket socket) {
        this.socket = socket;
        this.db = new JDBCManager() ;
    }

    @Override
    public void run() {

        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Server connected");

            patientManager = new JDBCPatientManager(db);
            doctorManager = new JDBCDoctorManager(db);
            recordingManager = new JDBCRecordingManager(db);
            framesManager = new JDBCRecordingFramesManager(db);

            String message;
            while ((message=in.readLine()) != null){
                System.out.println("Received message: " + message);

                String[] parts = message.split("\\|");
                String command = parts[0];

                switch(command) {
                    case "ADD_DOCTOR":
                        saveDoctorRegistration(parts[1]);
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
                        deleteDoctor();
                        break;
                    case "VIEW_ALL_PATIENTS":
                        viewAllPatients(parts[1]);
                        break;
                    case "VIEW_PATIENT_DATA":
                        viewPatientData(parts[1], parts[2]);
                        break;
                    case "UPDATE_PATIENT_HISTORY":
                        updatePatientHistory(parts[1], parts[2], parts[3]);
                        break;
                    default:
                        out.println("ERROR|Unknown command");
                        break;
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        } finally{
            releaseResourcesServer(out,in,socket);
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
            //List<Patient> patients = patientManager.getListOfPatientsOfDoctor();

            Doctor doctor = new Doctor(name, surname, phone, email, password);

            doctorManager.addDoctor(doctor);

            out.println("DOCTOR_SAVED");
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
            Doctor doctor = doctorManager.searchDoctorByEmail(email);

            if (doctor == null) {
                out.println("ERROR|NO_SUCH_EMAIL");
                return;
            }

            if (doctorManager.checkPassword(email, password)) {

                loggedDoctor = doctor;
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
            if (loggedDoctor == null) {
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }
            String[] parts = data.split(";");

            String oldPassword = parts[0];
            String newPassword = parts[1];

            //Comprobar contraseña antigua:
            if (!loggedDoctor.getPassword().equals(oldPassword)) {
                out.println("ERROR|WRONG_OLD_PASSWORD");
                return;
            }
            doctorManager.updatePassword(loggedDoctor.getDoctor_id(), newPassword);
            loggedDoctor.setPassword(newPassword);
            out.println("OK|PASSWORD_CHANGED");

        }catch(Exception e){
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("Exception in CHANGE_PASSWORD: " + e.getMessage());
        }
    }

    private void handleChangeEmail(String data){
        try {
            if (loggedDoctor == null) {
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }
            String[] parts = data.split(";");
            String newEmail = parts[1];


            doctorManager.updateEmail(loggedDoctor.getDoctor_id(), newEmail);
            loggedDoctor.setEmail(newEmail);
            out.println("OK|EMAIL_CHANGED");
        }catch (Exception e) {
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
            System.out.println("Exception in CHANGE_EMAIL: " + e.getMessage());
        }
    }

    private void deleteDoctor(){
        try {
            if (loggedDoctor == null) {
                out.println("ERROR|NOT_LOGGED_IN");
                return;
            }
            String email = loggedDoctor.getEmail();
            boolean deleted = doctorManager.deleteDoctor(email);
            if (deleted) {
                out.println("OK|DOCTOR_DELETED");
                System.out.println("Doctor deleted correctly");
                loggedDoctor = null; // La sesión se acaba porque el doctor ya no existe
            } else {
                out.println("ERROR|DOCTOR_NOT_FOUND");
            }
        }catch (Exception e){
            out.println("ERROR|EXCEPTION");
            System.out.println("ERROR deleting doctor: " + e.getMessage());
        }
    }

    private static void releaseResourcesServer(PrintWriter out, BufferedReader in, Socket socket){
        try {
            try{
                out.close();
            }catch(Exception ex){
                Logger.getLogger(Connection_With_Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
                in.close();
            }catch(Exception ex){
                Logger.getLogger(Connection_With_Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
            socket.close();
        }catch(IOException ex){
            Logger.getLogger(Connection_With_Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }






}
