package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;

import org.example.POJOS.Administrator;

public class Connection_With_Server {
   private Socket socket;
   private PrintWriter out;
   private BufferedReader in;

   public boolean connection(String ip, int port) {
       try{
           socket = new Socket(ip,port);
           in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out = new PrintWriter(socket.getOutputStream(),true);

           System.out.println("SERVER:  " + in.readLine());
           return true;
       }catch(IOException ex){
           System.out.println("Error connecting to server: " + ex.getMessage());
           return false;
       }
   }

   public void close() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.out.println("ERROR closing connection: " + e.getMessage());
        }
   }

   //Admin LogIn:
    public boolean sendAdminLogIn(String email, String password) {
       try{
           out.println("ADMIN_LOGIN|" + email + ";" + password);


           String response = in.readLine();
           return "OK|LOGIN_SUCCESS".equals(response);
       }catch(IOException ex){
           ex.printStackTrace();
           return false;
       }
    }

    //Close server
    public boolean sendAdminShutdown(String adminPassword){
       try{
           out.println("ADMIN_SHUTDOWN|" + adminPassword);
           //El server recibe este mensaje y comprueba si la contraseña del admin es correcta antes de cerrarlo

           String response = in.readLine();
           if ("OK|SERVER_CLOSING".equals(response)) {
               System.out.println("Server accepted shutdown. Closing administrator");
               close();
               System.exit(0);
           }
           return false;
       }catch(IOException ex){
           ex.printStackTrace();
           return false;
       }
    }

    public static String encryptAdminPassword(String password){
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
