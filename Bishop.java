package ProyectoVideojuego;

public class Bishop extends Chesspiece {
    public Bishop(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "bishop-white.png" : "bishop-black.png");
    }
}
