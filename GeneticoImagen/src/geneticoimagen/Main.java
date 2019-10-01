/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticoimagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import geneticoimagen.Aptitud;
import geneticoimagen.Circulo;
import geneticoimagen.Genoma;
import geneticoimagen.Poblacion;

/**
 * @author gusta_000
 *
 */
public class Main {
	
    private static String imagen = "dar.jpg";
    private static BufferedImage imagenPrototipo;
    private Poblacion poblacion;
    private static int ancho;
    private static int altura;
    private  int tamCirculos = 14;
    //Cambiar población en dos clases
    private  int numeroPoblacion = 24;
    private static int[][] individuoR;
    private static int[][] individuoG;
    private static int[][] individuoB;
    private static int ultimaMejora = 1;
    private static int deltaG = 1;
    
    private static double probabilidadMutacion = 0.045;		
    private static int colorMax = 50;
    private static int cambioMaxDiametro = 50;
    private static int posicionMaxima = 50;

    //Conservar valores inicales en las sig variables
    private static int colorMaxDefault = colorMax;
    private static int posicionMaximaDefault = posicionMaxima;
    private static int cambioMaxDiametroDefault = cambioMaxDiametro;
    private static double probabilidadDeMutacion = probabilidadMutacion;

    public Main() throws IOException, ClassNotFoundException {
        
        imagenPrototipo = ImageIO.read(new File(imagen));
        Main.ancho = imagenPrototipo.getWidth();
        Main.altura = imagenPrototipo.getHeight();
        individuoR = new int[Main.ancho][Main.altura];
        individuoG = new int[Main.ancho][Main.altura];
        individuoB = new int[Main.ancho][Main.altura];
        
        for (int i = 0; i < Main.ancho; i++) {
            for (int j = 0; j < Main.altura; j++) {
                individuoR[i][j] = new Color(imagenPrototipo.getRGB(i, j)).getRed();
                individuoG[i][j] = new Color(imagenPrototipo.getRGB(i, j)).getGreen();
                individuoB[i][j] = new Color(imagenPrototipo.getRGB(i, j)).getBlue();
            }
        }
        //Inicializar la población
        this.poblacion = new Poblacion();
        this.poblacion.setTamPoblacion(numeroPoblacion);
        this.poblacion.setTamGenoma(Main.ancho * Main.altura / tamCirculos);

        Genoma genSeleccionado = this.poblacion.getGenomas().get(0);
        for (Genoma g : this.poblacion.getGenomas()) { 
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            new DibujarImagen(ancho, altura, g).start();
        }
        //Calcular funcion aptitud
        for (Genoma g : this.poblacion.getGenomas()) {
            Aptitud.getFuncAptitud(g);
            if (genSeleccionado.getAptitud() > g.getAptitud()) {
                genSeleccionado = g;
            }
        }
        Interfaz gui = null;
       
        gui = new Interfaz(imagenPrototipo, this);
        gui.imagenCandidata(genSeleccionado.getIndividuo());
          
        int i = 0;
        ultimaMejora = 1;
        long aptitudAnterior = genSeleccionado.getAptitud();
        while (true) {
            i++;
            
            this.poblacion.setGenomas(seleccion(this.poblacion.getGenomas()));
            BufferedImage allImages =  null;
            allImages = gui.combinarIndividuos();
            //Mostrar el Genoma seleccionado en pantalla
            
            gui.imagenCandidata(this.poblacion.getGenomas().get(0).getIndividuo());
            
            if (this.poblacion.getGenomas().get(0).getAptitud() < aptitudAnterior) {
                System.out.println("Generacion: " + i +  " \tAptitud: " + (aptitudAnterior - this.poblacion.getGenomas().get(0).getAptitud()) +" \t" );
                aptitudAnterior = this.poblacion.getGenomas().get(0).getAptitud();
                ultimaMejora = i;
            }
            deltaG = Math.max(1, i - ultimaMejora);
         
            Vector<Genoma> mejoresGenomas = new Vector<Genoma>();
            mejoresGenomas.add(this.poblacion.getGenomas().get(0).circuloDuplicado());
            mejoresGenomas.add(this.poblacion.getGenomas().get(1).circuloDuplicado());
            mejoresGenomas.add(this.poblacion.getGenomas().get(2).circuloDuplicado());

            //Mutación
            for(Genoma g : this.poblacion.getGenomas()) {
                mutacion(g);
            }

            //Cruza
            Vector<Genoma> nuevosGenomas = new Vector<Genoma>();
            for (Genoma madre : this.poblacion.getGenomas()) {
                //Seleccionar el padre 
                int index = Circulo.getRand().nextInt(this.poblacion.getGenomas().size());
                Genoma padre = this.poblacion.getGenomas().get(index);
                nuevosGenomas.add(Cruza(madre, padre));
            }
            this.poblacion.setGenomas(nuevosGenomas);
            this.poblacion.getGenomas().remove(this.poblacion.getGenomas().size()-1);
            this.poblacion.getGenomas().remove(this.poblacion.getGenomas().size()-1);
            this.poblacion.getGenomas().remove(this.poblacion.getGenomas().size()-1);
            this.poblacion.getGenomas().add(0, mejoresGenomas.get(2));
            this.poblacion.getGenomas().add(0, mejoresGenomas.get(1));
            this.poblacion.getGenomas().add(0, mejoresGenomas.get(0));

            for (Genoma g : this.poblacion.getGenomas()) {   
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                new DibujarImagen(ancho, altura, g).start();
            }
        }
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Main m = new Main();
    }
    
    public static Vector<Genoma> seleccion(Vector<Genoma> genomas) {
        Vector<Genoma> result = new Vector<Genoma>();
        while(!genomas.isEmpty()) {
            Genoma seleccionado = genomas.get((int)Math.random()*genomas.size());
            for(Genoma g : genomas) {
                if (g.getAptitud() < seleccionado.getAptitud()) {
                    seleccionado = g;
                }
            }
            result.add(seleccionado);
            genomas.remove(seleccionado);
        }
        
        return result;
    }
    
    public static Genoma Cruza(Genoma madre, Genoma padre) {

        Genoma hijos = new Genoma(madre.getGen(), 0);
        for (int i = 0; i < madre.getCirculos().size(); i++) {
            if (Circulo.getRand().nextBoolean()) {
                hijos.getCirculos().add(madre.getCirculos().get(i));
            } else {
                //System.out.println(padre.getCirculos().size());
                hijos.getCirculos().add(padre.getCirculos().get(i));
            }
        }

        return hijos;
    }
    //La mutación se realiza cambiando las propiedades de los circulos, como diametro, posicion y color
    public static void mutacion(Genoma g) {
        for (Circulo c : g.getCirculos()) {
            //Se realiza una mutacion en el color del circulo
            c = nuevoColorDeCirculo(c);

            int nuevoDiametro = c.getDiametro();
            probabilidadMutacion = Math.max(1.0d / Double.valueOf(Main.getDeltaG()), probabilidadDeMutacion);

            cambioMaxDiametro = (int)Math.round(Math.max(1.0, Math.floor(Double.valueOf(cambioMaxDiametroDefault) / Double.valueOf(Main.getDeltaG()))));
            //System.out.println(cambioMaxDiametro);
            posicionMaxima = (int)Math.round(Math.max(1.0, Math.floor(Double.valueOf(posicionMaximaDefault) / Double.valueOf(Main.getDeltaG()))));

            if (Circulo.getRand().nextDouble() < probabilidadMutacion) {
                nuevoDiametro = (int) Math.floor(nuevoDiametro+(cambioMaxDiametro * (Circulo.getRand().nextDouble()-0.5)*2.0 ));
                nuevoDiametro = Math.max(nuevoDiametro, 1);
                nuevoDiametro = Math.min(nuevoDiametro, 50);
                c.setDiametro(nuevoDiametro);
            }
            int nuevoX = c.getX();
            if (Circulo.getRand().nextDouble() < probabilidadMutacion) {
                nuevoX = (int) Math.floor(nuevoX+(posicionMaxima * (Circulo.getRand().nextDouble()-0.5)*2.0 ));
                nuevoX = Math.max(nuevoX, 0);
                nuevoX = Math.min(nuevoX, Main.getAncho()-1);
                c.setX(nuevoX);
            }
            int nuevoY = c.getY();
            if (Circulo.getRand().nextDouble() < probabilidadMutacion) {
                nuevoY = (int) Math.floor(nuevoY+(posicionMaxima * (Circulo.getRand().nextDouble()-0.5)*2.0 ));
                nuevoY = Math.max(nuevoY, 0);
                nuevoY = Math.min(nuevoY, Main.getAto()-1);
                c.setY(nuevoY);
                //c.setDiameter(nuevoDiametro);
            }
        }
    }
    
    //Funcion utilizada en la mutación 
    private static Circulo nuevoColorDeCirculo(Circulo c) {
        if (Circulo.getRand().nextDouble() < probabilidadMutacion) {
            //Color centerColor = new Color();
            int r = 0;
            int g = 0;
            int b = 0;
            colorMax = (int)Math.round(Math.max(1.0, Math.floor(Double.valueOf(colorMaxDefault) / Double.valueOf(Main.getDeltaG()))));
            if (Main.getDeltaG() > 100) {
                if (Circulo.getRand().nextBoolean()) {
                    r = Math.max(0, Math.min(c.getR() - Circulo.getRand().nextInt(colorMax), 255));
                } else {
                    r = Math.max(0, Math.min(c.getR() + Circulo.getRand().nextInt(colorMax), 255));
                }
                if (Circulo.getRand().nextBoolean()) {
                    g = Math.max(0, Math.min(c.getG() - Circulo.getRand().nextInt(colorMax), 255));
                } else {
                    g = Math.max(0, Math.min(c.getG() + Circulo.getRand().nextInt(colorMax), 255));
                }
                if (Circulo.getRand().nextBoolean()) {
                    b = Math.max(0, Math.min(c.getB() - Circulo.getRand().nextInt(colorMax), 255));
                } else {
                    b = Math.max(0, Math.min(c.getB() + Circulo.getRand().nextInt(colorMax), 255));
                }
            } else {
                if (individuoR[c.getX()][c.getY()] <= c.getR()) {
                    r = Math.max(0, Math.min(c.getR() - Circulo.getRand().nextInt(colorMax), 255));
                } else {
                    r = Math.max(0, Math.min(c.getR() + Circulo.getRand().nextInt(colorMax), 255));
                }
                if (individuoG[c.getX()][c.getY()] <= c.getG()) {
                    g = Math.max(0, Math.min(c.getG() - Circulo.getRand().nextInt(colorMax), 255));
                } else {
                    g = Math.max(0, Math.min(c.getG() + Circulo.getRand().nextInt(colorMax), 255));
                }
                if (individuoB[c.getX()][c.getY()] <= c.getB()) {
                    b = Math.max(0, Math.min(c.getB() - Circulo.getRand().nextInt(colorMax), 255));
                } else {
                    b = Math.max(0, Math.min(c.getB() + Circulo.getRand().nextInt(colorMax), 255));
                }
            }
            c.setR(r);
            c.setG(g);
            c.setB(b);
            return c;
        } else {
            return c;
        }
    }
    public static int getAncho() {
        return ancho;
    }

    public static int getAto() {
        return altura;
    }

    public static BufferedImage imagenPrototipo() {
        return imagenPrototipo;
    }
    
    public static int[][] getIndividuoR() {
        return individuoR;
    }

    public static int[][] getIndividuoG() {
        return individuoG;
    }

    public static int[][] getIndividuoB() {
        return individuoB;
    }

    public Poblacion getPopulation() {
        return poblacion;
    }

    public static int getDeltaG() {
        return deltaG;
    }
}