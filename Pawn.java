package ProyectoVideojuego;

public class Pawn extends Chesspiece {
    private boolean firstMove = true;

    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "pawn-white.png" : "pawn-black.png");
    }

    public boolean moveForward() {
        int direction;
        if (isWhite) {
            direction = -1; // Blancas suben -1 es porque el origen (0,0) está en la esquina superior izquierda
        } else {
            direction = 1; // Negras bajan hace +1 porque al añadir filas se baja
        }
        int maxStep;
        if (firstMove) {
            maxStep = 2;
        } else {
            maxStep = 1;
        }
        // Aquí deberías verificar si la casilla está libre, pero esto es solo el movimiento simple
        row += direction * maxStep;
        firstMove = false;
        return true;
    }

    // Si quieres mover solo 1 casilla (por ejemplo, si el usuario lo elige)
    public boolean moveForwardOne() {
        int direction;
        if (isWhite) {
            direction = -1;
        } else {
            direction = 1;
        }
        row += direction; // A la fila actual le sumas o restas 1
        firstMove = false;
        return true;
    }

    public boolean isFirstMove() {
        return firstMove;
    }
}
