package com.company.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Socket socket;
    private ServerSocket serverSocket;
    private int port;
    private LogWriter logWriter;

    public Server(int port) {
        this.port = port;
        this.logWriter = new LogWriter();
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println("Error al intentar crear el socket.");
        }
    }

    public void startServer() {
        System.out.println("El servidor está esperando nuevas conexiones...");
        while (true) {
            try {
                this.socket = this.serverSocket.accept();
                System.out.println("Conexión establecida de manera exitosa.");
                ServerThread serverThread = new ServerThread(this.socket, this.logWriter);
                serverThread.start();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
    }

}
