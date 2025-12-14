package ProyectoVideojuego;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    int columns = 8;
    int rows = 8;
    // Tamaño de cada casilla en píxeles van a sobrar 160 pixeles en cada dimensión
    int squareSize = 80;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            int boardWidth = columns * squareSize;
            int boardHeight = rows * squareSize;
            // Centrar el tablero en el panel
            int xOffset = (getWidth() - boardWidth) / 2;
            int yOffset = (getHeight() - boardHeight) / 2;
            String letters = "ABCDEFGH";
            
            // Dibujar el tablero
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if ((row + col) % 2 == 0) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(xOffset + col * squareSize, yOffset + row * squareSize, squareSize, squareSize);
                }
            }
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            // Letras superiores e inferiores
            for (int i = 0; i < columns; i++) {
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(letters.charAt(i)), xOffset + i * squareSize + squareSize / 2 - 5, yOffset - 10);
                g.drawString(String.valueOf(letters.charAt(i)), xOffset + i * squareSize + squareSize / 2 - 5, yOffset + boardHeight + 20);
            }
            // Números laterales
            for (int i = 0; i < rows; i++) {
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(8 - i), xOffset - 20, yOffset + i * squareSize + squareSize / 2 + 5);
                g.drawString(String.valueOf(8 - i), xOffset + boardWidth + 10, yOffset + i * squareSize + squareSize / 2 + 5);
            }
    }
}
