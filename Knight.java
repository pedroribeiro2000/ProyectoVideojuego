package ProyectoVideojuego;

public class Knight extends Chesspiece {
    public Knight(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "knight-white.png" : "knight-black.png");
    }
}
