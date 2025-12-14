package ProyectoVideojuego;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ajedrez -Proyecto Videojuego");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 800);
        ventana.setLayout(new BorderLayout());

        Board board = new Board();
        ventana.add(board, BorderLayout.CENTER);
        ventana.setVisible(true);

    }
}
