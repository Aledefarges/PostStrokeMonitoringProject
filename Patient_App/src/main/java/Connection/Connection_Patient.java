package Connection;

import Bitalino.Frame;
import org.example.POJOS.Exceptions;
import org.example.POJOS.Patient;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.sql.Date;

public class Connection_Patient {


    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Patient loggedPatient;

    public boolean connection(String ip_host, int port){
        // The function is boolean because it indicates whether the connection has succeeded or not
        try{
            socket = new Socket(ip_host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String response = readLineHandlingListener();  // Response of the server
            System.out.println("Server: " +response);
            return true;

        } catch (IOException e) {
            System.out.println("Connection ERROR: " + e.getMessage());
            return false;
        }
    }
    public void close() {
        try {
            if (out != null) out.close();  // Stop sending data to server
            if (in != null) in.close();   // Stop receiving data to server
            if (socket != null) socket.close(); // close connection
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }


    public boolean sendPatientToServer(Patient patient, int doctor_id ) {
        try {

            String message = "ADD_PATIENT|" +
                    patient.getName() + ";" +
                    patient.getSurname() + ";" +
                    patient.getDob().toString() + ";" +
                    patient.getEmail() + ";" +
                    patient.getPhone() + ";" +
                    patient.getMedicalhistory() + ";" +
                    patient.getSex().name() + ";" +
                    patient.getPassword() + ";" +
                    doctor_id;

                out.println(message);

                String response = readLineHandlingListener();
                return "PATIENT_SAVED".equals(response); // It confirms the connection with server

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

    }

    public String requestAllDoctor(){
        try{
            out.println("VIEW_ALL_DOCTORS|");
            String response = readLineHandlingListener();
            return response;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean deletePatientFromServer(){
        try{
            String message = "DELETE_ACCOUNT|";
            out.println(message);
            String response = readLineHandlingListener();
            if("OK|PATIENT_DELETED".equals(response)){
                if(out != null) out.println("LOGOUT|");
                return true;
            }
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;

        }
    }

    // Indicates that a new Recording needs to be created as a new row in the Database
    // Then it send to the server which channels are being used by BITalino
    public int[][] startRecording(String type){

        int [] channel;
        if (type.equals("EMG")){
            channel = new int[]{0};
        } else if (type.equals("ECG")){
            channel = new int[]{1};
        } else if (type.equals("BOTH")){
            channel = new int[]{0,1};
        } else{
            throw new IllegalArgumentException("Invalid type");
        }

        out.println("START_RECORDING|" + type);

        int recording_id = -1;
        try{
            String response = readLineHandlingListener();
            if(!response.startsWith("OK|RECORDING_STARTED")){
                System.out.println("Server error to start recording");
            }else{
                //Para obtener recording_id
                String[] parts = response.split("\\|");
                recording_id = Integer.parseInt(parts[2]);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return new int[][]{channel, new int[]{recording_id}};
    }



    // It sends each BITalino frame as a text to the server
    public void sendFrames(Frame[] frames, int[] channel){
        if(frames == null || frames.length == 0) return;
        int channelCount = channel.length;
        for (Frame f : frames) {
            StringBuilder msg = new StringBuilder("FRAME|");
            msg.append(f.seq);

            // send only active channels
            for (int i = 0; i < channelCount; i++) {
                msg.append(";").append(f.analog[i]);
            }
            //digital channels (always 4)
            for (int d = 0; d < 4; d++) {
                msg.append(";").append(f.digital[d]);
            }
            out.println(msg.toString());
            out.flush();
        }
    }

    public String endRecordingAndGetResponse() throws IOException {
        try{
            out.println("END_RECORDING");
            return readLineHandlingListener();
        }catch(Exception e){
            return "ERROR|EXCEPTION";
        }
    }


    public Double[][] requestRecordingData(int recording_id, String type) throws IOException{
        //Pedir al servidor el recording
        out.println("GET_RECORDING|" + recording_id);

        //Leer la respuesta
        String response = readLineHandlingListener();

        if(response == null || !response.startsWith("RECORDING_DATA|")){
            System.out.println("invalid server response: "+response);
            return new Double[0][];
        }

        String[] parts = response.split("\\|");
        String csv = parts[2];
        String[] samples = csv.split(",");

        //Si solo es un canal:
        if(type.equals("ECG")||type.equals("EMG")){
            Double[] oneChannel = new Double[samples.length];
            for(int i = 0; i<samples.length; i++){
                oneChannel[i] = Double.parseDouble(samples[i]);
            }
            return new Double[][]{oneChannel};
        }

        //Si son both
        Double[] emgArray = new Double[samples.length];
        Double[] ecgArray = new Double[samples.length];

        for(int i = 0; i<samples.length; i++){
            String[] pair = samples[i].split(";");
            emgArray[i] = Double.parseDouble(pair[0]);
            ecgArray[i] = Double.parseDouble(pair[1]);
        }
        return new Double[][]{emgArray, ecgArray};
    }


    public String sendLogIn(String email, String password) throws IOException {

            out.println("LOGIN|" + email + ";" + password);

            String response = readLineHandlingListener();
            if("OK|LOGIN_SUCCESS_PATIENT".equals(response)){
                out.println("GET_LOGGED_PATIENT");
                String data = readLineHandlingListener();
                try{
                    if (data != null && data.startsWith("PATIENT_DATA|")){
                        String[] patient = data.split("\\|")[1].split(";");
                        loggedPatient = new Patient(
                                Integer.parseInt(patient[0]),
                                "", patient[1],
                                patient[2],
                                Date.valueOf(patient[3]),
                                patient[4],
                                Integer.parseInt(patient[5]),
                                patient[6],
                                Patient.Sex.valueOf(patient[7]));
                        }
                }catch (Exceptions e){
                    e.printStackTrace();
                }

            }
            return response;
    }

    public boolean sendChangePassword(String oldPassword, String newPassword){
        try{
            out.println("CHANGE_PASSWORD|" + oldPassword + ";" + newPassword);
            String response = readLineHandlingListener();
            return response.equals("OK|PASSWORD_CHANGED");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendChangeEmail(String email, String newEmail) {
        try {
            out.println("CHANGE_EMAIL|" + email + ";" + newEmail);

            String response = readLineHandlingListener();
            return response.equals("OK|EMAIL_CHANGED");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean sendUpdateToServer(String p, String value){
        try{
            String message = "UPDATE_PATIENT|"  + p + ";" + value;
            out.println(message);
            String response = readLineHandlingListener();
            return "OK|PATIENT_UPDATED".equals(response);
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //Encryption of password of patient:
    public static String encryptPatientPassword(String password){
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
            return null;
        }
        return response;
    }

    public String requestMedicalHistory(){
        try{
            out.println("GET_MEDICAL_HISTORY|" + loggedPatient.getPatient_id());
            String response = readLineHandlingListener();

            if(response != null && response.startsWith("MEDICAL_HISTORY|")){
                return response.split("\\|")[1];
            }
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public Socket getSocket(){return socket;}
    public PrintWriter getPrintWriter(){return out;}
    public BufferedReader getBufferedReader(){return in;}
    public void setSocket(Socket s){ this.socket = s;}
    public void setPrintWriter(PrintWriter pw_out){ this.out = pw_out;}
    public void setBufferedReader(BufferedReader br_in){ this.in = br_in;}

    public Patient getLoggedPatient(){
        return loggedPatient;
    }
    public void setLoggedPatient(Patient p){this.loggedPatient = p;}


}


