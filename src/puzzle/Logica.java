package puzzle;

import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Logica {
	private PApplet app;
	private PImage[] img;
	private PImage[][] temp;
	private PImage[] button;
	private Ficha miFicha;
	private LinkedList<Tablero> cuadros;
	private LinkedList<Ficha> fichas;
	private int puntaje, nImg;
	private int x, y;
	private Timer time;
	private PFont[] nunito;
	private long startMilis, finalMilis, stopMilis;
	private int stage;
	private int puntajeFinal;

	public Logica(PApplet app) {
		this.app = app;

		// CARGO LOS ROMPECABEZAS;
		img = new PImage[6];
		img[0] = app.loadImage("rompecabezas/4x4.png");
		img[1] = app.loadImage("rompecabezas/4x5.png");
		img[2] = app.loadImage("rompecabezas/5x5.png");
		img[3] = app.loadImage("rompecabezas/5x6.png");
		img[4] = app.loadImage("rompecabezas/6x4.png");
		img[5] = app.loadImage("rompecabezas/6x6.png");

		// CARGO LOS BOTONES

		button = new PImage[3];
		button[0] = app.loadImage("boton/enviar1.png");
		button[1] = app.loadImage("boton/enviar2.png");
		button[2] = app.loadImage("boton/enviar3.png");

		// CARGO TYPO
		nunito = new PFont[3];
		nunito[0] = app.createFont("font/Nunito-Light.ttf", 20);
		nunito[1] = app.createFont("font/Nunito-Regular.ttf", 20);
		nunito[2] = app.createFont("font/Nunito-Bold.ttf", 20);

		cuadros = new LinkedList<Tablero>();
		fichas = new LinkedList<Ficha>();
		x = 4;
		y = 4;
		cargarFichas(img[0], x, y);
		puntaje = 0;
		nImg = 0;

		time = new Timer();
		time.start();

		startMilis = System.currentTimeMillis();

		puntajeFinal = 0;
	}

	private void cargarTablero(PImage img, int x, int y) {
		float cutX, cutY;

		cutX = img.width / x;
		cutY = img.height / y;
		int count = 0;
		for (int j = 0; j < x; j++) {
			for (int i = 0; i < y; i++) {
				count++;
				cuadros.add(new Tablero(app, ((int) cutX * j) + (app.width / 2) - (img.width / 2),
						((int) cutY * i) + (app.height / 2) - (img.height / 2), count, cutX, cutY));
			}
		}
	}

	private void cargarFichas(PImage img, int x, int y) {
		temp = new PImage[x][y];
		int cutX, cutY;
		int count = 0;

		cutX = (int) img.width / x;
		cutY = (int) img.height / y;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				count++;
				temp[i][j] = img.get(cutX * i, cutY * j, cutX, cutY);
				fichas.add(new Ficha(app, temp[i][j], count));
			}
		}
		cargarTablero(img, x, y);
	}

	public void pintar() {
		finalMilis = System.currentTimeMillis();
		stopMilis = finalMilis - startMilis;
		if (stopMilis >= 360000) {
			startMilis = System.currentTimeMillis();
			stage = 1;
		}
		app.fill(0);
		app.ellipse(650, 50, 30, 30);
		app.fill(0);

		nextButton(button, app.width / 2, 685);
		app.pushStyle();
		app.textFont(nunito[2]);
		app.fill(360);
		app.text(puntaje, 50, 650);
		// app.text(time.getMin() + " : " + time.getSeg() + " : " +
		// time.getMilis(), 10, 20);
		app.popStyle();

		finalMilis = System.currentTimeMillis();
		stopMilis = finalMilis - startMilis;
		if (stopMilis <= 5000) {
			// System.out.println(stopMilis);

			app.image(img[nImg], (app.width / 2) - (img[nImg].width / 2), (app.height / 2) - (img[nImg].height / 2));
		} else {
			for (Tablero tablero : cuadros) {
				tablero.pintar();
			}
			for (Ficha ficha : fichas) {
				ficha.pintar();
			}
		}
		System.out.println(puntaje);
	}

	private void nextButton(PImage[] bt, int x, int y) {
		int state;
		if (validar(bt[0], x, y) && app.mousePressed) {
			state = 2;
		} else if (validar(bt[0], x, y)) {
			state = 1;
		} else {
			state = 0;
		}

		app.pushStyle();
		app.imageMode(PApplet.CENTER);
		app.image(bt[state], x, y);
		app.popStyle();
	}

	private boolean validar(PImage img, int x, int y) {
		return app.mouseX > x - (img.width / 2) && app.mouseX < x + (img.width / 2) && app.mouseY > y - (img.height / 2)
				&& app.mouseY < y + (img.height / 2);
	}

	public void select() {
		for (int i = 0; i < fichas.size(); i++) {
			if (fichas.get(i).validar(app.mouseX, app.mouseY)) {
				miFicha = fichas.get(i);
			}
		}
	}

	public void dragged() {
		if (miFicha != null)
			miFicha.mover(app.mouseX, app.mouseY);
	}

	public void released() {
		if (x == 6 && y == 6) {
			stage = 1;
		}
		int punta = 0;
		int dato = 0;
		for (Tablero tablero : cuadros) {
			if (validar(button[0], app.width / 2, 685)) {
				tablero.validar(fichas);
				if (tablero.getScore()) {
					punta += 1;
					int verificado = (x * y);
					System.out.println(punta + " " + verificado);
					if (verificado == punta) {
						if (verificado == 16)
							puntaje += 5;
						if (verificado == 20)
							puntaje += 8;
						if (verificado == 25)
							puntaje += 10;
						if (verificado == 30)
							puntaje += 12;
						if (verificado == 36)
							puntaje += 15;
					}
				}
				// mostrarImage(nImg, 4, 4);
			}
		}

		if (miFicha != null) {
			for (Tablero tablero : cuadros) {
				if (tablero.validatePos(miFicha)) {
					miFicha.mover(tablero.getPosX(), tablero.getPosY());

				}
			}
		}

		if (validar(button[0], app.width / 2, 685)) {
			startMilis = System.currentTimeMillis();
			fichas.clear();
			cuadros.clear();
			nImg++;
			if (x == y) {
				x++;
			} else if (y < x) {
				y++;
			} else {
				x++;
			}

			cargarFichas(img[nImg], x, y);
		}
		miFicha = null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getStage() {
		return stage;
	}

	public long getTime() {
		return startMilis;
	}

	public void mostrarImage(int nimg, int sx, int sy) {
		cargarFichas(img[nimg], sx, sy);
	}
	
	public int getPuntajeFinal() {
		return puntajeFinal;
	}
}
