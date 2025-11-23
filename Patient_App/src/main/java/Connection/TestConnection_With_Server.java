package Connection;



import org.example.POJOS.Patient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.Scanner;

public class TestConnection_With_Server {

    public static void main(String[] args) {

        //String ip = "172.20.10.3";
         //   String ip = "10.60.103.7";
        String ip = "172.16.205.116";
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
                Date dob = Date.valueOf(sc.nextLine());

                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Phone: ");
                int phone = Integer.parseInt(sc.nextLine());

                System.out.print("Medical History: ");
                String history = sc.nextLine();

                System.out.print("Sex (M/F): ");
                String sex = sc.nextLine().toUpperCase();
                Patient.Sex sexEnum = Patient.Sex.valueOf(sex);

                System.out.println("Create a password: ");
                String password = sc.nextLine();
                Patient p = new Patient(name, surname, dob, email, phone, history, sexEnum, password);

                // 3. Send patient to server
                boolean ok = Connection_With_Server.sendPatientToServer(p, writer_out, read_in);

                if (ok) System.out.println("✔ Patient saved in server!");
                else System.out.println("✘ Error saving patient.");

                //DELETE PATIENT TEST
                System.out.println("Enter email to delete: ");
                String email_to_delete = sc.nextLine();

                boolean deleted = Connection.Connection_With_Server.deletePatientFromServer(email_to_delete, writer_out, read_in);
                if(deleted) System.out.println("Patient deleted successfully!");
                else System.out.println("Could not delete patient.");

                //LOGIN TEST
                System.out.println("Enter email to log in: ");
                String emailLogin = sc.nextLine();

                System.out.print("Enter password to log in: ");
                String pwLogin = sc.nextLine();

                boolean loginOK = Connection_With_Server.sendLogIn(emailLogin, pwLogin, writer_out, read_in);
                System.out.println(loginOK ? "✔ Login successful!" : "✘ Login failed.");

                //CHANGE PASSWORD TEST:
                System.out.print("Email: ");
                String cpEmail = sc.nextLine();

                System.out.print("New password: ");
                String newPw = sc.nextLine();

                boolean pwChanged = Connection_With_Server.sendChangePassword(cpEmail, newPw, writer_out, read_in);
                System.out.println(pwChanged ? "✔ Password changed!" : "✘ Error changing password.");

                //CHANGE EMAIL TEST:
                System.out.print("Current email: ");
                String oldEmail = sc.nextLine();

                System.out.print("New email: ");
                String newEmail = sc.nextLine();

                boolean emailChanged = Connection_With_Server.sendChangeEmail(oldEmail, newEmail, writer_out, read_in);
                System.out.println(emailChanged ? "✔ Email updated!" : "✘ Error updating email.");

                socket.close();

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());


            }
        }
}


