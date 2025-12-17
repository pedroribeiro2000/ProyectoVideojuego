package ProyectoVideojuego;

public class King extends Chesspiece {
    public King(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "king-white.png" : "king-black.png");
    }
}
