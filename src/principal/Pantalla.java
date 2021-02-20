package principal;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.*;

/**
 * Metodos funndamentales de una pantalla.
 */
public interface Pantalla {
    public void inicializarPantalla();

    public void pintarPantalla(Graphics g);

    public void ejecutarFrame();

    public void pulsarRaton(MouseEvent e);

    public void pulsarTecla(KeyEvent e);

    public void moverRaton(MouseEvent e);

    public void redimensionarPantalla(ComponentEvent e);

    public void rellenarFondo(Graphics g);
}
