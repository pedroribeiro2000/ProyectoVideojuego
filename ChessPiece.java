import java.awt.Color;//Para poder usar en los atributos colores tengo que usar esta biblioteca

public class ChessPiece {
	
	
	public String tipologia; //Nombre que se le asignara las pizas 
	public Color colorpieza; //Color que tiene la pieda 
	public int x,y; 

	
	//Implemento método: 

	public chessPiece (String tipologia,Color colorpieza,public int x,y) {
		
		this.tipologia=tipologia; 
		this.colorpieza=colorpieza; 
		this.x=x;
		this.y=y;
		
	}


	// Método abstracto: Obliga a cada pieza a definir sus propias reglas
 	public abstract boolean validarMovimiento(int nuevaX, int nuevaY);
			
    	// Getters para acceder a la información
    public Color getColor() { return colorPieza; }
    public String getTipo() { return tipologia; }
}




	public static void main(String[] args) {
		

	}

}
 /// Hacer clases por pieza, diferenciando