package com.company;

import java.util.HashMap;

/**
 * Class that creates the response header and concatenates it with the payload.
 *
 * @author Maria Jose Cubero
 * @author Josue Leon
 */
public class ResponseBuilder{

    /**
     * Payload in case the payload is a text.
     */
    String textPayload;

    /**
     * Payload in case the payload is an image.
     */
    byte[] imagePayload;

    /**
     * Map that contains the request header.
     */
    private HashMap<String, String> requestHeader;

    public ResponseBuilder (){


    }

    //create response() {} no se que tipo devuelve

    public String createHeader() {return null;}

    public void setTextPayload(String textPayload) {
        this.textPayload = textPayload;
    }

    public void setImagePayload(byte[] imagePayload) {
        this.imagePayload = imagePayload;
    }

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }
}
