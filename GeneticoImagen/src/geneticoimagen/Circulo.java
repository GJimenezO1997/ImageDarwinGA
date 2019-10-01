/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticoimagen;

import java.io.Serializable;
import java.util.Random;


/**
 * 
 * @author gusta_000
 *
 */
public class Circulo implements Serializable {
	
    private int x;
    private int y;
    private int diametro;
    private int r;
    private int g;
    private int b;
    private static Random rand = new Random();
    //Inicializar constrctor para generar parametros aleatorios
    public Circulo(int maxX, int maxY) {

        this.x = rand.nextInt(maxX);
        this.y = rand.nextInt(maxY);
        this.diametro = rand.nextInt(Math.max(maxX, maxY) / 5);
        //this.diametro = rand.nextInt(15);
        this.r = rand.nextInt(255);
        this.g = rand.nextInt(255);
        this.b = rand.nextInt(255);
        //System.out.println(x + " " + y + " " + diametro + " " + this.r + " " + g + " " + b);
    }

    public Circulo(int x, int y, int diametro, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Circulo circuloDuplicado() {
        Circulo resultado = new Circulo(x, y, diametro, r, g, b);
        return resultado;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDiametro() {
        return diametro;
    }

    public void setDiametro(int diametro) {
        this.diametro = diametro;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public static Random getRand() {
        return rand;
    }
}
