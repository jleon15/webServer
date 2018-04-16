package com.company.Connection;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

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

    /**
     * Constructor de la clase
     * @throws Exception
     */
    public LogWriter() throws Exception {
        this.bitacoraSolicitudes = new ArrayList<>();
    }

    /**
     * Genera la bitacora HTML.
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
        tem.merge(context,writer);
        bitacora = new File("Bitacora.html");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bitacora))) {
            bw.write(writer.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getBitacoraSolicitudes() {
        return bitacoraSolicitudes;
    }
}
