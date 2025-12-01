package Connection;

import org.example.POJOS.Doctor;
import org.example.POJOS.Patient;
import org.example.POJOS.Recording;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.sql.Date;

public class Connection_Doctor {


    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    boolean loggedIn = false;


    public boolean connection(String ip, int port) {
        try {
            socket = new Socket(ip, port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String response = readLineHandlingListener();
            System.out.println("SERVER:  " + response);
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

    public boolean sendRegisterDoctor(Doctor doctor) {
        try{
            String message =
                    "ADD_DOCTOR|" +
                            doctor.getName() + ";" +
                            doctor.getSurname() + ";" +
                            doctor.getPhone() + ";" +
                            doctor.getEmail() + ";" +
                            doctor.getPassword();

            out.println(message);
            String response = readLineHandlingListener();
            return response.equals("OK|DOCTOR_SAVED");


        }catch(IOException e){
            return false;
        }
    }


    public String sendDoctorLogin(String email, String password) throws IOException {
        // Enviar comando
        out.println("LOGIN|" + email + ";" + password);
        // Leer respuesta
        String response = readLineHandlingListener();
        return response;
    }


    public List<Patient> requestAllPatients(){
        try{
            out.println("VIEW_ALL_PATIENTS|");
            out.flush();

            String response = readLineHandlingListener();
            if(response == null){
                System.out.println("ERROR: returned list error");
                return null;
            }

            if(response.equals("PATIENTS_LIST|EMPTY")){
                return new ArrayList<>();
            }
            if(!response.startsWith("PATIENTS_LIST|")){
                System.out.println("Error" + response);
                return null;
            }

                String data = response.substring("PATIENTS_LIST|".length());
                String[] parts = data.split("\\|");
                List<Patient> patient_list = new ArrayList<>();
                for(String part:parts){
                    String[] patient = part.split(";");
                    int patient_id = Integer.parseInt(patient[0]);
                    String name = patient[1];
                    String surname = patient[2];
                    String dob = patient[3];
                    Date dob1 = Date.valueOf(dob);
                    String email = patient[4];
                    int phone = Integer.parseInt(patient[5]);
                    String medical_history = patient[6];
                    String sex = patient[7];
                    Patient.Sex sexEnum = Patient.Sex.valueOf(sex);
                    String feedback = patient[8];

                    Patient patients = new Patient(patient_id, "", name, surname, dob1, email, phone,
                            medical_history, sexEnum, feedback);
                    patient_list.add(patients);
                }
                return patient_list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Recording> requestRecordingsByPatient(int patient_id){
            try{
                out.println("VIEW_RECORDINGS_BY_PATIENT|" + patient_id);
                out.flush();

                String response = readLineHandlingListener();
                if(response == null){
                    System.out.println("ERROR: returned list error");
                    return null;
                }

                if(response.equals("RECORDINGS_LIST|EMPTY")){
                    return new ArrayList<>();
                }
                if(!response.startsWith("RECORDINGS_LIST|")){
                    System.out.println("Error" + response);
                    return null;
                }

                String data = response.substring("RECORDINGS_LIST|".length());
                String[] parts = data.split("\\|");
                List<Recording> recording_list = new ArrayList<>();
                for(String part:parts){
                        String[] recording = part.split(";");
                        int recording_id = Integer.parseInt(recording[0]);
                        String type = recording[1];
                        Recording.Type typeEnum = Recording.Type.valueOf(type);
                        String dateRecording = recording[2];
                        LocalDateTime dt =  LocalDateTime.parse(dateRecording);

                        Recording recordings = new Recording(dt, typeEnum, patient_id);
                        recordings.setId(recording_id);
                        recording_list.add(recordings);
                }
                return recording_list;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
    }

    public String requestSpecificRecording(int recording_id){
        try{
            out.println("GET_RECORDING|" + recording_id);
            String response = readLineHandlingListener();
            return response;
        }catch(IOException e){
            return null;
        }
    }


    public boolean sendFeedback(String email, String feedback){
        try{
            out.println("ADD_FEEDBACK|" + email + ";" + feedback);
            String response = readLineHandlingListener();
            return response.equals("OK|FEEDBACK_SAVED");
        }catch(IOException e){
            return false;
        }
    }

    public boolean sendChangeEmail(String email, String newEmail){
        try{
            out.println("CHANGE_EMAIL|" + email + ";" + newEmail);
            String response = readLineHandlingListener();
            return response.equals("OK|EMAIL_CHANGED");

        }catch(IOException e){
            return false;
        }
    }


    public boolean sendChangePassword(String oldPassword, String newPassword){
        try{
            out.println("CHANGE_PASSWORD|" + oldPassword + ";" + newPassword);
            String response = readLineHandlingListener();
            return response.equals("OK|PASSWORD_CHANGED");
        }catch(IOException e){
            return false;
        }
    }

    public static String encryptDoctorPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b: digest){
                sb.append(String.format("%02x", b));

            }
            return sb.toString();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String readLineHandlingListener()throws IOException{
        String response = in.readLine();

        if (response == null || response.equals("SERVER SHUTDOWN")) {
            close();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        null,
                        "The server has been shut down. The application will close.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                for(Window window : Window.getWindows()){
                    window.dispose();
                }
                System.exit(0);
            });
        }
        return response;
    }

    public Socket getSocket(){return socket;}
    public PrintWriter getPrintWriter(){return out;}
    public BufferedReader getBufferedReader(){return in;}
    public void setSocket(Socket s){ this.socket = s;}
    public void setPrintWriter(PrintWriter pw_out){ this.out = pw_out;}
    public void setBufferedReader(BufferedReader br_in){ this.in = br_in;}


}
