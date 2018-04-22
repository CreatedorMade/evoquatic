package evoquatic;

import java.awt.*;

public abstract class AbstractVectorObject implements Neural {
	
	public abstract void addInput(Neural n);
	public abstract Neural[] getInputs();
	public abstract double getOutput();
	
	public AbstractVectorObject(AbstractNodeObject n1, AbstractNodeObject n2) {
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public final void applyImpulse(double d, double e) {
		n1.applyImpulse(d/2, e/2);
		n2.applyImpulse(d/2, e/2);
	}
	
	boolean collides = false;
	
	AbstractNodeObject n1;
	AbstractNodeObject n2;
	
	public void code(int time) {}
	
	public abstract Color getColor();
	public abstract double getTargetLength();
}
