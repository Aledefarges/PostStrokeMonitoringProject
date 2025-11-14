package Connection;

import java.io.*;
import java.net.Socket;

public class Connection_With_Server {
    public static void main(String ip_host) throws IOException {
        Socket socket = new Socket(ip_host, 9000);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        System.out.println("Connected to the Server");
    }

}
