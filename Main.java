package ProyectoVideojuego;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame("Proyecto Videojuego");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 800);
        window.setLayout(new BorderLayout());

        showMenu(window);

        window.setVisible(true);
    }

    private static JButton createButton(String text){ // Método para crear botones con estilo uniforme

        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setMaximumSize(new Dimension(250,50));

        button.setFocusPainted(false);
        button.setBackground(new Color(70,130,180));
        button.setForeground(Color.WHITE);

        return button;
    }

    private static void showMenu(JFrame window) {

        JPanel menuPanel = new JPanel(); // Panel para el menú principal
        menuPanel.setBackground(new Color(30,30,30));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("PROYECTO VIDEOJUEGO");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);

        menuPanel.add(Box.createVerticalStrut(120)); // Espacio superior
        menuPanel.add(titulo);
        menuPanel.add(Box.createVerticalStrut(80)); // Espacio entre título y botones
        // Crear botones para los juegos
        JButton chessButton = createButton("Ajedrez");
        JButton catHunterButton = createButton("Buscagatos");
        JButton exitButton = createButton("Salir");

        menuPanel.add(chessButton);
        menuPanel.add(Box.createVerticalStrut(20)); // Espacio entre botones
        menuPanel.add(catHunterButton);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(exitButton);

        window.add(menuPanel, BorderLayout.CENTER);

        chessButton.addActionListener(e -> { // Al hacer clic en el botón de Ajedrez, se muestra el tablero
            window.getContentPane().removeAll();
            window.add(new Board(), BorderLayout.CENTER);
            window.revalidate();
            window.repaint();
        });

        catHunterButton.addActionListener(e -> { // Al hacer clic en el botón de Buscagatos, se muestra el panel del juego
            window.getContentPane().removeAll();
            window.add(new CatHunter(), BorderLayout.CENTER);
            window.revalidate();
            window.repaint();
        });

        exitButton.addActionListener(e -> System.exit(0)); // Al hacer clic en el botón de Salir, se cierra la aplicación
    }
}