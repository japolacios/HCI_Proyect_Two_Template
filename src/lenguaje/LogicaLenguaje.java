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
	private boolean start, gameOver, dataSaved, released;
	private Table table; // Se recibe desde Ejecutable para guardar los datos
	public String tipoInteligencia = "Inteligencia Linguistica";

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
		released = true;
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
		// Correcta 1
		MultipleChoiseModel q1 = new MultipleChoiseModel("1.Internet es a Comnuicacion como Carro es a", "Esta",
				"Queso", "Zapato", "Sizas");
		// Correcta 2
		MultipleChoiseModel q2 = new MultipleChoiseModel("2.Perro esa purina como wiskas es a", "Queso", "Esta", "Zapato",
				"Sizas");
		// correcta 1
		MultipleChoiseModel q3 = new MultipleChoiseModel("3.Hitman es a matar como Politico es a", "Esta", "Queso",
				"Zapato", "Sizas");
		// correcta 2
		MultipleChoiseModel q4 = new MultipleChoiseModel("4.Hitman es a matar como Politico es a", "Robar", "Esta",
				"Zapato", "Sizas");
		// correcta 4
		MultipleChoiseModel q5 = new MultipleChoiseModel("5.Hitman es a matar como Politico es a", "Robar", "Queso",
				"Zapato", "Esta");
		// correcta 1
		MultipleChoiseModel q6 = new MultipleChoiseModel("6.Hitman es a matar como Politico es a", "Esta", "Queso",
				"Zapato", "Sizas");
		// correcta 4
		MultipleChoiseModel q7 = new MultipleChoiseModel("7.Hitman es a matar como Politico es a", "Robar", "Queso",
				"Zapato", "Esta");
		// correcta 2
		MultipleChoiseModel q8 = new MultipleChoiseModel("8.Hitman es a matar como Politico es a", "Robar", "Esta",
				"Zapato", "Sizas");
		// correcta 3
		MultipleChoiseModel q9 = new MultipleChoiseModel("9.Hitman es a matar como Politico es a", "Robar", "Queso",
				"Esta", "Sizas");
		// correcta 3
		MultipleChoiseModel q10 = new MultipleChoiseModel("10.Hitman es a matar como Politico es a", "Robar", "Queso",
				"Esta", "Sizas");
		// correcta 2
		MultipleChoiseModel q11 = new MultipleChoiseModel("11.Hitman es a matar como Politico es a", "Robar", "Esta",
				"Zapato", "Sizas");
		// correcta 1
		MultipleChoiseModel q12 = new MultipleChoiseModel("12.Hitman es a matar como Politico es a", "Esta", "Queso",
				"Zapato", "Sizas");
		// correcta 4
		MultipleChoiseModel q13 = new MultipleChoiseModel("13.Hitman es a matar como Politico es a", "Robar", "Queso",
				"Zapato", "Esta");
		// correcta 1
		MultipleChoiseModel q14 = new MultipleChoiseModel("14.Hitman es a matar como Politico es a", "Esta", "Queso",
				"Zapato", "Sizas");
		// correcta 3
		MultipleChoiseModel q15 = new MultipleChoiseModel("15.Hitman es a matar como Politico es a", "Robar", "Queso",
				"Esta", "Sizas");

		multiples.add(q1);
		multiples.add(q2);
		multiples.add(q3);
		multiples.add(q4);
		multiples.add(q5);
		multiples.add(q6);
		multiples.add(q7);
		multiples.add(q8);
		multiples.add(q9);
		multiples.add(q10);
		multiples.add(q11);
		multiples.add(q12);
		multiples.add(q13);
		multiples.add(q14);
		multiples.add(q15);
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
			if (released == true) {
				if (question == 0) {
					if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
						released = false;
						question++;
						correct++;
						return;
					}
				}
				if (question == 1) {
					if (app.dist(app.mouseX, app.mouseY, (app.width / 3), (app.height / 4) * 3) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				}
				if (question == 2) {
					if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				} 
				if (question == 3) {
					if (app.dist(app.mouseX, app.mouseY, (app.width / 3), (app.height / 4) * 3) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				}
				if (question == 4) {
					if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				}
				if (question == 5) {
					if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				}
				if (question == 6) {
					if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
						question++;
						correct++;						
						released = false;
						return;
					}
				}
				if (question == 7) {
					if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				}
				if (question == 8) {
					if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				}
				if (question == 9) {
					if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				}
				if (question == 10) {
					if (app.dist(app.mouseX, app.mouseY, (app.width / 3), (app.height / 4) * 3) <= 100) {
						question++;
						correct++;
						released = false;
						return;
					}
				}
				if (question == 11) {
					if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
						question++;
						correct++;						
						released = false;
						return;
					}
				}
				if (question == 12) {
					if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
						question++;
						correct++;						
						released = false;
						return;
					}
				}
				if (question == 13) {
					if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
						question++;
						correct++;						
						released = false;
						return;
					}
				}
				if (question == 14) {
					if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
						question++;
						correct++;
						released = false;
						gameOver = true;
						return;
					}
					else {
						gameOver = true;
						return;
					}
				}
				else {
					question++;
					released = false;
					return;
				}
			}
		}
		if (gameOver == true) {
			ui.click();
		}
	}

	public void released() {
		released = true;
		//System.out.println("Really released");
	}
	
	
	public boolean getDataSaved() {
		return dataSaved;
	}
}
