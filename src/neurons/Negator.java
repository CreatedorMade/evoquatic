package neurons;

import neurality.*;

public class Negator extends AbstractNeuron {
	public Negator() {
		super();
		super.maxInputs = 1;
	}
	
	public String getName() { return "negator"; }

	public void eval(int tick) {
		if(super.values.length != 0) { super.output = -super.values[0]; }
		else super.output = 0;
	}
	
	
}
