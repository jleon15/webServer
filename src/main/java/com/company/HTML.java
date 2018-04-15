package com.company;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.*;
import java.util.*;

/**
 * Clase que genera el archivo html con la bitácora usando Apache Velocity.
 * @author María José Cubero Hidalgo
 * @author Josué León Sarkis
 */
public class HTML {
    private VelocityEngine velo;
    private Template tem;
    private VelocityContext context;
    private StringWriter writer;
    File statistics;
    private int fileNumber;
    private List<Integer> files;

    /**
     * Class Constructor
     * @throws Exception
     */
    public HTML() throws Exception {
        velo = new VelocityEngine();
        velo.init();
        tem = velo.getTemplate("HtmlTemplate.vm");
        context = new VelocityContext();
        writer = new StringWriter();
        files = new ArrayList();
    }

    public void generateHTMLLog() throws Exception {
        fileNumber = fileNumber + 1;
        files.add(fileNumber);
        VelocityEngine v = new VelocityEngine();
        v.init();
        Template t = v.getTemplate("ModuleStatsTemplate.vm");
        VelocityContext c = new VelocityContext();
        StringWriter w = new StringWriter();
        //c.put("estadisticasMod1",s.getStage1().getStatistics());

        t.merge(c,w);
        File bitacora = new File("bitácora.html");
        try (BufferedWriter buf = new BufferedWriter(new FileWriter(bitacora))) {
            buf.write(w.toString());
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the HTML.
     * @throws IOException
     */
    public void generateHtml() throws IOException {
        context.put("fileList", files);
        context.put("fileNumber", files.listIterator());
        tem.merge(context,writer);
        statistics = new File("statistics/GlobalStatistics.html");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(statistics))) {
            bw.write(writer.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
