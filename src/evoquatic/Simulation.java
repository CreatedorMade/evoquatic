package evoquatic;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;

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

	OpenSimplexNoise nx = new OpenSimplexNoise((int) (Math.random()*1000000));
	OpenSimplexNoise ny = new OpenSimplexNoise((int) (Math.random()*1000000));
	
	int time = 0;
	public int tool = 0;
	int size = 5;
	int genDelay = 600;
	int genTimer = 600;
	int foodDelay = 6;
	int foodTimer = 6;
	int foodSize = 0;
	
	ArrayList<AbstractNodeObject> nodes = new ArrayList<AbstractNodeObject>();
	
	public Simulation() {
	}
	
	private class SimThread implements Runnable {
		
		static final int STOPPED = 0;
		static final int REALTIME = 1;
		static final int INCUBATE = 2;
		
		boolean rt = false;
		boolean running = false;
		
		@SuppressWarnings("unused")
		Simulation sim;
		
		public SimThread(Simulation simulation) {
			sim = simulation;
		}
		
		public void run() {
			running = true;
			while(running) {
				long target = System.nanoTime() + 16666666L; //haha 666
				
				//Creature spawn timing
				genTimer--;
				if(genTimer == 0) {
					genTimer = genDelay;
				}
				
				//Food spawn timing
				foodTimer--;
				if(foodTimer == 0) {
					foodTimer = foodDelay;
					
					double angle = Math.random() * Math.PI * 2;
					double distance = Math.random()*size/2;
					
					//nodes.add(new GreenFood((Math.random()*0.15+0.05)*foodSize, Math.cos(angle)*distance, Math.sin(angle)*distance));
					nodes.add(new GreenFood(Math.random()*0.05+0.05, 0, 0));
				}
				
				//Remove null nodes
				for(int i = 0; i < nodes.size(); i++) {
					if(nodes.get(i) == null) nodes.remove(i);
				}
				
				//Iteration over each node
				for(AbstractNodeObject n : nodes) {
					n.tick(time);
					n.applyImpulse((float) nx.eval((float) (time)/1000, n.x/10, n.y/10)/10, (float) ny.eval((float) (time)/1000, n.x/10, n.y/10)/10);
					if(Point2D.distance(n.x, n.y, 0, 0) > size/2) {
						double angle = Math.atan2(n.y, n.x);
						n.applyImpulse((float) (n.x-(Math.cos(angle)*sim.size*50))/1000f, (float) (n.y-(Math.sin(angle)*sim.size*50))/1000f);
					} else {
						n.vx = (float) (n.vx*0.95);
						n.vy = (float) (n.vy*0.95);
					}
					
					//Collision detection
					for(AbstractNodeObject n2 : nodes) {
						if(n != n2) {
							double n1r = n.getRadius();
							double n2r = n2.getRadius();
							
							if(Math.abs(n.x-n2.x) < n1r+n2r && Math.abs(n.y-n2.y) < n1r+n2r) {
								double dist = Point2D.distance(n.x, n.y, n2.x, n2.y);
								if(dist < n1r+n2r) {
									double theta = (Math.atan2(n2.y - n.y, n2.x - n.x));
									dist += dist*0.1;
									n.applyImpulse(-(float) (Math.cos(theta)*(n1r+n2r-dist)), -(float) (Math.sin(theta)*(n1r+n2r-dist)));
									n.collideWith(n2);
								}
							}
						}
					}
				}
				
				if(rt && running) sync(target);
				
				time++;
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
	
	public boolean click(MouseEvent e) {
		EPanel panel = HudPart.panel;
		
		if(tool == 2) return true;
		
		for(AbstractNodeObject n : nodes) {
			if(Point2D.distance(((n.x*100*panel.zoom)-(panel.camX*panel.zoom)+(panel.getWidth()/2)), ((n.y*100*panel.zoom)-(panel.camY*panel.zoom)+(panel.getHeight()/2)), e.getX(), e.getY()) < (n.getRadius()*panel.zoom*100)) {
				HudPart.panel.selection = n;
				return false;
			}
		}
		HudPart.panel.selection = null;
		return true;
	}
}
