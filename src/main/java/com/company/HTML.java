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
        fileNumber = 0;
        files = new ArrayList();
    }

    /**
     * Sets the initial parameters in the HTML.
     * @param iter
     * @param maxT
     * @param slowMode
     * @param k
     * @param n
     * @param p
     * @param m
     * @param t
     */
    public void setInitialParameters(int iter,double maxT,boolean slowMode, int k, int n, int p, int m, double t){
        context.put("numeroIteraciones",iter);
        context.put("tiempoSimulacion",maxT);
        context.put("modoLento",slowMode);
        context.put("k",k);
        context.put("n",n);
        context.put("p",p);
        context.put("m",m);
        context.put("t",t);
    }

    /**
     * Set the Global statistics in the HTML.
     * @param gui
     */
    public void setGlobalStatistics(GUI gui){
        context.put("estadisticasGui",gui);
    }

    /**
     * Generates the module's statistics in the HTML.
     * @param s
     * @param iter
     * @param maxT
     * @param slowMode
     * @param k
     * @param n
     * @param p
     * @param m
     * @param ti
     * @throws Exception
     */
    public void generateModuleStatistics(Simulation s, int iter,double maxT,boolean slowMode, int k, int n, int p, int m, double ti) throws Exception {
        fileNumber = fileNumber + 1;
        files.add(fileNumber);
        VelocityEngine v = new VelocityEngine();
        v.init();
        Template t = v.getTemplate("ModuleStatsTemplate.vm");
        VelocityContext c = new VelocityContext();
        StringWriter w = new StringWriter();
        c.put("estadisticasMod1",s.getStage1().getStatistics());
        c.put("estadisticasMod2",s.getStage2().getStatistics());
        c.put("estadisticasMod3",s.getStage3().getStatistics());
        c.put("estadisticasMod4",s.getStage4().getStatistics());
        c.put("estadisticasMod5",s.getStage5().getStatistics());
        c.put("numeroIteracion",fileNumber);
        c.put("numeroIteracion", fileNumber);
        c.put("numeroIteraciones",iter);
        c.put("tiempoSimulacion",maxT);
        c.put("modoLento",slowMode);
        c.put("k",k);
        c.put("n",n);
        c.put("p",p);
        c.put("m",m);
        c.put("t",ti);
        t.merge(c,w);
        File iterationStatistics = new File("statistics/statisticsIteration" + fileNumber +".html");
        try (BufferedWriter buf = new BufferedWriter(new FileWriter(iterationStatistics))) {
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
