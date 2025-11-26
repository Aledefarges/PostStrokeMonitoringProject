package org.example.Connection;

import org.example.POJOS.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Connection_With_Server {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    boolean loggedIn=false;


    public boolean connection(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("SERVER:  " + in.readLine());
            return true;
        } catch (IOException ex) {
            System.out.println("Error connecting to server: " + ex.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.out.println("ERROR closing connection: " + e.getMessage());
        }
    }

    public boolean sendRegisterDoctor(String name, String surname, int phone, List<Patient> patients , String email, String password) {
        try{
            String message = "REGISTER_DOCTOR|" +
                    name + ";" +
                    surname + ";" +
                    phone + ";" +
                    patients + ";" +
                    email + ";" +
                    password + ";";

            out.println(message);
            String response = in.readLine();
            return response.equals("OK|DOCTOR_REGISTERED");


        }catch(IOException e){
            return false;
        }
    }

    public boolean sendDoctorLogin(String email, String password) {
        try{
            out.println("LOGIN_DOCTOR|" + email + ";" + password);

            String response = in.readLine();
            if(response.equals("OK|DOCTOR_LOGGED_IN")) {
                loggedIn=true;
                return true;
            }
            return false;
        }catch(IOException e){
            return false;
        }
    }

    public void sendLogout(){
        if(!loggedIn) return;
        out.println("LOG_OUT_DOCTOR");
        loggedIn=false;
    }

    public String sendAllPatients(){
        try{
            out.println("GET_ALL_PATIENTS");
            String response = in.readLine();
            return response;
        }catch(IOException e){
            return null;
        }
    }

    public String sendPatientData(String email){
        try{
            out.println("GET_PATIENT_DATA|" + email);
            String response = in.readLine();
            return response;
        }catch(IOException e){
            return null;
        }
    }

    public boolean sendPatientHistory(String email, String newMedicalHistory){
        try{
            out.println("UPDATE_HISTORY|" + email + ";" + newMedicalHistory);
            String response = in.readLine();
            return response.equals("OK|HISTORY_UPDATED");
        }catch(IOException e){
            return false;
        }
    }

    public boolean sendChangeEmail(String oldEmail, String newEmail){
        try{
            out.println("CHANGE_DOCTOR_EMAIL|" + oldEmail + ";" + newEmail);
            String response = in.readLine();
            return response.equals("OK|EMAIL_CHANGED");

        }catch(IOException e){
            return false;
        }
    }

    public boolean sendChangePassword(String oldPassword, String newPassword){
        try{
            out.println("CHANGE_DOCTOR_PASSWORD|" + oldPassword + ";" + newPassword);
            String response = in.readLine();
            return response.equals("OK|PASSWORD_CHANGED");
        }catch(IOException e){
            return false;
        }
    }
}
