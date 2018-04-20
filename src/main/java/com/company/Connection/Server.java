package com.company.Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor que está escuchando en cualquier momento para aceptar nuevas conexiones
 * y procesarlas.
 *
 * @author María José Cubero Hidalgo
 * @author Josué León Sarkis
 */
public class Server {

    /**
     * Socket para establecer la comunicación entre el cliente y el servidor.
     */
    private Socket socket;

    /**
     * ServerSocket que espera nuevas conexiones.
     */
    private ServerSocket serverSocket;

    /**
     * Puerto en el cual escuchará el servidor.
     */
    private int port;

    /**
     * Bitácora que registra los resultados.
     */
    private LogWriter logWriter;

    /**
     * Constructor que crea la bitácora y el ServerSocket.
     * @param port
     */
    public Server(int port) throws Exception {
        this.port = port;
        this.logWriter = new LogWriter();
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println("Error al intentar crear el socket.");
        }
    }

    /**
     * Método que inicia el servidor para recibir nuevas conexiones y crear un
     * hilo de ServerThread que procesa la solicitud para cada conexión.
     */
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
