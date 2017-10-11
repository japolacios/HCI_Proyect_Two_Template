
import lenguaje.LogicaLenguaje;
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

/****************************
 * Esta clase es quien inicializa y corre el applet para probar cada modulo del
 * app de manera independiente. Aqui se debe cargar unicamente el draw de la
 * logica. con el fin de integrar facil y rapidamente los diferentes modulos.
 * 
 * @author Japo
 *
 */

public class Ejecutable extends PApplet {
	public Table table;
	public int stage;
	public boolean newStage;
	// Atributos/relaciones
	public Inicio inicio;
	public LogicaLenguaje logicaLenguaje, logicaLenguaje2;

	public static void main(String[] args) {
		PApplet.main("Ejecutable");

	}

	public void settings() {
		System.out.println("Set Canvas Size");
		size(1024, 760);
	}

	public void setup() {
		System.out.println("Setting stage to Initial state");
		stage = 0;
		newStage = false;
		System.out.println("Calling create Stage Method");
		createStage();
		createCVS();
	}

	public void createStage() {
		if (newStage == false) {
			if (stage == 0) {
				inicio = new Inicio(this);
				System.out.println("Stage 0 Created");
			}
			if (stage == 1) {
				logicaLenguaje = new LogicaLenguaje(this, table);
				System.out.println("Stage 1 Created");
			}
			if (stage == 2) {
				logicaLenguaje2 = new LogicaLenguaje(this, table);
				System.out.println("Stage 2 Created");
			}
			
			newStage = true;
		}
		
	}

	

	public void createCVS() {
		// Probando guardarile de CVS
		table = new Table();

		table.addColumn("Tipo");
		table.addColumn("Puntaje");
		table.addColumn("Autopuntaje");
		table.addColumn("Posicion");
		saveTable(table, "data/new.csv");
	}

	public void saveData() {

		TableRow newRow = table.addRow();
		newRow.setString("Tipo", "hola");
		newRow.setString("Tiempo", "Reptil");
		newRow.setString("Puntaje", "Pejelagarto");
		newRow.setString("Autopuntaje", "Pejelagarto");
		newRow.setString("Posicion", "Pejelagarto");
		System.out.println("Saving CSV");

		saveTable(table, "data/new.csv");

	}

	// Stage paint changes depending on the stage number
	public void paintStage() {
		if (stage == 0) {
			inicio.paint();
		}
		if (stage == 1) {
			logicaLenguaje.paint();
		}
	}

	public void draw() {
		checkStage();
		createStage();
		background(255);
		fill(0);
		textAlign(CENTER);
		paintStage();
	}

	public void checkStage() {
		if (stage == 0) {
			if (inicio.getGameOver() == true) {
				stage = 1;
				newStage = false;
			}
		}
		if (stage == 1) {
			if (logicaLenguaje != null && logicaLenguaje.getDataSaved() == true) {
				stage = 2;
				newStage = false;
			}
		}
		
	}

	public void mouseClicked() {
		if (stage == 0) {
			inicio.click();
		}
		if (stage == 1) {
			logicaLenguaje.click();
		}
	}
}
