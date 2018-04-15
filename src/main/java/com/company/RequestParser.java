package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class RequestParser{

    private BufferedReader bufferedReader;
    private HashMap<String, String> requestHeader;


    public RequestParser (BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        requestHeader = new HashMap<String, String>();
    }

    public String parseRequest() throws IOException {
        String request = "";
        while(this.bufferedReader.ready()) {
            request += (char) this.bufferedReader.read();
        }

        return request;
    }

    public HashMap<String, String> readHeader(){
        return null;
    }
}
