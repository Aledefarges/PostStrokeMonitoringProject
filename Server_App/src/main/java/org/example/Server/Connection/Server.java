package org.example.Server.Connection;

import org.example.Server.GUI.AdminPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void main(String[] args){

        try{
            ServerSocket serverSocket = new ServerSocket(9000);
            ServerSocket finalServerSocket = serverSocket;

            JFrame frame = new JFrame("Admin Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600,400);
            frame.setLocationRelativeTo(null);

            AdminPanel adminPanel = new AdminPanel(serverSocket);
            frame.setContentPane(adminPanel);
            frame.setVisible(true);

            Thread serverThread = new Thread(() -> {
                try{
                    while(!finalServerSocket.isClosed()){
                        Socket socket = finalServerSocket.accept();
                        new Thread(new Connection_Server(socket)).start();
                    }
                }catch (IOException e){
                    System.out.println("Server stopped");
                }
            });
            serverThread.start();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
