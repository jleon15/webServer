package com.company;

import java.io.InputStream;
import java.util.HashMap;

public class RequestParser{

    private HashMap<String, String> requestHeader;
    private InputStream inputStream;

    public RequestParser (InputStream inputStream) {
        requestHeader = new HashMap<String, String>();
        this.inputStream = inputStream;
    }

    public HashMap<String, String> readHeader(){
        return null;
    }
}
