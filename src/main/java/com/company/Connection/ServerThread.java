package com.company.Connection;

import com.company.RequestManager;

import java.io.*;
import java.net.Socket;

/**
 * Clase que se crea como un hilo por cada conexión establecida, para procesar las solicitudes.
 *
 * @author María José Cubero Hidalgo
 * @author Josué León Sarkis
 */
public class ServerThread extends Thread {
    private Socket socket;

    /**
     * Flujo por el cual se puede escribir al socket para comunicarse con el cliente.
     */
    private PrintWriter printWriter;

    /**
     * Flujo por el cual se puede leer el contenido del socket.
     */
    private BufferedReader bufferedReader;

    private RequestManager requestManager;
    private LogWriter logWriter;

    /**
     * Constructor que recibe el socket y la bitácora para inicializar el bufferedReader y el printWriter.
     * @param socket
     * @param logWriter
     * @throws IOException
     */
    public ServerThread(Socket socket, LogWriter logWriter) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.printWriter = new PrintWriter(this.socket.getOutputStream());
        this.logWriter = logWriter;

    }

    /**
     * Método que ejecuta el hilo por defecto al iniciarlo desde el Server. Cada hilo crea un nuevo
     * RequestManager que maneje toda la solicitud y luego cierra el socket y sus flujos.
     */
    public void run() {
        System.out.println("Entro al run");
        try {
            this.requestManager = new RequestManager(this.bufferedReader, this.printWriter, logWriter);
            this.requestManager.manageRequest();
            this.socket.close();
            this.bufferedReader.close();
            this.printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
