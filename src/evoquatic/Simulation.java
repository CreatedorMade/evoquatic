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
	ArrayList<AbstractVectorObject> vectors = new ArrayList<AbstractVectorObject>();
	
	public Simulation() {
		for(int i = 0; i < 10; i++) {
			GreenFood a = new GreenFood(0.01, 0.2+i, 0);
			GreenFood b = new GreenFood(0.01, 0+i, -1);
			GreenFood c = new GreenFood(0.01, -0.2+i, 0);
			GreenFood d = new GreenFood(0.01, 0+i, 1);
			nodes.add(a);
			nodes.add(b);
			nodes.add(c);
			nodes.add(d);
			vectors.add(new Rod(a, b));
			vectors.add(new Rod(b, c));
			vectors.add(new Rod(c, d));
			vectors.add(new Rod(d, a));
			vectors.add(new Rod(a, c));
		}
	}
	
	private class SimThread implements Runnable {
		
		static final int STOPPED = 0;
		static final int REALTIME = 1;
		static final int INCUBATE = 2;
		
		boolean rt = false;
		boolean running = false;
		
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
					
					double angle = Math.random() * Math.PI * 2;
					double distance = Math.random()*size/2;
					
					nodes.add(new Egg(Math.cos(angle)*distance, Math.sin(angle)*distance));
				}
				
				//Food spawn timing
				foodTimer--;
				if(foodTimer == 0) {
					foodTimer = foodDelay;
					
					double angle = Math.random() * Math.PI * 2;
					double distance = Math.random()*size/2;
					
					nodes.add(new GreenFood((Math.random()*0.15+0.05)*foodSize, Math.cos(angle)*distance, Math.sin(angle)*distance));
				}
				
				//Remove null nodes
				for(int i = 0; i < nodes.size(); i++) {
					if(nodes.get(i) == null) nodes.remove(i);
					else if(nodes.get(i).delete) {
						nodes.remove(i);
					}
				}
				
				//Iteration over each vector
				for(AbstractVectorObject v : vectors) {
					if(v.n1 == null || v.n2 == null) {
						v = null;
						vectors.remove(v);
					} else {
						v.code(time);
						
						double theta = (Math.atan2(v.n2.y - v.n1.y, v.n2.x - v.n1.x));
						double force = Point2D.distance(v.n1.x, v.n1.y, v.n2.x, v.n2.y)-v.getTargetLength();
							
						v.n1.applyImpulse((float) (Math.cos(theta)*force), (float) (Math.sin(theta)*force));
						v.n2.applyImpulse((float) -(Math.cos(theta)*force), (float) -(Math.sin(theta)*force));
						
						//Calculate the drag based on direction of movement and theta
						double semitheta = theta % Math.PI;

						double mt = (Math.atan2((v.n1.vy+v.n2.vy)/2, (v.n1.vx+v.n2.vx)/2)) - semitheta;
						double md = Point2D.distance((v.n1.vx+v.n2.vx)/2, (v.n1.vy+v.n2.vy)/2, 0, 0);
						
						double slippage = Math.sin(mt)*md;
						double length = Point2D.distance(v.n1.x, v.n1.y, v.n2.x, v.n2.y);
						
						v.applyImpulse(-Math.cos(semitheta+Math.PI/2)*slippage*length, -Math.sin(semitheta+Math.PI/2)*slippage*length);
					}
				}
				
				//Iteration over each node
				for(AbstractNodeObject n : nodes) {
					n.tick(time);
					n.applyImpulse((float) nx.eval((float) (time)/1000, n.x/10, n.y/10)/10, (float) ny.eval((float) (time)/1000, n.x/10, n.y/10)/10);
					if(Point2D.distance(n.x, n.y, 0, 0) > size/2) {
						double angle = Math.atan2(n.y, n.x);
						n.applyImpulse((float) (n.x-(Math.cos(angle)*sim.size*50))/1000f, (float) (n.y-(Math.sin(angle)*sim.size*50))/1000f);
					} else {
						n.vx = (float) (n.vx*0.99);
						n.vy = (float) (n.vy*0.99);
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
									n.applyImpulse(-(float) (Math.cos(theta)*(n1r+n2r-dist))*10, -(float) (Math.sin(theta)*(n1r+n2r-dist))*10);
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
