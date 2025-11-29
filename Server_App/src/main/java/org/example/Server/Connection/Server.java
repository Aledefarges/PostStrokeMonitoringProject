package org.example.Server.Connection;

import org.example.Server.GUI.AdminPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private static final List<Connection_Server> activeConnections = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args){
        try{
            System.out.println("Server Started");
            System.out.println("DB USED → " +
                    new java.io.File("Server_App/db/PostStrokedb3.db").getAbsolutePath());
            ServerSocket serverSocket = new ServerSocket(9000);
            ServerSocket finalServerSocket = serverSocket;

            JFrame frame = new JFrame("Administrator Server Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600,400);
            frame.setLocationRelativeTo(null);

            AdminPanel adminPanel = new AdminPanel(serverSocket);
            frame.setContentPane(adminPanel);
            frame.setVisible(true);
            while(true){
                Socket socket = serverSocket.accept();
                Connection_Server connection = new Connection_Server(socket);
                activeConnections.add(connection);
                new Thread(connection).start();
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeConnection(Connection_Server connection){
        activeConnections.remove(connection);
    }

    public static void broadcastShutdown(){
        synchronized (activeConnections){
            for(Connection_Server c : activeConnections){
                c.sendShutdownMessage();
            }
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
