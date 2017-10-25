package puzzle;

public class Timer extends Thread {

	int milli, seg, min;
	private boolean isStarted;

	public Timer() {
		milli = 0;
		seg = 0;
		min = 0;
		isStarted = true;
	}

	@Override
	public void run() {
		System.out.println(isStarted);
		while (isStarted) {
			try {
				milli++;
				if (milli == 1000) {
					seg++;
					milli = 0;
				}
				if (seg == 60) {
					min++;
					seg = 0;
				}

				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getMilis() {
		return milli;
	}

	public int getSeg() {
		return seg;
	}

	public int getMin() {
		return min;
	}

	public void setMilis(int milis) {
		this.milli = milis;
	}

	public void setSeg(int seg) {
		this.seg = seg;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public void setTime() {
		isStarted = !isStarted;
	}
}
