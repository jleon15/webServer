package com.company;

import com.company.Connection.LogWriter;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class RequestManager {

    private InputStream inputStream;
    private OutputStream outputStream;
    private RequestProcessor requestProcessor;
    private RequestParser requestParser;
    private ResponseBuilder responseBuilder;
    private LogWriter logWriter;

    public RequestManager(OutputStream outputStream, InputStream inputStream, LogWriter logWriter){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.requestProcessor = new RequestProcessor();
        this.requestParser = new RequestParser(this.inputStream);
        this.responseBuilder = new ResponseBuilder();
        this.logWriter = logWriter;
    }


}
