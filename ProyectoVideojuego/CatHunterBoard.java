package ProyectoVideojuego;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

public class CatHunterBoard extends JPanel {

    public enum Difficulty {
        EASY(8, 8, 10),
        MEDIUM(16, 16, 40),
        HARD(16, 30, 99);

        public final int rows;
        public final int cols;
        public final int mines;

        Difficulty(int r, int c, int m) {
            rows = r;
            cols = c;
            mines = m;
        }
    }

    private int rows, cols, mines;
    private Cell[][] grid;
    private int cellSize = 30; // Tamaño de cada celda en píxeles
    private boolean firstClick = true;
    private boolean gameOver = false; // Variable para controlar si el juego ha terminado, 
    private int flagsUsed = 0;
    private JButton resetButton;
    private JLabel infoLabel;
    private int topOffset = 40;

    public CatHunterBoard(Difficulty difficulty) {
        // Configuración según dificultad
        this.rows = difficulty.rows;
        this.cols = difficulty.cols;
        this.mines = difficulty.mines;

        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize)); // Tamaño del panel según el número de celdas
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        initBoard();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int c = e.getX() / cellSize; // Columna clickeada
                int r = (e.getY() - topOffset) / cellSize; // Fila clickeada (ajustada por el panel superior)

                if (!isInside(r, c) || gameOver) { // Si la celda está fuera del tablero o termina el juego, no hacer nada
                    return;
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    toggleFlag(r, c);
                } else {
                    if (firstClick) {
                        placeMines(r, c);
                        calculateNumbers();
                        firstClick = false;
                    }
                    reveal(r, c);
                }
                repaint();
            }
        });
    }

    // Método para inicializar el tablero
    private void initBoard() {
        grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }
    }

    private boolean isInside(int r, int c) { // Método para verificar si una celda está dentro de los límites del tablero
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    // Método para colocar minas en el tablero, asegurando que la primera casilla clickeada y sus alrededores estén libres de minas
    private void placeMines(int safeRow, int safeCol) {
        Random rand = new Random();
        int placed = 0;

        while (placed < mines) { // Mientras no se hayan colocado todas las minas
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);

            if (Math.abs(r - safeRow) <= 1 && Math.abs(c - safeCol) <= 1) { // Evita colocar minas en la casilla segura y en sus alrededores
                continue;
            }

            if (!grid[r][c].hasMine()) { // Si no hay mina en esa celda, colocarla
                grid[r][c].setMine(true);
                placed++;
            }
        }
    }

    private void calculateNumbers() { // Método para calcular el número de minas adyacentes a cada celda, ejemplo: si una celda tiene 2 minas alrededor, se le asigna el número 2
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].hasMine()) { // Si la celda tiene mina, no se calcula el número de minas adyacentes
                    continue;
                }

                int count = 0;
                for (int i = 0; i < 8; i++) {
                    int nr = r + dr[i];
                    int nc = c + dc[i];

                    if (isInside(nr, nc) && grid[nr][nc].hasMine()) {  // Si la celda vecina tiene mina, incrementar el contador
                        count++;
                    }
                }
                grid[r][c].setAdjacentMines(count); // Dice a la celda cuántas minas tiene alrededor
            }
        }
    }

    private void reveal(int r, int c) { // Método para revelar una celda.
        if (!isInside(r, c) || grid[r][c].isRevealed() || grid[r][c].isFlagged()) { // Si la celda está fuera del tablero, ya está revelada o tiene una bandera, no hacer nada
            return;
        }

        grid[r][c].setRevealed(true); // Revelar la celda actual

        if (grid[r][c].hasMine()) { // Si la celda tiene una mina, el juego termina
            gameOver = true;
            JOptionPane.showMessageDialog(this, "¡Has perdido! te has encontrado un gato.");
            return;
        }

        if (grid[r][c].getAdjacentMines() == 0) { // Si la celda no tiene minas alrededor, revelar las celdas vecinas
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    reveal(r + dr, c + dc);
                }
            }
        }

        checkWin(); // Verificar si el jugador ha ganado después de revelar la celda
    }

    private void toggleFlag(int r, int c) { // Método para colocar o quitar una bandera en una celda
        if (!grid[r][c].isRevealed()) {
            grid[r][c].setFlagged(!grid[r][c].isFlagged());

            if (grid[r][c].isFlagged()) {
                flagsUsed++;
            } else {
                flagsUsed--;
            }

            updateInfo();
        }
    }

    private void resetGame() {
        // pone los valores iniciales para reiniciar el juego
        firstClick = true;
        gameOver = false;
        flagsUsed = 0;

        initBoard();
        updateInfo();
        repaint();
    }

    private void checkWin() { // Método para verificar si el jugador ha ganado
        int unrevealed = 0;

        for (int r = 0; r < rows; r++) { //rrecorre todas las filas y columnas del tablero para contar cuántas celdas no han sido reveladas
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isRevealed()) {
                    unrevealed++;
                }
            }
        }

        if (unrevealed == mines) { // Si el número de celdas no reveladas es igual al número de minas, el jugador ha ganado
            gameOver = true;
            JOptionPane.showMessageDialog(this, "¡Has ganado! Encontraste todos los gatos.");
        }
    }

    private JPanel createTopPanel() { // Método para crear el panel superior que muestra la información del juego y el botón de reiniciar
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        top.setBackground(Color.DARK_GRAY);

        infoLabel = new JLabel();
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        resetButton = new JButton("Reiniciar");
        resetButton.addActionListener(e -> resetGame());

        top.add(infoLabel);
        top.add(resetButton);

        updateInfo();

        return top;
    }

    private void updateInfo() { // Método para actualizar la información mostrada en el panel superior, como el número de minas, banderas usadas y minas restantes
        int remaining = mines - flagsUsed;
        infoLabel.setText("Minas: " + mines + " | Banderas: " + flagsUsed + " | Restantes: " + remaining);
    }

    @Override
    protected void paintComponent(Graphics g) { // Método para dibujar el tablero y las celdas igual que en el ajedrez
        super.paintComponent(g);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = c * cellSize;
                int y = r * cellSize + topOffset;

                Cell cell = grid[r][c];

                if (!cell.isRevealed()) { // Si la celda no está revelada, se dibuja un rectángulo gris.
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, cellSize, cellSize);

                    if (cell.isFlagged()) { // Si la celda tiene una bandera, se dibuja una "F" roja para indicar que el jugador ha marcado esa celda como sospechosa de tener una mina.
                        g.setColor(Color.RED);
                        g.drawString("F", x + 10, y + 20);
                    }
                } else { // Si la celda está revelada, se dibuja un rectángulo claro.
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(x, y, cellSize, cellSize);

                    if (cell.hasMine()) { // Si la celda tiene una mina, se dibuja un círculo
                        g.setColor(Color.BLACK);
                        g.fillOval(x + 5, y + 5, 20, 20);
                    } else if (cell.getAdjacentMines() > 0) { // Si no tiene mina, se dibuja el número de minas adyacentes (mayor a 0)
                        g.setColor(Color.BLUE);
                        g.drawString(String.valueOf(cell.getAdjacentMines()), x + 10, y + 20);
                    }
                }

                g.setColor(Color.DARK_GRAY); // Se dibujan las líneas del tablero
                g.drawRect(x, y, cellSize, cellSize); // Dibuja el borde de cada celda
            }
        }
    }
}
