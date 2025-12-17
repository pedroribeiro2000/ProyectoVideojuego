package ProyectoVideojuego;

public class Rook extends Chesspiece {
    public Rook(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "rook-white.png" : "rook-black.png");
    }
}
