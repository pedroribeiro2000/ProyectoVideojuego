package ProyectoVideojuego;

import javax.swing.*;
import java.awt.*;

public class CatHunter extends JPanel {

    public CatHunter() {
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30)); // Título del juego
        g.drawString("CAT HUNTER", 300, 200); 

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Encuentra al gato oculto", 270, 250); // Instrucciones básicas
    }
}