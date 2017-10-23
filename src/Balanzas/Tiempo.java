package Balanzas;

public class Tiempo extends Thread {

	int min, seg;

	public Tiempo() {
	}

	public void run() {
		try {
			for (min = 0; min < 60; min++) {
				for (seg = 0; seg < 60; seg++) {
					Thread.sleep(1000);
				}
			}

		} catch (InterruptedException e) {
		}
	}

	public int getMin() {
		return min;
	}

	public int getSeg() {
		return seg;
	}
}