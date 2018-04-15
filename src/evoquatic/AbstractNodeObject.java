package evoquatic;

public abstract class AbstractNodeObject implements Neural {
	
	double x = 0;
	double y = 0;
	float vx = 0;
	float vy = 0;
	
	float mass = 0;
	
	double output = 0;
	int inputCount = 0;
	AbstractNodeObject inputs[] = new AbstractNodeObject[0];
	double[] values = new double[0];
	
	public AbstractNodeObject() {
		
	}
	
	public final void tick(int t) {
		
	}
	
	public void applyImpulse(float x, float y) {
		vx += x/mass/60;
		vy += y/mass/60;
	}
	
	public void code(int tick) {
		
	}
	
	public void collideWith(AbstractNodeObject o) {
		
	}
	
	public void collideWith(AbstractVectorObject o) {
		
	}
}
