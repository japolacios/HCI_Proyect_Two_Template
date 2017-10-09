
import lenguaje.LogicaLenguaje;
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

/****************************
 * Esta clase es quien inicializa y corre el applet para probar cada modulo del app de manera independiente.
 * Aqui se debe cargar unicamente el draw de la logica.
 * con el fin de integrar facil y rapidamente los diferentes modulos.
 * @author Japo
 *
 */



public class Ejecutable extends PApplet {
 	public Table table;
 	public int stage;
	//Atributos/relaciones
 	public Inicio inicio;
	public LogicaLenguaje logicaLenguaje;
	
	
	public static void main(String[] args) {
		PApplet.main("Ejecutable");

	}

	public void settings() {
		System.out.println("Set Canvas Size");
		size(1024, 760);
	}
	
	public void setup() {
		stage = 1;
		createStage();
		createCVS();
	}
	
	public void createStage() {
		if(stage == 0) {
			inicio = new Inicio(this);
		}
		if(stage == 1) {
			logicaLenguaje = new LogicaLenguaje(this);
		}
	}
	
	
	
	
	
	public void createCVS() {
		//Probando guardarile de CVS
		  table = new Table();
		  
		  table.addColumn("Tipo de Inteligencia");
		  table.addColumn("Tiempo");
		  table.addColumn("Puntaje");
		  /*
		  TableRow newRow = table.addRow();
		  newRow.setInt("id", table.getRowCount() - 1);
		  newRow.setString("species", "Reptil");
		  newRow.setString("name", "Pejelagarto");
		  System.out.println("Saving CSV");
		  saveTable(table, "data/new.csv");
		  TableRow newRow2 = table.addRow();
		  newRow2.setInt("id", table.getRowCount() - 1);
		  newRow2.setString("species", "Aver");
		  newRow2.setString("name", "Pedoractilo");
		  System.out.println("Saving CSV");
		  */
		  saveTable(table, "data/new.csv");
	}
	
	public void saveData() {
		
	}
	
	//Stage paint changes depending on the stage number
	public void paintStage() {
		if(stage == 0) {
			inicio.paint();
		}
		if(stage == 1) {
			logicaLenguaje.paint();
		}
	}

	public void draw() {
		background(255);
		paintStage();
		}
	

}
