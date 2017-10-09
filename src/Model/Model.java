package Model;


public class Model {
	
	public String name;
	public double time;
	public int score;
	
	
	public Model(String _name, double _time, int _score) {
		name = _name;
		time = _time;
		score = _score;
	}
	
	public String getName() {
		return name;
	}
}
