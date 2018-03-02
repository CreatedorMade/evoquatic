package evoquatic;

public class SimThread implements Runnable {
	
	EFrame frame;
	int state = 0;
	boolean running = false;
	
	public void stop() { state = 0; }
	public void realtime() { state = 1; if(!running) new Thread(this).start(); }
	public void incubate() { state = 2; if(!running) new Thread(this).start(); }
	
	public SimThread(EFrame frame) {
		this.frame = frame;
	}
	
	public void run() {
		running = true;
		while(state != 0) {
			if(state == 1) try {Thread.sleep(1000/60);}catch(Exception e) {}
			tick();
		}
		running = false;
	}
	
	private void tick() {
		
		
		frame.update();
	}
	
}
