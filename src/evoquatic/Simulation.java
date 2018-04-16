package evoquatic;

public class Simulation {
	
	public static final int STATE_LOADING = -1;
	public static final int STATE_TITLE_MAIN = 0;
	public static final int STATE_TITLE_OPTIONS = 1;
	public static final int STATE_TITLE_LOAD = 2;
	public static final int STATE_GAME = 3;
	
	int state = STATE_LOADING;
	
	private SimThread thread = new SimThread(this);
	
	public void setMode(int m) { thread.setMode(m); }
	
	public int getMode() {
		if(!thread.running) return 0;
		if(thread.rt) return 1;
		return 2;
	}
	
	long time;
	int size = 5;
	
	public Simulation() {
		thread.setMode(SimThread.REALTIME);
	}
	
	private class SimThread implements Runnable {
		
		static final int STOPPED = 0;
		static final int REALTIME = 1;
		static final int INCUBATE = 2;
		
		boolean rt = false;
		boolean running = false;
		
		Simulation sim;
		
		public SimThread(Simulation sim) {
			this.sim = sim;
		}
		
		public void run() {
			running = true;
			while(running) {
				long target = System.nanoTime() + 16666666L; //haha 666
				
				
				
				if(rt && running) sync(target);
			}
		}
		
		private void sync(Long time) {
			while(System.nanoTime() < time) {
				try {
					Thread.sleep(1);
				} catch(InterruptedException e) {
					
				}
			}
		}
		
		void setMode(int m) {
			if(m == STOPPED) {
				running = false;
			} else if(m == REALTIME) {
				rt = true;
				if(!running) new Thread(null, this, "SimThread").start();
			} else if(m == INCUBATE) {
				rt = false;
				if(!running) new Thread(null, this, "SimThread").start();
			}
		}
	}
}
