package puzzle;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;

public class LogicaPuzzle {

	PApplet app;
	Table table;
	Logica log;
	PImage img;
	EncontrarFigura fig;
	String tipoInteligencia = "Puzzle";
	boolean dataSaved;
	private int stage;
	private boolean changeDos;
	Ui ui;
	boolean gameOver;
	int counter;

	public LogicaPuzzle(PApplet _app, Table _table) {
		stage = 0;
		app = _app;
		ui = new Ui(app);
		table = _table;
		app.colorMode(app.HSB, 360, 100, 100);
		log = new Logica(app);
		fig = new EncontrarFigura(app, System.currentTimeMillis());
		img = app.loadImage("fondo.png");
		changeDos = true;
		dataSaved = false;
		gameOver = false;
		counter = 1;
	}

	public void paint() {
		
		if (gameOver == true) {
			ui.paint();
		} else {
		app.image(img, 0, 0);
		if (stage == 0) {
			log.pintar();
			if (log.getEndTime()>=360000) {
				stage = 1;
			}
		} else if (stage == 1) {
			fig.setTime(log.getTime());
			fig.pintar();
			
			//CAMBIO DE PANTALLA, ENTRA S�LO UNA VEZ
			if(fig.getTime()>=60000 && changeDos){
				stage = 2;
					gameOver = true;
				changeDos = false;
				return;
			}
		}
		}
		
		//System.out.println(fig.getCambio());
	}

	public void pressed() {
		
		if(gameOver == true) {
			ui.click();
			if(ui.getDoneHere() == true) {
				saveData();
			}
		} else {
		
		if (stage == 0)
			log.select();
		System.out.println(app.mouseX + " " + app.mouseY);
		}
	}

	public void dragged() {
		if (stage == 0)
			log.dragged();
	}

	public void released() {
		System.out.println(stage);

		if (log.getX() == 6 && log.getY() == 6) {
			stage = 1;
			fig.setTime(System.currentTimeMillis());
		}

		if (fig.getCambio() == true) {
			stage = 2;
		}

		if (stage == 0) {
			log.released();
		} else if (stage == 1) {
			fig.released();
			System.out.println("logica Click");
		}
	}
	
	public void saveData() {
		TableRow newRow = table.addRow();
		newRow.setString("Tipo", tipoInteligencia);
		newRow.setInt("Puntaje", log.getPuntajeFinal());
		newRow.setInt("Autopuntaje", ui.getAutoScore());
		newRow.setInt("Posicion", ui.getPosition());
		System.out.println("Saving CSV");

		app.saveTable(table, "data/new.csv");
		dataSaved = true;
	}
	public boolean getDataSaved() {
		return dataSaved;
	}
}
