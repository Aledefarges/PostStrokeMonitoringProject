package org.example.Server.Connection;

import org.example.POJOS.Administrator;
import org.example.Server.JDBC.JDBCAdministratorManager;
import org.example.Server.JDBC.JDBCManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection_With_Administrator {
   private JDBCManager db;
   private JDBCAdministratorManager adminManager;
   private BufferedReader in;
   private PrintWriter out;

   public Connection_With_Administrator(JDBCManager db) {
       this.db=db;
   }

   public static void main(String[] args){
       JDBCManager db = new JDBCManager();
       Connection_With_Administrator server = new Connection_With_Administrator(db);
       server.start();
   }

   public void start(){
       try{
           ServerSocket serverSocket = new ServerSocket(9000);
           Socket socket = serverSocket.accept();
           System.out.println("Administrator connected");

           in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out = new PrintWriter(socket.getOutputStream(),true);

           adminManager = new JDBCAdministratorManager(db);

           out.println("WELCOME_ADMIN");

           String message;

           while((message=in.readLine())!=null){
               System.out.println("Received: "+message);
               String [] parts = message.split("\\|");
               String command = parts[0];

               switch(command){
                   case "ADMIN_LOGIN":
                       handleAdminLogin(parts[1]);
                       break;
                   case "ADMIN_REGISTER":
                       handleAdminRegister(parts[1]);
                       break;

                   case "ADMIN_SHUTDOWN":
                       handleAdminShutdown(parts[1]);
                       break;

                   default:
                       out.println("ERROR|UNKNOWN_COMMAND");
                       break;
               }
           }
       }catch(IOException ex){
           System.out.println("ERROR: " + ex.getMessage());
       }
   }

   private void handleAdminLogin(String data){
       try{
           String [] parts = data.split(";");

           String email = parts[0].trim();
           String password = parts[1].trim();

           Administrator admin = adminManager.searchAdministratorByEmail(email);

           if (admin == null) {
               out.println("ERROR|NO_SUCH_EMAIL");
               return;
           }

           if (admin.getPassword().equals(password)) {
               out.println("OK|LOGIN_SUCCESS");
           } else {
               out.println("ERROR|WRONG_PASSWORD");
           }
       }catch(Exception ex){
           out.println("ERROR|EXCEPTION");
           ex.printStackTrace();
       }
   }

   private void handleAdminRegister(String data){
       try{
           String[] parts = data.split(";");

           String name = parts[0];
           String surname = parts[1];
           int phone = Integer.parseInt(parts[2]);
           String email = parts[3];
           String password = parts[4];

           Administrator admin = new Administrator(0,email,password,name,surname,phone);
           adminManager.addAdministrator(admin);

           out.println("OK|ADMIN_REGISTERED");
       }catch(Exception ex){
           out.println("ERROR|EXCEPTION");
           ex.printStackTrace();
       }
   }

   private void handleAdminShutdown(String password){
       try{
           String shutdownPw = "admin123"; //Clave de cierre

           if(password.equals(shutdownPw)){
               out.println("OK|SERVER_CLOSING");
               System.out.println("SERVER CLOSING NOW...");
               System.exit(0);
           }else{
               out.println("ERROR|WRONG_PASSWORD");
           }
       }catch(Exception ex){
           out.println("ERROR|EXCEPTION");
       }
   }
}

