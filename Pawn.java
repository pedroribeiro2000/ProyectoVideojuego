package ProyectoVideojuego;

import java.util.*;


public class Pawn extends Chesspiece {
    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite, isWhite ? "pawn-white.png" : "pawn-black.png");
    }


}
