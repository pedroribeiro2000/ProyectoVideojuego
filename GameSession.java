package ProyectoVideojuego;

public class GameSession {

    private int score;
    private int level;

    public GameSession(int score, int level) {
        this.score = score;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }
    
}
