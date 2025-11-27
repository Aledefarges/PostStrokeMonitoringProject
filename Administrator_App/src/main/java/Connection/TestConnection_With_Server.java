package Connection;

import org.example.POJOS.Administrator;
import java.util.Scanner;

public class TestConnection_With_Server {
    public static void main(String[] args){
        String ip = "172.20.10.3"; //Cambiar al ip del server
        int port = 9000;

        try{
            Connection_With_Server connect = new Connection_With_Server();

            if(!connect.connection(ip,port)){
                System.out.println("Could not connect to admin server.");
                return;
            }else System.out.println("Connected to admin server!");

            Scanner sc = new Scanner(System.in);
            boolean running=true;
            while(running){
                System.out.println("\n===== ADMINISTRATOR MENU =====");
                System.out.println("1. Administrator Login");
                System.out.println("2. Register new Administrator");
                System.out.println("3. Shutdown Server");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int option = Integer.parseInt(sc.nextLine());

                switch(option){
                    case 1:
                        System.out.println("\n--- LOGIN ADMIN ---");
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        boolean okLogin = connect.sendAdminLogIn(email, password);
                        if (okLogin) System.out.println("Login successful!");
                        else System.out.println("Login failed.");
                        break;
                    case 2:
                        System.out.println("\n--- REGISTER ADMINISTRATOR ---");
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

                        Administrator admin = new Administrator(0,regEmail,regPw,name,surname,phone);

                        //boolean okReg  = connect.sendAdminRegister(admin);
                        //if (okReg) System.out.println("Register successful!");
                        //else System.out.println("Register failed.");
                        break;
                    case 3:
                        System.out.println("\n--- SHUTDOWN SERVER ---");
                        System.out.print("Enter shutdown password: ");
                        String pw = sc.nextLine();
                        connect.sendAdminShutdown(pw);

                        System.out.println("Wrong password, server will not shutdown");
                        break;
                    case 0:
                        running = false;
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
