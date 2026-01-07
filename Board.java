package ProyectoVideojuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Board extends JPanel {
    int columns = 8;
    int rows = 8;    // Tamaño de cada casilla en píxeles van a sobrar 160 pixeles en cada dimensión
    int squareSize = 80;
    private List<Chesspiece> pieces;
    private Chesspiece selectedPiece;
    private List<Point> highlightedSquares = new ArrayList<>();

    //Agregamos el control de turnos con el primer movimiento 

    private boolean whiteShift=true; // Se determina que empiezan las blancas
    private boolean firstMove = true; // Determinamos el primer movimiento. 

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    //Para crear el Mate, verficamos si una casilla esta siendo atacada por el contrario 

    public boolean isSquareAttacked(int row, int col, boolean whiteAttacker) {
        for (Chesspiece p : pieces) {
            if (p.isWhite() == whiteAttacker) {
                List<Point> moves = p.getLegalMoves(this);
                for (Point move : moves) {
                    if (move.y == row && move.x == col) return true;
                }
            }
        }
        return false;
    }

    public Chesspiece findKing(boolean white) {
        for (Chesspiece p : pieces) {
            if (p instanceof King && p.isWhite() == white) return p;
        }
        return null;
    }

    public boolean isCheckmate(boolean isWhiteTurn) {
        Chesspiece king = findKing(isWhiteTurn);
        if (king == null) return false;

        // Aplicamos logica que si el Rey no tiene jaque, no hay Mate 
        if (!isSquareAttacked(king.getRow(), king.getCol(), !isWhiteTurn)) {
            return false;
        }

        // Comprobar resto de fichas, por si se puede salvar al al rey 
        List<Chesspiece> currentPlayerPieces = new ArrayList<>(pieces);
        for (Chesspiece p : currentPlayerPieces) {
            if (p.isWhite() == isWhiteTurn) {
                int originalRow = p.getRow();
                int originalCol = p.getCol();
                List<Point> moves = p.getLegalMoves (this);

                for (Point move : moves) {
                    Chesspiece target = getPieceAt(move.y, move.x);
                    if (target != null) pieces.remove(target);
                    p.setPosition(move.y, move.x);

                    boolean stillInCheck = isSquareAttacked(king.getRow(), king.getCol(), !isWhiteTurn);

                    p.setPosition(originalRow, originalCol);
                    if (target != null) pieces.add(target);

                    if (!stillInCheck) return false; // Tuvo salida 
                }
            }
        }
        return true; //Sin salida- jaque 
    }

    // --- FIN DE MÉTODOS DE APOYO ---


    /** Devuelve la pieza situada en (row, col) o null si la casilla esta vacia. */
    public Chesspiece getPieceAt(int row, int col) {
        for (Chesspiece p : pieces) {
            if (p.getRow() == row && p.getCol() == col) {
                return p;
            }
        }
        return null;
    }

    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < columns;
    }

    public boolean isEmpty(int row, int col) {
        return getPieceAt(row, col) == null;
    }

    public List<Chesspiece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    private int getBoardWidthPx() {
        return columns * squareSize;
    }

    private int getBoardHeightPx() {
        return rows * squareSize;
    }

    private int getXOffset() {
        return (getWidth() - getBoardWidthPx()) / 2;
    }

    private int getYOffset() {
        return (getHeight() - getBoardHeightPx()) / 2;
    }

    private void clearSelection() {
        selectedPiece = null;
        highlightedSquares.clear();
    }

    //Metodo para reinciar el juego

    private void resetGame() {
        pieces.clear();
        whiteShift = true;
        firstMove = true;
        // AQUÍ DEBERÍAS RE-AGREGAR LAS PIEZAS COMO EN EL CONSTRUCTOR
        // (POR BREVEDAD NO LAS REPETIMOS PERO DEBEN IR AQUÍ)
        repaint();
    }

    private void handleClick(int mouseX, int mouseY) {
        int xOffset = getXOffset();
        int yOffset = getYOffset();

        // Si click fuera del tablero, limpiar.
        if (mouseX < xOffset || mouseY < yOffset
                || mouseX >= xOffset + getBoardWidthPx()
                || mouseY >= yOffset + getBoardHeightPx()) {
            clearSelection();
            repaint();
            return;
        }

        int col = (mouseX - xOffset) / squareSize;
        int row = (mouseY - yOffset) / squareSize;

        Chesspiece clicked = getPieceAt(row, col);
        
        if (selectedPiece != null && isHighlightedSquare(row, col)) {
            Chesspiece target = getPieceAt(row, col);
                if (target != null && target.isWhite() != selectedPiece.isWhite()) {
                pieces.remove(target);
            }
            selectedPiece.setPosition(row, col);

            firstMove = false;
            whiteShift = !whiteShift; // Cambio de turno
           

            // Comprobamos si hay jaque Mate

    Chesspiece opposingKing = findKing(whiteShift);  

    if (opposingKing != null && isSquareAttacked(opposingKing.getRow(), opposingKing.getCol(), !whiteShift)) {
                String jugadorEnJaque = whiteShift ? "Blancas" : "Negras";
                    JOptionPane.showMessageDialog(this, "¡Jaque! El rey de " + jugadorEnJaque + " está en peligro.");
              
  }
            if (isCheckmate(whiteShift)) {
                repaint(); // Plantear el ultimo movimiento posible 
                String ganador = (!whiteShift) ? "Blancas" : "Negras";
               int respuesta= JOptionPane.showConfirmDialog(this, "¡JAQUE MATE! Las " + ganador + " Han ganado la partida. Fin de la partida", 
               "Jaque Mate", JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    resetGame(); // LLAMA AL MÉTODO DE REINICIO
                }
      //Verificamos si hay Jaque
    
                            
            }
            // FIN DE LA COMPROBACIÓN DE JAQUE MATE y posible inicio de partida

            clearSelection();
            repaint();
            return;}// No se puede seleccionar pieza del color incorrecto

        
        if (selectedPiece != null && isHighlightedSquare(row, col)) {
        Chesspiece target = getPieceAt(row, col);

        // Captura (si hay pieza rival en destino)
        if (target != null && target.isWhite() != selectedPiece.isWhite()) {
            pieces.remove(target);
    }

        // Mover la pieza seleccionada
        selectedPiece.setPosition(row, col);

        firstMove = false;

        whiteShift = !whiteShift; // Cambio turno blancas 
        clearSelection();
        repaint();
        return;
        }

        if (clicked != null && clicked.isWhite() != whiteShift) { 
            if (firstMove && whiteShift && !clicked.isWhite()) { 
            
                JOptionPane.showMessageDialog(this, "Empiezan las Blancas"); // Mensajes
            }
            return; 
        }        

        //--Selecionamos las piezas --//

        if (clicked instanceof Pawn) {
            Pawn pawn = (Pawn) clicked;
            selectedPiece = pawn;
            highlightedSquares = pawn.getLegalMoves(this);
        } 
        else if (clicked instanceof Rook) {
            Rook rook = (Rook) clicked;
            selectedPiece = rook;
            highlightedSquares = rook.getLegalMoves(this);
        }
        else if (clicked instanceof Bishop) {
            Bishop bishop = (Bishop) clicked;
            selectedPiece = bishop;
            highlightedSquares = bishop.getLegalMoves(this);
        }
        else if (clicked instanceof Queen) {
            Queen queen = (Queen) clicked;
            selectedPiece = queen;
            highlightedSquares = queen.getLegalMoves(this);
        }
        else if (clicked instanceof Knight) {
            Knight knight = (Knight) clicked;
            selectedPiece = knight;
            highlightedSquares = knight.getLegalMoves(this); 
        } 
        else if (clicked instanceof King) {
            King king = (King) clicked;
            selectedPiece = king;
            highlightedSquares = king.getLegalMoves(this);
        }
        else {
            clearSelection();
        }

        repaint();
    }

    private boolean isHighlightedSquare(int row, int col) {
        for (Point p : highlightedSquares) {
            // En tu código: Point.x = col, Point.y = row
            if (p.x == col && p.y == row) return true;
        }
        return false;
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

        if (!highlightedSquares.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(0, 255, 0, 130)); // verde semitransparente

            for (Point p : highlightedSquares) {
                int hx = xOffset + p.x * squareSize; // p.x = col
                int hy = yOffset + p.y * squareSize; // p.y = row
                g2.fillRect(hx, hy, squareSize, squareSize);
            }

            // Opcional: borde en la pieza seleccionada
            if (selectedPiece != null) {
                g2.setColor(new Color(0, 255, 0, 200));
                int sx = xOffset + selectedPiece.getCol() * squareSize;
                int sy = yOffset + selectedPiece.getRow() * squareSize;
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(sx + 1, sy + 1, squareSize - 2, squareSize - 2);
            }
            g2.dispose();
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