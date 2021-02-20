package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.plaf.FontUIResource;

import principal.PanelJuego;
import principal.Pantalla;
import principal.Sprite;

public class PantallaInicio implements Pantalla {

    private static final int ANCHOSPRITE = 10;
    private static final int ALTOSPRITE = 10;

    private Color colorIntro;
    private Font fuenteGrandre, fuenteCambio, fuentePistas;
    private PanelJuego panelJuego;

    // FONFO
    private BufferedImage img;
    private Image imagenReescalada;

    private Sprite verde, rojo, naranja;

    public PantallaInicio(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.fuenteGrandre = new FontUIResource("Arial", Font.BOLD, 20);
        this.fuenteCambio = new FontUIResource("Arial", Font.BOLD, 35);
        this.fuentePistas = new FontUIResource("Arial", Font.BOLD, 15);

        this.colorIntro = Color.RED;
    }

    @Override
    public void inicializarPantalla() {
        // Fondo
        try {
            img = ImageIO.read(new File("Imagenes/inicio.jpg"));
            imagenReescalada = img.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
                    BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sprites
        verde = new Sprite(Color.GREEN, ANCHOSPRITE, ALTOSPRITE, panelJuego.getWidth() / 2 + 160,
                panelJuego.getHeight() / 2 - 100, 0, 0);
        rojo = new Sprite(Color.RED, ANCHOSPRITE, ALTOSPRITE, panelJuego.getWidth() / 2 + 160,
                panelJuego.getHeight() / 2 - 50, 0, 0);
        naranja = new Sprite(Color.ORANGE, ANCHOSPRITE, ALTOSPRITE, panelJuego.getWidth() / 2 + 160,
                panelJuego.getHeight() / 2, 0, 0);

    }

    @Override
    public void pintarPantalla(Graphics g) {
        rellenarFondo(g);
        verde.pintar(g);
        rojo.pintar(g);
        naranja.pintar(g);

        // titulo
        g.setFont(fuenteCambio);
        g.setColor(Color.ORANGE);
        g.drawString("SNAKE!!!!", 55, 55);

        // jugar
        g.setFont(fuenteGrandre);
        g.setColor(colorIntro);
        g.drawString("Press Enter para jugar.", panelJuego.getWidth() - 300, panelJuego.getHeight() - 75);

        // pistas
        g.setFont(fuentePistas);
        g.setColor(Color.WHITE);
        g.drawString("RECUERDE!!!!", 100, 100);

        // pista 1
        g.drawString("Suma 1 punto: ", 370, 150);
        g.drawString("Pierdes: ", 410, 200);
        g.drawString("Suma 2 puntos: ", 360, 250);
    }

    @Override
    public void ejecutarFrame() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // cambio el color
        if (colorIntro == Color.RED) {
            colorIntro = Color.GREEN;
        } else {
            colorIntro = Color.RED;
        }

        // cabio tamaño de la fuente
        if (fuenteCambio.getSize() == 35) {
            fuenteCambio = new FontUIResource("Arial", Font.BOLD, 25);
        } else {
            fuenteCambio = new FontUIResource("Arial", Font.BOLD, 35);
        }
    }

    @Override
    public void pulsarRaton(MouseEvent e) {
    }

    @Override
    public void moverRaton(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void redimensionarPantalla(ComponentEvent e) {
        if (panelJuego.getWidth() > 0 && panelJuego.getHeight() > 0) {
            imagenReescalada = img.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
                    BufferedImage.SCALE_SMOOTH);
        }
    }

    @Override
    public void rellenarFondo(Graphics g) {
        g.drawImage(imagenReescalada, 0, 0, null);
    }

    @Override
    public void pulsarTecla(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            panelJuego.cambiarPantalla(new PantallaJuego(panelJuego));
        }
    }

}
