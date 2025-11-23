package org.example.Server.Connection;

import org.example.Server.JDBC.JDBCManager;
import org.example.Server.JDBC.JDBCPatientManager;
import org.example.POJOS.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.Scanner;

public class Connection_with_Patient {
    private static JDBCPatientManager patientManager;
    public static void main(String [] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(9000);
            Socket socket = serverSocket.accept();
            System.out.println("Patient connected");

            BufferedReader read_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer_out = new PrintWriter(socket.getOutputStream(), true);

            // *** IMPORTANTÍSIMO ***
            writer_out.println("SERVER: Connected");  // EL PACIENTE YA PUEDE LEERLO

            JDBCManager db = new JDBCManager();
            patientManager = new JDBCPatientManager(db);

            String message = read_in.readLine();

            //Chat me ha dicho que añadamos esto para tenerlo ordenado y añadir lo de Utilities:
            while(message!=null) {
                System.out.println("Received: " + message);

                String[] parts = message.split("\\|");
                String command = parts[0];

                switch (command) {
                    case "ADD_PATIENT":
                        savePatientRegistration(parts[1], writer_out); //Funcion ya creada por nerea, se puede cambiar el nombre a handleAddPatients
                        break;
                    case "LOGIN":
                        handleLogIn(parts[1], writer_out);
                    case "CHANGE_PASSWORD":
                        handleChangePassword(parts[1], writer_out);
                    case "CHANGE_EMAIL":
                        handleChangeEmail(parts[1], writer_out);
                    default:
                        writer_out.println("ERROR|Unknown command");
                        break;
                }
            }

            /*
            if (message.startsWith("PATIENT|")) {
                savePatientRegistration(message.substring(8), writer_out);
            }  else if (message.startsWith("DELETE_PATIENT|")){
                String email = message.substring("DELETE PATIENT|".length());
                deletePatient(email, writer_out);
            } else {
                writer_out.println("UNKNOWN_COMMAND");
            }*/

        } catch (IOException e){
            System.out.println("ERROR " + e.getMessage());
        }


    }

    private static void savePatientRegistration(String p, PrintWriter out){

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

            Patient patient = new Patient(name, surname, dob, email, phone, medicalHistory, sex, password);

            patientManager.addPatient(patient);

            out.println("PATIENT_SAVED");
            System.out.println(" Patient saved correctly");

        }catch(Exception e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    private static void deletePatient(String email, PrintWriter out){
        try {
            boolean deleted = patientManager.deletePatient(email);
            if (deleted) {
                out.println("PATIENT_DELETED");
                System.out.println("Patient deleted correctly");
            } else {
                out.println("PATIENT_NOT_FOUND");
            }
        }catch (Exception e){
            out.println("ERROR");
            System.out.println("ERROR deleteing patient: " + e.getMessage());
        }
    }

    //Funciones que teníamos en Utilities:

    private static void handleLogIn(String data, PrintWriter out){
        try {
            String[] parts = data.split(";");
            String email = parts[0];
            String password = parts[1];

            Patient patient = patientManager.getPatientByEmail(email);

            if (patient == null) {
                out.println("ERROR|NO_SUCH_EMAIL");
                return;
            }

            if (patient.getPassword().equals(password)) {
                out.println("OK|LOGIN_SUCCESS");

            } else {
                out.println("ERROR|WRONG_PASSWORD");
            }
        }catch(Exception e){
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
        }
    }

    private static void handleChangePassword(String data, PrintWriter out){
        try {
            String[] parts = data.split(";");
            String email = parts[0];
            String newPassword = parts[1];

            Patient patient = patientManager.getPatientByEmail(email);

            if (patient == null) {
                out.println("ERROR|NO_SUCH_EMAIL");
                return;
            }

            patientManager.updatePassword(patient.getPatient_id(), newPassword);
            out.println("OK|PASSWORD_CHANGED");

        }catch(Exception e){
            e.printStackTrace();
            out.println("ERROR|EXCEPTION");
        }
    }

    private static void handleChangeEmail(String data, PrintWriter out){
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
        }
    }

}



