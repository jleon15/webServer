package com.company;

import com.company.Connection.LogWriter;
import javafx.util.Pair;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class RequestManager {

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private LogWriter logWriter;
    private RequestProcessor requestProcessor;
    private RequestParser requestParser;
    private ResponseBuilder responseBuilder;
    private HashMap<String, String> requestHeader;
    private OutputStream outputStream;

    public RequestManager(BufferedReader bufferedReader, PrintWriter printWriter, LogWriter logWriter, OutputStream outputStream) throws IOException {
        this.requestHeader = new HashMap<String, String>();
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
        this.requestParser = new RequestParser(this.bufferedReader, this.requestHeader);
        this.requestProcessor = new RequestProcessor();
        this.responseBuilder = new ResponseBuilder(this.requestHeader);
        this.logWriter = logWriter;
        this.outputStream = outputStream;
    }

    public void printHM() {
        for (String name : requestHeader.keySet()) {
            String key = name.toString();
            String value = requestHeader.get(name).toString();
        }
    }

    public void manageRequest() throws IOException {
        this.requestProcessor.setRequestHeader(this.requestHeader);
        boolean isImage = this.requestProcessor.processRequest();

        String responseHeader = this.responseBuilder.createHeader();
        this.printWriter.write(responseHeader);
        this.printWriter.flush();

        if (!this.requestHeader.containsKey("HEAD")) {
            if (this.requestHeader.get("statusCode").equalsIgnoreCase("HTTP/1.1 200 OK")) {
                if (isImage) {
                    byte[] imagePayload = this.requestProcessor.getImagePayload();
                    this.outputStream.write(imagePayload);

                } else {
                    String textString = this.requestProcessor.getTextPayload();
                    this.printWriter.write(textString);
                }
                this.printWriter.flush();
            }
        }

        if (this.requestHeader.get("statusCode").equalsIgnoreCase("HTTP/1.1 200 OK")) {
            String[] registroConexion = new String[6];
            registroConexion[0] = this.requestHeader.get("method");
            registroConexion[1] = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
            registroConexion[2] = "localhost";
            registroConexion[3] = this.requestHeader.get("referer");
            registroConexion[4] = this.requestHeader.get(this.requestHeader.get("method"));
            registroConexion[5] = this.requestHeader.get("postBody");
            this.logWriter.getBitacoraSolicitudes().add(registroConexion);
            this.logWriter.generarBitacoraHTML();
        }

    }
}
