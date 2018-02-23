package neurons;

import neurality.AbstractNeuron;

public class Multiplicator extends AbstractNeuron {
	
	public Multiplicator() {
		super();
	}
	
	public String getName() {
		return "multiplicator";
	}
	
	public void eval(int tick) {
		if(super.values.length != 0) {
			double prod = super.values[0];
			for(int i = 1; i < super.values.length; i++) {
				prod *= super.values[i];
			}
			super.output = prod;
		} else {
			super.output = 0;
		}
	}
}
