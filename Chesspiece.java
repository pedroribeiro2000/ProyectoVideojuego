package ProyectoVideojuego;

import java.awt.*;
import javax.swing.*;

public abstract class Chesspiece {
    public  int row;
    public int col;
    public  boolean isWhite;
    public Image image;

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

    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isWhite() { return isWhite; }
}
