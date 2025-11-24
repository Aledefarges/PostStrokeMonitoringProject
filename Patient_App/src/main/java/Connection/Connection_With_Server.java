package Connection;

import Bitalino.Frame;
import org.example.POJOS.Patient;

import java.io.*;
import java.net.Socket;

public class Connection_With_Server {


    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

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



    public boolean sendPatientToServer(Patient patient) {

        try {
            String message = "ADD_PATIENT|" +
                    patient.getName() + ";" +
                    patient.getSurname() + ";" +
                    patient.getDob().toString() + ";" +
                    patient.getEmail() + ";" +
                    patient.getPhone() + ";" +
                    patient.getMedicalhistory() + ";" +
                    patient.getSex().name() + ";" +
                    patient.getPassword();

                out.println(message);

                String response = in.readLine();
                return "PATIENT_SAVED".equals(response); // It confirms the connection with server

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

    }

    public boolean deletePatientFromServer(String email){
        try{
            String message = "DELETE_PATIENT|" + email;
            out.println(message);

            String response = in.readLine();
            return "PATIENT_DELETED|".equals(response);
        }catch (IOException e){
            e.printStackTrace();
            return false;

        }
    }

    // Indicates that a new Recording needs to be created as a new row in the Database
    // Then it send to the server which channels are being used by BITalino
    public int[] startRecording(int patient_id, String type){
        int [] channel;
        if (type.equals("EMG")){
            channel = new int[]{1};
        } else if (type.equals("ECG")){
            channel = new int[]{2};
        } else if (type.equals("BOTH")){
            channel = new int[]{1,2};
        } else{
            throw new IllegalArgumentException("Invalid type");
        }

        out.println("START_RECORDING|" +
                patient_id + ";" +
                type);
        return channel;
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

    public void endRecording(){
        out.println("END_RECORDING");
    }

    //Funciones que teníamos en Utilities:

    public boolean sendLogIn(String email, String password) throws IOException {
        try{
            // Enviar comando
            out.println("LOGIN|" + email + ";" + password);

            // Leer respuesta
            String response = in.readLine();
            return response.equals("OK|LOGIN_SUCCESS");
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendChangePassword(String email, String oldPassword, String newPassword){
        try{
            out.println("CHANGE_PASSWORD|" + email + ";" + oldPassword + ";" + newPassword);
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
    public boolean sendUpdateToServer(String email, String p, String value){
        try{
            String message = "UPDATE_PATIENT|" + email + ";" + p + ";" + value;
            out.println(message);
            String response = in.readLine();
            return "OK|PATIENT_UPDATED".equals(response);
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
}


