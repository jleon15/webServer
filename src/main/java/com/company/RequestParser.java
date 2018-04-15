package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class RequestParser{

    private BufferedReader bufferedReader;
    private HashMap<String, String> requestHeader;

    public RequestParser (BufferedReader bufferedReader, HashMap<String, String> requestHeader) {
        this.bufferedReader = bufferedReader;
        this.requestHeader = requestHeader;
        try {
            this.parseRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseRequest() throws IOException {
        String request = "";
        while(this.bufferedReader.ready()) {
            request += (char) this.bufferedReader.read();
        }
        String[] requestParts = request.split("\n");
        for (int i = 0; i < requestParts.length; i++) {
            String line = requestParts[i];
            String[] lineParts = line.split(" ");
            if (isNecessaryField(lineParts[0])){
                this.requestHeader.put(lineParts[0], lineParts[1]);
            }
        }
        System.out.println(request);
    }

    private boolean isNecessaryField(String field){
        if (field.equals("GET")||field.equals("HEAD")||field.equals("POST")||field.equals("Accept:")||
                field.equals("Content-type:")||field.equals("Content-length:")||
                field.equals("Date:")|| field.equals("Host:")|| field.equals("Referer:")|| field.equals("Server")){
            return true;
        }
        return false;
    }

    public HashMap<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }
}
