import java.awt.Color;//Para poder usar en los atributos colores tengo que usar esta biblioteca

public  abstract class ChessPiece {
	
	
	public String tipologia; //Nombre que se le asignara las pizas 
	public Color colorpieza; //Color que tiene la pieda 
	public int x,y; 

	
	//Implemento constructor

	public ChessPiece (String tipologia,Color colorpieza, int x, int y) {
		
		this.tipologia=tipologia; 
		this.colorpieza=colorpieza; 
		this.x=x;
		this.y=y;	}


<<<<<<< HEAD
	// Método abstracto: Obliga a cada pieza a definir sus propias reglas
	
 	public abstract boolean validarMovimiento(int nuevaX, int nuevaY);
			
    	// Getters para acceder a la información
    public Color getColor() { return colorpieza; }
    public String getTipo() { return tipologia; }


	public static void main(String[] args) {

		System.out.println("Comprobar que la Clase ChessPiece compilada bien.") ;  }

	} 
// Fin de la clase ChessPiece
=======
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isWhite() { return isWhite; }
}
>>>>>>> eaaf18f75ce4343e2be0efca486a80a255a54710
