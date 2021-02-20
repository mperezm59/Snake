package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.plaf.FontUIResource;

import principal.PanelJuego;
import principal.Pantalla;

public class PantallaDerrota implements Pantalla {

    private final static String RUTA = "Ficheros/record.txt";

    private Color colorIntro;

    // PANEL JUEGO
    private PanelJuego panelJuego;

    // FONDO
    private BufferedImage img;
    private Image imagenReescalada;

    // VOLVER A JUGAR
    private Font fuenteJugar;

    // TITULO
    private Font fuente;

    // RECORD
    private int record, recordAux;
    private boolean superado;

    public PantallaDerrota(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.fuenteJugar = new FontUIResource("Times New Roman", Font.BOLD, 20);
        this.fuente = new FontUIResource("Times New Roman", Font.ITALIC, 40);
        this.colorIntro = Color.BLACK;
    }

    @Override
    public void inicializarPantalla() {
        // Fondo
        try {
            img = ImageIO.read(new File("Imagenes/derrota.jpg"));
            imagenReescalada = img.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
                    BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Record
        superado = false;
        record = PantallaJuego.puntuacion();
        actualizarRecord();
    }

    /**
     * actualiza el record guardandolo en un fichero siempre y cuando supere al
     * record ya guardado
     */
    private void actualizarRecord() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA));

            // lemos el fichero y vemos que record esta almacenado
            String linea;
            while ((linea = br.readLine()) != null) {
                recordAux = Integer.parseInt(linea);
            }
            br.close();

            // si nuestro record supera al record almacenado se sobreescribe
            if (record > recordAux) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA));
                bw.write(record + "");
                bw.close();
                superado = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pintarPantalla(Graphics g) {
        rellenarFondo(g);

        // Record
        g.setFont(fuente);
        g.setColor(new Color(0, 255, 112));
        if (superado) {
            g.drawString("Nuevo record: " + record, panelJuego.getWidth() / 2 - 100, 75);
        } else {
            g.drawString("El record es " + recordAux, panelJuego.getWidth() / 2 - 100, 75);
        }

        // Volver a jugar
        g.setFont(fuenteJugar);
        g.setColor(colorIntro);
        g.drawString("Press Enter para volver a jugar.", 300, 400);

    }

    @Override
    public void ejecutarFrame() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (colorIntro == Color.ORANGE) {
            colorIntro = Color.GREEN;
        } else {
            colorIntro = Color.ORANGE;
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
