package org.example.Connection;

import org.example.POJOS.Doctor;

import java.util.Scanner;

public class TestConnection_With_Server {
    public static void main(String [] args) {
        String ip="localhost";
        int port=9000;

        try{
            Connection_With_Server connect = new Connection_With_Server();

            if(!connect.connection(ip,port)){
                System.out.println("Could not connect to doctor server.");
                return;
            }else System.out.println("Connected to doctor server!");

            Scanner sc = new Scanner(System.in);
            boolean running=true;
            while(running){
                System.out.println("\n===== DOCTOR MENU =====");
                System.out.println("1. Doctor Login");
                System.out.println("2. Register new Doctor");
                System.out.println("3. View all patients");
                System.out.println("4. View all patient data");
                System.out.println("5. Update medical history of patient");
                System.out.println("6. Change email");
                System.out.println("7. Change password");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int option = Integer.parseInt(sc.nextLine());

                switch(option){
                    case 1:
                        System.out.println("\n--- LOGIN DOCTOR ---");
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        boolean okLogin = connect.sendDoctorLogin(email, password);
                        if (okLogin) System.out.println("Login successful!");
                        else System.out.println("Login failed.");
                        break;
                    case 2:
                        System.out.println("\n--- REGISTER DOCTOR ---");
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Surname: ");
                        String surname = sc.nextLine();
                        System.out.print("Phone: ");
                        int phone = Integer.parseInt(sc.nextLine());
                        System.out.print("Email: ");
                        String regEmail = sc.nextLine();
                        System.out.print("Password: ");
                        String regPw = sc.nextLine();

                        Doctor doctor = new Doctor(name, surname, phone, regEmail, regPw);

                        boolean okReg  = connect.sendRegisterDoctor(doctor);
                        if (okReg) System.out.println("Register successful!");
                        else System.out.println("Register failed.");
                        break;
                    case 3:
                        System.out.println("\n--- VIEW ALL PATIENTS ---");
                        String allPatients = connect.sendAllPatients();
                        System.out.println("Patients: "+allPatients);
                        break;
                    case 4:
                        System.out.println("\n--- VIEW PATIENT DATA ---");
                        System.out.print("Enter patient email: ");
                        String patientEmail = sc.nextLine();

                        String patientData= connect.sendPatientData(patientEmail);
                        System.out.println("Patient data: "+patientData);
                        break;
                    case 5:
                        System.out.println("\n--- UPDATE MEDICAL HISTORY ---");
                        System.out.print("Enter patient email: ");
                        String pEmail= sc.nextLine();
                        System.out.println("New medical history of patient:");
                        String newHistory = sc.nextLine();

                        boolean okHist = connect.sendPatientHistory(pEmail,newHistory);
                        if(okHist) System.out.println("Medical history updated!");
                        else  System.out.println("Medical history update failed");
                        break;
                    case 6:
                        System.out.println("\n--- CHANGE DOCTOR EMAIL ---");
                        System.out.println("Enter email: ");
                        String oldEmail = sc.nextLine();
                        System.out.println("New email: ");
                        String newEmail = sc.nextLine();

                        boolean okEmail = connect.sendChangeEmail(oldEmail,newEmail);
                        if (okEmail) System.out.println("Change email successful!");
                        else System.out.println("Change email failed.");
                    case 7:
                        System.out.println("\n--- CHANGE DOCTOR PASSWORD ---");
                        System.out.println("Old password: ");
                        String oldPassword = sc.nextLine();
                        System.out.println("New password: ");
                        String newPassword = sc.nextLine();

                        boolean okPassword = connect.sendChangePassword(oldPassword,newPassword);
                        if  (okPassword) System.out.println("Change password successful!");
                        else System.out.println("Change password failed.");
                    case 0:
                        running=false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }

            connect.close();
        }catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}

