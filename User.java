package ProyectoVideojuego;

public class User {
    private String username;
    private String password; // Pendiente de revisar si se debe almacenar de forma segura (hashing)
    private int score;
    private int level; 

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0; // Empezamos con una puntuación de 0;
        this.level = 0; // Empezamos en el nivel 0 , que es el más basico
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
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
}
