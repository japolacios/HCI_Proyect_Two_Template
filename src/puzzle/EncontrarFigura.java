package puzzle;

import java.util.Collections;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PImage;

public class EncontrarFigura {

	private PApplet app;
	private PImage[] img;
	private PImage[] button;
	int[] xSaved, ySaved;
	private int clickQ;
	private int cantidad;
	private LinkedList<FichaOpcion> opciones;
	private int imgNow;
	private final int cantidadFichas;
	private float puntuacion, puntaje;
	private float totales;
	private long timeS, timeF, timeD;
	private boolean cambio, isPlaying;
	private int stage;
	private PImage rule;

	public EncontrarFigura(PApplet app) {
		this.app = app;

		// CARGO LAS IMAGENES A USAR
		img = new PImage[8];
		img[0] = app.loadImage("encontrarFicha/01.png");
		img[1] = app.loadImage("encontrarFicha/02.png");
		img[2] = app.loadImage("encontrarFicha/03.png");
		img[3] = app.loadImage("encontrarFicha/04.png");
		img[4] = app.loadImage("encontrarFicha/05.png");
		img[5] = app.loadImage("encontrarFicha/06.png");
		img[6] = app.loadImage("encontrarFicha/07.png");
		img[7] = app.loadImage("encontrarFicha/08.png");

		button = new PImage[3];
		button[0] = app.loadImage("boton2/enviar1.png");
		button[1] = app.loadImage("boton2/enviar2.png");
		button[2] = app.loadImage("boton2/enviar3.png");

		rule = app.loadImage("pantalla2.png");

		cantidadFichas = 8;
		xSaved = new int[cantidadFichas];
		ySaved = new int[cantidadFichas];

		opciones = new LinkedList<FichaOpcion>();
		cantidad = 1;
		imgNow = 0;
		clickQ = cantidad;
		ficha(img[imgNow], 6, 6, cantidad);
		puntuacion = 0;
		timeS = System.currentTimeMillis();
		timeF = System.currentTimeMillis();
		totales = 0;
		cambio = false;
		stage = 0;
		isPlaying = false;
	}

	public void pintar() {
		switch (stage) {
		case 0:
			app.image(rule, 0, 0);
			nextButton(button, app.width / 2, 600);
			break;
		case 1:
			// app.text(puntuacion, app.width / 2, 150);
			timeF = System.currentTimeMillis();
			timeD = timeF - timeS;
			puntaje = (puntuacion/totales)*50;

			app.pushStyle();
			app.image(img[imgNow], (app.width / 2) - (img[imgNow].width / 2),
					(app.height / 2) - (img[imgNow].height / 2));
			app.popStyle();
			for (FichaOpcion opcion : opciones) {
				opcion.pintar();
			}
			app.pushStyle();
			app.fill(360);
			app.textAlign(PApplet.CENTER,PApplet.CENTER);
			//app.text(puntaje, app.width / 2, 30);
			app.textSize(12);
			app.text("Cantidad de click restantes: " + clickQ, app.width/2, 80);
			app.popStyle();
			break;
		}
	}

	public long getTime() {
		return timeD;
	}

	public void ficha(PImage img, int x, int y, int cantidad) {
		int count = 0;
		int tam = 0;
		boolean add;
		while (tam < cantidadFichas) {
			
			add = false;

			int xx = (int) app.random(0, x);
			int yy = (int) app.random(0, y);

			int posX = ((int) (img.width / x)) * xx;
			int posY = ((int) (img.height / y)) * yy;
			System.out.println("-----------------------");
			for (int i = 0; i < tam+1; i++) {
				System.out.println(posX+" | "+xSaved[i]+" :: "+posY+" | "+ySaved[i]+" ");
				if (posX == xSaved[i] && posY == ySaved[i]) {
					add = false;
					break;
				} else {
					add = true;
					System.out.println("adding");
				}
			}

			if (add) {
				tam++;
				xSaved[count] = posX;
				ySaved[count] = posY;
				count++;
			}
		}
		
		for (int i = 0; i < xSaved.length; i++) {
			System.out.println(xSaved[i]+" "+ySaved[i]);
		}

		PImage temp;
		boolean isCovered;
		int covering = 0;
		for (int j = 0; j < cantidadFichas; j++) {
			isCovered = false;
			temp = img.get((int) xSaved[j], (int) ySaved[j], img.width / x, img.width / y);

			if (covering < cantidad) {
				isCovered = true;
				covering++;
			} else
				isCovered = false;

			int medioX = (app.width / 2) - (img.width / 2);
			int medioY = (app.height / 2) - (img.height / 2);
			opciones.add(new FichaOpcion(app, temp, 20 + temp.width / 2 + (100 * j), 690, isCovered,
					(int) xSaved[j] + medioX, (int) ySaved[j] + medioY));
		}

		// DESORGANIZA LAS FICHAS
		Collections.shuffle(opciones);
		for (int i = 0; i < opciones.size(); i++) {
			opciones.get(i).setMover(120 + opciones.get(i).getxS() / 2 + (100 * i), 690);
		}
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

	public void released() {

		for (FichaOpcion opcion : opciones) {
			if (opcion.validar(app.mouseX, app.mouseY)) {
				totales += 100;
				if (opcion.getIsRight()) {
					puntuacion += 100;
					opcion.setIsRight(false);
				}
				clickQ--;
			}
		}

		if (clickQ <= 0) {
			imgNow++;
			if (imgNow == 8) {
				imgNow = 0;
				cantidad++;
			}
			clickQ = cantidad;
			opciones.clear();

			ficha(img[imgNow], 6, 6, cantidad);
		}

		if (stage == 0 && validar(button[0], app.width / 2, 600) && isPlaying == false) {
			stage = 1;
			System.out.println("Cambiando en Figura");

			timeS = System.currentTimeMillis();
			isPlaying = true;
		}
	}
	
	public float getPuntaje(){
		return puntaje;
	}

	public boolean getCambio() {
		return cambio;
	}

	public void setTime(long time) {
		this.timeS = time;
	}
}
