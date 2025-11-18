package Connection;

import POJOS.Patient;

import java.io.*;
import java.net.Socket;

public class Connection_With_Server {
    public static void main(String [] args) throws IOException {

        String ip_host = "172.10.20.2";
        try{
            Socket socket = new Socket(ip_host, 9000);
            BufferedReader read_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer_out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to the Server");
            writer_out.println("Hello");
            String response = read_in.readLine();  // Response of the server

            socket.close();
            System.out.println("Connection closed");

        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }


    public static boolean sendPatientToServer(Patient patient, PrintWriter out, BufferedReader in) {

        try {
            // Build message in ONE line
            String message = "PATIENT|" +
                    patient.getName() + ";" +
                    patient.getSurname() + ";" +
                    patient.getDob().toString() + ";" +
                    patient.getEmail() + ";" +
                    patient.getPhone() + ";" +
                    patient.getMedicalhistory() + ";" +
                    patient.getSex().name();

                out.println(message);
                // Receive server confirmation
                String response = in.readLine();
                return "PATIENT_SAVED".equals(response);

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

    }

}
