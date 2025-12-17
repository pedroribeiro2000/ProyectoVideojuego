package ProyectoVideojuego;

public class Queen extends Chesspiece {
    public Queen(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "queen-white.png" : "queen-black.png");
    }
}
