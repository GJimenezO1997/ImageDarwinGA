/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticoimagen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import geneticoimagen.Main;

/**
 *
 * @author gusta_000
 */
public class Interfaz extends javax.swing.JFrame {
    
    private ImageIcon imagenMuestra;
    private ImageIcon imagenOriginal;
    private Main mn;
    private int poblacion = 24;
    
    /**
     * Creates new form Interfaz
     */
    
    public Interfaz(BufferedImage prototype, Main mn) {
        initComponents();
        this.getContentPane().setLayout(new BorderLayout());
        this.imagenOriginal = new ImageIcon(prototype);
        this.getContentPane().add(new JLabel(imagenOriginal), BorderLayout.WEST);
        this.mn = mn;
        this.imagenMuestra = new ImageIcon("img.jpg");
        this.getContentPane().add(new JLabel(imagenMuestra), BorderLayout.EAST);  
        this.pack();
        this.setVisible(true);
        //this.setSize(900,900);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Darwin");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 559, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void imagenPototitpo(BufferedImage image) {
        if (image != null) {
            this.imagenOriginal.setImage(image);
            this.pack();
            this.repaint();
        }
    }

    public void imagenCandidata(BufferedImage imagenMuestra) {
        if (imagenMuestra != null) {
            this.imagenMuestra.setImage(imagenMuestra);
            this.repaint();
        }
    }

    public BufferedImage combinarIndividuos() {
        int longTotalImagen = (int) Math.ceil(Math.sqrt(poblacion));
        int anchoTotal = longTotalImagen * Main.getAncho();
        int altoTotal = longTotalImagen * Main.getAto();
        BufferedImage result = new BufferedImage(anchoTotal, altoTotal, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, anchoTotal, altoTotal);
        g2d.drawImage(Main.imagenPrototipo(), 0, 0, null);
        for (int i = 0; i < longTotalImagen; i++) {
            for (int j = 0; j < longTotalImagen; j++) {
                if (i != 0 || j != 0) {				
                    g2d.drawImage(this.mn.getPopulation().getGenomas().get(j*5+i-1).getIndividuo(), i*Main.getAncho(), j*Main.getAto(), null);
                    if (j*5+i-1 >= poblacion) {
                        break;
                    }
                }
            }
        }
        g2d.dispose();
        return result;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
