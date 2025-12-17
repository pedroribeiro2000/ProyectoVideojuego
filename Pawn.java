package ProyectoVideojuego;

public class Pawn extends Chesspiece {
    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "pawn-white.png" : "pawn-black.png");
    }
}

//Movimientos del Peon. 

// El peon se mueve una casilla hacia adelante, pero captura en diagonal.
// En su primer movimiento, puede avanzar dos casillas hacia adelante.
// No puede moverse hacia atrás ni capturar hacia adelante.

public List<Move> getLegalMoves(Chesspiece[][] board) {
    List<Move> legalMoves = new ArrayList<>();
    int direction = isWhite ? -1 : 1; // Las piezas blancas se mueven hacia arriba, las negras hacia abajo
    int startRow = isWhite ? 6 : 1;

    // Movimiento hacia adelante
    if (isInBounds(row + direction, col) && board[row + direction][col] == null) {
        legalMoves.add(new Move(row, col, row + direction, col));

        // Movimiento inicial de dos casillas
        if (row == startRow && board[row + 2 * direction][col] == null) {
            legalMoves.add(new Move(row, col, row + 2 * direction, col));
        }
    }

    // Captura diagonal izquierda
    if (isInBounds(row + direction, col - 1) && board[row + direction][col - 1] != null &&
        board[row + direction][col - 1].isWhite() != isWhite) {
        legalMoves.add(new Move(row, col, row + direction, col - 1));
    }

    // Captura diagonal derecha
    if (isInBounds(row + direction, col + 1) && board[row + direction][col + 1] != null &&
        board[row + direction][col + 1].isWhite() != isWhite) {
        legalMoves.add(new Move(row, col, row + direction, col + 1));
    }

    return legalMoves;
}