package com.company;

import javafx.util.Pair;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Esta clase se encarga de procesar la solicituda ya parseada y obtener el payload que solicitan,
 * para posteriormente pasarle ese payload a {@link com.company.RequestManager}
 *
 * @author María José Cubero Hidalgo
 * @author Josué León Sarkis
 */
public class RequestProcessor {

    /**
     * El hashmap con el request del header.
     */
    private HashMap<String, String> requestHeader;

    /**
     * Define si el payload es una imagen.
     */
    private boolean isImage;

    /**
     * El array de bytes donde se guarda el payload si es una imagen.
     */
    private byte[] imagePayload;

    /**
     * La string donde se guarda el payload si es una imagen.
     */
    private String textPayload;

    /**
     * Busca el archivo, el tipo de request, obtiene la respuesta esperada, guarda datos del header.
     */
    public boolean processRequest() {
        boolean isImage= false;
        String requestedFileName = this.obtainRequestedFileName();
        if (requestedFileName == null) {
            //Error 501
            this.requestHeader.put("statusCode", "HTTP/1.1 501 Not Implemented");
            System.out.println("0000000000000000000 HTTP/1.1 501 Not Implemented");
        } else if (requestedFileName.equals("noURL")) { //cuando no se solicita un elemento
            this.requestHeader.put("statusCode", "HTTP/1.1 200 OK");
            System.out.println("0000000000000000000 HTTP/1.1 200 OK");
        } else {
            System.out.println("1111111111111111111111  " + requestedFileName);
            File requestedFile = this.findFile(requestedFileName, new File("./src/main/resources"));
            if (requestedFile == null) {
                // Error 404
                this.requestHeader.put("statusCode", "HTTP/1.1 404 Not Found");
                System.out.println("0000000000000000000 HTTP/1.1 404 Not Found");
            } else {
                //el archivo fue encontrado
                long contentLength = requestedFile.length();
                this.requestHeader.put("contentLength", String.valueOf(contentLength));
                this.requestHeader.put("statusCode", "HTTP/1.1 200 OK");
                System.out.println("0000000000000000000 HTTP/1.1 200 OK");
                String[] fileNameParts = requestedFileName.split("\\."); //obtener extension del archivo
                System.out.println("--------------------------" + requestedFileName);
                this.storeMimeType(fileNameParts[1]);
                //buscar payload solo si es un GET
                if (this.requestHeader.containsKey("GET")) {
                    //ver si es una imagen o si es texto
                    if (fileNameParts[1].equals("jpg") || fileNameParts[1].equals("png")) {
                        //es imagen
                        isImage = true;
                        try {
                            imagePayload = extractImageBytes(requestedFile, fileNameParts[1]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
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
        return isImage;
    }

    /**
     * Toma una imagen y la pasa de tipo File a un array de bytes.
     *
     * @param image
     * @return Array de bytes con la imagen.
     * @throws IOException
     */
    private byte[] extractImageBytes(File image, String imageType) throws IOException {
        // open image
        BufferedImage bufferedImage = ImageIO.read(image);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(imageType.equals("jpg")) {
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        } else {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        }

        return byteArrayOutputStream.toByteArray();
    }


    /**
     * Lee el request y consigue el nombre del archivo que se está solicitando acceder.
     *
     * @return Nombre del archivo donde se hace el request.
     */
    private String obtainRequestedFileName() {
        String URL;
        if (this.requestHeader.containsKey("GET")) {
            URL = this.requestHeader.get("GET");
        } else if (this.requestHeader.containsKey("HEAD")) {
            URL = this.requestHeader.get("HEAD");
        } else if (this.requestHeader.containsKey("POST")) {
            URL = this.requestHeader.get("POST");
        } else {
            return null;
        }
        String[] urlParts = URL.split("/");
        int numElements = urlParts.length;
        if (urlParts.length == 0) {
            System.out.println("FDGHJVFKVIJIVG++++++++++++++++++++++++++++");
            return "noURL";
        }
        return urlParts[numElements - 1];
    }

    /**
     * Busca el archivo con el nombre de la imagen.
     *
     * @param name
     * @param file
     * @return Archivo que se ha solicitado en el request.
     */
    private File findFile(String name, File file) {

        File[] filesList = file.listFiles();
        if (filesList != null) {
            for (File currentFile : filesList) {
                if (currentFile.isDirectory()) {
                    findFile(name, currentFile);
                } else if (name.equalsIgnoreCase(currentFile.getName())) {
                    return currentFile;
                }
            }
        }
        return null;
    }

    /**
     * Guarda el mimetype del request en el request header.
     *
     * @param extension
     */
    private void storeMimeType(String extension) {
        if (extension.equals("jpg") || extension.equals("jpeg")) {
            this.requestHeader.put("mimeType", "image/jpeg");
        } else if (extension.equals("png")) {
            this.requestHeader.put("mimeType", "image/jpeg");
        } else if (extension.equals("html")) {
            this.requestHeader.put("mimeType", "text/html");
        } else if (extension.equals("css")) {
            this.requestHeader.put("mimeType", "text/css");
        } else if (extension.equals("txt") || extension.equals("c") || extension.equals("h")) {
            this.requestHeader.put("mimeType", "text/plain");
        } else {
            if (!this.requestHeader.containsKey("POST")) {
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
