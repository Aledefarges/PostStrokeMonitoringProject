package UI;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class menuPatient {

    public static  void main(String[] args) {

        try {
            int choice;
            do{
                System.out.println("Welcome to the Patient Menu");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("0. Exit");

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:

                        break;
                    case 2:

                        break;

                    case 0:
                        System.out.println("Exiting the application. Goodbye!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } while (choice != 0);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
