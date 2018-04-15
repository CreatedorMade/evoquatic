package evoquatic;

public class Simulation {
	
	private SimThread thread = new SimThread();
	
	public Simulation() {
		
	}
	
	public void setMode(int m) { thread.setMode(m); }
	
	private class SimThread implements Runnable {
		
		static final int STOPPED = 0;
		static final int REALTIME = 1;
		static final int INCUBATE = 2;
		
		private boolean rt = false;
		private boolean running = false;
		
		public void run() {
			running = true;
			while(running) {
				long target = System.nanoTime() + 16666666L; //haha 666
				
				System.out.print("wep ");
				
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
				if(!running) new Thread(this).start();
			} else if(m == INCUBATE) {
				rt = false;
				if(!running) new Thread(this).start();
			}
		}
	}
}
