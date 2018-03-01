package neurons;

import neurality.AbstractNeuron;

public class Fin extends AbstractNeuron {
	
	double direction = 0;
	
	public double getSensoryInput() {
		return direction;
	}
	
	public Fin() {
		super();
		super.maxInputs = 1;
		super.mods = new double[2];
	}
	
	public String getName() {
		return "pelvic_fin";
	}
	
	public void eval(int tick) {
		if(super.values.length != 0) {
			direction = Math.max(-1, Math.min(1, super.values[0]));
		}
	}

}
