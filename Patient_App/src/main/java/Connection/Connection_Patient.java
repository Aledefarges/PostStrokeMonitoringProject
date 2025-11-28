package Connection;

import Bitalino.Frame;
import org.example.POJOS.Patient;

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;

public class Connection_Patient {


    private Socket socket;
    private PrintWriter out;
    public BufferedReader in;

    public boolean connection(String ip_host, int port){
        // The function is boolean because it indicates whether the connection has succeeded or not
        try{
            socket = new Socket(ip_host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String response = in.readLine();  // Response of the server
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

                String response = in.readLine();
                return "PATIENT_SAVED".equals(response); // It confirms the connection with server

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

    }

    public String requestAllDoctor(){
        try{
            out.println("VIEW_ALL_DOCTORS|");
            String response = in.readLine();
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
            String response = in.readLine();
            return "OK|PATIENT_DELETED".equals(response);
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
            channel = new int[]{0};
        } else if (type.equals("BOTH")){
            channel = new int[]{0,1};
        } else{
            throw new IllegalArgumentException("Invalid type");
        }

        out.println("START_RECORDING|" + type);

        int recording_id = -1;
        try{
            String response = in.readLine();
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
        int channelCount = channel.length;
        for (Frame f : frames) {
            StringBuilder msg = new StringBuilder("FRAME|");
            msg.append(f.seq);

            //solo envía los canales activados
            for (int i = 0; i < channelCount; i++) {
                msg.append(";").append(f.analog[channel[i]]);
            }
            //digitales siempre igual
            for (int d = 0; d < 4; d++) {
                msg.append(";").append(f.digital[d]);
            }
            out.println(msg.toString());
            out.flush();
        }
    }

    public void endRecording() throws IOException {
        out.println("END_RECORDING");
        in.readLine();
    }


    public Double[][] requestRecordingData(int recording_id, String type) throws IOException{
        //Pedir al servidor el recording
        out.println("GET_RECORDING|" + recording_id);

        //Leer la respuesta
        String response = in.readLine();

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

    //Funciones que teníamos en Utilities:

    public String sendLogIn(String email, String password) throws IOException {
            // Enviar comando
            out.println("LOGIN|" + email + ";" + password);

            // Leer respuesta
            String response = in.readLine();
            return response;
    }

    public boolean sendChangePassword(String oldPassword, String newPassword){
        try{
            out.println("CHANGE_PASSWORD|" + oldPassword + ";" + newPassword);
            String response = in.readLine();
            return response.equals("OK|PASSWORD_CHANGED");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendChangeEmail(String email, String newEmail) {
        try {
            out.println("CHANGE_EMAIL|" + email + ";" + newEmail);

            String response = in.readLine();
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
            String response = in.readLine();
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
}


