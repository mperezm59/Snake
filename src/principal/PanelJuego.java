package principal;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import pantallas.PantallaInicio;

public class PanelJuego extends JPanel
        implements Runnable, MouseListener, MouseMotionListener, ComponentListener, KeyListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // INSTANCIA DE LA PANTALLA QUE SE EJECUTA EN ESTE MOMENTO
    private Pantalla pantallaActual;

    /**
     * Constructor
     */
    public PanelJuego() {
        this.setBounds(0, 0, 640, 480);
        this.pantallaActual = new PantallaInicio(this);
        pantallaActual.inicializarPantalla();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);

        new Thread(this).start();
    }

    // Método que se llama automáticamente para pintar el componente.
    @Override
    public void paintComponent(Graphics g) {
        pantallaActual.pintarPantalla(g);
    }

    @Override
    public void run() {
        while (true) {
            pantallaActual.ejecutarFrame();

            repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        pantallaActual.moverRaton(e);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        pantallaActual.redimensionarPantalla(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentShown(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pantallaActual.pulsarRaton(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void cambiarPantalla(Pantalla nuevaPantalla) {
        pantallaActual = nuevaPantalla;
        pantallaActual.inicializarPantalla();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pantallaActual.pulsarTecla(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
