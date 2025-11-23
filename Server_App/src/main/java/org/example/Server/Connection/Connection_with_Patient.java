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



            if (message.startsWith("PATIENT|")) {
                savePatientRegistration(message.substring(8), writer_out);
            } else {
                writer_out.println("UNKNOWN_COMMAND");
            }

        } catch (IOException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    private static void savePatientRegistration(String p, PrintWriter out){

        try{
            String [] parts = p.split(";");
            int patient_id = Integer.parseInt(parts[0]);
           String name = parts[1];
           String surname = parts[2];
           Date dob = Date.valueOf(parts[3]);
           String email = parts[4];
           int phone = Integer.parseInt(parts[5]);
           String medicalHistory = parts[6];
           Patient.Sex sex = Patient.Sex.valueOf(parts[7].trim().toUpperCase());
           String password = parts[8];

            Patient patient = new Patient(patient_id, name, surname, dob, email, phone, medicalHistory, sex, password);

            patientManager.addPatient(patient);

            out.println("PATIENT_SAVED");
            System.out.println("✔ Patient saved correctly");

        }catch(Exception e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

}
