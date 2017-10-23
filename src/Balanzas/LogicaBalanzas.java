package Balanzas;

import processing.core.PImage;

import java.util.ArrayList;

import lenguaje.MultipleChoiseModel;
import processing.core.PApplet;
import processing.core.PFont;
import processing.data.Table;
import processing.data.TableRow;

public class LogicaBalanzas {

	private PApplet app;
	private Tiempo tiempo;
	private Ui ui;

	private int correct, question, niveles, pantallas, quiz, min, seg;
	private boolean start, gameOver, dataSaved, released;

	private Table table; // Se recibe desde Ejecutable para guardar los datos
	public String tipoInteligencia = "Inteligencia  Perceptivo";

	private PImage[] uno;
	private PImage cuadrito;
	private PImage[] dos;
	private PImage[] tres;
	private PImage[] cuatro;
	private PImage fondo;

	private PFont letra;

	public LogicaBalanzas(PApplet app, Table _table) {

		this.app = app;
		start = false;
		gameOver = false;
		dataSaved = false;
		correct = 0;
		table = _table;
		ui = new Ui(app);
		tiempo = new Tiempo();
		// released = true;
		question = 0; // DevPurpose
		niveles = 0;
		quiz = 0;

		uno = new PImage[10];
		dos = new PImage[10];
		tres = new PImage[10];
		cuatro = new PImage[10];

		cargarImagenes();
		if (start == true) {
			tiempo.start();
		}
	}

	public void cargarImagenes() {
		System.out.println("Cargando Imagenes");

		uno[0] = app.loadImage("../data/Balanzas/Uno/uno-01.png");
		uno[1] = app.loadImage("../data/Balanzas/Uno/uno-02.png");
		uno[2] = app.loadImage("../data/Balanzas/Uno/uno-03.png");
		uno[3] = app.loadImage("../data/Balanzas/Uno/uno-04.png");
		uno[4] = app.loadImage("../data/Balanzas/Uno/uno-05.png");
		uno[5] = app.loadImage("../data/Balanzas/Uno/uno-06.png");
		uno[6] = app.loadImage("../data/Balanzas/Uno/uno-07.png");
		uno[7] = app.loadImage("../data/Balanzas/Uno/uno-08.png");
		uno[8] = app.loadImage("../data/Balanzas/Uno/uno-09.png");
		uno[9] = app.loadImage("../data/Balanzas/Uno/uno-10.png");

		dos[0] = app.loadImage("../data/Balanzas/Dos/dos-01.png");
		dos[1] = app.loadImage("../data/Balanzas/Dos/dos-02.png");
		dos[2] = app.loadImage("../data/Balanzas/Dos/dos-03.png");
		dos[3] = app.loadImage("../data/Balanzas/Dos/dos-04.png");
		dos[4] = app.loadImage("../data/Balanzas/Dos/dos-05.png");
		dos[5] = app.loadImage("../data/Balanzas/Dos/dos-06.png");
		dos[6] = app.loadImage("../data/Balanzas/Dos/dos-07.png");
		dos[7] = app.loadImage("../data/Balanzas/Dos/dos-08.png");
		dos[8] = app.loadImage("../data/Balanzas/Dos/dos-09.png");
		dos[9] = app.loadImage("../data/Balanzas/Dos/dos-10.png");

		tres[0] = app.loadImage("../data/Balanzas/Tres/tres-01.png");
		tres[1] = app.loadImage("../data/Balanzas/Tres/tres-02.png");
		tres[2] = app.loadImage("../data/Balanzas/Tres/tres-03.png");
		tres[3] = app.loadImage("../data/Balanzas/Tres/tres-04.png");
		tres[4] = app.loadImage("../data/Balanzas/Tres/tres-05.png");
		tres[5] = app.loadImage("../data/Balanzas/Tres/tres-06.png");
		tres[6] = app.loadImage("../data/Balanzas/Tres/tres-07.png");
		tres[7] = app.loadImage("../data/Balanzas/Tres/tres-08.png");
		tres[8] = app.loadImage("../data/Balanzas/Tres/tres-09.png");
		tres[9] = app.loadImage("../data/Balanzas/Tres/tres-10.png");

		cuatro[0] = app.loadImage("../data/Balanzas/Cuatro/cuatro-01.png");
		cuatro[1] = app.loadImage("../data/Balanzas/Cuatro/cuatro-02.png");
		cuatro[2] = app.loadImage("../data/Balanzas/Cuatro/cuatro-03.png");
		cuatro[3] = app.loadImage("../data/Balanzas/Cuatro/cuatro-04.png");
		cuatro[4] = app.loadImage("../data/Balanzas/Cuatro/cuatro-05.png");
		cuatro[5] = app.loadImage("../data/Balanzas/Cuatro/cuatro-06.png");
		cuatro[6] = app.loadImage("../data/Balanzas/Cuatro/cuatro-07.png");
		cuatro[7] = app.loadImage("../data/Balanzas/Cuatro/cuatro-08.png");
		cuatro[8] = app.loadImage("../data/Balanzas/Cuatro/cuatro-09.png");
		cuatro[9] = app.loadImage("../data/Balanzas/Cuatro/cuatro-10.png");

		fondo = app.loadImage("../data/Balanzas/fondis-01.png");
		cuadrito = app.loadImage("../data/Balanzas/fon-01.png");
		letra = app.createFont("../data/Balanzas/Nunito-BlackItalic.ttf", 30);
	}

	public void saveData() {
		TableRow newRow = table.addRow();
		newRow.setString("Tipo", tipoInteligencia);
		newRow.setInt("Puntaje", correct);
		newRow.setInt("Autopuntaje", ui.getAutoScore());
		newRow.setInt("Posicion", ui.getPosition());
		System.out.println("Saving CSV");

		app.saveTable(table, "data/new.csv");
		dataSaved = true;
	}

	public void paint() {
		
		System.out.println(min + "," + seg);
		if (start == false) {
			app.image(fondo, 0, 0);
		} else {
			if (start == true && gameOver == false) {
				paintNiveles();
				app.image(cuadrito, 0, 0);
				tiempo();
			}
			if (gameOver == true) {
				app.background(255);
				ui.paint();
				if (ui.getDoneHere() == true) {
					saveData();
				}
			}
		}
	}

	public void tiempo() {
		min = tiempo.getMin();
		seg = tiempo.getSeg();
		app.fill(255);
		app.textFont(letra, 45);
		app.text(min + ":" + seg, 910, 75);
		if (min == 1 && seg == 30) {
			gameOver = true;
		}
	}

	private void paintNiveles() {
		pasarnivel();

		switch (niveles) {
		case 0:
			nivelU();
			break;

		case 1:
			nivelD();
			break;

		case 2:
			nivelT();
			break;

		case 3:
			nivelC();
			break;
		}
	}

	public void nivelU() {
		switch (question) {
		case 0:
			app.image(uno[question], 0, 0);
			break;

		case 1:
			app.image(uno[question], 0, 0);
			break;

		case 2:
			app.image(uno[question], 0, 0);
			break;

		case 3:
			app.image(uno[question], 0, 0);
			break;

		case 4:
			app.image(uno[question], 0, 0);
			break;

		case 5:
			app.image(uno[question], 0, 0);
			break;

		case 6:
			app.image(uno[question], 0, 0);
			break;

		case 7:
			app.image(uno[question], 0, 0);
			break;

		case 8:
			app.image(uno[question], 0, 0);
			break;

		case 9:
			app.image(uno[question], 0, 0);
			break;
		}
	}

	public void nivelD() {
		switch (question) {
		case 0:
			app.image(dos[question], 0, 0);
			break;

		case 1:
			app.image(dos[question], 0, 0);
			break;

		case 2:
			app.image(dos[question], 0, 0);
			break;

		case 3:
			app.image(dos[question], 0, 0);
			break;

		case 4:
			app.image(dos[question], 0, 0);
			break;

		case 5:
			app.image(dos[question], 0, 0);
			break;

		case 6:
			app.image(dos[question], 0, 0);
			break;

		case 7:
			app.image(dos[question], 0, 0);
			break;

		case 8:
			app.image(dos[question], 0, 0);
			break;

		case 9:
			app.image(dos[question], 0, 0);
			break;
		}
	}

	public void nivelT() {
		switch (question) {
		case 0:
			app.image(tres[0], 0, 0);
			break;

		case 1:
			app.image(tres[1], 0, 0);
			break;

		case 2:
			app.image(tres[2], 0, 0);
			break;

		case 3:
			app.image(tres[3], 0, 0);
			break;

		case 4:
			app.image(tres[4], 0, 0);
			break;

		case 5:
			app.image(tres[5], 0, 0);
			break;

		case 6:
			app.image(tres[6], 0, 0);
			break;

		case 7:
			app.image(tres[7], 0, 0);
			break;

		case 8:
			app.image(tres[8], 0, 0);
			break;

		case 9:
			app.image(tres[9], 0, 0);
			break;
		}
	}

	public void nivelC() {
		switch (question) {
		case 0:
			app.image(cuatro[0], 0, 0);
			break;

		case 1:
			app.image(cuatro[1], 0, 0);
			break;

		case 2:
			app.image(cuatro[2], 0, 0);
			break;

		case 3:
			app.image(cuatro[3], 0, 0);
			break;

		case 4:
			app.image(cuatro[4], 0, 0);
			break;

		case 5:
			app.image(cuatro[5], 0, 0);
			break;

		case 6:
			app.image(cuatro[6], 0, 0);
			break;

		case 7:
			app.image(cuatro[7], 0, 0);
			break;

		case 8:
			app.image(cuatro[8], 0, 0);
			break;

		case 9:
			app.image(cuatro[9], 0, 0);
			break;
		}
	}

	public boolean getDataSaved() {
		return dataSaved;
	}

	public void click() {
		clickC();
		clickT();
		clickD();
		clickU();
		if (app.mouseX >= 437 && app.mouseX <= 590 && app.mouseY >= 607 && app.mouseY <= 656 && start == false) {
			start = true;
			tiempo.start();
		}
		System.out.println(quiz);
		if (gameOver == true) {
			ui.click();
		}

	}

	public void clickU() {
		// pregunta 10
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 9) {
			question = (int) app.random(1, 10);
			quiz += 1;
			correct = correct + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 9) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 9) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 9) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}

		// pregunta 9
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 8) {
			question = (int) app.random(1, 10);
			correct = correct + 1;
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		// pregunta 8
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz += 1;
			correct = correct + 1;
		}

		// pregunta 7
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz += 1;
			correct = correct + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}

		// pregunta 6
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz += 1;

		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz += 1;
			correct = correct + 1;
		}

		// pregunta 5
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz += 1;
			correct = correct + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		// pregunta 4
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 3) {
			question = 4;
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 3) {
			question = (int) app.random(1, 10);
			correct = correct + 1;
			quiz += 1;
		}

		// pregunta 3
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz += 1;
			correct = correct + 1;
		}

		// pregunta 2
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz += 1;
			correct = correct + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}

		// pregunta 1
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 0
				&& question == 0) {
			question = (int) app.random(1, 10);
			correct = correct + 1;
			quiz += 1;
		}
	}

	public void clickD() {
		// pregunta 10
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 9) {
			question = (int) app.random(1, 10);
			niveles = 2;
			quiz = quiz + 1;

		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 9) {
			question = (int) app.random(1, 10);
			niveles = 2;
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 9) {
			question = (int) app.random(1, 10);
			niveles = 2;
			quiz = quiz + 1;
			correct = correct + 2;

		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 9) {
			question = (int) app.random(1, 10);
			niveles = 2;
			quiz = quiz + 1;
		}

		// pregunta 9
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 8) {
			question = (int) app.random(1, 10);
			correct = correct + 2;
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		// pregunta 8
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 2;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}

		// pregunta 7
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 2;
		}

		// pregunta 6
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 5) {
			quiz = quiz + 1;
			question = (int) app.random(1, 10);
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 2;

		}

		// pregunta 5
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 4) {
			correct = correct + 2;
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		// pregunta 4
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 3) {
			question = (int) app.random(1, 10);
			correct = correct + 2;
			quiz = quiz + 1;
		}

		// pregunta 3
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 2) {
			quiz = quiz + 1;
			question = (int) app.random(1, 10);
			correct = correct + 2;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}

		// pregunta 2
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 1) {
			question = (int) app.random(1, 10);
			correct = correct + 2;
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}

		// pregunta 1
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 2;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 1
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
	}

	public void clickT() {
		// pregunta 10
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 9) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 9) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 9) {
			question = (int) app.random(1, 10);

			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 9) {
			question = (int) app.random(1, 10);
			correct = correct + 3;
			quiz = quiz + 1;
		}

		// pregunta 9
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 3;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		// pregunta 8
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 7) {
			question = (int) app.random(1, 10);
			correct = correct + 3;
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 7) {
			question = (int) app.random(1, 10);
			correct = correct + 1;
			quiz = quiz + 1;
		}

		// pregunta 7
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 6) {
			correct = correct + 3;
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}

		// pregunta 6
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 5) {
			question = (int) app.random(1, 10);
			correct = correct + 3;
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}

		// pregunta 5
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 3;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		// pregunta 4
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 3;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}

		// pregunta 3
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 3;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}

		// pregunta 2
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 3;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}

		// pregunta 1
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
			correct = correct + 3;

		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;

		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 2
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz = quiz + 1;
		}
	}

	public void clickC() {
		// pregunta 10
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 9) {
			quiz += 1;
			question = (int) app.random(1, 10);
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 9) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 9) {
			question = (int) app.random(1, 10);
			quiz += 1;

		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 9) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}

		// pregunta 9
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 8) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 8) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		// pregunta 8
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 7) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 7) {
			question = (int) app.random(1, 10);
			quiz += 1;

		}

		// pregunta 7
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz += 1;

		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 6) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 6) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}

		// pregunta 6
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 5) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 5) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}

		// pregunta 5
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 4) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 4) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}
		// pregunta 4
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 3) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 3) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}

		// pregunta 3
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 2) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 2) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}

		// pregunta 2
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 1) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz += 1;

		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 1) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}

		// pregunta 1
		if (app.mouseX >= 150 && app.mouseX <= 285 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
		if (app.mouseX >= 348 && app.mouseX <= 475 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 0) {
			question = (int) app.random(1, 10);
			correct = correct + 4;
			quiz += 1;
		}
		if (app.mouseX >= 549 && app.mouseX <= 676 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 0) {
			question = (int) app.random(1, 10);
			correct = correct + 1;
			quiz += 1;
		}
		if (app.mouseX >= 749 && app.mouseX <= 867 && app.mouseY >= 511 && app.mouseY <= 634 && niveles == 3
				&& question == 0) {
			question = (int) app.random(1, 10);
			quiz += 1;
		}
	}

	public void released() {
		released = true;
	}

	public void pasarnivel() {
		if (niveles == 0 && quiz >= 10) {
			niveles = 1;
		}
		if (niveles == 1 && quiz >= 20) {
			niveles = 2;

		}
		if (niveles == 2 && quiz >= 30) {
			niveles = 3;

		}
		if (niveles == 3 && quiz >= 40) {
			gameOver = true;
		}
	}
}