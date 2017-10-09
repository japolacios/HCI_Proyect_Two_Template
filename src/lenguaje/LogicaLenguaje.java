package lenguaje;
import Model.Model;
import processing.core.PApplet;
public class LogicaLenguaje {
	
	
	public String nombrePrueba = "Aqui va el nombre de su prueba";
	public int totalQuestions = 20; // Numero total de preguntas para terminar el juego
	
	
	//----------------------------------------------------------------------
	//Atributos
	public PApplet app;
	//Tiempo y control
	public double startTime;
	public int maxTime = 600000; // 10 minutos (600'000 milisegundos)
	public double stageTime;
	public boolean gameOver = false; //True cuando se termine el tiempo o las preguntas	
	public int currentQuestion = 1; //Numero actual de la pregunta en la que se encuentra
	public int correctAnswers = 0; //Numero total de respuestas acertadas
	public Model gameModel;
	//------------------------------------------------------------------------
	
	//INICIA LA CLASE PRINCIPAL
	public LogicaLenguaje(PApplet _app) {
		app = _app;
		System.out.println("Logic Created");
		//Obtiene el primer valor de tiempo al crear la clase
		startTime = System.currentTimeMillis();
		//---------------------Abajo sigue tu codigo--------------------------------
		
		
		
	}
	//PAINT -> EQUIVALENTE A DRAW
	public void paint() {
		checkTime();
		//---------------------Abajo sigue tu codigo--------------------------------
		
		
	}
	
	//---------------------Abajo siguen tus  Metodos--------------------------------
	
	
	
	
	
	
	
	
	
	/*********************************************************************************************
	 * 
	 * 
	 * 
	 * METODOS GENERICOS PARA TIEMPO Y CAMBIO DE ACTIVIDAD
	 * 
	 * 
	 * 
	 */
	//Verificamos si ya transcurrieron 10minutos
	public void checkTime() {
		double actualTime = (maxTime + startTime) - System.currentTimeMillis();
		
		if(actualTime<= 0) {
			//Se termino el tiempo
			stageTime = 600000;
			gameOver = true;
			
		}
	}
	
	//Verificamos cuando se acaban las preguntas (Cada que el usuario responda una pregunta se debe llamar a este metodo)
	public void checkQuestions() {
		if (currentQuestion>totalQuestions) {
			stageTime = maxTime - (System.currentTimeMillis() - startTime);
			gameOver = true;
		}
	}
	
	
	public boolean getGameOver() {
		return gameOver;
	}	
	
	public void createModel() {
		gameModel = new Model(nombrePrueba, stageTime, correctAnswers);
	}
	
	public Model getModel() {
		return gameModel;
	}
	
	/*********************************************************************************************
	 * 
	 */
}
