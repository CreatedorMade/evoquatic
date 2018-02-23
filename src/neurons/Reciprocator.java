package neurons;

import neurality.AbstractNeuron;

public class Reciprocator extends AbstractNeuron {
	
	public Reciprocator() {
		super();
		super.maxInputs = 1;
	}
	
	public String getName() {
		return "reciprocator";
	}
	
	public void eval(int tick) {
		if(super.values.length != 0) {
			super.output = 1/super.values[0];
		} else {
			super.output = 0;
		}
	}
}
