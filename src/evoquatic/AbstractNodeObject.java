package evoquatic;

import java.awt.*;

public abstract class AbstractNodeObject implements Neural {
	
	boolean delete = false;
	
	double x = 0;
	double y = 0;
	float vx = 0;
	float vy = 0;
	
	double mass = getMass();
	
	double output = 0;
	int inputCount = 0;
	AbstractNodeObject inputs[] = new AbstractNodeObject[0];
	double[] values = new double[0];
	
	public AbstractNodeObject() {
		
	}
	
	public final void tick(int t) {
		mass = getMass();
		x += vx;
		y += vy;
		code(t);
	}
	
	public final void applyImpulse(double d, double e) {
		if(mass != 0) {
			vx += d/mass/60;
			vy += e/mass/60;
		}
	}
	
	public void code(int tick) {
		
	}
	
	public void collideWith(AbstractNodeObject o) {
		
	}
	
	public void collideWith(AbstractVectorObject o) {
		
	}
	
	public abstract Color getColor();
	public abstract double getMass();
	public abstract double getRadius();
	public abstract String[] getInfo();
}
