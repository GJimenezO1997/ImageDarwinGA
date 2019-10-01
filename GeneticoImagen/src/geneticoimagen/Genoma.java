/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticoimagen;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Vector;
import geneticoimagen.Main;

/**
 * 
 * @author gusta_000
 *
 */
public class Genoma implements Serializable {
	 
    private Vector<Circulo> circulos;
    private Poblacion poblacion;
    private int tamGenoma;
    private transient BufferedImage individuo;
    private boolean individuoValido;
    private long aptitud = Long.MAX_VALUE;

    public Genoma(Poblacion poblacion, int tamanio) {
        this.poblacion = poblacion;
        this.circulos = new Vector();
        this.tamGenoma = tamanio;
        this.generarCirculosAleatorios();
    }

    public Genoma circuloDuplicado() {
        Genoma nuevoGenoma = new Genoma(poblacion, 0);
        //circulos
        for (Circulo c : this.circulos) {
            nuevoGenoma.circulos.add(c.circuloDuplicado());
        }
        nuevoGenoma.tamGenoma = this.tamGenoma;
        nuevoGenoma.individuo = this.individuo;
        nuevoGenoma.aptitud = this.aptitud;
        return nuevoGenoma;
    }

    public void generarCirculosAleatorios() {
        for (int i = 0; i < tamGenoma; i++) {
            this.circulos.add(new Circulo(Main.getAncho(), Main.getAto()));
        }
    }

    public Vector<Circulo> getCirculos() {
        return circulos;
    }

    public void setCirculos(Vector<Circulo> poblacion) {
        this.circulos = poblacion;
    }

    public Poblacion getGen() {
        return poblacion;
    }

    public void setPoblacion(Poblacion poblacion) {
        this.poblacion = poblacion;
    }

    public BufferedImage getIndividuo() {
        return individuo;
    }

    public void setIndividuo(BufferedImage individuo) {
        this.individuo = individuo;
    }

    public boolean individuoValido() {
        return individuoValido;
    }

    public void setIndividuoValido(boolean individuoValido) {
        this.individuoValido = individuoValido;
    }

    public long getAptitud() {
        return aptitud;
    }

    public void setAptitud(long aptitud) {
        this.aptitud = aptitud;
    }
}
