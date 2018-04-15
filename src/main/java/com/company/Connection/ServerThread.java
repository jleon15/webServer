package com.company.Connection;

import com.company.RequestManager;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private RequestManager requestManager;
    private LogWriter logWriter;


    public ServerThread(Socket socket, LogWriter logWriter) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.printWriter = new PrintWriter(this.socket.getOutputStream());
        this.logWriter = logWriter;

    }

    public void run() {
        System.out.println("Entro al run");
        try {
            this.requestManager = new RequestManager(this.bufferedReader, this.printWriter, logWriter);
            this.socket.close();
            this.bufferedReader.close();
            this.printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
