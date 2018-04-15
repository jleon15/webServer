package com.company;

import com.company.Connection.LogWriter;

import java.io.*;
import java.util.HashMap;

public class RequestManager {

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private LogWriter logWriter;
    private RequestProcessor requestProcessor;
    private RequestParser requestParser;
    private ResponseBuilder responseBuilder;

    public RequestManager(BufferedReader bufferedReader, PrintWriter printWriter, LogWriter logWriter) throws IOException {
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
        this.requestParser = new RequestParser(this.bufferedReader);
        this.requestProcessor = new RequestProcessor();
        this.responseBuilder = new ResponseBuilder();
        this.logWriter = logWriter;

        System.out.println(this.requestParser.parseRequest());
    }


}
