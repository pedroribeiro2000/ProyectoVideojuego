package ProyectoVideojuego;
import java.io.BufferedReader;
import java.io.BufferedWriter; // Importamos ArrayList para manejar la lista de sesiones de juego. 
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class User {
    private String username;
    private String password; // Pendiente de revisar si se debe almacenar de forma segura (hashing)
    private int score;
    private int level; 
    private List<GameSession> gameSessions; // Historial partidas 

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0; // Empezamos con una puntuación de 0;
        this.level = 0; // Empezamos en el nivel 0 , que es el más basico
        this.gameSessions = new ArrayList<>(); // Iniciamos la lista de sesiones de juego
    }

    public void saveCurrentState() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(this.username + "_state.txt"))) {
            writer.println(this.password);
            writer.println(this.score);
            writer.println(this.level);
        } catch (IOException e) {
            System.err.println("Error al guardar estado: " + e.getMessage());
        }
    }

public static User loadUser(String username, String password) {
        File file = new File(username + "_state.txt");
        if (!file.exists()) return null; // No existe el usuario

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String savedPass = reader.readLine();
            if (!savedPass.equals(password)) return null; 

            User loadedUser = new User(username, savedPass);
            loadedUser.setScore(Integer.parseInt(reader.readLine()));
            loadedUser.setLevel(Integer.parseInt(reader.readLine()));
            return loadedUser;
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }
       
    public void saveGameSession(GameSession session) {
        this.gameSessions.add(session);
        // Al terminar una sesión, exportamos automáticamente al historial
        exportHistoryToFile();
    }

    public String getPassword() {
        return this.password;
    }

  
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    // Método de utilidad para subir puntos y niveles
    public void addScore(int points) {
        this.score += points;
        // Por ejemplo, cada 100 puntos sube un nivel
        if (this.score >= (this.level + 1) * 100) {
            this.level++;
        }
        saveCurrentState(); // Agrego para que se guaarde la puntuacion cada vez que actualice
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    //Hacemos un metodo que recoja las sesiones y las guarde en un fichero para sacar estadisticas de las partidas.
    public void exportHistoryToFile(){
        String filename= this.username + "_game_history.txt";
        File file = new File(filename);
        boolean filenew= !file.exists(); // Verificamos si el archivo ya existe. 

        //Crreamos formatos de la fecha 
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentdate = LocalDateTime.now().format(formatDate);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) { // Abrimos el archivo en modo append para no sobrescribir el historial existente
            if(filenew){
                writer.write("Usuario: " + this.username);
                writer.newLine();
                writer.write("Historial juego");
                writer.newLine();
            }
            for (GameSession session : gameSessions) {
                writer.write("Fecha: " + currentdate + " - Score: " + session.getScore() + " - Level: " + session.getLevel());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Se produjo un error al exportar el historial del juego: " + e.getMessage());
        }


    }
}
