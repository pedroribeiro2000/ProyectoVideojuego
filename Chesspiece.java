package ProyectoVideojuego;

import javax.swing.*;
import java.awt.*;

public abstract class Chesspiece {
    protected int row;
    protected int col;
    protected boolean isWhite;
    protected Image image;

    public Chesspiece(int row, int col, boolean isWhite, String imageName) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        // Quitar barra inicial y usar guion
        this.image = new ImageIcon(getClass().getResource("figures/" + imageName)).getImage();
    }

    public void draw(Graphics g, int x, int y, int size) {
        g.drawImage(image, x, y, size, size, null);
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isWhite() { return isWhite; }
}