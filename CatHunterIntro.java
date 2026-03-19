package ProyectoVideojuego;

import java.awt.*;
import javax.swing.*;

public class CatHunterIntro extends JPanel {

    public CatHunterIntro(JFrame window) {

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setBackground(Color.BLACK);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Serif", Font.PLAIN, 18));

        text.setText(
            "No estás solo.\n\n" +
            "Cada casilla que abras...\n" +
            "ellos también avanzan.\n\n" +
            "No puedes verlos,\n" +
            "pero ellos sí pueden verte.\n\n" +
            "Y cuando el tiempo se agote...\n\n" +
            "ya será demasiado tarde."
        );

        JButton startButton = new JButton("Comenzar");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));

        startButton.addActionListener(e -> {
            window.getContentPane().removeAll();
            window.add(new CatHunterBoard(CatHunterBoard.Difficulty.EASY), BorderLayout.CENTER);
            window.revalidate();
            window.repaint();
        });

        add(text, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.BLACK);
        bottom.add(startButton);

        add(bottom, BorderLayout.SOUTH);
    }
}