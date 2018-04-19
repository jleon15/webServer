package com.company;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clase que crea la respuesta y la concatena con el payload.
 *
 * @author Maria Jose Cubero
 * @author Josue Leon
 */
public class ResponseBuilder{

    /**
     * Mapa que contiene los valores de la solicitud.
     */
    private HashMap<String, String> requestHeader;
    private String responseHeader;

    public ResponseBuilder (HashMap<String, String> requestHeader){
        this.requestHeader = requestHeader;
        this.responseHeader = "";
    }

    /**
     * Método que concatena todos los valores del mapa para armar el header de la respuesta.
     * @return El encabezado de la respuesta ya listo.
     */
    public String createHeader() {

        this.responseHeader += this.requestHeader.get("statusCode") + "\r\n";
        this.responseHeader += "Date: " + this.getDate() + "\r\n";
        this.responseHeader += "Server: ServidorLocal/1.0\r\n";
        this.responseHeader += "Content-Length: " + this.requestHeader.get("contentLength") + "\r\n";
        this.responseHeader += "Content-Type " + this.requestHeader.get("mimeType") + "\r\n\r\n";

        return this.responseHeader;
    }

    /**
     * Método para calcular la fecha del servidor.
     * @return fecha del servidor en el formato necesario
     */
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new Date());
    }

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }
}
