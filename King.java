package ProyectoVideojuego;

public class King extends Chesspiece {
    public King(int row, int col, boolean isWhite) {
    super(row, col, isWhite, getImageName(isWhite));
    }

    private static String getImageName(boolean isWhite) {
        if (isWhite) {
            return "king-white.png";
        } else {
            return "king-black.png";
        }
    }
}
