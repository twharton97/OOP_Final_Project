package Game;


public class Engine {
	int tickCount = 0;

	public void startTicking() {

		try {
			while (true) {
				Thread.sleep(300);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tickCount++;
	}

}
