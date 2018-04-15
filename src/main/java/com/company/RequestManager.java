package com.company;

import com.company.Connection.LogWriter;
import javafx.util.Pair;

import java.io.*;
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
    private List<Pair<String,String>> postBody;

    public RequestManager(BufferedReader bufferedReader, PrintWriter printWriter, LogWriter logWriter) throws IOException {
        this.requestHeader = new HashMap<String, String>();
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
        this.requestParser = new RequestParser(this.bufferedReader, this.requestHeader, this.postBody);
        this.requestProcessor = new RequestProcessor();
        this.responseBuilder = new ResponseBuilder();
        this.logWriter = logWriter;
        this.printHM();
    }

    public void printHM(){
        System.out.println("***********************************");
        for (String name: requestHeader.keySet()){
            String key =name.toString();
            String value = requestHeader.get(name).toString();
            System.out.println(key + " " + value);
        }
    }

    public void manageRequest(){
        this.requestHeader = this.requestParser.getRequestHeader();
        this.requestProcessor.setRequestHeader(requestHeader);

    }


}
