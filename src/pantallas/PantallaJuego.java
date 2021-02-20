package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.plaf.FontUIResource;

import principal.PanelJuego;
import principal.Pantalla;
import principal.Sprite;

public class PantallaJuego implements Pantalla {

    // CONSTANTES COMIDA
    private static final int ALTOSPRITE = 10;
    private static final int ANCHOSPRITE = 10;

    // NUMERO TRAMPAS
    private static final int NUMEROTRAMPAS = 10;

    // CONSTANTES SNAKE
    private static final int VELOCIDAD = 10;

    // FUENTES
    private Font fuenteScore;

    // SCORE
    private static int score;

    // COMIDA
    private Sprite comida; // sprite que representa la comida de nuestra serviente
    private Sprite[] trampa; // sprite que se parece a la comida pero matara a nuestra serpierte
    private Sprite comida_fav; // sprite que representa la comida favorita de nuestra serpierte y sumara el
                               // doble de puntos

    // SNAKE
    private Sprite snake;
    private ArrayList<Sprite> lista;

    // PANEL DE JUEGO
    private PanelJuego panelJuego;

    // MOVIMIENTO
    private int direccion = KeyEvent.VK_ENTER;

    public PantallaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.fuenteScore = new FontUIResource("Arial", Font.BOLD, 15);
        score = 0;
    }

    @Override
    public void inicializarPantalla() {
        // Serpiente
        snake = new Sprite(Color.BLUE, ANCHOSPRITE, ALTOSPRITE, panelJuego.getWidth() / 2, panelJuego.getHeight() / 2,
                0, 0);

        // comida y trampas
        comida = new Sprite(Color.GREEN, ANCHOSPRITE, ALTOSPRITE, 150, 150, 0, 0);
        comida_fav = new Sprite(Color.ORANGE, ANCHOSPRITE, ALTOSPRITE, 350, 350, 0, 0);

        trampa = new Sprite[NUMEROTRAMPAS];
        for (int i = 0; i < trampa.length; i++) {
            trampa[i] = new Sprite(Color.RED, ANCHOSPRITE, ALTOSPRITE, generarPosIniX(), generarPosIniY(), 0, 0);
        }

        // añadimos a la lista
        lista = new ArrayList<>();
        lista.add(snake);
    }

    /**
     * genera un aleatorio para poner los Sprites en la pantalla
     * 
     * @return PosX
     */
    public int generarPosIniX() {
        int posX = 0;
        Random rd = new Random();

        posX = rd.nextInt(panelJuego.getWidth());

        // comprobar que el Sprite trampa entra entero en la pantalla
        while (posX + comida.getAncho() > panelJuego.getWidth()) {
            posX = rd.nextInt(panelJuego.getWidth());
        }

        return posX;
    }

    /**
     * genera un aleatorio para poner los Sprites en la pantalla
     * 
     * @return PosY
     */
    public int generarPosIniY() {
        int posY = 0;
        Random rd = new Random();

        posY = rd.nextInt(panelJuego.getWidth());

        // comprobar que el Sprite comida entra entero en la pantalla
        while (posY + comida.getAlto() > panelJuego.getHeight()) {
            posY = rd.nextInt(panelJuego.getHeight());
        }

        return posY;
    }

    @Override
    public void pintarPantalla(Graphics g) {
        // fondo negro
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());

        // pintamos la comida y las trampas
        comida.pintar(g);
        comida_fav.pintar(g);
        for (int i = 0; i < trampa.length; i++) {
            trampa[i].pintar(g);
        }
        // pintamos la serpiente para que al principio exita.
        snake.pintar(g);
        // pintamos la lista de sprite entera
        for (int i = 0; i < lista.size(); i++) {
            Sprite sprite = lista.get(i);
            sprite.pintar(g);
        }

        // pintamos el score
        g.setFont(fuenteScore);
        g.setColor(Color.WHITE);
        g.drawString("SCORE: " + score, 30, 30);

    }

    @Override
    public void ejecutarFrame() {
        // esperamos 20 milisegundos
        try {
            Thread.sleep(20);

            // nos movemos (intentamos)
            snake.mover();

            // actualizamos la velocidad
            teclado();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // añadimos un sprite a lista
        añadirLista();
        // borramos el que estaba añadido
        lista.remove(lista.size() - 1);

        // comida normal (sumamos 1 punto)
        if (snake.colisiona(comida)) {
            comprobarPosicionComida(comida);
            comprobarPosicionComida(comida_fav);
            for (int i = 0; i < trampa.length; i++) {
                comprobarPosicionComida(trampa[i]);
            }

            añadirLista();
            score++;
        }

        // trampa (perdemos)
        for (int i = 0; i < trampa.length; i++) {
            // si la serpiente colision con alguna trampa y se esta moviendo perdemos
            if (snake.colisiona(trampa[i]) && direccion != KeyEvent.VK_ENTER) {
                panelJuego.cambiarPantalla(new PantallaDerrota(panelJuego));
            } else {
                // si colisiona pero no se mueve significa que se esta cargando la pantalla para
                // poder jugar
                while (snake.colisiona(trampa[i]) && direccion == KeyEvent.VK_ENTER) {
                    comprobarPosicionComida(trampa[i]);
                }

            }
            // comprobamos que los sprites no se superpongan
            if (comida.colisiona(trampa[i]) || comida_fav.colisiona(trampa[i]) || comida.colisiona(comida_fav)) {
                comprobarPosicionComida(comida);
                comprobarPosicionComida(comida_fav);
                for (int j = 0; j < trampa.length; j++) {
                    comprobarPosicionComida(trampa[j]);
                }
            }
        }

        // comida favorita (sumamos el doble de puntos)
        if (snake.colisiona(comida_fav)) {
            comprobarPosicionComida(comida);
            comprobarPosicionComida(comida_fav);
            for (int i = 0; i < trampa.length; i++) {
                comprobarPosicionComida(trampa[i]);
            }

            añadirLista();
            score += 2;
        }
    }

    /**
     * metodo que añede un sprite con las caracteristas de snake para asi simular
     * que la serpiente crece
     */
    private void añadirLista() {
        lista.add(0, new Sprite(snake.getColor(), snake.getAncho(), snake.getAlto(), snake.getPosx(), snake.getPosy(),
                snake.getVelX(), snake.getVelY()));
    }

    /**
     * metodo donde se actualiza las velocidades dependiendo de la direccion
     */
    public void teclado() {

        switch (direccion) {
            case KeyEvent.VK_UP:
                snake.setVelY(-VELOCIDAD);
                snake.setVelX(0);
                // si se va por arriba aparece por abajo
                if (snake.getPosy() < 0) {
                    snake.setPosy(panelJuego.getHeight());
                }
                break;
            case KeyEvent.VK_LEFT:
                snake.setVelX(-VELOCIDAD);
                snake.setVelY(0);
                // si se va por izquierda aparec por derecha
                if (snake.getPosx() < 0) {
                    snake.setPosx(panelJuego.getWidth());
                }
                break;
            case KeyEvent.VK_RIGHT:
                snake.setVelX(VELOCIDAD);
                snake.setVelY(0);
                // si se va por derecha aparece popr izquierda
                if (snake.getPosx() > panelJuego.getWidth()) {
                    snake.setPosx(0);
                }
                break;
            case KeyEvent.VK_DOWN:
                snake.setVelY(VELOCIDAD);
                snake.setVelX(0);
                // si se va por abajo aparece por arriba
                if (snake.getPosy() > panelJuego.getHeight()) {
                    snake.setPosy(0);
                }
                break;
            default:
                snake.setVelY(0);
                snake.setVelX(0);
                break;
        }
    }

    /**
     * genera una posicion aleatoria para el sprite que recibe siempre dentro de la
     * pantalla.
     */
    public void comprobarPosicionComida(Sprite cambiarPos) {
        int posX = 0, posY = 0;
        Random rd = new Random();

        posX = rd.nextInt(panelJuego.getWidth());
        posY = rd.nextInt(panelJuego.getHeight());

        // comprobar que el Sprite comida entra entero en la pantalla
        while (posX + comida.getAncho() > panelJuego.getWidth()) {
            posX = rd.nextInt(panelJuego.getWidth());
        }
        while (posY + comida.getAlto() > panelJuego.getHeight()) {
            posY = rd.nextInt(panelJuego.getHeight());
        }

        cambiarPos.setPosx(posX);
        cambiarPos.setPosy(posY);
    }

    /**
     * devuelbe la puntuacion actual
     * 
     * @return
     */
    public static int puntuacion() {
        return score;
    }

    @Override
    public void pulsarRaton(MouseEvent e) {

    }

    @Override
    public void moverRaton(MouseEvent e) {

    }

    @Override
    public void redimensionarPantalla(ComponentEvent e) {

    }

    @Override
    public void rellenarFondo(Graphics g) {

    }

    @Override
    public void pulsarTecla(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_UP:
                if (direccion != KeyEvent.VK_DOWN) {
                    direccion = KeyEvent.VK_UP;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direccion != KeyEvent.VK_RIGHT) {
                    direccion = KeyEvent.VK_LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direccion != KeyEvent.VK_LEFT) {
                    direccion = KeyEvent.VK_RIGHT;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direccion != KeyEvent.VK_UP) {
                    direccion = KeyEvent.VK_DOWN;
                }
                break;
            default:
                direccion = KeyEvent.VK_ENTER;
                break;
        }
    }
}
