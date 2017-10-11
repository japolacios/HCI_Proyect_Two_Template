package lenguaje;
import processing.core.PApplet;

public class Ui {
	PApplet app;
	
	public Ui(PApplet _app) {
		app = _app;
	}
	
	public void paint() {
		//Here we paint the UI
		app.text("Como crees que te fue en la prueba", app.width/2, app.height/3);
		for (int i = 0; i < 10; i++) {
			app.fill(0);
			app.rect(((app.width/10)*i)+((app.width/10)/2), (app.height/3)+50, app.width/10, 50);
			app.fill(255);
			app.text(i+1, ((app.width/10)*i)+((app.width/10)/2), (app.height/3)+50);
		}
		app.fill(0);
		app.text("Como crees que estas con respecto al resto de participantes", app.width/2, (app.height/3)*2);
		for (int i = 0; i < 10; i++) {
			app.fill(0);
			app.rect(((app.width/10)*i)+((app.width/10)/2), (app.height/3)*2+50, app.width/10, 50);
			app.fill(255);
			app.text(i+1, ((app.width/10)*i)+((app.width/10)/2), (app.height/3)*2+50);
		}
	}
}
