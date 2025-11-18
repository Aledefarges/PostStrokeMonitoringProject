package org.example.Server.Connection;

import org.example.Server.JDBC.JDBCManager;
import org.example.Server.JDBC.JDBCPatientManager;
import org.example.Server.POJOS.Patient;

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
            Socket socket = serverSocket.accept();   // Waiting for one patient to connect
            System.out.println("Patient connected");

            BufferedReader read_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer_out = new PrintWriter(socket.getOutputStream(), true);

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
           String name = parts[0];
           String surname = parts[1];
           Date dob = Date.valueOf(parts[2]);
           String email = parts[3];
           int phone = Integer.parseInt(parts[4]);
           String medicalHistory = parts[5];
            Patient.Sex sex = Patient.Sex.valueOf(parts[6].toUpperCase());

            Patient patient = new Patient(name, surname, dob, email, phone, medicalHistory, sex);

            patientManager.addPatient(patient);

            out.println("PATIENT_SAVED");
            System.out.println("✔ Patient saved correctly");

        }catch(Exception e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

}
