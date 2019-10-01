/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticoimagen;

import java.awt.Color;
import geneticoimagen.Main;

/**
 * @author gusta_000
 *
 */
public class Aptitud extends Thread {
	
    private Genoma genoma;

    public Aptitud(Genoma genoma) {    
        this.genoma = genoma;
    }
    public void run() {
        getFuncAptitud(this.genoma);
    }
    //La función aptitud es obtenida mediante el calculo de sitancias de el color de cada circulo generado
    public static long getFuncAptitud(Genoma genoma) {
        if (!genoma.individuoValido()) {
            return Long.MAX_VALUE;
        } else {
            long aptitud = Long.MAX_VALUE;
            for (int x = 0; x < Main.getAncho(); x++) {
                for (int y = 0; y < Main.getAto(); y++) {
                    Color obtenerColor = new Color(genoma.getIndividuo().getRGB(x, y));
                    //Color colorOriginal = Main.getPhenotypeColors()[x][y];
                    aptitud += calcularDifDecolores(Main.getIndividuoR()[x][y], Main.getIndividuoG()[x][y], Main.getIndividuoB()[x][y], obtenerColor);
                }
            }
            genoma.setAptitud(aptitud);
            return aptitud;
        }
    }

    public static long calcularDifDecolores(long r1, long g1, long b1,  Color c2) {
        long diff = 0;
        long r2 = c2.getRed(); 
        long g2 = c2.getGreen(); 
        long b2 = c2.getBlue();
      
        long dR = (r2-r1)*(r2-r1);
        long dG = (g2-g1)*(g2-g1);
        long dB = (b2-b1)*(b2-b1);
        diff = dR + dG + dB;
        return diff;
    }
}
