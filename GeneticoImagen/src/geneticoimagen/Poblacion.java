/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticoimagen;

import java.io.Serializable;
import java.util.Vector;

/**
 * 
 * @author gusta_000
 *
 */
public class Poblacion implements Serializable {

    private int poblacionTam;
    private Vector<Genoma> genomas;
    private int genomaTam;

    public Poblacion() {
        this.genomas = new Vector();
    }
    //Función para generar la poblacion
    private void generarPoblacion() {
        for (int i = 0; i < poblacionTam; i++) {
            this.genomas.add(new Genoma(this, this.genomaTam));
        }
    }
    public int getTamPoblacion() {
        return poblacionTam;
    }

    public void setTamPoblacion(int poblacionTam) {
        this.poblacionTam = poblacionTam;
    }

    public Vector<Genoma> getGenomas() {
        return genomas;
    }

    public void setGenomas(Vector<Genoma> genomas) {
        this.genomas = genomas;
    }

    public int getTamGenoma() {
        return genomaTam;
    }

    public void setTamGenoma(int size) {
        this.genomaTam = size;
        this.generarPoblacion();
    }
}