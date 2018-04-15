package com.company;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestProcessor{

    private HashMap<String, String> requestHeader;
    private boolean isImage;
    private byte[] imagePayload;
    private String textPayload;

    public RequestProcessor(){
        isImage = false;
    }

    public void processRequest(){
        if (this.obtainRequestedFileName() == null){
            //Error 501
            this.requestHeader.put("statusCode", "HTTP/1.1 501 Not Implemented");
            System.out.println("0000000000000000000 HTTP/1.1 501 Not Implemented");
        }
        else if(this.obtainRequestedFileName().equals(".")){ //cuando no se solicita un elemento
            this.requestHeader.put("statusCode", "HTTP/1.1 200 OK");
            System.out.println("0000000000000000000 HTTP/1.1 200 ok");
        }
        else{
            System.out.println("WHATISWHAT");
            String requestedFileName = this.obtainRequestedFileName();
            System.out.println("1111111111111111111111  "+requestedFileName);
            File requestedFile = this.findFile(requestedFileName ,new File("./resources"));
            if (requestedFile ==null){
                // Error 404
                this.requestHeader.put("statusCode", "HTTP/1.1 404 Not Found");
                System.out.println("0000000000000000000 HTTP/1.1 404 Not Found");
            }
            else{
                //el archivo fue encontrado
                long contentLength = requestedFile.length();
                this.requestHeader.put("contentLength", String.valueOf(contentLength));
                this.requestHeader.put("statusCode", "HTTP/1.1 200 OK");
                System.out.println("0000000000000000000 HTTP/1.1 200 OK");
                String[] fileNameParts = requestedFileName.split("."); //obtener extension del archivo
                this.storeMimeType(fileNameParts[1]);
                //buscar payload solo si es un GET
                if (this.requestHeader.containsKey("GET")){
                    //ver si es una imagen o si es texto
                    if (fileNameParts[1].equals("jpg") || fileNameParts[1].equals("png")){
                        //es imagen
                        this.isImage = true;
                        try {
                            imagePayload = extractImageBytes(requestedFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        //es texto
                        try {
                            textPayload = FileUtils.readFileToString(requestedFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        }
    }

    private byte[] extractImageBytes (File image) throws IOException {
        // open image
        File imgPath = image;
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        return ( data.getData() );
    }

    private String obtainRequestedFileName () {
        String URL;
        if (this.requestHeader.containsKey("GET")){
            URL = this.requestHeader.get("GET");
        }
        else if (this.requestHeader.containsKey("HEAD")){
            URL = this.requestHeader.get("HEAD");
        }
        else if (this.requestHeader.containsKey("POST")){
            URL = this.requestHeader.get("POST");
        }
        else{
            return null;
        }
        String[] urlParts= URL.split("/");
        int numElements = urlParts.length;
        if (urlParts.length == 0){
            return ".";
        }
        return urlParts[numElements-1];
    }

    private File findFile(String name, File file)
    {
        
        File[] filesList = file.listFiles();
        if (filesList != null){
            System.out.println("          FILE LIST DIFERENTE DE NULL");
            for (File currentFile : filesList){
                if (currentFile.isDirectory()){
                    findFile( name , currentFile);
                }
                else if (name.equalsIgnoreCase(currentFile.getName())){
                    return currentFile;
                }
            }
        }
        return null;
    }

    private void storeMimeType (String extension){
        if (extension.equals("jpg") || extension.equals("jpeg")){
            this.requestHeader.put("mimeType", "image/jpeg");
        }
        else if (extension.equals("png")){
            this.requestHeader.put("mimeType", "image/jpeg");
        }
        else if (extension.equals("html")){
            this.requestHeader.put("mimeType", "text/html");
        }
        else if (extension.equals("css")){
            this.requestHeader.put("mimeType", "text/css");
        }
        else if (extension.equals("txt") || extension.equals("c") || extension.equals("h")){
            this.requestHeader.put("mimeType", "text/plain");
        }
        else{
            if (!this.requestHeader.containsKey("POST")){
                this.requestHeader.put("statusCode", "HTTP/1.1 406 Not Acceptable");
                System.out.println("0000000000000000000000000 HTTP/1.1 406 Not Acceptable");
            }
        }

    }

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public boolean isImage() {
        return isImage;
    }

    public byte[] getImagePayload() {
        return imagePayload;
    }

    public String getTextPayload() {
        return textPayload;
    }
}
