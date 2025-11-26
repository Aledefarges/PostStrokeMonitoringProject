package org.example.Server.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(9000);
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(new Connection_With_Doctor(socket)).start();
            }
        }catch(IOException ex){
            Logger.getLogger(DoctorServer.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            releaseResourcesDoctorServer(serverSocket);
        }
    }

    private static void releaseResourcesDoctorServer(ServerSocket serverSocket){
        try{
            serverSocket.close();
        }catch(IOException ex){
            Logger.getLogger(DoctorServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
