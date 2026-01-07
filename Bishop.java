package ProyectoVideojuego;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Chesspiece {
    public Bishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "bishop-white.png" : "bishop-black.png");
    }

}; 


        /**
         * Movimientos legales básicos del alfil:
         * - Se mueve cualquier número de casillas en diagonal.
         * - No puede saltar piezas.
         * - Puede capturar la primera pieza rival que encuentre en una dirección diagonal.
         */

    public List<Point> getLegalMoves(Board board) {
        List<Point> moves = new ArrayList<>();

        // Direcciones diagonales: ↖, ↗, ↙, ↘
        int[][] dirs = {
            {-1, -1},
            {-1,  1},
            { 1, -1},
            { 1,  1}
        };

        for (int[] d : dirs) {
            int dr = d[0];
            int dc = d[1];

            int r = row + dr;
            int c = col + dc;

            while (board.isInsideBoard(r, c)) {
                Chesspiece target = board.getPieceAt(r, c);

                if (target == null) {
                    // Casilla libre: se puede seguir avanzando
                    moves.add(new Point(c, r));
                } else {
                    // Casilla ocupada: si es rival, se puede capturar, luego se detiene
                    if (target.isWhite() != this.isWhite) {
                        moves.add(new Point(c, r));
                    }
                    break;
                }

                r += dr;
                c += dc;
            }
        }

        return moves;
    }
}

