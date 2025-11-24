package Connection;



import org.example.POJOS.Patient;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Scanner;

public class TestConnection_With_Server {

    public static void main(String[] args) {

        //String ip = "172.20.10.3";
           String ip = "10.60.109.214";
        //String ip = "172.16.205.116";
        try {
                // 1. Connect
                Connection_With_Server connect = new Connection_With_Server();
                if (!connect.connection(ip,9000)){
                    System.out.println("Could not connect to server.");
                    return;
                } else System.out.println("Connected to server!");

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
                Patient p = new Patient(name, surname, dob, email,sexEnum, history, phone, password);

                // 3. Send patient to server
                boolean send_patient = connect.sendPatientToServer(p);
                if (send_patient) System.out.println("Patient saved in server!");
                else System.out.println("Error saving patient.");


                // 4. Delete patient from server
                System.out.println("Enter email to delete: ");
                String email_to_delete = sc.nextLine();
                boolean delete_patient = connect.deletePatientFromServer(email_to_delete);
                if(delete_patient) System.out.println("Patient deleted successfully!");
                else System.out.println("Could not delete patient.");

                //5. Log In
                System.out.println("Enter email to log in: ");
                String emailLogin = sc.nextLine();
                System.out.print("Enter password to log in: ");
                String pwLogin = sc.nextLine();
                boolean loginOK = connect.sendLogIn(emailLogin, pwLogin);
                if(loginOK) System.out.println("Login successful!");
                else System.out.println("Login failed!.");

                //6. Change password
                System.out.print("Email: ");
                String cpEmail = sc.nextLine();
                System.out.print("New password: ");
                String newPw = sc.nextLine();
                boolean pwChanged = connect.sendChangePassword(cpEmail, newPw);
                if(pwChanged) System.out.println("Password changed!");
                else System.out.println("Error changing password.");

                //7. Change email
                System.out.print("Current email: ");
                String oldEmail = sc.nextLine();
                System.out.print("New email: ");
                String newEmail = sc.nextLine();
                boolean emailChanged = connect.sendChangeEmail(oldEmail, newEmail);
                if(emailChanged) System.out.println("Email changed successfully!");
                else System.out.println("Error changing email.");


                //8. Update patient information

                System.out.print("Email: ");
                String upemail = sc.nextLine();
                updatePatient(connect, upemail);

                connect.close();

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());

            }
        }//
        private static void updatePatient(Connection_With_Server connect, String email) throws Exception {
                Scanner sc = new Scanner(System.in);
                boolean ok = true;

                while (ok) {
                        System.out.print("Choose the field you want to update:");
                        System.out.println("1. Name:");
                        System.out.println("2. Surname:");
                        System.out.println("3. Phone:");
                        System.out.println("4. Medical History:");
                        System.out.println("5. Date of Birth:");
                        System.out.println("6. Sex:");
                        System.out.println("0. Exit:");

                        int field = Integer.parseInt(sc.nextLine());
                        String value;

                        switch (field) {
                                case 1:
                                        System.out.print("Enter new name:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "name", value);
                                        break;

                                case 2:
                                        System.out.print("Enter new surname:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "surname", value);
                                        break;

                                case 3:
                                        System.out.print("Enter new phone:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "phone", value);
                                        break;

                                case 4:
                                        System.out.print("Enter new medical history:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "medical history", value);
                                        break;

                                case 5:
                                        System.out.print("Enter new date of birth:");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "dob", value);
                                        break;

                                case 6:
                                        System.out.println("Enter new sex (M/F):");
                                        value = sc.nextLine();
                                        connect.sendUpdateToServer(email, "sex", value);
                                        break;

                                case 0:
                                        ok = false;
                                        break;

                                default:
                                        System.out.println("Invalid choice");

                        }
                }

        }
}


