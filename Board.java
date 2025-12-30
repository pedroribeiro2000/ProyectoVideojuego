package ProyectoVideojuego;

import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {
    private Chesspiece selectedPiece = null; // Pieza seleccionada para mover
    int columns = 8;
    int rows = 8;
    // Tamaño de cada casilla en píxeles van a sobrar 160 pixeles en cada dimensión
    int squareSize = 80;
    private List<Chesspiece> pieces;

    public Board() {
        pieces = new ArrayList<>();
        // Piezas blancas
        pieces.add(new Rook(7, 0, true));
        pieces.add(new Knight(7, 1, true));
        pieces.add(new Bishop(7, 2, true));
        pieces.add(new Queen(7, 3, true));
        pieces.add(new King(7, 4, true));
        pieces.add(new Bishop(7, 5, true));
        pieces.add(new Knight(7, 6, true));
        pieces.add(new Rook(7, 7, true));
        for (int i = 0; i < columns; i++) {
            pieces.add(new Pawn(6, i, true));
        }
        // Piezas negras
        pieces.add(new Rook(0, 0, false));
        pieces.add(new Knight(0, 1, false));
        pieces.add(new Bishop(0, 2, false));
        pieces.add(new Queen(0, 3, false));
        pieces.add(new King(0, 4, false));
        pieces.add(new Bishop(0, 5, false));
        pieces.add(new Knight(0, 6, false));
        pieces.add(new Rook(0, 7, false));
        for (int i = 0; i < columns; i++) {
            pieces.add(new Pawn(1, i, false));
        }

        // Movimiento del peon con clic
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int boardWidth = columns * squareSize;
                int boardHeight = rows * squareSize;
                int xOffset = (getWidth() - boardWidth) / 2;
                int yOffset = (getHeight() - boardHeight) / 2;
                int col = (e.getX() - xOffset) / squareSize;
                int row = (e.getY() - yOffset) / squareSize;
                if (col < 0 || col >= columns || row < 0 || row >= rows) return; // esto evita errores si se clica fuera del tablero

                // Buscar peón en la posición clicada
                for (Chesspiece pieza : pieces) {
                    if (pieza instanceof Pawn && pieza.getRow() == row && pieza.getCol() == col) {// si es un peón y está en la posición clicada
                        Pawn peon = (Pawn)pieza;
                        if (peon.isFirstMove()) {
                            Object[] options = {"Mover 1 casilla", "Mover 2 casillas"}; //Opciones que aparecerán en el JOptionPane de selección de si o no
                            int n = JOptionPane.showOptionDialog(
                                    Board.this, //esto es para centrar el diálogo en el panel
                                    "¿Cuántas casillas quieres mover el peón?", // mensaje
                                    "Mover peón",
                                    JOptionPane.YES_NO_OPTION, // es una opción de sí o no
                                    JOptionPane.QUESTION_MESSAGE, // es un panel de pregunta
                                    null,
                                    options,
                                    options[0]
                            );
                            if (n == 0) {
                                peon.moveForwardOne(); // Mover 1 casilla
                            } else if (n == 1) {
                                peon.moveForward(); // Mover 2 casillas
                            }
                        } else {
                            peon.moveForwardOne(); // Mover 1 casilla si no es el primer movimiento
                        }
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    public List<Chesspiece> getPieces() {
        return pieces;
    }

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

        // Dibujar las piezas
        for (Chesspiece piece : pieces) {
            int px = xOffset + piece.getCol() * squareSize;
            int py = yOffset + piece.getRow() * squareSize;
            piece.draw(g, px, py, squareSize);
        }
    }
}
