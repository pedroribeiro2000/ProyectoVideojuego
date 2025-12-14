import java.awt.Color;//Para poder usar en los atributos colores tengo que usar esta biblioteca

public class Pawn extends ChessPiece {

    // Constructor del Peón
    public Pawn(Color colorpieza, int x, int y) {
        // Llamamos al constructor del padre.
        // Pasamos "Pawn" automáticamente como tipología. Es un String. 
        super("Pawn", colorpieza, x, y);
    }

    @Override
    public boolean validarMovimiento(int nuevaX, int nuevaY) {
        // El peón  depende del color (avanza hacia arriba o abajo)
        //El peón solo se mueve "hacia adelante" en las filas.
        // Asumiendo que las blancas avanzan hacia Y positivo y negras a Y negativo.
        
        int direccion = (this.colorpieza == Color.WHITE) ? 1 : -1;

        // 1. Verificar que no se mueve en X (a menos que coma, que es otra lógica)
        if (this.x != nuevaX) {
            return false; 
        }

        // 2. Verificar que avanza exactamente 1 casilla en su dirección
        if (nuevaY == this.y + direccion) {
            return true;
        }

        return false;
    }
}