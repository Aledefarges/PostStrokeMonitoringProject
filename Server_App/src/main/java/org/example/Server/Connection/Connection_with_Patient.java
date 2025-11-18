package org.example.Server.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Connection_with_Patient {
    public static void main(String [] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(9000);
            Socket socket = serverSocket.accept();   // Waiting for one patient to connect
            System.out.println("Patient connected");

            BufferedReader read_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer_out = new PrintWriter(socket.getOutputStream(), true);

            String message = read_in.readLine();
            writer_out.println("Works");

        } catch (IOException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

}
