package com.company;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestProcessor{

    private HashMap<String, String> requestHeader;
    private List<String> mimetypes;

    public RequestProcessor(){
        this.mimetypes = new ArrayList<String>();
    }

    public HashMap<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String obtainTextPayload () {
        String payload = "";


        //BODY
        return payload;
    }

    public byte[] obtainImagePayload () {
        byte[] payload = null;


        //BODY
        return payload;
    }
}
