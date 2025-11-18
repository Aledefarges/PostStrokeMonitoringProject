package Connection;

import POJOS.Patient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestConnection_With_Server {

    public static void main(String[] args) {

        String ip = "172.10.20.2";

        try {
                // 1. Connect
                Socket socket = new Socket(ip, 9000);
                System.out.println("Connected to server!");

                BufferedReader read_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer_out = new PrintWriter(socket.getOutputStream(), true);

                // Optional handshake
                System.out.println(read_in.readLine()); // "SERVER: Connected"

                // 2. Ask patient data
                Scanner sc = new Scanner(System.in);

                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Surname: ");
                String surname = sc.nextLine();

                System.out.print("Date of Birth (yyyy-mm-dd): ");
                String dob =sc.nextLine();

                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Phone: ");
                int phone = Integer.parseInt(sc.nextLine());

                System.out.print("Medical History: ");
                String history = sc.nextLine();

                System.out.print("Sex (MALE/FEMALE): ");
                String sex = sc.nextLine().toUpperCase();
                Patient.Sex sexEnum = Patient.Sex.valueOf(sex);
                Patient p = new Patient(name, surname, dob, email, phone, history, sex);

                // 3. Send patient to server
                boolean ok = Connection_With_Server.sendPatientToServer(p, writer_out, read_in);

                if (ok) System.out.println("✔ Patient saved in server!");
                else System.out.println("✘ Error saving patient.");

                socket.close();

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

}
