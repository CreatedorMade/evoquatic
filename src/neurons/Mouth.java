package neurons;

import neurality.AbstractNeuron;

public class Mouth extends AbstractNeuron {
	
	double data = 0;
	
	public Mouth() {
		super();
		super.maxInputs = 1;
		super.mods = new double[1];
	}
	
	public String getName() {
		return "mouth";
	}
	
	public void eval(int tick) {
		if(super.values.length != 0) {
			data = super.values[0];
		}
	}
}
