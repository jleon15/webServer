package com.company.Connection;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Bitácora que almacena los resultados y estadísticas obtenidas de las solicitudes y respuestas del servidor.
 *
 * @author María José Cubero Hidalgo
 * @author Josué León Sarkis
 */
public class LogWriter {
    private VelocityEngine velo;
    private Template tem;
    private VelocityContext context;
    private StringWriter writer;
    File bitacora;
    List<String[]> bitacoraSolicitudes;
    private String nombreBitacora;

    /**
     * Constructor de la clase
     *
     * @throws Exception
     */
    public LogWriter() throws Exception {
        this.bitacoraSolicitudes = new ArrayList<>();
        this.nombreBitacora = this.obtenerNombreBitacora();
    }

    /**
     * Genera la bitacora HTML.
     *
     * @throws IOException
     */
    public void generarBitacoraHTML() throws IOException {
        velo = new VelocityEngine();
        velo.init();
        tem = velo.getTemplate("HtmlTemplate.vm");
        context = new VelocityContext();
        writer = new StringWriter();

        context.put("bitacoraSolicitudes", this.bitacoraSolicitudes);
        context.put("solicitud", this.bitacoraSolicitudes.listIterator());
        tem.merge(context, writer);
        bitacora = new File(this.nombreBitacora);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bitacora))) {
            bw.write(writer.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File bitacora = new File(this.nombreBitacora);
        Desktop.getDesktop().browse(bitacora.toURI());
    }

    /**
     * Metodo que obtiene el nombre del archivo de la bitacora.
     * @return nombre del archivo a crear
     */
    private String obtenerNombreBitacora() {
        String total = "";
        String totalIteraciones = "";
        File archivo = new File("./src/main/resources/totalIteraciones.txt");
        if (!archivo.exists()) {
            System.err.println("El archivo totalIteraciones con el contador no existe, se llamará 'bitacora0' por defecto.");
            return "bitacora0.html";
        } else {
            try {
                FileReader flujoArchivo = new FileReader(archivo);
                BufferedReader bufferedReader = new BufferedReader(flujoArchivo);
                int i = 0;
                while ((totalIteraciones = bufferedReader.readLine()) != null) {
                    total = totalIteraciones;
                }
                bufferedReader.close();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivo));
                bufferedWriter.write(String.valueOf(Integer.parseInt(total) + 1));
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "bitacora" + total + ".html";
    }

    public List<String[]> getBitacoraSolicitudes() {
        return bitacoraSolicitudes;
    }
}