package com.company;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * El parseo de la solicitud se realiza aquí, para devolverle al RequestManager
 * un mapa con los valores del header y, en el caso de un POST, el body.
 *
 * @author María José Cubero Hidalgo
 * @author Josué León Sarkis
 */
public class RequestParser{

    private BufferedReader bufferedReader;

    /**
     * Mapa el cual almacena los valores del header de la solicitud y el body si es un POST.
     */
    private HashMap<String, String> requestHeader;

    /**
     * Constructor que inicializa los atributos de la clase.
     * @param bufferedReader Flujo que es el mismo creado en el ServerThread para leer la solicitud.
     * @param requestHeader Mapa creado en el RequestManager para llenarlo con los valores parseados.
     */
    public RequestParser (BufferedReader bufferedReader, HashMap<String, String> requestHeader) {
        this.bufferedReader = bufferedReader;
        this.requestHeader = requestHeader;
        try {
            this.parseRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parsea la solicitud e ingresa los valores en el mapa.
     * @throws IOException
     */
    private void parseRequest() throws IOException {
        String request = "";
        boolean endOfHeader = false;
        while(this.bufferedReader.ready()) {
            System.out.println("entra al buffer reader");
            request += (char) this.bufferedReader.read();
        }
        String[] requestParts = request.split("\n");
        for (int i = 0; i < requestParts.length; i++) {
            String line = requestParts[i];
            if(!(line.trim().isEmpty())){
                String[] lineParts = line.split(" ");
                if(i == 0) {
                    this.requestHeader.put("method",lineParts[0]);
                }
                if (isNecessaryField(lineParts[0]) && !endOfHeader){
                    this.requestHeader.put(lineParts[0], lineParts[1]);
                } else if(endOfHeader){
                    this.requestHeader.put("postBody",lineParts[0]);
                }
            } else {
                endOfHeader = true;
            }
        }
        System.out.println("2222222222222222222222222222222222222222222 "+request);
    }

    /**
     * Chequea si un elemento del header es de los necesarios para el servidor.
     * @param field Elemento del header.
     * @return true si es necesario, false si no.
     */
    private boolean isNecessaryField(String field){
        if (field.equals("GET")||field.equals("HEAD")||field.equals("POST")||field.equals("Accept:")||
                field.equals("Content-type:")||field.equals("Content-length:")||
                field.equals("Date:")|| field.equals("Host:")|| field.equals("Referer:")|| field.equals("Server:")){
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
