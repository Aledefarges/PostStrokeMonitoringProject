package org.example.Server.GUI;

import org.example.Server.Connection.Connection_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private boolean running = true;

    public Server(int port){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run(){
        while(running){
            try{
                Socket s = serverSocket.accept();
                new Connection_Server(s).run();
            }catch (IOException e){
                if(running) e.printStackTrace();
            }
        }
    }

    public void stopServer(){
        try{
            running = false;
            serverSocket.close();
            System.out.println("Server Stopped");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
