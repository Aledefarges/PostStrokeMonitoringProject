package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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

    //Admin register:
    public boolean sendAdminRegister(Administrator admin) {
       try{
           String message =
                   "ADMIN_REGISTER|" +
                           admin.getName() + ";" +
                           admin.getSurname() + ";" +
                           admin.getPhone() + ";" +
                           admin.getEmail() + ";" +
                           admin.getPassword();

           out.println(message);

           String response = in.readLine();
           return "OK|ADMIN_REGISTERED".equals(response);
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
           return "OK|SERVER_CLOSING".equals(response);
       }catch(IOException ex){
           ex.printStackTrace();
           return false;
       }
    }
}
