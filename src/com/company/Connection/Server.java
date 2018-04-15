package com.company.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Socket socket;
    private ServerSocket serverSocket;
    private int port;

    public Server(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println("Error trying to create the server socket.");
        }
    }

    public void startServer() {
        Thread serverThread;

        while (true) {
            try {
                System.out.println("Server is waiting for connections...");
                this.socket = this.serverSocket.accept();
                System.out.println("Connection successfully established");
                serverThread = new Thread(new ServerThread(this.socket.getOutputStream(), this.socket.getInputStream()));
                serverThread.start();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
    }

}
