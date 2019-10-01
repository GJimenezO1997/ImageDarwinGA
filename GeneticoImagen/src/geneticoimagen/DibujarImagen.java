/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticoimagen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import geneticoimagen.Aptitud;
import geneticoimagen.Circulo;
import geneticoimagen.Genoma;

/**
 * @author gusta_000
 *
 */
public class DibujarImagen extends Thread {

    private BufferedImage g2d;
    private Genoma g;
    private int ancho;
    private int alto;

    public DibujarImagen(int ancho, int alto, Genoma g) {
        this.g = g;
        this.ancho = ancho;
        this.alto = alto;
    }
    @Override
    public void run() {
        g2d = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig2 = g2d.createGraphics();
        //Colocar circulos con colores y posiciones aleatorias
        for (int i = 50+1; i >= 0; i--) {
                for (Circulo c : g.getCirculos()) {
                    if (c.getDiametro() == i) {
                        ig2.setColor(new Color(c.getR(), c.getG(), c.getB()));
                        ig2.fillOval(c.getX()-(c.getDiametro()/2), c.getY()-(c.getDiametro()/2), c.getDiametro(), c.getDiametro());
                    }
                }
            
        }
        g.setIndividuo(g2d);
        g.setIndividuoValido(true);
        new Aptitud(g).start();
    }

}
