package org.example.Server.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientServer {
    public static void main(String[] args){
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(9000);

            while(true){
                Socket socket = serverSocket.accept();

                new Thread(new Connection_with_Patient(socket)).start();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            releaseResourcesPatientServer(serverSocket);
        }
    }

    private static void releaseResourcesPatientServer(ServerSocket serverSocket){
        try{
            serverSocket.close();
        }catch(IOException ex){
            Logger.getLogger(PatientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
