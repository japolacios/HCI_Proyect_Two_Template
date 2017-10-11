package lenguaje;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class LogicaLenguaje {

	private PApplet app;
	private ArrayList<MultipleChoiseModel> multiples;
	private Ui ui;
	private int correct, question;
	private boolean start, gameOver, dataSaved;
	private Table table;
	public String tipoInteligencia = "El Tipo de Inteligencia";

	public LogicaLenguaje(PApplet _app, Table _table) {
		app = _app;
		start = false;
		gameOver = false;
		dataSaved = false;
		correct = 0;
		table = _table;
		multiples = new ArrayList<MultipleChoiseModel>();
		ui = new Ui(app);
		populateMultiple();
		question = 0; // DevPurpose
	}

	public void paint() {
		if (start == false) {
			app.text("Pruebas de Lenguaje", app.width / 2, app.height / 2);
		} else {
			if (start == true && gameOver == false) {
				paintQuestions(question);
			}
			if (gameOver == true) {
				ui.paint();
				if (ui.getDoneHere() == true) {
					saveData();
				}
			}
		}
	}

	public void paintQuestions(int _q) {
		app.text(multiples.get(_q).getQuestion(), (app.width / 2), (app.height / 4));

		app.text((String) multiples.get(_q).gewtAnswerOne().get(0), (app.width / 3), (app.height / 4) * 2);
		app.text((String) multiples.get(_q).gewtAnswerOne().get(1), (app.width / 3), (app.height / 4) * 3);
		app.text((String) multiples.get(_q).gewtAnswerOne().get(2), (app.width / 3) * 2, (app.height / 4) * 2);
		app.text((String) multiples.get(_q).gewtAnswerOne().get(3), (app.width / 3) * 2, (app.height / 4) * 3);

	}

	public void populateMultiple() {
		System.out.println("Creating Questions DataBase");
		MultipleChoiseModel q1 = new MultipleChoiseModel("Internet es a Comnuicacion como Carro es a", "Transporte",
				"Queso", "Zapato", "Sizas");

		MultipleChoiseModel q2 = new MultipleChoiseModel("Perro esa purina como huskies es a", "Gato", "Queso",
				"Zapato", "Sizas");

		MultipleChoiseModel q3 = new MultipleChoiseModel("Hitman es a matar como Politico es a", "Robar", "Queso",
				"Zapato", "Sizas");

		multiples.add(q1);
		multiples.add(q2);
		multiples.add(q3);
		start = true;
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

	public void click() {
		if (gameOver == false) {
			if (question == 0) {
				if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
					question++;
					correct++;
				}
			}
			if (question == 1) {
				if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
					question++;
					correct++;
					gameOver = true;
				}
			}
		}
		if (gameOver == true) {
			ui.click();
		}
	}
	
	public boolean getDataSaved() {
		return dataSaved;
	}
}
