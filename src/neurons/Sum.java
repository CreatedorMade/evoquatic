package neurons;

import neurality.AbstractNeuron;

public class Sum extends AbstractNeuron {
	
	public String getName() { return "sum"; }
	
	public void eval(int tick) {
		double sum = 0;
		for(int i = 0; i < super.values.length; i++) {
			sum+=super.values[i];
		}
		super.output = sum;
	}
	
}
