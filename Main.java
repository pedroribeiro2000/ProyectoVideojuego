package ProyectoVideojuego;

import java.awt.*;
import javax.swing.*;
import java.io.*; // Importante para leer los archivos que  guardamos,.


public class Main {

    //Al inicio agrego la variable que determinara quien esta jugando y guardar su avance. 
        public static User currentUser; // Variable estática para almacenar el usuario actual

    public static void main(String[] args) {

        String[] options = {"Iniciar Sesión", "Registrarse", "Invitado"};
        int selecction = JOptionPane.showOptionDialog(null, "¿Cómo deseas ingresar?", "Bienvenido",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

         //Segun lo que selecciones, te dara la opcion para ingresar. 
        if (selecction == 0) { // Iniciar Sesión
            String username = JOptionPane.showInputDialog(null, "Introduce tu nombre de usuario:");
            String password = JOptionPane.showInputDialog(null, "Introduce tu contraseña:");
            currentUser = User.loadUser(username, password);
            if (currentUser == null) {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                System.exit(0);
            }
         
        } else if (selecction == 1) { // Registrarse
            String username = JOptionPane.showInputDialog(null, "Elige un nombre de usuario:");
            String password = JOptionPane.showInputDialog(null, "Elige una contraseña:");
            currentUser = new User(username, password);
            currentUser.saveCurrentState(); // Guardamos el nuevo usuario
            JOptionPane.showMessageDialog(null, "¡Usuario registrado exitosamente!");
        
        } else { // Invitado
            currentUser = new User("Invitado", "");
            JOptionPane.showMessageDialog(null, "Has ingresado como invitado. Tu progreso no se guardará.");
        }               
          System.out.println("Usuario actual: " + currentUser.getUsername()); // Para verificar que se ha cargado el usuario correctamente   

         
        //Estructura inicial de la ventana principal del proyecto, con un menú para elegir entre los juegos disponibles y salir. 

        JFrame window = new JFrame("Proyecto Videojuego");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 800);
        window.setLayout(new BorderLayout());

        showMenu(window);

        window.setVisible(true);
    }
    //para leer los archivos ya existentes : 

    private static User loadUser (String user, String pass) {
        try (BufferedReader reader = new BufferedReader(new FileReader(user + "_state.txt"))) {
            String savedPass = reader.readLine();
            
            // Verificamos si la contraseña coincide
            if (savedPass.equals(pass)) {
                User loadedUser = new User(user, savedPass);
                loadedUser.setScore(Integer.parseInt(reader.readLine()));
                loadedUser.setLevel(Integer.parseInt(reader.readLine()));
                return loadedUser;
            }
        } catch (Exception e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
        return null;
    }
  

       public static void saveUserState() {
    if (currentUser == null || currentUser.getUsername().equals("Invitado")) return;
    
    try (PrintWriter writer = new PrintWriter(new FileWriter(currentUser.getUsername() + "_state.txt"))) {
        // Cambiamos para que se guarde la contraseña, puntuación y nivel actual del usuario en el archivo de texto
        writer.println(currentUser.getPassword()); 
        writer.println(currentUser.getScore());
        writer.println(currentUser.getLevel());
    } catch (IOException e) {
        System.out.println("Error al guardar: " + e.getMessage());
    }
} 


    private static JButton createButton(String text) { // Método para crear botones con estilo uniforme

        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setMaximumSize(new Dimension(250, 50));

        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);

        return button;
    }

    private static void showMenu(JFrame window) {

        JPanel menuPanel = new JPanel(); // Panel para el menú principal
        menuPanel.setBackground(new Color(30, 30, 30));
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

        // catHunterButton.addActionListener(e -> { // Al hacer clic en el botón de Buscagatos, se muestra el panel del juego
        //     window.getContentPane().removeAll();
        //     window.add(new CatHunterBoard(CatHunterBoard.Difficulty.EASY), BorderLayout.CENTER);
        //     window.revalidate();
        //     window.repaint();
        // });
        catHunterButton.addActionListener(e -> {
            window.getContentPane().removeAll();
            window.add(new CatHunterIntro(window), BorderLayout.CENTER);
            window.revalidate();
            window.repaint();
        });

        //Cambio el metodo para que a la hora de salir pueda registrar las partidas y el progreso del usuario en el fichero definido 
       // exitButton.addActionListener(e -> System.exit(0)); // Al hacer clic en el botón de Salir, se cierra la aplicación
       //Antes de salir guardamos los datos. 

       exitButton.addActionListener(e -> {
    
        if (currentUser != null) {
        currentUser.exportHistoryToFile();
        }
        
    System.exit(0);
    });
    }

}