package ProyectoVideojuego;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Ajedrez -Proyecto Videojuego"); // Título de la ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana
        ventana.setSize(800, 800); // Tamaño de la ventana
        ventana.setLayout(new BorderLayout()); // border layout significa que se pueden agregar componentes en 5 áreas: norte, sur, este, oeste y centro
        // Crear el tablero y agregarlo al centro de la ventana
        Board board = new Board();
        ventana.add(board, BorderLayout.CENTER);
        ventana.setVisible(true);

    }
}
