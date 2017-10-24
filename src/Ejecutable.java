
import Balanzas.LogicaBalanzas;
import lenguaje.LogicaLenguaje;
import memoria.LogicaMemoria;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;
import velprocesamiento.LogicaVp;

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
	public PImage bg;
	public LogicaLenguaje logicaLenguaje;
	public LogicaBalanzas logicaBalanzas;
	public LogicaVp logicaVp;
	private LogicaMemoria logicaMemoria;

	public static void main(String[] args) {
		PApplet.main("Ejecutable");

	}

	public void settings() {
		System.out.println("Set Canvas Size");
		size(1024, 760);
	}

	public void setup() {
		loadImages();
		System.out.println("Setting stage to Initial state");
		stage = 0;
		newStage = false;
		System.out.println("Calling create Stage Method");
		createStage();
		createCVS();
	}
	
	public void loadImages() {
		try {
		bg = loadImage("./data/lenguaje/background.jpg");
		} catch(Exception e) {
			
		}
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
				logicaBalanzas = new LogicaBalanzas(this, table);
				System.out.println("Stage 2 Created");
			}
			if (stage == 3) {
				logicaVp = new LogicaVp(this, table);
				System.out.println("Stage 3 Created");
			}
			if (stage == 4) {
				logicaMemoria = new LogicaMemoria(this, table);
				System.out.println("Stage 4 Created");
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
		if (stage == 2) {
			logicaBalanzas.paint();
		}
		if (stage == 3) {
			logicaVp.paint();
		}
		if (stage == 4) {
			logicaMemoria.paint();
		}
	}

	public void draw() {
		
		image(bg,0,0);
		checkStage();
		createStage();
		//background(255);
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
		if (stage == 2) {
			if (logicaBalanzas != null && logicaBalanzas.getDataSaved() == true) {
				stage = 3;
				newStage = false;
			}
		}
		if (stage == 3) {
			if (logicaVp != null && logicaVp.getDataSaved() == true) {
				stage = 4;
				newStage = false;
			}
		}
		if (stage == 4) {
			if (logicaMemoria != null && logicaMemoria.getDataSaved() == true) {
				stage = 1;
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
		if (stage == 2) {
			logicaBalanzas.click();
		}
		if (stage == 3) {
			logicaVp.click();
		}
	}
	
	public void mouseReleased() {
		if(stage == 1) {
			logicaLenguaje.released();
			//System.out.println("Released");
		}
		
		if (stage == 2) {
			logicaBalanzas.released();
		}
		
		if (stage == 3) {
			logicaVp.mReleased();
			}
		if (stage == 4) {
			logicaMemoria.soltar();
		}
		
	}
	
	public void mousePressed() {
		if (stage == 4) {
			logicaMemoria.press();
		}
	}
	
	@Override
	public void mouseDragged() {
		if (stage == 4) {
			logicaMemoria.dragged();
		}
	}
	
	public void keyPressed(){
		if (stage == 3) {
			logicaVp.key();
		}
	}
}
