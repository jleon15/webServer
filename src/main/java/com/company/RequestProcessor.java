package com.company;

import javafx.util.Pair;

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
    private List<Pair<String,String>> postBody;
    private List<String> mimeTypes;
    private boolean isImage;

    public RequestProcessor(){
        this.mimeTypes = new ArrayList<String>();
        this.fillMimetypesList(this.mimeTypes);
        isImage = false;
    }

    public void processRequest(){
        if (this.obtainRequestedFileName().equals(null)){
            //Error 501
            this.requestHeader.put("statusCode", "HTTP/1.1 501 Not Implemented");
        }
        else{
            String requestedFileName = this.obtainRequestedFileName();
            File requestedFile = this.findFile(requestedFileName ,new File("./resources"));
            if (requestedFile.equals(null)){
                // Error 404
                this.requestHeader.put("statusCode", "HTTP/1.1 404 Not Found");
            }
            else{
                //ver si es una imagen o si es texto
                String[] fileNameParts = requestedFileName.split(".");
                this.storeMimeType(fileNameParts[1]);
                if (fileNameParts[1].equals("jpg") || fileNameParts[1].equals("png")){
                    //es imagen
                    this.isImage = true;
                    try {
                        byte[] imageBytes = extractImageBytes(requestedFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //es texto
                  //  String str = FileUtils.readFileToString(requestedFile);

                }
            }
        }
    }

    public byte[] extractImageBytes (File image) throws IOException {
        // open image
        File imgPath = image;
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        return ( data.getData() );
    }

    public String obtainRequestedFileName () {
        String URL = "";
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
            return URL;
        }
        String[] urlParts= URL.split("/");
        int numElements = urlParts.length;
        return urlParts[numElements];
    }

    public File findFile(String name, File file)
    {
        File[] filesList = file.listFiles();
        if (filesList != null){
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

    public void storeMimeType (String extension){
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

    }

    private void fillMimetypesList(List<String> mimetypes){
        this.mimeTypes.add("text/css");
        this.mimeTypes.add("text/html");
        this.mimeTypes.add("text/plain");
        this.mimeTypes.add("image/jpeg");
        this.mimeTypes.add("image/png");
    }

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public void setPostBody(List<Pair<String, String>> postBody) {
        this.postBody = postBody;
    }

    public boolean isImage() {
        return isImage;
    }
}
