package puzzle;

import java.util.Collections;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PImage;

public class EncontrarFigura {

	private PApplet app;
	private PImage[] img;
	int[] xSaved, ySaved;
	private int clickQ;
	private int cantidad;
	private LinkedList<FichaOpcion> opciones;
	private int imgNow;
	private final int cantidadFichas;
	private float puntuacion;
	private float totales;
	private long timeS, timeF, timeD;
	private boolean cambio;

	public EncontrarFigura(PApplet app, long time) {
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

		cantidadFichas = 8;
		xSaved = new int[cantidadFichas];
		ySaved = new int[cantidadFichas];

		opciones = new LinkedList<FichaOpcion>();
		cantidad = 1;
		imgNow = 0;
		clickQ = cantidad;
		ficha(img[imgNow], 6, 6, cantidad);
		puntuacion = 0;
		timeS = time;
		totales = 0;
		cambio = false;
	}

	public void pintar() {
		timeF = System.currentTimeMillis();
		timeD = timeF - timeS;

		app.pushStyle();
		app.image(img[imgNow], (app.width / 2) - (img[imgNow].width / 2), (app.height / 2) - (img[imgNow].height / 2));
		app.popStyle();
		for (FichaOpcion opcion : opciones) {
			opcion.pintar();
		}
		app.pushStyle();
		app.fill(360);
		app.text(timeD, app.width / 2, 30);
		if (totales != 0)
			app.text((puntuacion / totales) * 50 + " | cantidad: " + cantidad + " " + clickQ, 529, 43);
		app.popStyle();
	}
	
	public long getTime(){
		return timeD;
	}

	public void ficha(PImage img, int x, int y, int cantidad) {
		int count = 0;
		int tam = 0;
		boolean add;
		while (count < cantidadFichas && tam < cantidadFichas) {
			tam++;
			add = false;

			int xx = (int) app.random(0, x);
			int yy = (int) app.random(0, y);

			int posX = ((int) (img.width / x)) * xx;
			int posY = ((int) (img.height / y)) * yy;

			for (int i = 0; i < tam; i++) {
				if (posX != xSaved[i] || posY != ySaved[i]) {
					add = true;
				} else {
					add = false;
					break;
				}
			}

			if (add) {
				xSaved[count] = posX;
				ySaved[count] = posY;
				count++;
			}
		}
		System.out.println("HOLI");

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
	}
	

	
	public boolean getCambio() {
		return cambio;
	}

	public void setTime(long time) {
		this.timeS = time;
	}
}
