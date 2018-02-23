package neurons;

import neurality.AbstractNeuron;

public class DorsalFin extends AbstractNeuron {
	double input = 0;
	
	public DorsalFin() {
		super();
		super.maxInputs = 1;
	}
	
	public String getName() {
		return "dorsal_fin";
	}
	
	public void eval(int tick) {
		if(super.values.length != 0) {
			input = super.values[0];
		}
	}
	
	public double getSensoryData() {
		return input;
	}
}
