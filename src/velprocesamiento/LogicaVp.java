package velprocesamiento;

import java.util.LinkedList;

import lenguaje.Ui;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;

public class LogicaVp {

	private PApplet app;
	private Ui ui;
	private boolean start, gameOver, dataSaved;
	private Table table;
	public String tipoInteligencia = "Velocidad Procesamiento";
	private float correct;
	private LinkedList<Patron> patrones;
	private LinkedList<Bandera> banderas;
	private Cronometro crono;
	private boolean isReleased;
	private Patron first;
	private PImage fondo1;
	private PImage fondo2;
	private int min = 0, max = 7;
	private boolean isIn;
	private int pantalla;
	private PImage[] boton;
	private PImage[] asteroides;

	private PImage instruc;
	private int button;
	private PImage asteroide;
	private int turnosG2;

	public LogicaVp(PApplet _app, Table _table) {
		app = _app;
		table = _table;
		start = false;
		gameOver = false;
		dataSaved = false;
		isIn = true;
		correct = 0;
		ui = new Ui(app);
		patrones = new LinkedList<Patron>();
		banderas = new LinkedList<Bandera>();
		databaseFlags();
		crono = new Cronometro(app);


		boton = new PImage[3];
		boton[0] = app.loadImage("../data/boton/Recurso 1.png");
		boton[1] = app.loadImage("../data/boton/Recurso 2.png");
		boton[2] = app.loadImage("../data/boton/Recurso 3.png");

		fondo1 = app.loadImage("../data/minigame1.png");
		fondo2 = app.loadImage("../data/minigame2.png");
		instruc = app.loadImage("../data/instrucciones.png");

		asteroides = new PImage[6];
		cargarAsteroides();
		first = new Patron(app, asteroides[0], app.width / 2, -50, 0);
		patrones.add(first);

	}

	private void cargarAsteroides() {
		for (int i = 0; i < asteroides.length; i++) {
			asteroides[i] = app.loadImage("../data/asteroides/asteriode_" + i + ".png");
		}
		
		asteroide = app.loadImage("../data/asteroides/asteroide.png");
	}

	private void crearAsteroides() {
		int tipoAst = (int) app.random(0, 2);
		switch (tipoAst) {
		case 0:
			int r = (int) app.random(0, 6);
			patrones.add(new Patron(app, asteroides[r], app.width / 2, -50, 0));
			break;

		case 1:
			patrones.add(new Patron(app, asteroide, app.width / 2, -50, 1));

			break;
		}
	}

	public void paint() {
		if (start != true) {
			app.text("Pruebas Velocidad de procesamiento", app.width / 2, app.height / 2);
		} else {
			if (start == true && gameOver == false) {
				switch (pantalla) {
				case 0:
					instrucciones();
					break;

				case 1:
					secondGame();
					break;

				case 2:
					crono.setCorriendo(true);
					firstGame();
					break;
				}

//				app.textSize(20);
//				app.textAlign(PApplet.CENTER, PApplet.CENTER);
//				app.rectMode(PApplet.CENTER);
//				app.fill(255);
//				app.rect( app.width / 2, app.height - 100, 150, 40);
//				app.rectMode(PApplet.CORNER);
//				app.fill(0);
//				app.text("puntaje: " + correct, app.width / 2, app.height - 100);
//				app.noFill();
			}

			// System.out.println("tiempo: " + crono.getSec());
			if (gameOver == true) {
				ui.paint();
				if (ui.getDoneHere() == true) {
					saveData();
				}
			}
		}
	}

	private void instrucciones() {
		app.image(instruc, 0, 0);
		app.imageMode(PApplet.CENTER);
		if (!isReleased) {
			if (app.mouseX >= ((app.width / 2) - 100) && app.mouseX <= ((app.width / 2) + 100)
					&& app.mouseY >= ((app.height / 3) * 2) - 15 && app.mouseY <= ((app.height / 3) * 2) + 15) {
				app.image(boton[1], ((app.width / 2)), ((app.height / 3) * 2 + 50));
			} else {
				app.image(boton[0], ((app.width / 2)), ((app.height / 3) * 2 + 50));
			}
		} else {
			app.image(boton[2], ((app.width / 2)), ((app.height / 3) * 2 + 50));
		}
		app.imageMode(PApplet.CORNER);
	}

	private void firstGame() {
		app.image(fondo1, 0, 0);
		if ((crono.getSec() >= 10 || crono.getMin() >= 0) ) {

			for (int i = 0; i < patrones.size(); i++) {
				patrones.get(i).mover();
				patrones.get(i).pintar();
				Patron pTemp = patrones.getLast();
				if (pTemp.getX() >= app.width + 40 || pTemp.getX() <= -40) {
					System.out.println("SE CREA NUEVO ASTEROIDE");
					validacionAsteroides();
					System.out.println("PUNTAJE: " + correct);
					crearAsteroides();
					patrones.getLast().setSpeed();
					patrones.getLast().setDer(false);
					patrones.getLast().setIzq(false);
					patrones.removeFirst();
					turnosG2 += 1;
				} else if (patrones.getLast().getY() >= app.height + 60) {
					crearAsteroides();
					System.out.println("PUNTAJE: " + correct);
					patrones.getLast().setSpeed();
					patrones.getLast().setDer(false);
					patrones.getLast().setIzq(false);
					patrones.removeFirst();
					turnosG2 += 1;
				}
			}
		}
		if (turnosG2 == 15) {
			gameOver = true;
		}

	}
	private void validacionAsteroides() {
		if (patrones.getLast().getType() == 0 && patrones.getLast().getX() <= -40) {
			correct+=1;
		} else if (patrones.getLast().getType() == 1 && patrones.getLast().getX() >= app.width + 40) {
			correct+=1;
		}
	}

	private void secondGame() {
		app.image(fondo2, 0, 0);

		for (int i = min; i < max; i++) {
			banderas.get(i).pintar();
			/*
			 * app.rectMode(PApplet.CENTER); app.fill(255);
			 * app.rect(banderas.get(i).getX(), banderas.get(i).getY()+20, 30,
			 * 30); app.fill(0); app.text(i, banderas.get(i).getX(),
			 * banderas.get(i).getY()+25); app.rectMode(PApplet.CORNER);
			 */
		}
	}

	public void key() {
		if (app.keyCode == PApplet.RIGHT) {
			patrones.getLast().setDer(true);
			patrones.getLast().setIzq(false);
			System.out.println("entra der");
		}

		if (app.keyCode == PApplet.LEFT) {
			patrones.getLast().setIzq(true);
			patrones.getLast().setDer(false);
		}
	}

	private void cargarBanderas() {

		for (int i = 1; i < 71; i++) {
			PImage imgTemp = app.loadImage("../data/banderas/Recurso " + i + ".png");
			Bandera bTemp = new Bandera(app, imgTemp, 120 + 130 * (i % 7), 450, 0);
			banderas.add(bTemp);
		}

		banderas.get(3).setType(1);
		banderas.get(11).setType(1);
		banderas.get(16).setType(1);
		banderas.get(19).setType(1);
		banderas.get(25).setType(1);
		banderas.get(32).setType(1);
		banderas.get(36).setType(1);
		banderas.get(48).setType(1);
		banderas.get(50).setType(1);
		banderas.get(57).setType(1);
		banderas.get(66).setType(1);

	}

	public void databaseFlags() {
		cargarBanderas();
		// CONDICI�N DE INICIO
		start = true;
	}

	public void validacionBanderas() {
		for (int i = min; i < max; i++) {

			// System.out.println(app.mouseX + " " + app.mouseY);

			if (isIn) {

				if (banderas.get(i).validarClick(app.mouseX, app.mouseY)) {
					if (banderas.get(i).getType() == 1) {
						correct += 1;
						System.out.println("PUNTAJE = " + correct);
					}
					if (max < 70) {
						min += 7;
						max += 7;
						System.out.println("BANDERA # " + i + "POS X: " + banderas.get(i).getX() + "POS Y: "
								+ banderas.get(i).getY());
						System.out.println("entra");
						isIn = false;
						break;
					}

					if (max >= 70) {
						pantalla = 2;
					}

				}
			}

		}
	}

	public void click() {
		if (isReleased == true) {
			if (pantalla == 0) {
				if (app.mouseX >= ((app.width / 2) - 100) && app.mouseX <= ((app.width / 2) + 100)
						&& app.mouseY >= ((app.height / 3) * 2 + 50) - 15
						&& app.mouseY <= ((app.height / 3) * 2) + 15 + 50) {
					pantalla = 1;
				}
			}
			if (pantalla == 1) {
				validacionBanderas();
				isReleased = false;
				System.out.println("El min es: " + min + " y el max es: " + max);

			}
			if (gameOver == true) {
				ui.click();
			}
		}
	}

	public void mReleased() {
		isReleased = true;
		isIn = true;
	}

	public void saveData() {
		TableRow newRow = table.addRow();
		newRow.setString("Tipo", tipoInteligencia);
		newRow.setFloat("Puntaje", calcularPuntaje());
		newRow.setInt("Autopuntaje", ui.getAutoScore());
		newRow.setInt("Posicion", ui.getPosition());
		System.out.println("Saving CSV");
		app.saveTable(table, "data/new.csv");
		dataSaved = true;
	}

	public boolean getDataSaved() {
		return dataSaved;
	}
	public float calcularPuntaje(){
		float porcentaje;
		porcentaje= (correct/25)*100;
		return porcentaje;
	}
}
