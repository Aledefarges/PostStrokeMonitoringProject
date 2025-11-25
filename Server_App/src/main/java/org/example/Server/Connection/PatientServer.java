package org.example.Server.Connection;

import org.example.Server.JDBC.JDBCManager;

import java.net.ServerSocket;
import java.net.Socket;

public class PatientServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(9100);
            JDBCManager db = new JDBCManager();
            System.out.println("Patient server running on port 9100");
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("New patient connected");

                Connection_with_Patient patientThread = new Connection_with_Patient(socket,db);

                new Thread(patientThread).start();
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(serverSocket !=null){
                try{
                    serverSocket.close();
                }catch(Exception e){
                    System.out.println("Error closing server socket: " + e.getMessage());
                }
            }
        }
    }
}
