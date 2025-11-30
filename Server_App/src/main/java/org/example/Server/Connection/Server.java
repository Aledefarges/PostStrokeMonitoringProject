package org.example.Server.Connection;

import com.sun.jdi.connect.spi.Connection;
import org.example.Server.GUI.AdminPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
                try {
                    Socket socket = serverSocket.accept();
                    Connection_Server connection = new Connection_Server(socket);
                    activeConnections.add(connection);
                    new Thread(connection).start();
                }catch (SocketException e){
                    break;
                }
            }

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeConnection(Connection_Server connection){
        activeConnections.remove(connection);
    }

    public static void broadcastShutdown(){
        List<Connection_Server> snapshot;
        synchronized (activeConnections) {
            snapshot = new ArrayList<>(activeConnections);
        }

        for(Connection_Server c : snapshot) {
            try {
               c.sendShutdownMessage();
            } catch (Exception ignored) {
            }
        }
    }

    public static int getActiveClientCount(){
        synchronized (activeConnections){
            return activeConnections.size();
        }
    }

}


