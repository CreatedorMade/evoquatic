package neurons;

import neurality.AbstractNeuron;

public class Modulator extends AbstractNeuron {
	
	public Modulator() {
		super();
		super.maxInputs = 2;
	}
	
	public String getName() {
		return "modulator";
	}
	
	public void eval(int tick) {
		if(super.values.length == 2) {
			super.output = super.values[0] % super.values[1];
		} else {
			super.output = 0;
		}
	}
}
