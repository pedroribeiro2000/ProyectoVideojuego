public class Pawn extends ChessPiece {

    // Constructor del Peón
    public Pawn(Color colorPieza, int x, int y) {
        // Llamamos al constructor del padre.
        // Fíjate que pasamos "Pawn" automáticamente como tipología.
        super("Pawn", colorPieza, x, y);
    }

    @Override
    public boolean validarMovimiento(int nuevaX, int nuevaY) {
        // AQUI VA LA LÓGICA DEL AJEDREZ
        // El peón es complicado porque depende del color (avanza hacia arriba o abajo)
        
        // Ejemplo básico: El peón solo se mueve "hacia adelante" en el eje Y.
        // Asumiendo que las blancas avanzan hacia Y positivo y negras a Y negativo.
        
        int direccion = (this.colorPieza == Color.BLANCO) ? 1 : -1;

        // 1. Verificar que no se mueve en X (a menos que coma, que es otra lógica)
        if (this.x != nuevaX) {
            return false; 
        }

        // 2. Verificar que avanza exactamente 1 casilla en su dirección
        if (nuevaY == this.y + direccion) {
            return true;
        }

        // (Falta lógica de primer movimiento doble, pero esto es lo básico)
        return false;
    }
}