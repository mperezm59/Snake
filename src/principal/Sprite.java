package principal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
    private BufferedImage buffer;
    private Color color = Color.BLACK;
    // variables de dimension
    private int ancho;
    private int alto;
    // variables de colocacion
    private int posx;
    private int posy;
    // velocidades
    private int velX;
    private int velY;

    public Sprite(Color color, int ancho, int alto, int posx, int posy, int velX, int velY) {
        this.color = color;
        this.ancho = ancho;
        this.alto = alto;
        this.posx = posx;
        this.posy = posy;
        this.velX = velX;
        this.velY = velY;
        inicializarBuffer();
    }

    /**
     * crea una imagen (buffer) vacio del color del Sprite
     */
    protected void inicializarBuffer() {
        buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

        Graphics graficos = buffer.getGraphics();
        graficos.setColor(color);
        graficos.fillRect(0, 0, ancho, alto);
        graficos.dispose();
    }

    public Sprite(String rutaImagen, int ancho, int alto, int posx, int posy, int velX, int velY) {
        this.ancho = ancho;
        this.alto = alto;
        this.posx = posx;
        this.posy = posy;
        this.velX = velX;
        this.velY = velY;
        inicializarBuffer(rutaImagen);
    }

    /**
     * crea una imagen (buffer) vacio del color del Sprite
     */
    protected void inicializarBuffer(String rutaImagen) {
        buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        try {
            buffer = ImageIO.read(new File(rutaImagen));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * estanpa el grafico en la ventana.
     * 
     * @param g
     */
    public void pintar(Graphics g) {
        g.drawImage(buffer.getScaledInstance(getAncho(), getAlto(), BufferedImage.SCALE_SMOOTH), posx, posy, null);
    }

    public void mover() {
        // mover el cuadrado
        this.setPosx(posx + velX);
        this.setPosy(posy + velY);
    }

    public Boolean colisiona(Sprite otroSprite) {
        boolean colision = false;
        boolean colisionEjeX = false;
        boolean colisionEjeY = false;
        int borde_derecho, borde_abajo;

        // eje X
        if (this.posx < otroSprite.posx) {
            borde_derecho = this.posx + this.ancho;
            if (borde_derecho >= otroSprite.posx) {
                colisionEjeX = true;
            }
        } else {
            borde_derecho = otroSprite.posx + otroSprite.ancho;
            if (borde_derecho >= this.posx) {
                colisionEjeX = true;
            }
        }

        if (!colisionEjeX) {
            return false;
        }

        // eje Y
        if (this.posy < otroSprite.posy) {
            borde_abajo = this.posy + this.alto;
            if (borde_abajo >= otroSprite.posy) {
                colisionEjeY = true;
            }
        } else {
            borde_abajo = otroSprite.posy + otroSprite.alto;
            if (borde_abajo >= this.posy) {
                colisionEjeY = true;
            }
        }

        if (colisionEjeX && colisionEjeY) {
            colision = true;
        }

        return colision;
    }

    public BufferedImage getBuffer() {
        return this.buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAncho() {
        return this.ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getPosx() {
        return this.posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return this.posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getVelX() {
        return this.velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getVelY() {
        return this.velY;
    }

}
